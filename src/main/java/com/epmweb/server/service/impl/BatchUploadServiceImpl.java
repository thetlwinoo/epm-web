package com.epmweb.server.service.impl;

import com.epmweb.server.domain.*;
import com.epmweb.server.repository.*;
import com.epmweb.server.service.*;
import com.epmweb.server.service.dto.*;
import com.epmweb.server.service.mapper.SuppliersMapper;
import com.epmweb.server.service.mapper.UploadTransactionsMapper;
import com.epmweb.server.service.util.CommonUtil;
import io.github.jhipster.service.filter.StringFilter;
import liquibase.util.file.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BatchUploadServiceImpl implements BatchUploadService {

    private final Logger log = LoggerFactory.getLogger(BatchUploadServiceImpl.class);
    private final UploadActionTypesRepository uploadActionTypesRepository;
    private final UploadTransactionsRepository uploadTransactionsRepository;
    private final ProductCategoryExtendRepository productCategoryExtendRepository;
    private final UploadTransactionsMapper uploadTransactionsMapper;
    private final BarcodeTypesQueryService barcodeTypesQueryService;
    private final ProductAttributeQueryService productAttributeQueryService;
    private final ProductOptionQueryService productOptionQueryService;
    private final MaterialsQueryService materialsQueryService;
    private final ProductBrandQueryService productBrandQueryService;
    private final WarrantyTypesQueryService warrantyTypesQueryService;
    private final BatchUploadRepository batchUploadRepository;
    private final ProductsRepository productsRepository;
    private final ProductBrandRepository productBrandRepository;
    private final BarcodeTypesRepository barcodeTypesRepository;
    private final MaterialsRepository materialsRepository;
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductOptionRepository productOptionRepository;
    private final SuppliersExtendService suppliersExtendService;
    private final SuppliersMapper suppliersMapper;

    public BatchUploadServiceImpl(UploadActionTypesRepository uploadActionTypesRepository, UploadTransactionsRepository uploadTransactionsRepository, ProductCategoryExtendRepository productCategoryExtendRepository, UploadTransactionsMapper uploadTransactionsMapper, BarcodeTypesQueryService barcodeTypesQueryService, ProductAttributeQueryService productAttributeQueryService, ProductOptionQueryService productOptionQueryService, MaterialsQueryService materialsQueryService, ProductBrandQueryService productBrandQueryService, WarrantyTypesQueryService warrantyTypesQueryService, BatchUploadRepository batchUploadRepository, ProductsRepository productsRepository, ProductBrandRepository productBrandRepository, BarcodeTypesRepository barcodeTypesRepository, MaterialsRepository materialsRepository, ProductAttributeRepository productAttributeRepository, ProductOptionRepository productOptionRepository, SuppliersExtendService suppliersExtendService, SuppliersMapper suppliersMapper) {
        this.uploadActionTypesRepository = uploadActionTypesRepository;
        this.uploadTransactionsRepository = uploadTransactionsRepository;
        this.productCategoryExtendRepository = productCategoryExtendRepository;
        this.uploadTransactionsMapper = uploadTransactionsMapper;
        this.barcodeTypesQueryService = barcodeTypesQueryService;
        this.productAttributeQueryService = productAttributeQueryService;
        this.productOptionQueryService = productOptionQueryService;
        this.materialsQueryService = materialsQueryService;
        this.productBrandQueryService = productBrandQueryService;
        this.warrantyTypesQueryService = warrantyTypesQueryService;
        this.batchUploadRepository = batchUploadRepository;
        this.productsRepository = productsRepository;
        this.productBrandRepository = productBrandRepository;
        this.barcodeTypesRepository = barcodeTypesRepository;
        this.materialsRepository = materialsRepository;
        this.productAttributeRepository = productAttributeRepository;
        this.productOptionRepository = productOptionRepository;
        this.suppliersExtendService = suppliersExtendService;
        this.suppliersMapper = suppliersMapper;
    }

    @Override
    @Transactional
    public UploadTransactionsDTO readDataFromExcel(MultipartFile file, String serverUrl, Principal principal) {
        try {
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();

            Optional<SuppliersDTO> suppliersDTOOptional = suppliersExtendService.getSupplierByPrincipal(principal);
            Suppliers suppliers = new Suppliers();
            if (suppliersDTOOptional.isPresent()) {
                suppliers = suppliersMapper.toEntity(suppliersDTOOptional.get());
            }

            Integer currentColumnIndex = 0;
            List<Integer> failedIndexList = new ArrayList<>();
            UploadTransactions uploadTransactions = new UploadTransactions();
            UploadActionTypes uploadActionTypes = uploadActionTypesRepository.getOne(Long.parseLong("2151"));
            uploadTransactions.setActionType(uploadActionTypes);
//            uploadTransactions.setTemplateFile(file.getBytes());
//            uploadTransactions.setTemplateFileContentType(file.getContentType());
            uploadTransactions.setFileName(file.getOriginalFilename());
            uploadTransactions.setGeneratedCode(file.getOriginalFilename());
            uploadTransactions.setStatus(0);
            uploadTransactions.setSupplier(suppliers);

            CellStyle style = workbook.createCellStyle();
            style.setFillBackgroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            style.setFillPattern(CellStyle.BIG_SPOTS);

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Iterator<Cell> cellIterator = row.cellIterator();
                if (row.getRowNum() < 3) continue;

                StockItemTemp stockItemTemp = new StockItemTemp();

                if (isBlank(row.getCell(1).getStringCellValue().trim())) {
                    continue;
                }
                try {
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        String ColumnName = cell.getSheet().getRow(1).getCell(cell.getColumnIndex()).getStringCellValue().trim();

                        currentColumnIndex = cell.getColumnIndex();
                        if (ColumnName.equalsIgnoreCase("Vendor Code") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setVendorCode(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Vendor SKU") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setVendorSKU(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Barcode") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setBarcode(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Barcode Type") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            BarcodeTypesDTO barcodeTypesDTO = getBarCodesType(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                            stockItemTemp.setBarcodeTypeId(barcodeTypesDTO.getId());
                            stockItemTemp.setBarcodeTypeName(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Item Name") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setStockItemName(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Brand Name") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            ProductBrandDTO productBrandDTO = getProductBrands(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                            stockItemTemp.setProductBrandId(productBrandDTO.getId());
                            stockItemTemp.setProductBrandName(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Product Sub Category") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            ProductCategory productCategory = productCategoryExtendRepository.findProductCategoryByNameContaining(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                            stockItemTemp.setProductCategoryId(productCategory.getId());
                            stockItemTemp.setProductCategoryName(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Product Attribute") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            ProductAttributeDTO productAttributeDTO = getProductAttribute(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                            stockItemTemp.setProductAttributeId(productAttributeDTO.getId());
                            stockItemTemp.setProductAttributeValue(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Product Option") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            ProductOptionDTO productOptionDTO = getProductOption(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                            stockItemTemp.setProductOptionId(productOptionDTO.getId());
                            stockItemTemp.setProductOptionValue(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Product Type") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setProductType(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Short Descriptions") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
//                            Integer shortDescriptionLength = row.getCell(cell.getColumnIndex()).getStringCellValue().trim().length();
//                            String shortDescription = shortDescriptionLength > 255 ? row.getCell(cell.getColumnIndex()).getStringCellValue().trim().substring(0, Math.min(shortDescriptionLength, 255)) : row.getCell(cell.getColumnIndex()).getStringCellValue().trim();
                            stockItemTemp.setShortDescription(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Model Name") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setModelName(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Model Number") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setModelNumber(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Number of Pieces") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            stockItemTemp.setNoOfPieces(new Double(row.getCell(cell.getColumnIndex()).getNumericCellValue()).intValue());
                        } else if (ColumnName.equalsIgnoreCase("Number of Item") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            stockItemTemp.setNoOfItems(new Double(row.getCell(cell.getColumnIndex()).getNumericCellValue()).intValue());
                        } else if (ColumnName.equalsIgnoreCase("Product Material") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            MaterialsDTO materialsDTO = getMaterials(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                            stockItemTemp.setMaterialId(materialsDTO.getId());
                            stockItemTemp.setMaterialName(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Manufacture") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setManufacture(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("VideoUrl") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setVideoUrl(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Care Instruction") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setCareInstructions(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Special Features") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setSpecialFeactures(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Quantity On Hand") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            stockItemTemp.setQuantityOnHand(new Double(row.getCell(cell.getColumnIndex()).getNumericCellValue()).intValue());
                        } else if (ColumnName.equalsIgnoreCase("Highlights") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
//                            Integer highlightLength = row.getCell(cell.getColumnIndex()).getStringCellValue().trim().length();
//                            String highlight = highlightLength > 255 ? row.getCell(cell.getColumnIndex()).getStringCellValue().trim().substring(0, Math.min(highlightLength, 255)) : row.getCell(cell.getColumnIndex()).getStringCellValue().trim();
                            stockItemTemp.setHighlights(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Search Keywords") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
//                            Integer searchKeywordsLength = row.getCell(cell.getColumnIndex()).getStringCellValue().trim().length();
//                            String searchKeywords = searchKeywordsLength > 255 ? row.getCell(cell.getColumnIndex()).getStringCellValue().trim().substring(0, Math.min(searchKeywordsLength, 255)) : row.getCell(cell.getColumnIndex()).getStringCellValue().trim();
                            stockItemTemp.setSearchDetails(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Product Compliance Certificate") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setProductComplianceCertificate(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Genuine And Legal") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                            stockItemTemp.setGenuineAndLegal(Boolean.parseBoolean(row.getCell(cell.getColumnIndex()).getStringCellValue().trim()));
                        } else if (ColumnName.equalsIgnoreCase("Country of Origin") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setCountryOfOrigin(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Instructions For Usage And Side Effects") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setUsageAndSideEffects(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Safety Warnning") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setSafetyWarnning(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Warrenty Period") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setWarrantyPeriod(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Warrenty Type") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            WarrantyTypesDTO warrantyTypesDTO = getWarrantyTypes(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                            stockItemTemp.setWarrantyTypeId(warrantyTypesDTO.getId());
                            stockItemTemp.setWarrantyTypeName(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Dangerous Goods Regulations") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setDangerousGoods(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Selling Price") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            stockItemTemp.setRemommendedRetailPrice(BigDecimal.valueOf(row.getCell(cell.getColumnIndex()).getNumericCellValue()));
                        } else if (ColumnName.equalsIgnoreCase("Retail Price") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            stockItemTemp.setUnitPrice(BigDecimal.valueOf(row.getCell(cell.getColumnIndex()).getNumericCellValue()));
                        } else if (ColumnName.equalsIgnoreCase("Item Length") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            stockItemTemp.setItemLength(new Double(row.getCell(cell.getColumnIndex()).getNumericCellValue()).intValue());
                        } else if (ColumnName.equalsIgnoreCase("Item Length Unit") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setItemLengthUnitMeasureCode(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Item Width") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            stockItemTemp.setItemWidth(new Double(row.getCell(cell.getColumnIndex()).getNumericCellValue()).intValue());
                        } else if (ColumnName.equalsIgnoreCase("Item Width Unit") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setItemWidthUnitMeasureCode(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Item Height") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            stockItemTemp.setItemHeight(new Double(row.getCell(cell.getColumnIndex()).getNumericCellValue()).intValue());
                        } else if (ColumnName.equalsIgnoreCase("Item Height Unit") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setItemHeightUnitMeasureCode(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Item Weight") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            stockItemTemp.setItemWeight(BigDecimal.valueOf(row.getCell(cell.getColumnIndex()).getNumericCellValue()));
                        } else if (ColumnName.equalsIgnoreCase("Item Weight Unit") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setItemWeightUnitMeasureCode(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Item Package Length") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            stockItemTemp.setItemPackageLength(new Double(row.getCell(cell.getColumnIndex()).getNumericCellValue()).intValue());
                        } else if (ColumnName.equalsIgnoreCase("Package Length Unit") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setItemPackageLengthUnitMeasureCode(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Item Package Width") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            stockItemTemp.setItemPackageWidth(new Double(row.getCell(cell.getColumnIndex()).getNumericCellValue()).intValue());
                        } else if (ColumnName.equalsIgnoreCase("Package Width Unit") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setItemPackageWidthUnitMeasureCode(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Item Package Height") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            stockItemTemp.setItemPackageHeight(new Double(row.getCell(cell.getColumnIndex()).getNumericCellValue()).intValue());
                        } else if (ColumnName.equalsIgnoreCase("Package Height Unit") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setItemPackageHeightUnitMeasureCode(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        } else if (ColumnName.equalsIgnoreCase("Item Package Weight") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            stockItemTemp.setItemPackageWeight(BigDecimal.valueOf(row.getCell(cell.getColumnIndex()).getNumericCellValue()));
                        } else if (ColumnName.equalsIgnoreCase("Item Package Weight Unit") && row.getCell(cell.getColumnIndex()).getCellType() == Cell.CELL_TYPE_STRING) {
                            stockItemTemp.setItemPackageWeightUnitMeasureCode(row.getCell(cell.getColumnIndex()).getStringCellValue().trim());
                        }
                    }
                } catch (Exception ex) {
                    failedIndexList.add(row.getRowNum());
                    row.getCell(currentColumnIndex).setCellStyle(style);
                    ex.printStackTrace();
                    continue;
                }

                stockItemTemp.setUploadTransaction(uploadTransactions);
                uploadTransactions.getStockItemTempLists().add(stockItemTemp);
            }

//            uploadTransactions.setFailedFile(getFailedRecordsFile(file, failedIndexList));
//            uploadTransactions.setFailedFileContentType(file.getContentType());
//            shadeAlt(sheet);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                workbook.write(bos);
            } finally {
                bos.close();
            }

            byte[] bytes = bos.toByteArray();
            SupplierImportedDocument supplierImportedDocument = new SupplierImportedDocument();
            supplierImportedDocument.setImportedTemplate(bytes);
            supplierImportedDocument.setImportedTemplateContentType(file.getContentType());
            supplierImportedDocument.setUploadTransaction(uploadTransactions);
            uploadTransactions.getImportDocumentLists().add(supplierImportedDocument);

            UploadTransactions savedUploadTransactions = uploadTransactionsRepository.save(uploadTransactions);
            uploadTransactions.setTemplateUrl(serverUrl + "/supplier-imported-document-extend/download/" + savedUploadTransactions.getId() + "/template");
            savedUploadTransactions = uploadTransactionsRepository.save(savedUploadTransactions);
//
//            return savedUploadTransactions;
            return uploadTransactionsMapper.toDto(savedUploadTransactions);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    private BarcodeTypesDTO getBarCodesType(String filter) {
        try {
            BarcodeTypesCriteria barcodeTypesCriteria = new BarcodeTypesCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setContains(filter.trim());
            barcodeTypesCriteria.setName(stringFilter);
            return barcodeTypesQueryService.findByCriteria(barcodeTypesCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public void importToSystem(Long transactionId, Principal principal) {
        try {
            List<Object[]> productsList = batchUploadRepository.getProductsFromTemp(transactionId);
            Optional<SuppliersDTO> suppliersDTOOptional = suppliersExtendService.getSupplierByPrincipal(principal);
            Suppliers suppliers = new Suppliers();
            if (suppliersDTOOptional.isPresent()) {
                suppliers = suppliersMapper.toEntity(suppliersDTOOptional.get());
            }

            for (Object[] productObj : productsList) {
                Products products = new Products();
                products.setName(productObj[0].toString());
                ProductBrand productBrand = productBrandRepository.getOne(Long.parseLong(productObj[2].toString()));
                products.setProductBrand(productBrand);
                products.setHandle(CommonUtil.handleize(productObj[0].toString()));
                ProductCategory productCategory = productCategoryExtendRepository.getOne(Long.parseLong(productObj[1].toString()));
                products.setProductCategory(productCategory);
                products.setSupplier(suppliers);
                products.setLastEditedBy(principal.getName());
                products.setLastEditedWhen(Instant.now());
                products.setActiveInd(false);
                List<StockItemTemp> stockItemTempList = batchUploadRepository.getStockItemTemp(productObj[0].toString());

                for (StockItemTemp stockItemTemp : stockItemTempList) {
                    StockItems stockItems = new StockItems();
                    stockItems.setName(stockItemTemp.getStockItemName());
                    stockItems.setVendorCode(stockItemTemp.getVendorCode());
                    stockItems.setVendorSKU(stockItemTemp.getVendorSKU());
                    stockItems.setBarcode(stockItemTemp.getBarcode());
                    stockItems.setUnitPrice(stockItemTemp.getUnitPrice());
                    stockItems.setRecommendedRetailPrice(stockItemTemp.getRemommendedRetailPrice());
                    stockItems.setQuantityOnHand(stockItemTemp.getQuantityOnHand());
                    stockItems.setItemLength(stockItemTemp.getItemLength());
                    stockItems.setItemWidth(stockItemTemp.getItemWidth());
                    stockItems.setItemHeight(stockItemTemp.getItemHeight());
                    stockItems.setItemWeight(stockItemTemp.getItemWeight());
                    stockItems.setItemPackageLength(stockItemTemp.getItemPackageLength());
                    stockItems.setItemPackageWidth(stockItemTemp.getItemPackageWidth());
                    stockItems.setItemPackageHeight(stockItemTemp.getItemPackageHeight());
                    stockItems.setItemPackageWeight(stockItemTemp.getItemPackageWeight());
                    stockItems.setNoOfPieces(stockItemTemp.getNoOfPieces());
                    stockItems.setNoOfItems(stockItemTemp.getNoOfItems());
                    stockItems.setManufacture(stockItemTemp.getManufacture());
                    stockItems.setLastEditedBy(principal.getName());
                    stockItems.setLastEditedWhen(Instant.now());
                    stockItems.setActiveInd(false);

                    BarcodeTypes barcodeTypes = barcodeTypesRepository.getOne(stockItemTemp.getBarcodeTypeId());
                    stockItems.setBarcodeType(barcodeTypes);

                    Materials materials = materialsRepository.getOne(stockItemTemp.getMaterialId());
                    stockItems.setMaterial(materials);

                    ProductAttribute productAttribute = productAttributeRepository.getOne(stockItemTemp.getProductAttributeId());
                    stockItems.setProductAttribute(productAttribute);

                    ProductOption productOption = productOptionRepository.getOne(stockItemTemp.getProductOptionId());
                    stockItems.setProductOption(productOption);

                    stockItems.setProduct(products);
                    products.getStockItemLists().add(stockItems);
                }

                products = productsRepository.save(products);
                String _productnumber = products.getName().replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
                _productnumber = _productnumber.length() > 8 ? _productnumber.substring(0, 8) : _productnumber;
                _productnumber = _productnumber + "-" + products.getId();
                products.setProductNumber(_productnumber);
                productsRepository.save(products);
            }

            batchUploadRepository.clearStockItemTemps(transactionId);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException(ex.getMessage());
        }

    }

    private ProductAttributeDTO getProductAttribute(String filter) {
        try {
            ProductAttributeCriteria productAttributeCriteria = new ProductAttributeCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setContains(filter.trim());
            productAttributeCriteria.setValue(stringFilter);
            return productAttributeQueryService.findByCriteria(productAttributeCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    private ProductOptionDTO getProductOption(String filter) {
        try {
            ProductOptionCriteria productOptionCriteria = new ProductOptionCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setContains(filter.trim());
            productOptionCriteria.setValue(stringFilter);
            return productOptionQueryService.findByCriteria(productOptionCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    private MaterialsDTO getMaterials(String filter) {
        try {
            MaterialsCriteria materialsCriteria = new MaterialsCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setContains(filter.trim());
            materialsCriteria.setName(stringFilter);
            return materialsQueryService.findByCriteria(materialsCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    private ProductBrandDTO getProductBrands(String filter) {
        try {
            ProductBrandCriteria productBrandCriteria = new ProductBrandCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setContains(filter.trim());
            productBrandCriteria.setName(stringFilter);
            return productBrandQueryService.findByCriteria(productBrandCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    private WarrantyTypesDTO getWarrantyTypes(String filter) {
        try {
            WarrantyTypesCriteria warrantyTypesCriteria = new WarrantyTypesCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setContains(filter.trim());
            warrantyTypesCriteria.setName(stringFilter);
            return warrantyTypesQueryService.findByCriteria(warrantyTypesCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

//    private UnitMeasureDTO getUnitMeasures(String filter) {
//        UnitMeasure warrantyTypesCriteria = new WarrantyTypesCriteria();
//        StringFilter stringFilter = new StringFilter();
//        stringFilter.setContains(filter);
//        warrantyTypesCriteria.setWarrantyTypeName(stringFilter);
//        return warrantyTypesQueryService.findByCriteria(warrantyTypesCriteria).get(0);
//    }

    static void shadeAlt(Sheet sheet) {
        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        // Condition 1: Formula Is   =A2=A1   (White Font)
//        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule("MOD(ROW(),2)");
//        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule("AND(ISNUMBER($AH4), ISBLANK($A4), ISBLANK($B4))");
        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule("$AZ4 = FALSE");
        PatternFormatting fill1 = rule1.createPatternFormatting();
        fill1.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.index);
        fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

//        ConditionalFormattingRule[] cfRules = new ConditionalFormattingRule[] { ruleGreen, ruleRed, ruleOrange };

        CellRangeAddress[] regions = {
            CellRangeAddress.valueOf("A4:AZ207")
        };

        sheetCF.addConditionalFormatting(regions, rule1);
    }

    private byte[] getFailedRecordsFile(MultipartFile file, List<Integer> failedIndexList) throws IOException {
        Workbook workbookNew = getWorkBook(file);
        Sheet sheet = workbookNew.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() < 3) continue;

            if (!failedIndexList.contains(row.getRowNum())) {
                rowIterator.remove();
            }
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbookNew.write(bos);
        } finally {
            bos.close();
        }
        byte[] bytes = bos.toByteArray();

        return bytes;
    }

    public void removeRowByIndex(Sheet sheet, Integer rowIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            if (rowIndex < sheet.getLastRowNum()) {
                sheet.shiftRows(rowIndex + 1, sheet.getLastRowNum(), -1);
            }
        } else {
            try {
                if (row.getRowNum() < sheet.getLastRowNum()) {
                    sheet.shiftRows(row.getRowNum() + 1, sheet.getLastRowNum(), -1);
                } else {
                    sheet.removeRow(row);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isBlank(String param) {
        return param.isEmpty() || param.trim().equals("");
    }

    private Workbook getWorkBook(MultipartFile file) {

        Workbook workbook = null;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        try {
            if (extension.equalsIgnoreCase("xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else if (extension.equalsIgnoreCase("xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return workbook;
    }
}
