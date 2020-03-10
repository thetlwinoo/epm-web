package com.epmweb.server.web.rest;

import com.epmweb.server.service.BatchUploadService;
import com.epmweb.server.service.ProductsService;
import com.epmweb.server.service.dto.ProductsDTO;
import com.epmweb.server.service.dto.UploadTransactionsDTO;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import liquibase.util.file.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * BatchUploadResource controller
 */
@RestController
@RequestMapping("/api")
public class BatchUploadResource {

    private final Logger log = LoggerFactory.getLogger(BatchUploadResource.class);
    private static final String ENTITY_NAME = "batchUpload";
    private final ProductsService productsService;
    private final BatchUploadService batchUploadService;


    public BatchUploadResource(ProductsService productsService, BatchUploadService batchUploadService) {
        this.productsService = productsService;
        this.batchUploadService = batchUploadService;
    }

    @CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
    @PostMapping("/excelupload")
//    public String uploadBatchFile(@RequestBody MultipartFile file, RedirectAttributes redirectAttributes) throws URISyntaxException {
    public ResponseEntity uploadExcelFile(@RequestBody MultipartFile file, Principal principal, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Products : {}", file);
        if (file == null) {
            throw new BadRequestAlertException("No File to upload", ENTITY_NAME, "No File Exist");
        }

        UploadTransactionsDTO uploadTransactionsDTO = new UploadTransactionsDTO();

        try{
            String _serverUrl = request.getRequestURL().toString().replace("/excelupload", "");
            uploadTransactionsDTO = batchUploadService.readDataFromExcel(file, _serverUrl, principal);
        }
        catch(Exception ex){
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "Error");
        }


//        redirectAttributes.addFlashAttribute("successmessage", "File Upload Sucessfully");

        return ResponseEntity.ok()
            .body(uploadTransactionsDTO);
    }

    @CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
    @PostMapping("/batchupload")
//    public String uploadBatchFile(@RequestBody MultipartFile file, RedirectAttributes redirectAttributes) throws URISyntaxException {
    public ResponseEntity<RedirectAttributes> uploadBatchFile(@RequestBody MultipartFile file, RedirectAttributes redirectAttributes) throws URISyntaxException {
        log.debug("REST request to save Products : {}", file);
        if (file == null) {
            throw new BadRequestAlertException("No File to upload", ENTITY_NAME, "No File Exist");
        }
        boolean isFlag = saveDataFromUploadFile(file);

        if (isFlag) {
            redirectAttributes.addFlashAttribute("successmessage", "File Upload Sucessfully");
        } else {
            redirectAttributes.addFlashAttribute("errormessage", "File Upload not done, Please try again!");
        }

        return ResponseEntity.ok()
            .body(redirectAttributes);
//        return "redirect:/";
    }

    @PostMapping("/importtosystem")
    public ResponseEntity importToSystem(@RequestParam("id") Long id, Principal principal) throws URISyntaxException {

//        List<StockItemsDTO> result = null;
//        return ResponseEntity.created(new URI("/api/upload-transactions/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
        batchUploadService.importToSystem(id, principal);
        return ResponseEntity.ok().body(null);
    }

    private boolean saveDataFromUploadFile(MultipartFile file) {
        boolean isFlag = false;

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (extension.equalsIgnoreCase("json")) {
            isFlag = readDataFromJson(file);
        } else if (extension.equalsIgnoreCase("csv")) {
            isFlag = readDataFromCsv(file);
        } else if (extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
            isFlag = readDataFromExcel(file);
        }

        return isFlag;
    }

    private boolean readDataFromExcel(MultipartFile file) {
        Workbook workbook = getWorkBook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();
        rows.next();

        while (rows.hasNext()) {
            Row row = rows.next();
            ProductsDTO product = new ProductsDTO();


            if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
                product.setName(row.getCell(0).getStringCellValue());
            }
            if (row.getCell(1).getCellType() == Cell.CELL_TYPE_STRING) {
                product.setName(row.getCell(0).getStringCellValue());
            }
            if (row.getCell(2).getCellType() == Cell.CELL_TYPE_STRING) {
                product.setName(row.getCell(0).getStringCellValue());
            }

//            this.productsService.save(product);
        }

        return true;
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

    private boolean readDataFromCsv(MultipartFile file) {

        try {
            InputStreamReader reader = new InputStreamReader(file.getInputStream());
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            List<String[]> rows = csvReader.readAll();
            Random random = new Random();

            for (String[] row : rows) {
                ProductsDTO product = new ProductsDTO();
                product.setName(row[0]);
                product.setProductNumber(row[1]);
//                product.setMakeFlag(Boolean.getBoolean(row[2]));
//                product.setFinishedGoodsFlag(Boolean.getBoolean(row[3]));
//                product.setColor(row[4]);
//                product.setSafetyStockLevel(Integer.parseInt(row[5]));
//                product.setReorderPoint(Integer.parseInt(row[6]));
//                product.setStandardCost(Float.parseFloat(row[7]));
//                product.setUnitPrice(Float.parseFloat((row[8])));
//                product.setRecommendedRetailPrice(Float.parseFloat((row[9])));
//                product.setBrand(row[10]);
//                product.setSpecifySize(row[11]);
//                product.setWeight(Float.parseFloat((row[12])));
//                product.setDaysToManufacture(Integer.parseInt(row[13]));
//                product.setProductLine(row[14]);
//                product.setClassType(row[15]);
//                product.setStyle(row[16]);
//                product.setCustomFields(row[17]);
//                product.setTags(row[18]);
                product.setSearchDetails(row[19]);
//                product.setProductSubCategoryId(Long.valueOf(random.nextInt(1187-1151) + 1151));
//                product.setProductModelId(Long.valueOf(random.nextInt(1250-1201) + 1201));
//                product.setSellCount(Integer.parseInt(row[25]));
//                product.setSellStartDate(LocalDate.now());
                this.productsService.save(product);
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    private boolean readDataFromJson(MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            List<ProductsDTO> products = Arrays.asList(mapper.readValue(inputStream, ProductsDTO[].class));

            if (products != null && products.size() > 0) {
                for (ProductsDTO product : products) {
                    this.productsService.save(product);
                }
            }
        } catch (Exception ex) {
            return false;
        }

        return true;
    }
}
