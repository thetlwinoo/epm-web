package com.epmweb.server.web.rest;

import com.epmweb.server.EpmwebApp;
import com.epmweb.server.config.TestSecurityConfiguration;
import com.epmweb.server.domain.StockItemTemp;
import com.epmweb.server.domain.UploadTransactions;
import com.epmweb.server.repository.StockItemTempRepository;
import com.epmweb.server.service.StockItemTempService;
import com.epmweb.server.service.dto.StockItemTempDTO;
import com.epmweb.server.service.mapper.StockItemTempMapper;
import com.epmweb.server.web.rest.errors.ExceptionTranslator;
import com.epmweb.server.service.dto.StockItemTempCriteria;
import com.epmweb.server.service.StockItemTempQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.epmweb.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link StockItemTempResource} REST controller.
 */
@SpringBootTest(classes = {EpmwebApp.class, TestSecurityConfiguration.class})
public class StockItemTempResourceIT {

    private static final String DEFAULT_STOCK_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_ITEM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_SKU = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_SKU = "BBBBBBBBBB";

    private static final String DEFAULT_BARCODE = "AAAAAAAAAA";
    private static final String UPDATED_BARCODE = "BBBBBBBBBB";

    private static final Long DEFAULT_BARCODE_TYPE_ID = 1L;
    private static final Long UPDATED_BARCODE_TYPE_ID = 2L;
    private static final Long SMALLER_BARCODE_TYPE_ID = 1L - 1L;

    private static final String DEFAULT_BARCODE_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BARCODE_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_PRODUCT_CATEGORY_ID = 1L;
    private static final Long UPDATED_PRODUCT_CATEGORY_ID = 2L;
    private static final Long SMALLER_PRODUCT_CATEGORY_ID = 1L - 1L;

    private static final String DEFAULT_PRODUCT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_CATEGORY_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PRODUCT_ATTRIBUTE_SET_ID = 1L;
    private static final Long UPDATED_PRODUCT_ATTRIBUTE_SET_ID = 2L;
    private static final Long SMALLER_PRODUCT_ATTRIBUTE_SET_ID = 1L - 1L;

    private static final Long DEFAULT_PRODUCT_ATTRIBUTE_ID = 1L;
    private static final Long UPDATED_PRODUCT_ATTRIBUTE_ID = 2L;
    private static final Long SMALLER_PRODUCT_ATTRIBUTE_ID = 1L - 1L;

    private static final String DEFAULT_PRODUCT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final Long DEFAULT_PRODUCT_OPTION_SET_ID = 1L;
    private static final Long UPDATED_PRODUCT_OPTION_SET_ID = 2L;
    private static final Long SMALLER_PRODUCT_OPTION_SET_ID = 1L - 1L;

    private static final Long DEFAULT_PRODUCT_OPTION_ID = 1L;
    private static final Long UPDATED_PRODUCT_OPTION_ID = 2L;
    private static final Long SMALLER_PRODUCT_OPTION_ID = 1L - 1L;

    private static final String DEFAULT_PRODUCT_OPTION_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_OPTION_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_MATERIAL_ID = 1L;
    private static final Long UPDATED_MATERIAL_ID = 2L;
    private static final Long SMALLER_MATERIAL_ID = 1L - 1L;

    private static final String DEFAULT_MATERIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_PRODUCT_BRAND_ID = 1L;
    private static final Long UPDATED_PRODUCT_BRAND_ID = 2L;
    private static final Long SMALLER_PRODUCT_BRAND_ID = 1L - 1L;

    private static final String DEFAULT_PRODUCT_BRAND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_BRAND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HIGHLIGHTS = "AAAAAAAAAA";
    private static final String UPDATED_HIGHLIGHTS = "BBBBBBBBBB";

    private static final String DEFAULT_SEARCH_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_CARE_INSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_CARE_INSTRUCTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_DANGEROUS_GOODS = "AAAAAAAAAA";
    private static final String UPDATED_DANGEROUS_GOODS = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_UNIT_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_REMOMMENDED_RETAIL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_REMOMMENDED_RETAIL_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_REMOMMENDED_RETAIL_PRICE = new BigDecimal(1 - 1);

    private static final String DEFAULT_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY_ON_HAND = 1;
    private static final Integer UPDATED_QUANTITY_ON_HAND = 2;
    private static final Integer SMALLER_QUANTITY_ON_HAND = 1 - 1;

    private static final String DEFAULT_WARRANTY_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_WARRANTY_PERIOD = "BBBBBBBBBB";

    private static final String DEFAULT_WARRANTY_POLICY = "AAAAAAAAAA";
    private static final String UPDATED_WARRANTY_POLICY = "BBBBBBBBBB";

    private static final Long DEFAULT_WARRANTY_TYPE_ID = 1L;
    private static final Long UPDATED_WARRANTY_TYPE_ID = 2L;
    private static final Long SMALLER_WARRANTY_TYPE_ID = 1L - 1L;

    private static final String DEFAULT_WARRANTY_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WARRANTY_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WHAT_IN_THE_BOX = "AAAAAAAAAA";
    private static final String UPDATED_WHAT_IN_THE_BOX = "BBBBBBBBBB";

    private static final Integer DEFAULT_ITEM_LENGTH = 1;
    private static final Integer UPDATED_ITEM_LENGTH = 2;
    private static final Integer SMALLER_ITEM_LENGTH = 1 - 1;

    private static final Integer DEFAULT_ITEM_WIDTH = 1;
    private static final Integer UPDATED_ITEM_WIDTH = 2;
    private static final Integer SMALLER_ITEM_WIDTH = 1 - 1;

    private static final Integer DEFAULT_ITEM_HEIGHT = 1;
    private static final Integer UPDATED_ITEM_HEIGHT = 2;
    private static final Integer SMALLER_ITEM_HEIGHT = 1 - 1;

    private static final BigDecimal DEFAULT_ITEM_WEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ITEM_WEIGHT = new BigDecimal(2);
    private static final BigDecimal SMALLER_ITEM_WEIGHT = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_ITEM_PACKAGE_LENGTH = 1;
    private static final Integer UPDATED_ITEM_PACKAGE_LENGTH = 2;
    private static final Integer SMALLER_ITEM_PACKAGE_LENGTH = 1 - 1;

    private static final Integer DEFAULT_ITEM_PACKAGE_WIDTH = 1;
    private static final Integer UPDATED_ITEM_PACKAGE_WIDTH = 2;
    private static final Integer SMALLER_ITEM_PACKAGE_WIDTH = 1 - 1;

    private static final Integer DEFAULT_ITEM_PACKAGE_HEIGHT = 1;
    private static final Integer UPDATED_ITEM_PACKAGE_HEIGHT = 2;
    private static final Integer SMALLER_ITEM_PACKAGE_HEIGHT = 1 - 1;

    private static final BigDecimal DEFAULT_ITEM_PACKAGE_WEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ITEM_PACKAGE_WEIGHT = new BigDecimal(2);
    private static final BigDecimal SMALLER_ITEM_PACKAGE_WEIGHT = new BigDecimal(1 - 1);

    private static final Long DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID = 1L;
    private static final Long UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID = 2L;
    private static final Long SMALLER_ITEM_LENGTH_UNIT_MEASURE_ID = 1L - 1L;

    private static final String DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID = 1L;
    private static final Long UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID = 2L;
    private static final Long SMALLER_ITEM_WIDTH_UNIT_MEASURE_ID = 1L - 1L;

    private static final String DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID = 1L;
    private static final Long UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID = 2L;
    private static final Long SMALLER_ITEM_HEIGHT_UNIT_MEASURE_ID = 1L - 1L;

    private static final String DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID = 1L;
    private static final Long UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID = 2L;
    private static final Long SMALLER_ITEM_WEIGHT_UNIT_MEASURE_ID = 1L - 1L;

    private static final String DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID = 1L;
    private static final Long UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID = 2L;
    private static final Long SMALLER_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID = 1L - 1L;

    private static final String DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID = 1L;
    private static final Long UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID = 2L;
    private static final Long SMALLER_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID = 1L - 1L;

    private static final String DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID = 1L;
    private static final Long UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID = 2L;
    private static final Long SMALLER_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID = 1L - 1L;

    private static final String DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID = 1L;
    private static final Long UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID = 2L;
    private static final Long SMALLER_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID = 1L - 1L;

    private static final String DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NO_OF_PIECES = 1;
    private static final Integer UPDATED_NO_OF_PIECES = 2;
    private static final Integer SMALLER_NO_OF_PIECES = 1 - 1;

    private static final Integer DEFAULT_NO_OF_ITEMS = 1;
    private static final Integer UPDATED_NO_OF_ITEMS = 2;
    private static final Integer SMALLER_NO_OF_ITEMS = 1 - 1;

    private static final String DEFAULT_MANUFACTURE = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURE = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIAL_FEACTURES = "AAAAAAAAAA";
    private static final String UPDATED_SPECIAL_FEACTURES = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GENUINE_AND_LEGAL = false;
    private static final Boolean UPDATED_GENUINE_AND_LEGAL = true;

    private static final String DEFAULT_COUNTRY_OF_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_OF_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_USAGE_AND_SIDE_EFFECTS = "AAAAAAAAAA";
    private static final String UPDATED_USAGE_AND_SIDE_EFFECTS = "BBBBBBBBBB";

    private static final String DEFAULT_SAFETY_WARNNING = "AAAAAAAAAA";
    private static final String UPDATED_SAFETY_WARNNING = "BBBBBBBBBB";

    private static final Instant DEFAULT_SELL_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SELL_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SELL_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SELL_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private StockItemTempRepository stockItemTempRepository;

    @Autowired
    private StockItemTempMapper stockItemTempMapper;

    @Autowired
    private StockItemTempService stockItemTempService;

    @Autowired
    private StockItemTempQueryService stockItemTempQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restStockItemTempMockMvc;

    private StockItemTemp stockItemTemp;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockItemTempResource stockItemTempResource = new StockItemTempResource(stockItemTempService, stockItemTempQueryService);
        this.restStockItemTempMockMvc = MockMvcBuilders.standaloneSetup(stockItemTempResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItemTemp createEntity(EntityManager em) {
        StockItemTemp stockItemTemp = new StockItemTemp()
            .stockItemName(DEFAULT_STOCK_ITEM_NAME)
            .vendorCode(DEFAULT_VENDOR_CODE)
            .vendorSKU(DEFAULT_VENDOR_SKU)
            .barcode(DEFAULT_BARCODE)
            .barcodeTypeId(DEFAULT_BARCODE_TYPE_ID)
            .barcodeTypeName(DEFAULT_BARCODE_TYPE_NAME)
            .productType(DEFAULT_PRODUCT_TYPE)
            .productCategoryId(DEFAULT_PRODUCT_CATEGORY_ID)
            .productCategoryName(DEFAULT_PRODUCT_CATEGORY_NAME)
            .productAttributeSetId(DEFAULT_PRODUCT_ATTRIBUTE_SET_ID)
            .productAttributeId(DEFAULT_PRODUCT_ATTRIBUTE_ID)
            .productAttributeValue(DEFAULT_PRODUCT_ATTRIBUTE_VALUE)
            .productOptionSetId(DEFAULT_PRODUCT_OPTION_SET_ID)
            .productOptionId(DEFAULT_PRODUCT_OPTION_ID)
            .productOptionValue(DEFAULT_PRODUCT_OPTION_VALUE)
            .modelName(DEFAULT_MODEL_NAME)
            .modelNumber(DEFAULT_MODEL_NUMBER)
            .materialId(DEFAULT_MATERIAL_ID)
            .materialName(DEFAULT_MATERIAL_NAME)
            .shortDescription(DEFAULT_SHORT_DESCRIPTION)
            .description(DEFAULT_DESCRIPTION)
            .productBrandId(DEFAULT_PRODUCT_BRAND_ID)
            .productBrandName(DEFAULT_PRODUCT_BRAND_NAME)
            .highlights(DEFAULT_HIGHLIGHTS)
            .searchDetails(DEFAULT_SEARCH_DETAILS)
            .careInstructions(DEFAULT_CARE_INSTRUCTIONS)
            .dangerousGoods(DEFAULT_DANGEROUS_GOODS)
            .videoUrl(DEFAULT_VIDEO_URL)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .remommendedRetailPrice(DEFAULT_REMOMMENDED_RETAIL_PRICE)
            .currencyCode(DEFAULT_CURRENCY_CODE)
            .quantityOnHand(DEFAULT_QUANTITY_ON_HAND)
            .warrantyPeriod(DEFAULT_WARRANTY_PERIOD)
            .warrantyPolicy(DEFAULT_WARRANTY_POLICY)
            .warrantyTypeId(DEFAULT_WARRANTY_TYPE_ID)
            .warrantyTypeName(DEFAULT_WARRANTY_TYPE_NAME)
            .whatInTheBox(DEFAULT_WHAT_IN_THE_BOX)
            .itemLength(DEFAULT_ITEM_LENGTH)
            .itemWidth(DEFAULT_ITEM_WIDTH)
            .itemHeight(DEFAULT_ITEM_HEIGHT)
            .itemWeight(DEFAULT_ITEM_WEIGHT)
            .itemPackageLength(DEFAULT_ITEM_PACKAGE_LENGTH)
            .itemPackageWidth(DEFAULT_ITEM_PACKAGE_WIDTH)
            .itemPackageHeight(DEFAULT_ITEM_PACKAGE_HEIGHT)
            .itemPackageWeight(DEFAULT_ITEM_PACKAGE_WEIGHT)
            .itemLengthUnitMeasureId(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID)
            .itemLengthUnitMeasureCode(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE)
            .itemWidthUnitMeasureId(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID)
            .itemWidthUnitMeasureCode(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE)
            .itemHeightUnitMeasureId(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID)
            .itemHeightUnitMeasureCode(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE)
            .itemWeightUnitMeasureId(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID)
            .itemWeightUnitMeasureCode(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE)
            .itemPackageLengthUnitMeasureId(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID)
            .itemPackageLengthUnitMeasureCode(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE)
            .itemPackageWidthUnitMeasureId(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID)
            .itemPackageWidthUnitMeasureCode(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE)
            .itemPackageHeightUnitMeasureId(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID)
            .itemPackageHeightUnitMeasureCode(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE)
            .itemPackageWeightUnitMeasureId(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID)
            .itemPackageWeightUnitMeasureCode(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE)
            .noOfPieces(DEFAULT_NO_OF_PIECES)
            .noOfItems(DEFAULT_NO_OF_ITEMS)
            .manufacture(DEFAULT_MANUFACTURE)
            .specialFeactures(DEFAULT_SPECIAL_FEACTURES)
            .productComplianceCertificate(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE)
            .genuineAndLegal(DEFAULT_GENUINE_AND_LEGAL)
            .countryOfOrigin(DEFAULT_COUNTRY_OF_ORIGIN)
            .usageAndSideEffects(DEFAULT_USAGE_AND_SIDE_EFFECTS)
            .safetyWarnning(DEFAULT_SAFETY_WARNNING)
            .sellStartDate(DEFAULT_SELL_START_DATE)
            .sellEndDate(DEFAULT_SELL_END_DATE)
            .status(DEFAULT_STATUS)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return stockItemTemp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItemTemp createUpdatedEntity(EntityManager em) {
        StockItemTemp stockItemTemp = new StockItemTemp()
            .stockItemName(UPDATED_STOCK_ITEM_NAME)
            .vendorCode(UPDATED_VENDOR_CODE)
            .vendorSKU(UPDATED_VENDOR_SKU)
            .barcode(UPDATED_BARCODE)
            .barcodeTypeId(UPDATED_BARCODE_TYPE_ID)
            .barcodeTypeName(UPDATED_BARCODE_TYPE_NAME)
            .productType(UPDATED_PRODUCT_TYPE)
            .productCategoryId(UPDATED_PRODUCT_CATEGORY_ID)
            .productCategoryName(UPDATED_PRODUCT_CATEGORY_NAME)
            .productAttributeSetId(UPDATED_PRODUCT_ATTRIBUTE_SET_ID)
            .productAttributeId(UPDATED_PRODUCT_ATTRIBUTE_ID)
            .productAttributeValue(UPDATED_PRODUCT_ATTRIBUTE_VALUE)
            .productOptionSetId(UPDATED_PRODUCT_OPTION_SET_ID)
            .productOptionId(UPDATED_PRODUCT_OPTION_ID)
            .productOptionValue(UPDATED_PRODUCT_OPTION_VALUE)
            .modelName(UPDATED_MODEL_NAME)
            .modelNumber(UPDATED_MODEL_NUMBER)
            .materialId(UPDATED_MATERIAL_ID)
            .materialName(UPDATED_MATERIAL_NAME)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .description(UPDATED_DESCRIPTION)
            .productBrandId(UPDATED_PRODUCT_BRAND_ID)
            .productBrandName(UPDATED_PRODUCT_BRAND_NAME)
            .highlights(UPDATED_HIGHLIGHTS)
            .searchDetails(UPDATED_SEARCH_DETAILS)
            .careInstructions(UPDATED_CARE_INSTRUCTIONS)
            .dangerousGoods(UPDATED_DANGEROUS_GOODS)
            .videoUrl(UPDATED_VIDEO_URL)
            .unitPrice(UPDATED_UNIT_PRICE)
            .remommendedRetailPrice(UPDATED_REMOMMENDED_RETAIL_PRICE)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .quantityOnHand(UPDATED_QUANTITY_ON_HAND)
            .warrantyPeriod(UPDATED_WARRANTY_PERIOD)
            .warrantyPolicy(UPDATED_WARRANTY_POLICY)
            .warrantyTypeId(UPDATED_WARRANTY_TYPE_ID)
            .warrantyTypeName(UPDATED_WARRANTY_TYPE_NAME)
            .whatInTheBox(UPDATED_WHAT_IN_THE_BOX)
            .itemLength(UPDATED_ITEM_LENGTH)
            .itemWidth(UPDATED_ITEM_WIDTH)
            .itemHeight(UPDATED_ITEM_HEIGHT)
            .itemWeight(UPDATED_ITEM_WEIGHT)
            .itemPackageLength(UPDATED_ITEM_PACKAGE_LENGTH)
            .itemPackageWidth(UPDATED_ITEM_PACKAGE_WIDTH)
            .itemPackageHeight(UPDATED_ITEM_PACKAGE_HEIGHT)
            .itemPackageWeight(UPDATED_ITEM_PACKAGE_WEIGHT)
            .itemLengthUnitMeasureId(UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID)
            .itemLengthUnitMeasureCode(UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE)
            .itemWidthUnitMeasureId(UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID)
            .itemWidthUnitMeasureCode(UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE)
            .itemHeightUnitMeasureId(UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID)
            .itemHeightUnitMeasureCode(UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE)
            .itemWeightUnitMeasureId(UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID)
            .itemWeightUnitMeasureCode(UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE)
            .itemPackageLengthUnitMeasureId(UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID)
            .itemPackageLengthUnitMeasureCode(UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE)
            .itemPackageWidthUnitMeasureId(UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID)
            .itemPackageWidthUnitMeasureCode(UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE)
            .itemPackageHeightUnitMeasureId(UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID)
            .itemPackageHeightUnitMeasureCode(UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE)
            .itemPackageWeightUnitMeasureId(UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID)
            .itemPackageWeightUnitMeasureCode(UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE)
            .noOfPieces(UPDATED_NO_OF_PIECES)
            .noOfItems(UPDATED_NO_OF_ITEMS)
            .manufacture(UPDATED_MANUFACTURE)
            .specialFeactures(UPDATED_SPECIAL_FEACTURES)
            .productComplianceCertificate(UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE)
            .genuineAndLegal(UPDATED_GENUINE_AND_LEGAL)
            .countryOfOrigin(UPDATED_COUNTRY_OF_ORIGIN)
            .usageAndSideEffects(UPDATED_USAGE_AND_SIDE_EFFECTS)
            .safetyWarnning(UPDATED_SAFETY_WARNNING)
            .sellStartDate(UPDATED_SELL_START_DATE)
            .sellEndDate(UPDATED_SELL_END_DATE)
            .status(UPDATED_STATUS)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return stockItemTemp;
    }

    @BeforeEach
    public void initTest() {
        stockItemTemp = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockItemTemp() throws Exception {
        int databaseSizeBeforeCreate = stockItemTempRepository.findAll().size();

        // Create the StockItemTemp
        StockItemTempDTO stockItemTempDTO = stockItemTempMapper.toDto(stockItemTemp);
        restStockItemTempMockMvc.perform(post("/api/stock-item-temps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTempDTO)))
            .andExpect(status().isCreated());

        // Validate the StockItemTemp in the database
        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeCreate + 1);
        StockItemTemp testStockItemTemp = stockItemTempList.get(stockItemTempList.size() - 1);
        assertThat(testStockItemTemp.getStockItemName()).isEqualTo(DEFAULT_STOCK_ITEM_NAME);
        assertThat(testStockItemTemp.getVendorCode()).isEqualTo(DEFAULT_VENDOR_CODE);
        assertThat(testStockItemTemp.getVendorSKU()).isEqualTo(DEFAULT_VENDOR_SKU);
        assertThat(testStockItemTemp.getBarcode()).isEqualTo(DEFAULT_BARCODE);
        assertThat(testStockItemTemp.getBarcodeTypeId()).isEqualTo(DEFAULT_BARCODE_TYPE_ID);
        assertThat(testStockItemTemp.getBarcodeTypeName()).isEqualTo(DEFAULT_BARCODE_TYPE_NAME);
        assertThat(testStockItemTemp.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testStockItemTemp.getProductCategoryId()).isEqualTo(DEFAULT_PRODUCT_CATEGORY_ID);
        assertThat(testStockItemTemp.getProductCategoryName()).isEqualTo(DEFAULT_PRODUCT_CATEGORY_NAME);
        assertThat(testStockItemTemp.getProductAttributeSetId()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_SET_ID);
        assertThat(testStockItemTemp.getProductAttributeId()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_ID);
        assertThat(testStockItemTemp.getProductAttributeValue()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_VALUE);
        assertThat(testStockItemTemp.getProductOptionSetId()).isEqualTo(DEFAULT_PRODUCT_OPTION_SET_ID);
        assertThat(testStockItemTemp.getProductOptionId()).isEqualTo(DEFAULT_PRODUCT_OPTION_ID);
        assertThat(testStockItemTemp.getProductOptionValue()).isEqualTo(DEFAULT_PRODUCT_OPTION_VALUE);
        assertThat(testStockItemTemp.getModelName()).isEqualTo(DEFAULT_MODEL_NAME);
        assertThat(testStockItemTemp.getModelNumber()).isEqualTo(DEFAULT_MODEL_NUMBER);
        assertThat(testStockItemTemp.getMaterialId()).isEqualTo(DEFAULT_MATERIAL_ID);
        assertThat(testStockItemTemp.getMaterialName()).isEqualTo(DEFAULT_MATERIAL_NAME);
        assertThat(testStockItemTemp.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testStockItemTemp.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStockItemTemp.getProductBrandId()).isEqualTo(DEFAULT_PRODUCT_BRAND_ID);
        assertThat(testStockItemTemp.getProductBrandName()).isEqualTo(DEFAULT_PRODUCT_BRAND_NAME);
        assertThat(testStockItemTemp.getHighlights()).isEqualTo(DEFAULT_HIGHLIGHTS);
        assertThat(testStockItemTemp.getSearchDetails()).isEqualTo(DEFAULT_SEARCH_DETAILS);
        assertThat(testStockItemTemp.getCareInstructions()).isEqualTo(DEFAULT_CARE_INSTRUCTIONS);
        assertThat(testStockItemTemp.getDangerousGoods()).isEqualTo(DEFAULT_DANGEROUS_GOODS);
        assertThat(testStockItemTemp.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testStockItemTemp.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testStockItemTemp.getRemommendedRetailPrice()).isEqualTo(DEFAULT_REMOMMENDED_RETAIL_PRICE);
        assertThat(testStockItemTemp.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testStockItemTemp.getQuantityOnHand()).isEqualTo(DEFAULT_QUANTITY_ON_HAND);
        assertThat(testStockItemTemp.getWarrantyPeriod()).isEqualTo(DEFAULT_WARRANTY_PERIOD);
        assertThat(testStockItemTemp.getWarrantyPolicy()).isEqualTo(DEFAULT_WARRANTY_POLICY);
        assertThat(testStockItemTemp.getWarrantyTypeId()).isEqualTo(DEFAULT_WARRANTY_TYPE_ID);
        assertThat(testStockItemTemp.getWarrantyTypeName()).isEqualTo(DEFAULT_WARRANTY_TYPE_NAME);
        assertThat(testStockItemTemp.getWhatInTheBox()).isEqualTo(DEFAULT_WHAT_IN_THE_BOX);
        assertThat(testStockItemTemp.getItemLength()).isEqualTo(DEFAULT_ITEM_LENGTH);
        assertThat(testStockItemTemp.getItemWidth()).isEqualTo(DEFAULT_ITEM_WIDTH);
        assertThat(testStockItemTemp.getItemHeight()).isEqualTo(DEFAULT_ITEM_HEIGHT);
        assertThat(testStockItemTemp.getItemWeight()).isEqualTo(DEFAULT_ITEM_WEIGHT);
        assertThat(testStockItemTemp.getItemPackageLength()).isEqualTo(DEFAULT_ITEM_PACKAGE_LENGTH);
        assertThat(testStockItemTemp.getItemPackageWidth()).isEqualTo(DEFAULT_ITEM_PACKAGE_WIDTH);
        assertThat(testStockItemTemp.getItemPackageHeight()).isEqualTo(DEFAULT_ITEM_PACKAGE_HEIGHT);
        assertThat(testStockItemTemp.getItemPackageWeight()).isEqualTo(DEFAULT_ITEM_PACKAGE_WEIGHT);
        assertThat(testStockItemTemp.getItemLengthUnitMeasureId()).isEqualTo(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemLengthUnitMeasureCode()).isEqualTo(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemWidthUnitMeasureId()).isEqualTo(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemWidthUnitMeasureCode()).isEqualTo(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemHeightUnitMeasureId()).isEqualTo(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemHeightUnitMeasureCode()).isEqualTo(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemWeightUnitMeasureId()).isEqualTo(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemWeightUnitMeasureCode()).isEqualTo(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemPackageLengthUnitMeasureId()).isEqualTo(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemPackageLengthUnitMeasureCode()).isEqualTo(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemPackageWidthUnitMeasureId()).isEqualTo(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemPackageWidthUnitMeasureCode()).isEqualTo(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemPackageHeightUnitMeasureId()).isEqualTo(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemPackageHeightUnitMeasureCode()).isEqualTo(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemPackageWeightUnitMeasureId()).isEqualTo(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemPackageWeightUnitMeasureCode()).isEqualTo(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getNoOfPieces()).isEqualTo(DEFAULT_NO_OF_PIECES);
        assertThat(testStockItemTemp.getNoOfItems()).isEqualTo(DEFAULT_NO_OF_ITEMS);
        assertThat(testStockItemTemp.getManufacture()).isEqualTo(DEFAULT_MANUFACTURE);
        assertThat(testStockItemTemp.getSpecialFeactures()).isEqualTo(DEFAULT_SPECIAL_FEACTURES);
        assertThat(testStockItemTemp.getProductComplianceCertificate()).isEqualTo(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);
        assertThat(testStockItemTemp.isGenuineAndLegal()).isEqualTo(DEFAULT_GENUINE_AND_LEGAL);
        assertThat(testStockItemTemp.getCountryOfOrigin()).isEqualTo(DEFAULT_COUNTRY_OF_ORIGIN);
        assertThat(testStockItemTemp.getUsageAndSideEffects()).isEqualTo(DEFAULT_USAGE_AND_SIDE_EFFECTS);
        assertThat(testStockItemTemp.getSafetyWarnning()).isEqualTo(DEFAULT_SAFETY_WARNNING);
        assertThat(testStockItemTemp.getSellStartDate()).isEqualTo(DEFAULT_SELL_START_DATE);
        assertThat(testStockItemTemp.getSellEndDate()).isEqualTo(DEFAULT_SELL_END_DATE);
        assertThat(testStockItemTemp.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testStockItemTemp.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testStockItemTemp.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createStockItemTempWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockItemTempRepository.findAll().size();

        // Create the StockItemTemp with an existing ID
        stockItemTemp.setId(1L);
        StockItemTempDTO stockItemTempDTO = stockItemTempMapper.toDto(stockItemTemp);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockItemTempMockMvc.perform(post("/api/stock-item-temps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTempDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemTemp in the database
        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStockItemNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemTempRepository.findAll().size();
        // set the field null
        stockItemTemp.setStockItemName(null);

        // Create the StockItemTemp, which fails.
        StockItemTempDTO stockItemTempDTO = stockItemTempMapper.toDto(stockItemTemp);

        restStockItemTempMockMvc.perform(post("/api/stock-item-temps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTempDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVendorCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemTempRepository.findAll().size();
        // set the field null
        stockItemTemp.setVendorCode(null);

        // Create the StockItemTemp, which fails.
        StockItemTempDTO stockItemTempDTO = stockItemTempMapper.toDto(stockItemTemp);

        restStockItemTempMockMvc.perform(post("/api/stock-item-temps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTempDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVendorSKUIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemTempRepository.findAll().size();
        // set the field null
        stockItemTemp.setVendorSKU(null);

        // Create the StockItemTemp, which fails.
        StockItemTempDTO stockItemTempDTO = stockItemTempMapper.toDto(stockItemTemp);

        restStockItemTempMockMvc.perform(post("/api/stock-item-temps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTempDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemTempRepository.findAll().size();
        // set the field null
        stockItemTemp.setProductCategoryId(null);

        // Create the StockItemTemp, which fails.
        StockItemTempDTO stockItemTempDTO = stockItemTempMapper.toDto(stockItemTemp);

        restStockItemTempMockMvc.perform(post("/api/stock-item-temps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTempDTO)))
            .andExpect(status().isBadRequest());

        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockItemTemps() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList
        restStockItemTempMockMvc.perform(get("/api/stock-item-temps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItemTemp.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockItemName").value(hasItem(DEFAULT_STOCK_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].vendorCode").value(hasItem(DEFAULT_VENDOR_CODE)))
            .andExpect(jsonPath("$.[*].vendorSKU").value(hasItem(DEFAULT_VENDOR_SKU)))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE)))
            .andExpect(jsonPath("$.[*].barcodeTypeId").value(hasItem(DEFAULT_BARCODE_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].barcodeTypeName").value(hasItem(DEFAULT_BARCODE_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE)))
            .andExpect(jsonPath("$.[*].productCategoryId").value(hasItem(DEFAULT_PRODUCT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].productCategoryName").value(hasItem(DEFAULT_PRODUCT_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].productAttributeSetId").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_SET_ID.intValue())))
            .andExpect(jsonPath("$.[*].productAttributeId").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_ID.intValue())))
            .andExpect(jsonPath("$.[*].productAttributeValue").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_VALUE)))
            .andExpect(jsonPath("$.[*].productOptionSetId").value(hasItem(DEFAULT_PRODUCT_OPTION_SET_ID.intValue())))
            .andExpect(jsonPath("$.[*].productOptionId").value(hasItem(DEFAULT_PRODUCT_OPTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].productOptionValue").value(hasItem(DEFAULT_PRODUCT_OPTION_VALUE)))
            .andExpect(jsonPath("$.[*].modelName").value(hasItem(DEFAULT_MODEL_NAME)))
            .andExpect(jsonPath("$.[*].modelNumber").value(hasItem(DEFAULT_MODEL_NUMBER)))
            .andExpect(jsonPath("$.[*].materialId").value(hasItem(DEFAULT_MATERIAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].materialName").value(hasItem(DEFAULT_MATERIAL_NAME)))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].productBrandId").value(hasItem(DEFAULT_PRODUCT_BRAND_ID.intValue())))
            .andExpect(jsonPath("$.[*].productBrandName").value(hasItem(DEFAULT_PRODUCT_BRAND_NAME)))
            .andExpect(jsonPath("$.[*].highlights").value(hasItem(DEFAULT_HIGHLIGHTS.toString())))
            .andExpect(jsonPath("$.[*].searchDetails").value(hasItem(DEFAULT_SEARCH_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].careInstructions").value(hasItem(DEFAULT_CARE_INSTRUCTIONS.toString())))
            .andExpect(jsonPath("$.[*].dangerousGoods").value(hasItem(DEFAULT_DANGEROUS_GOODS)))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].remommendedRetailPrice").value(hasItem(DEFAULT_REMOMMENDED_RETAIL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].quantityOnHand").value(hasItem(DEFAULT_QUANTITY_ON_HAND)))
            .andExpect(jsonPath("$.[*].warrantyPeriod").value(hasItem(DEFAULT_WARRANTY_PERIOD)))
            .andExpect(jsonPath("$.[*].warrantyPolicy").value(hasItem(DEFAULT_WARRANTY_POLICY)))
            .andExpect(jsonPath("$.[*].warrantyTypeId").value(hasItem(DEFAULT_WARRANTY_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].warrantyTypeName").value(hasItem(DEFAULT_WARRANTY_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].whatInTheBox").value(hasItem(DEFAULT_WHAT_IN_THE_BOX.toString())))
            .andExpect(jsonPath("$.[*].itemLength").value(hasItem(DEFAULT_ITEM_LENGTH)))
            .andExpect(jsonPath("$.[*].itemWidth").value(hasItem(DEFAULT_ITEM_WIDTH)))
            .andExpect(jsonPath("$.[*].itemHeight").value(hasItem(DEFAULT_ITEM_HEIGHT)))
            .andExpect(jsonPath("$.[*].itemWeight").value(hasItem(DEFAULT_ITEM_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].itemPackageLength").value(hasItem(DEFAULT_ITEM_PACKAGE_LENGTH)))
            .andExpect(jsonPath("$.[*].itemPackageWidth").value(hasItem(DEFAULT_ITEM_PACKAGE_WIDTH)))
            .andExpect(jsonPath("$.[*].itemPackageHeight").value(hasItem(DEFAULT_ITEM_PACKAGE_HEIGHT)))
            .andExpect(jsonPath("$.[*].itemPackageWeight").value(hasItem(DEFAULT_ITEM_PACKAGE_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].itemLengthUnitMeasureId").value(hasItem(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemLengthUnitMeasureCode").value(hasItem(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].itemWidthUnitMeasureId").value(hasItem(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemWidthUnitMeasureCode").value(hasItem(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].itemHeightUnitMeasureId").value(hasItem(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemHeightUnitMeasureCode").value(hasItem(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].itemWeightUnitMeasureId").value(hasItem(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemWeightUnitMeasureCode").value(hasItem(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].itemPackageLengthUnitMeasureId").value(hasItem(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemPackageLengthUnitMeasureCode").value(hasItem(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].itemPackageWidthUnitMeasureId").value(hasItem(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemPackageWidthUnitMeasureCode").value(hasItem(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].itemPackageHeightUnitMeasureId").value(hasItem(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemPackageHeightUnitMeasureCode").value(hasItem(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].itemPackageWeightUnitMeasureId").value(hasItem(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemPackageWeightUnitMeasureCode").value(hasItem(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].noOfPieces").value(hasItem(DEFAULT_NO_OF_PIECES)))
            .andExpect(jsonPath("$.[*].noOfItems").value(hasItem(DEFAULT_NO_OF_ITEMS)))
            .andExpect(jsonPath("$.[*].manufacture").value(hasItem(DEFAULT_MANUFACTURE)))
            .andExpect(jsonPath("$.[*].specialFeactures").value(hasItem(DEFAULT_SPECIAL_FEACTURES.toString())))
            .andExpect(jsonPath("$.[*].productComplianceCertificate").value(hasItem(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE)))
            .andExpect(jsonPath("$.[*].genuineAndLegal").value(hasItem(DEFAULT_GENUINE_AND_LEGAL.booleanValue())))
            .andExpect(jsonPath("$.[*].countryOfOrigin").value(hasItem(DEFAULT_COUNTRY_OF_ORIGIN)))
            .andExpect(jsonPath("$.[*].usageAndSideEffects").value(hasItem(DEFAULT_USAGE_AND_SIDE_EFFECTS.toString())))
            .andExpect(jsonPath("$.[*].safetyWarnning").value(hasItem(DEFAULT_SAFETY_WARNNING.toString())))
            .andExpect(jsonPath("$.[*].sellStartDate").value(hasItem(DEFAULT_SELL_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].sellEndDate").value(hasItem(DEFAULT_SELL_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getStockItemTemp() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get the stockItemTemp
        restStockItemTempMockMvc.perform(get("/api/stock-item-temps/{id}", stockItemTemp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockItemTemp.getId().intValue()))
            .andExpect(jsonPath("$.stockItemName").value(DEFAULT_STOCK_ITEM_NAME))
            .andExpect(jsonPath("$.vendorCode").value(DEFAULT_VENDOR_CODE))
            .andExpect(jsonPath("$.vendorSKU").value(DEFAULT_VENDOR_SKU))
            .andExpect(jsonPath("$.barcode").value(DEFAULT_BARCODE))
            .andExpect(jsonPath("$.barcodeTypeId").value(DEFAULT_BARCODE_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.barcodeTypeName").value(DEFAULT_BARCODE_TYPE_NAME))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE))
            .andExpect(jsonPath("$.productCategoryId").value(DEFAULT_PRODUCT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.productCategoryName").value(DEFAULT_PRODUCT_CATEGORY_NAME))
            .andExpect(jsonPath("$.productAttributeSetId").value(DEFAULT_PRODUCT_ATTRIBUTE_SET_ID.intValue()))
            .andExpect(jsonPath("$.productAttributeId").value(DEFAULT_PRODUCT_ATTRIBUTE_ID.intValue()))
            .andExpect(jsonPath("$.productAttributeValue").value(DEFAULT_PRODUCT_ATTRIBUTE_VALUE))
            .andExpect(jsonPath("$.productOptionSetId").value(DEFAULT_PRODUCT_OPTION_SET_ID.intValue()))
            .andExpect(jsonPath("$.productOptionId").value(DEFAULT_PRODUCT_OPTION_ID.intValue()))
            .andExpect(jsonPath("$.productOptionValue").value(DEFAULT_PRODUCT_OPTION_VALUE))
            .andExpect(jsonPath("$.modelName").value(DEFAULT_MODEL_NAME))
            .andExpect(jsonPath("$.modelNumber").value(DEFAULT_MODEL_NUMBER))
            .andExpect(jsonPath("$.materialId").value(DEFAULT_MATERIAL_ID.intValue()))
            .andExpect(jsonPath("$.materialName").value(DEFAULT_MATERIAL_NAME))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.productBrandId").value(DEFAULT_PRODUCT_BRAND_ID.intValue()))
            .andExpect(jsonPath("$.productBrandName").value(DEFAULT_PRODUCT_BRAND_NAME))
            .andExpect(jsonPath("$.highlights").value(DEFAULT_HIGHLIGHTS.toString()))
            .andExpect(jsonPath("$.searchDetails").value(DEFAULT_SEARCH_DETAILS.toString()))
            .andExpect(jsonPath("$.careInstructions").value(DEFAULT_CARE_INSTRUCTIONS.toString()))
            .andExpect(jsonPath("$.dangerousGoods").value(DEFAULT_DANGEROUS_GOODS))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.remommendedRetailPrice").value(DEFAULT_REMOMMENDED_RETAIL_PRICE.intValue()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE))
            .andExpect(jsonPath("$.quantityOnHand").value(DEFAULT_QUANTITY_ON_HAND))
            .andExpect(jsonPath("$.warrantyPeriod").value(DEFAULT_WARRANTY_PERIOD))
            .andExpect(jsonPath("$.warrantyPolicy").value(DEFAULT_WARRANTY_POLICY))
            .andExpect(jsonPath("$.warrantyTypeId").value(DEFAULT_WARRANTY_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.warrantyTypeName").value(DEFAULT_WARRANTY_TYPE_NAME))
            .andExpect(jsonPath("$.whatInTheBox").value(DEFAULT_WHAT_IN_THE_BOX.toString()))
            .andExpect(jsonPath("$.itemLength").value(DEFAULT_ITEM_LENGTH))
            .andExpect(jsonPath("$.itemWidth").value(DEFAULT_ITEM_WIDTH))
            .andExpect(jsonPath("$.itemHeight").value(DEFAULT_ITEM_HEIGHT))
            .andExpect(jsonPath("$.itemWeight").value(DEFAULT_ITEM_WEIGHT.intValue()))
            .andExpect(jsonPath("$.itemPackageLength").value(DEFAULT_ITEM_PACKAGE_LENGTH))
            .andExpect(jsonPath("$.itemPackageWidth").value(DEFAULT_ITEM_PACKAGE_WIDTH))
            .andExpect(jsonPath("$.itemPackageHeight").value(DEFAULT_ITEM_PACKAGE_HEIGHT))
            .andExpect(jsonPath("$.itemPackageWeight").value(DEFAULT_ITEM_PACKAGE_WEIGHT.intValue()))
            .andExpect(jsonPath("$.itemLengthUnitMeasureId").value(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID.intValue()))
            .andExpect(jsonPath("$.itemLengthUnitMeasureCode").value(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE))
            .andExpect(jsonPath("$.itemWidthUnitMeasureId").value(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID.intValue()))
            .andExpect(jsonPath("$.itemWidthUnitMeasureCode").value(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE))
            .andExpect(jsonPath("$.itemHeightUnitMeasureId").value(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID.intValue()))
            .andExpect(jsonPath("$.itemHeightUnitMeasureCode").value(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE))
            .andExpect(jsonPath("$.itemWeightUnitMeasureId").value(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID.intValue()))
            .andExpect(jsonPath("$.itemWeightUnitMeasureCode").value(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE))
            .andExpect(jsonPath("$.itemPackageLengthUnitMeasureId").value(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID.intValue()))
            .andExpect(jsonPath("$.itemPackageLengthUnitMeasureCode").value(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE))
            .andExpect(jsonPath("$.itemPackageWidthUnitMeasureId").value(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID.intValue()))
            .andExpect(jsonPath("$.itemPackageWidthUnitMeasureCode").value(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE))
            .andExpect(jsonPath("$.itemPackageHeightUnitMeasureId").value(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID.intValue()))
            .andExpect(jsonPath("$.itemPackageHeightUnitMeasureCode").value(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE))
            .andExpect(jsonPath("$.itemPackageWeightUnitMeasureId").value(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID.intValue()))
            .andExpect(jsonPath("$.itemPackageWeightUnitMeasureCode").value(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE))
            .andExpect(jsonPath("$.noOfPieces").value(DEFAULT_NO_OF_PIECES))
            .andExpect(jsonPath("$.noOfItems").value(DEFAULT_NO_OF_ITEMS))
            .andExpect(jsonPath("$.manufacture").value(DEFAULT_MANUFACTURE))
            .andExpect(jsonPath("$.specialFeactures").value(DEFAULT_SPECIAL_FEACTURES.toString()))
            .andExpect(jsonPath("$.productComplianceCertificate").value(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE))
            .andExpect(jsonPath("$.genuineAndLegal").value(DEFAULT_GENUINE_AND_LEGAL.booleanValue()))
            .andExpect(jsonPath("$.countryOfOrigin").value(DEFAULT_COUNTRY_OF_ORIGIN))
            .andExpect(jsonPath("$.usageAndSideEffects").value(DEFAULT_USAGE_AND_SIDE_EFFECTS.toString()))
            .andExpect(jsonPath("$.safetyWarnning").value(DEFAULT_SAFETY_WARNNING.toString()))
            .andExpect(jsonPath("$.sellStartDate").value(DEFAULT_SELL_START_DATE.toString()))
            .andExpect(jsonPath("$.sellEndDate").value(DEFAULT_SELL_END_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getStockItemTempsByIdFiltering() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        Long id = stockItemTemp.getId();

        defaultStockItemTempShouldBeFound("id.equals=" + id);
        defaultStockItemTempShouldNotBeFound("id.notEquals=" + id);

        defaultStockItemTempShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStockItemTempShouldNotBeFound("id.greaterThan=" + id);

        defaultStockItemTempShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStockItemTempShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByStockItemNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where stockItemName equals to DEFAULT_STOCK_ITEM_NAME
        defaultStockItemTempShouldBeFound("stockItemName.equals=" + DEFAULT_STOCK_ITEM_NAME);

        // Get all the stockItemTempList where stockItemName equals to UPDATED_STOCK_ITEM_NAME
        defaultStockItemTempShouldNotBeFound("stockItemName.equals=" + UPDATED_STOCK_ITEM_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByStockItemNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where stockItemName not equals to DEFAULT_STOCK_ITEM_NAME
        defaultStockItemTempShouldNotBeFound("stockItemName.notEquals=" + DEFAULT_STOCK_ITEM_NAME);

        // Get all the stockItemTempList where stockItemName not equals to UPDATED_STOCK_ITEM_NAME
        defaultStockItemTempShouldBeFound("stockItemName.notEquals=" + UPDATED_STOCK_ITEM_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByStockItemNameIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where stockItemName in DEFAULT_STOCK_ITEM_NAME or UPDATED_STOCK_ITEM_NAME
        defaultStockItemTempShouldBeFound("stockItemName.in=" + DEFAULT_STOCK_ITEM_NAME + "," + UPDATED_STOCK_ITEM_NAME);

        // Get all the stockItemTempList where stockItemName equals to UPDATED_STOCK_ITEM_NAME
        defaultStockItemTempShouldNotBeFound("stockItemName.in=" + UPDATED_STOCK_ITEM_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByStockItemNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where stockItemName is not null
        defaultStockItemTempShouldBeFound("stockItemName.specified=true");

        // Get all the stockItemTempList where stockItemName is null
        defaultStockItemTempShouldNotBeFound("stockItemName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByStockItemNameContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where stockItemName contains DEFAULT_STOCK_ITEM_NAME
        defaultStockItemTempShouldBeFound("stockItemName.contains=" + DEFAULT_STOCK_ITEM_NAME);

        // Get all the stockItemTempList where stockItemName contains UPDATED_STOCK_ITEM_NAME
        defaultStockItemTempShouldNotBeFound("stockItemName.contains=" + UPDATED_STOCK_ITEM_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByStockItemNameNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where stockItemName does not contain DEFAULT_STOCK_ITEM_NAME
        defaultStockItemTempShouldNotBeFound("stockItemName.doesNotContain=" + DEFAULT_STOCK_ITEM_NAME);

        // Get all the stockItemTempList where stockItemName does not contain UPDATED_STOCK_ITEM_NAME
        defaultStockItemTempShouldBeFound("stockItemName.doesNotContain=" + UPDATED_STOCK_ITEM_NAME);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByVendorCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where vendorCode equals to DEFAULT_VENDOR_CODE
        defaultStockItemTempShouldBeFound("vendorCode.equals=" + DEFAULT_VENDOR_CODE);

        // Get all the stockItemTempList where vendorCode equals to UPDATED_VENDOR_CODE
        defaultStockItemTempShouldNotBeFound("vendorCode.equals=" + UPDATED_VENDOR_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByVendorCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where vendorCode not equals to DEFAULT_VENDOR_CODE
        defaultStockItemTempShouldNotBeFound("vendorCode.notEquals=" + DEFAULT_VENDOR_CODE);

        // Get all the stockItemTempList where vendorCode not equals to UPDATED_VENDOR_CODE
        defaultStockItemTempShouldBeFound("vendorCode.notEquals=" + UPDATED_VENDOR_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByVendorCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where vendorCode in DEFAULT_VENDOR_CODE or UPDATED_VENDOR_CODE
        defaultStockItemTempShouldBeFound("vendorCode.in=" + DEFAULT_VENDOR_CODE + "," + UPDATED_VENDOR_CODE);

        // Get all the stockItemTempList where vendorCode equals to UPDATED_VENDOR_CODE
        defaultStockItemTempShouldNotBeFound("vendorCode.in=" + UPDATED_VENDOR_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByVendorCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where vendorCode is not null
        defaultStockItemTempShouldBeFound("vendorCode.specified=true");

        // Get all the stockItemTempList where vendorCode is null
        defaultStockItemTempShouldNotBeFound("vendorCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByVendorCodeContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where vendorCode contains DEFAULT_VENDOR_CODE
        defaultStockItemTempShouldBeFound("vendorCode.contains=" + DEFAULT_VENDOR_CODE);

        // Get all the stockItemTempList where vendorCode contains UPDATED_VENDOR_CODE
        defaultStockItemTempShouldNotBeFound("vendorCode.contains=" + UPDATED_VENDOR_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByVendorCodeNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where vendorCode does not contain DEFAULT_VENDOR_CODE
        defaultStockItemTempShouldNotBeFound("vendorCode.doesNotContain=" + DEFAULT_VENDOR_CODE);

        // Get all the stockItemTempList where vendorCode does not contain UPDATED_VENDOR_CODE
        defaultStockItemTempShouldBeFound("vendorCode.doesNotContain=" + UPDATED_VENDOR_CODE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByVendorSKUIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where vendorSKU equals to DEFAULT_VENDOR_SKU
        defaultStockItemTempShouldBeFound("vendorSKU.equals=" + DEFAULT_VENDOR_SKU);

        // Get all the stockItemTempList where vendorSKU equals to UPDATED_VENDOR_SKU
        defaultStockItemTempShouldNotBeFound("vendorSKU.equals=" + UPDATED_VENDOR_SKU);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByVendorSKUIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where vendorSKU not equals to DEFAULT_VENDOR_SKU
        defaultStockItemTempShouldNotBeFound("vendorSKU.notEquals=" + DEFAULT_VENDOR_SKU);

        // Get all the stockItemTempList where vendorSKU not equals to UPDATED_VENDOR_SKU
        defaultStockItemTempShouldBeFound("vendorSKU.notEquals=" + UPDATED_VENDOR_SKU);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByVendorSKUIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where vendorSKU in DEFAULT_VENDOR_SKU or UPDATED_VENDOR_SKU
        defaultStockItemTempShouldBeFound("vendorSKU.in=" + DEFAULT_VENDOR_SKU + "," + UPDATED_VENDOR_SKU);

        // Get all the stockItemTempList where vendorSKU equals to UPDATED_VENDOR_SKU
        defaultStockItemTempShouldNotBeFound("vendorSKU.in=" + UPDATED_VENDOR_SKU);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByVendorSKUIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where vendorSKU is not null
        defaultStockItemTempShouldBeFound("vendorSKU.specified=true");

        // Get all the stockItemTempList where vendorSKU is null
        defaultStockItemTempShouldNotBeFound("vendorSKU.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByVendorSKUContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where vendorSKU contains DEFAULT_VENDOR_SKU
        defaultStockItemTempShouldBeFound("vendorSKU.contains=" + DEFAULT_VENDOR_SKU);

        // Get all the stockItemTempList where vendorSKU contains UPDATED_VENDOR_SKU
        defaultStockItemTempShouldNotBeFound("vendorSKU.contains=" + UPDATED_VENDOR_SKU);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByVendorSKUNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where vendorSKU does not contain DEFAULT_VENDOR_SKU
        defaultStockItemTempShouldNotBeFound("vendorSKU.doesNotContain=" + DEFAULT_VENDOR_SKU);

        // Get all the stockItemTempList where vendorSKU does not contain UPDATED_VENDOR_SKU
        defaultStockItemTempShouldBeFound("vendorSKU.doesNotContain=" + UPDATED_VENDOR_SKU);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcode equals to DEFAULT_BARCODE
        defaultStockItemTempShouldBeFound("barcode.equals=" + DEFAULT_BARCODE);

        // Get all the stockItemTempList where barcode equals to UPDATED_BARCODE
        defaultStockItemTempShouldNotBeFound("barcode.equals=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcode not equals to DEFAULT_BARCODE
        defaultStockItemTempShouldNotBeFound("barcode.notEquals=" + DEFAULT_BARCODE);

        // Get all the stockItemTempList where barcode not equals to UPDATED_BARCODE
        defaultStockItemTempShouldBeFound("barcode.notEquals=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcode in DEFAULT_BARCODE or UPDATED_BARCODE
        defaultStockItemTempShouldBeFound("barcode.in=" + DEFAULT_BARCODE + "," + UPDATED_BARCODE);

        // Get all the stockItemTempList where barcode equals to UPDATED_BARCODE
        defaultStockItemTempShouldNotBeFound("barcode.in=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcode is not null
        defaultStockItemTempShouldBeFound("barcode.specified=true");

        // Get all the stockItemTempList where barcode is null
        defaultStockItemTempShouldNotBeFound("barcode.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcode contains DEFAULT_BARCODE
        defaultStockItemTempShouldBeFound("barcode.contains=" + DEFAULT_BARCODE);

        // Get all the stockItemTempList where barcode contains UPDATED_BARCODE
        defaultStockItemTempShouldNotBeFound("barcode.contains=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcode does not contain DEFAULT_BARCODE
        defaultStockItemTempShouldNotBeFound("barcode.doesNotContain=" + DEFAULT_BARCODE);

        // Get all the stockItemTempList where barcode does not contain UPDATED_BARCODE
        defaultStockItemTempShouldBeFound("barcode.doesNotContain=" + UPDATED_BARCODE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcodeTypeId equals to DEFAULT_BARCODE_TYPE_ID
        defaultStockItemTempShouldBeFound("barcodeTypeId.equals=" + DEFAULT_BARCODE_TYPE_ID);

        // Get all the stockItemTempList where barcodeTypeId equals to UPDATED_BARCODE_TYPE_ID
        defaultStockItemTempShouldNotBeFound("barcodeTypeId.equals=" + UPDATED_BARCODE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeTypeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcodeTypeId not equals to DEFAULT_BARCODE_TYPE_ID
        defaultStockItemTempShouldNotBeFound("barcodeTypeId.notEquals=" + DEFAULT_BARCODE_TYPE_ID);

        // Get all the stockItemTempList where barcodeTypeId not equals to UPDATED_BARCODE_TYPE_ID
        defaultStockItemTempShouldBeFound("barcodeTypeId.notEquals=" + UPDATED_BARCODE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcodeTypeId in DEFAULT_BARCODE_TYPE_ID or UPDATED_BARCODE_TYPE_ID
        defaultStockItemTempShouldBeFound("barcodeTypeId.in=" + DEFAULT_BARCODE_TYPE_ID + "," + UPDATED_BARCODE_TYPE_ID);

        // Get all the stockItemTempList where barcodeTypeId equals to UPDATED_BARCODE_TYPE_ID
        defaultStockItemTempShouldNotBeFound("barcodeTypeId.in=" + UPDATED_BARCODE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcodeTypeId is not null
        defaultStockItemTempShouldBeFound("barcodeTypeId.specified=true");

        // Get all the stockItemTempList where barcodeTypeId is null
        defaultStockItemTempShouldNotBeFound("barcodeTypeId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeTypeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcodeTypeId is greater than or equal to DEFAULT_BARCODE_TYPE_ID
        defaultStockItemTempShouldBeFound("barcodeTypeId.greaterThanOrEqual=" + DEFAULT_BARCODE_TYPE_ID);

        // Get all the stockItemTempList where barcodeTypeId is greater than or equal to UPDATED_BARCODE_TYPE_ID
        defaultStockItemTempShouldNotBeFound("barcodeTypeId.greaterThanOrEqual=" + UPDATED_BARCODE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeTypeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcodeTypeId is less than or equal to DEFAULT_BARCODE_TYPE_ID
        defaultStockItemTempShouldBeFound("barcodeTypeId.lessThanOrEqual=" + DEFAULT_BARCODE_TYPE_ID);

        // Get all the stockItemTempList where barcodeTypeId is less than or equal to SMALLER_BARCODE_TYPE_ID
        defaultStockItemTempShouldNotBeFound("barcodeTypeId.lessThanOrEqual=" + SMALLER_BARCODE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeTypeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcodeTypeId is less than DEFAULT_BARCODE_TYPE_ID
        defaultStockItemTempShouldNotBeFound("barcodeTypeId.lessThan=" + DEFAULT_BARCODE_TYPE_ID);

        // Get all the stockItemTempList where barcodeTypeId is less than UPDATED_BARCODE_TYPE_ID
        defaultStockItemTempShouldBeFound("barcodeTypeId.lessThan=" + UPDATED_BARCODE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeTypeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcodeTypeId is greater than DEFAULT_BARCODE_TYPE_ID
        defaultStockItemTempShouldNotBeFound("barcodeTypeId.greaterThan=" + DEFAULT_BARCODE_TYPE_ID);

        // Get all the stockItemTempList where barcodeTypeId is greater than SMALLER_BARCODE_TYPE_ID
        defaultStockItemTempShouldBeFound("barcodeTypeId.greaterThan=" + SMALLER_BARCODE_TYPE_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcodeTypeName equals to DEFAULT_BARCODE_TYPE_NAME
        defaultStockItemTempShouldBeFound("barcodeTypeName.equals=" + DEFAULT_BARCODE_TYPE_NAME);

        // Get all the stockItemTempList where barcodeTypeName equals to UPDATED_BARCODE_TYPE_NAME
        defaultStockItemTempShouldNotBeFound("barcodeTypeName.equals=" + UPDATED_BARCODE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeTypeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcodeTypeName not equals to DEFAULT_BARCODE_TYPE_NAME
        defaultStockItemTempShouldNotBeFound("barcodeTypeName.notEquals=" + DEFAULT_BARCODE_TYPE_NAME);

        // Get all the stockItemTempList where barcodeTypeName not equals to UPDATED_BARCODE_TYPE_NAME
        defaultStockItemTempShouldBeFound("barcodeTypeName.notEquals=" + UPDATED_BARCODE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcodeTypeName in DEFAULT_BARCODE_TYPE_NAME or UPDATED_BARCODE_TYPE_NAME
        defaultStockItemTempShouldBeFound("barcodeTypeName.in=" + DEFAULT_BARCODE_TYPE_NAME + "," + UPDATED_BARCODE_TYPE_NAME);

        // Get all the stockItemTempList where barcodeTypeName equals to UPDATED_BARCODE_TYPE_NAME
        defaultStockItemTempShouldNotBeFound("barcodeTypeName.in=" + UPDATED_BARCODE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcodeTypeName is not null
        defaultStockItemTempShouldBeFound("barcodeTypeName.specified=true");

        // Get all the stockItemTempList where barcodeTypeName is null
        defaultStockItemTempShouldNotBeFound("barcodeTypeName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeTypeNameContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcodeTypeName contains DEFAULT_BARCODE_TYPE_NAME
        defaultStockItemTempShouldBeFound("barcodeTypeName.contains=" + DEFAULT_BARCODE_TYPE_NAME);

        // Get all the stockItemTempList where barcodeTypeName contains UPDATED_BARCODE_TYPE_NAME
        defaultStockItemTempShouldNotBeFound("barcodeTypeName.contains=" + UPDATED_BARCODE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByBarcodeTypeNameNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where barcodeTypeName does not contain DEFAULT_BARCODE_TYPE_NAME
        defaultStockItemTempShouldNotBeFound("barcodeTypeName.doesNotContain=" + DEFAULT_BARCODE_TYPE_NAME);

        // Get all the stockItemTempList where barcodeTypeName does not contain UPDATED_BARCODE_TYPE_NAME
        defaultStockItemTempShouldBeFound("barcodeTypeName.doesNotContain=" + UPDATED_BARCODE_TYPE_NAME);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByProductTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productType equals to DEFAULT_PRODUCT_TYPE
        defaultStockItemTempShouldBeFound("productType.equals=" + DEFAULT_PRODUCT_TYPE);

        // Get all the stockItemTempList where productType equals to UPDATED_PRODUCT_TYPE
        defaultStockItemTempShouldNotBeFound("productType.equals=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productType not equals to DEFAULT_PRODUCT_TYPE
        defaultStockItemTempShouldNotBeFound("productType.notEquals=" + DEFAULT_PRODUCT_TYPE);

        // Get all the stockItemTempList where productType not equals to UPDATED_PRODUCT_TYPE
        defaultStockItemTempShouldBeFound("productType.notEquals=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductTypeIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productType in DEFAULT_PRODUCT_TYPE or UPDATED_PRODUCT_TYPE
        defaultStockItemTempShouldBeFound("productType.in=" + DEFAULT_PRODUCT_TYPE + "," + UPDATED_PRODUCT_TYPE);

        // Get all the stockItemTempList where productType equals to UPDATED_PRODUCT_TYPE
        defaultStockItemTempShouldNotBeFound("productType.in=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productType is not null
        defaultStockItemTempShouldBeFound("productType.specified=true");

        // Get all the stockItemTempList where productType is null
        defaultStockItemTempShouldNotBeFound("productType.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByProductTypeContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productType contains DEFAULT_PRODUCT_TYPE
        defaultStockItemTempShouldBeFound("productType.contains=" + DEFAULT_PRODUCT_TYPE);

        // Get all the stockItemTempList where productType contains UPDATED_PRODUCT_TYPE
        defaultStockItemTempShouldNotBeFound("productType.contains=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductTypeNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productType does not contain DEFAULT_PRODUCT_TYPE
        defaultStockItemTempShouldNotBeFound("productType.doesNotContain=" + DEFAULT_PRODUCT_TYPE);

        // Get all the stockItemTempList where productType does not contain UPDATED_PRODUCT_TYPE
        defaultStockItemTempShouldBeFound("productType.doesNotContain=" + UPDATED_PRODUCT_TYPE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByProductCategoryIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productCategoryId equals to DEFAULT_PRODUCT_CATEGORY_ID
        defaultStockItemTempShouldBeFound("productCategoryId.equals=" + DEFAULT_PRODUCT_CATEGORY_ID);

        // Get all the stockItemTempList where productCategoryId equals to UPDATED_PRODUCT_CATEGORY_ID
        defaultStockItemTempShouldNotBeFound("productCategoryId.equals=" + UPDATED_PRODUCT_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductCategoryIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productCategoryId not equals to DEFAULT_PRODUCT_CATEGORY_ID
        defaultStockItemTempShouldNotBeFound("productCategoryId.notEquals=" + DEFAULT_PRODUCT_CATEGORY_ID);

        // Get all the stockItemTempList where productCategoryId not equals to UPDATED_PRODUCT_CATEGORY_ID
        defaultStockItemTempShouldBeFound("productCategoryId.notEquals=" + UPDATED_PRODUCT_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductCategoryIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productCategoryId in DEFAULT_PRODUCT_CATEGORY_ID or UPDATED_PRODUCT_CATEGORY_ID
        defaultStockItemTempShouldBeFound("productCategoryId.in=" + DEFAULT_PRODUCT_CATEGORY_ID + "," + UPDATED_PRODUCT_CATEGORY_ID);

        // Get all the stockItemTempList where productCategoryId equals to UPDATED_PRODUCT_CATEGORY_ID
        defaultStockItemTempShouldNotBeFound("productCategoryId.in=" + UPDATED_PRODUCT_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductCategoryIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productCategoryId is not null
        defaultStockItemTempShouldBeFound("productCategoryId.specified=true");

        // Get all the stockItemTempList where productCategoryId is null
        defaultStockItemTempShouldNotBeFound("productCategoryId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductCategoryIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productCategoryId is greater than or equal to DEFAULT_PRODUCT_CATEGORY_ID
        defaultStockItemTempShouldBeFound("productCategoryId.greaterThanOrEqual=" + DEFAULT_PRODUCT_CATEGORY_ID);

        // Get all the stockItemTempList where productCategoryId is greater than or equal to UPDATED_PRODUCT_CATEGORY_ID
        defaultStockItemTempShouldNotBeFound("productCategoryId.greaterThanOrEqual=" + UPDATED_PRODUCT_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductCategoryIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productCategoryId is less than or equal to DEFAULT_PRODUCT_CATEGORY_ID
        defaultStockItemTempShouldBeFound("productCategoryId.lessThanOrEqual=" + DEFAULT_PRODUCT_CATEGORY_ID);

        // Get all the stockItemTempList where productCategoryId is less than or equal to SMALLER_PRODUCT_CATEGORY_ID
        defaultStockItemTempShouldNotBeFound("productCategoryId.lessThanOrEqual=" + SMALLER_PRODUCT_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductCategoryIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productCategoryId is less than DEFAULT_PRODUCT_CATEGORY_ID
        defaultStockItemTempShouldNotBeFound("productCategoryId.lessThan=" + DEFAULT_PRODUCT_CATEGORY_ID);

        // Get all the stockItemTempList where productCategoryId is less than UPDATED_PRODUCT_CATEGORY_ID
        defaultStockItemTempShouldBeFound("productCategoryId.lessThan=" + UPDATED_PRODUCT_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductCategoryIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productCategoryId is greater than DEFAULT_PRODUCT_CATEGORY_ID
        defaultStockItemTempShouldNotBeFound("productCategoryId.greaterThan=" + DEFAULT_PRODUCT_CATEGORY_ID);

        // Get all the stockItemTempList where productCategoryId is greater than SMALLER_PRODUCT_CATEGORY_ID
        defaultStockItemTempShouldBeFound("productCategoryId.greaterThan=" + SMALLER_PRODUCT_CATEGORY_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByProductCategoryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productCategoryName equals to DEFAULT_PRODUCT_CATEGORY_NAME
        defaultStockItemTempShouldBeFound("productCategoryName.equals=" + DEFAULT_PRODUCT_CATEGORY_NAME);

        // Get all the stockItemTempList where productCategoryName equals to UPDATED_PRODUCT_CATEGORY_NAME
        defaultStockItemTempShouldNotBeFound("productCategoryName.equals=" + UPDATED_PRODUCT_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductCategoryNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productCategoryName not equals to DEFAULT_PRODUCT_CATEGORY_NAME
        defaultStockItemTempShouldNotBeFound("productCategoryName.notEquals=" + DEFAULT_PRODUCT_CATEGORY_NAME);

        // Get all the stockItemTempList where productCategoryName not equals to UPDATED_PRODUCT_CATEGORY_NAME
        defaultStockItemTempShouldBeFound("productCategoryName.notEquals=" + UPDATED_PRODUCT_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductCategoryNameIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productCategoryName in DEFAULT_PRODUCT_CATEGORY_NAME or UPDATED_PRODUCT_CATEGORY_NAME
        defaultStockItemTempShouldBeFound("productCategoryName.in=" + DEFAULT_PRODUCT_CATEGORY_NAME + "," + UPDATED_PRODUCT_CATEGORY_NAME);

        // Get all the stockItemTempList where productCategoryName equals to UPDATED_PRODUCT_CATEGORY_NAME
        defaultStockItemTempShouldNotBeFound("productCategoryName.in=" + UPDATED_PRODUCT_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductCategoryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productCategoryName is not null
        defaultStockItemTempShouldBeFound("productCategoryName.specified=true");

        // Get all the stockItemTempList where productCategoryName is null
        defaultStockItemTempShouldNotBeFound("productCategoryName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByProductCategoryNameContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productCategoryName contains DEFAULT_PRODUCT_CATEGORY_NAME
        defaultStockItemTempShouldBeFound("productCategoryName.contains=" + DEFAULT_PRODUCT_CATEGORY_NAME);

        // Get all the stockItemTempList where productCategoryName contains UPDATED_PRODUCT_CATEGORY_NAME
        defaultStockItemTempShouldNotBeFound("productCategoryName.contains=" + UPDATED_PRODUCT_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductCategoryNameNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productCategoryName does not contain DEFAULT_PRODUCT_CATEGORY_NAME
        defaultStockItemTempShouldNotBeFound("productCategoryName.doesNotContain=" + DEFAULT_PRODUCT_CATEGORY_NAME);

        // Get all the stockItemTempList where productCategoryName does not contain UPDATED_PRODUCT_CATEGORY_NAME
        defaultStockItemTempShouldBeFound("productCategoryName.doesNotContain=" + UPDATED_PRODUCT_CATEGORY_NAME);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeSetIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeSetId equals to DEFAULT_PRODUCT_ATTRIBUTE_SET_ID
        defaultStockItemTempShouldBeFound("productAttributeSetId.equals=" + DEFAULT_PRODUCT_ATTRIBUTE_SET_ID);

        // Get all the stockItemTempList where productAttributeSetId equals to UPDATED_PRODUCT_ATTRIBUTE_SET_ID
        defaultStockItemTempShouldNotBeFound("productAttributeSetId.equals=" + UPDATED_PRODUCT_ATTRIBUTE_SET_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeSetIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeSetId not equals to DEFAULT_PRODUCT_ATTRIBUTE_SET_ID
        defaultStockItemTempShouldNotBeFound("productAttributeSetId.notEquals=" + DEFAULT_PRODUCT_ATTRIBUTE_SET_ID);

        // Get all the stockItemTempList where productAttributeSetId not equals to UPDATED_PRODUCT_ATTRIBUTE_SET_ID
        defaultStockItemTempShouldBeFound("productAttributeSetId.notEquals=" + UPDATED_PRODUCT_ATTRIBUTE_SET_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeSetIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeSetId in DEFAULT_PRODUCT_ATTRIBUTE_SET_ID or UPDATED_PRODUCT_ATTRIBUTE_SET_ID
        defaultStockItemTempShouldBeFound("productAttributeSetId.in=" + DEFAULT_PRODUCT_ATTRIBUTE_SET_ID + "," + UPDATED_PRODUCT_ATTRIBUTE_SET_ID);

        // Get all the stockItemTempList where productAttributeSetId equals to UPDATED_PRODUCT_ATTRIBUTE_SET_ID
        defaultStockItemTempShouldNotBeFound("productAttributeSetId.in=" + UPDATED_PRODUCT_ATTRIBUTE_SET_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeSetIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeSetId is not null
        defaultStockItemTempShouldBeFound("productAttributeSetId.specified=true");

        // Get all the stockItemTempList where productAttributeSetId is null
        defaultStockItemTempShouldNotBeFound("productAttributeSetId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeSetIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeSetId is greater than or equal to DEFAULT_PRODUCT_ATTRIBUTE_SET_ID
        defaultStockItemTempShouldBeFound("productAttributeSetId.greaterThanOrEqual=" + DEFAULT_PRODUCT_ATTRIBUTE_SET_ID);

        // Get all the stockItemTempList where productAttributeSetId is greater than or equal to UPDATED_PRODUCT_ATTRIBUTE_SET_ID
        defaultStockItemTempShouldNotBeFound("productAttributeSetId.greaterThanOrEqual=" + UPDATED_PRODUCT_ATTRIBUTE_SET_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeSetIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeSetId is less than or equal to DEFAULT_PRODUCT_ATTRIBUTE_SET_ID
        defaultStockItemTempShouldBeFound("productAttributeSetId.lessThanOrEqual=" + DEFAULT_PRODUCT_ATTRIBUTE_SET_ID);

        // Get all the stockItemTempList where productAttributeSetId is less than or equal to SMALLER_PRODUCT_ATTRIBUTE_SET_ID
        defaultStockItemTempShouldNotBeFound("productAttributeSetId.lessThanOrEqual=" + SMALLER_PRODUCT_ATTRIBUTE_SET_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeSetIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeSetId is less than DEFAULT_PRODUCT_ATTRIBUTE_SET_ID
        defaultStockItemTempShouldNotBeFound("productAttributeSetId.lessThan=" + DEFAULT_PRODUCT_ATTRIBUTE_SET_ID);

        // Get all the stockItemTempList where productAttributeSetId is less than UPDATED_PRODUCT_ATTRIBUTE_SET_ID
        defaultStockItemTempShouldBeFound("productAttributeSetId.lessThan=" + UPDATED_PRODUCT_ATTRIBUTE_SET_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeSetIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeSetId is greater than DEFAULT_PRODUCT_ATTRIBUTE_SET_ID
        defaultStockItemTempShouldNotBeFound("productAttributeSetId.greaterThan=" + DEFAULT_PRODUCT_ATTRIBUTE_SET_ID);

        // Get all the stockItemTempList where productAttributeSetId is greater than SMALLER_PRODUCT_ATTRIBUTE_SET_ID
        defaultStockItemTempShouldBeFound("productAttributeSetId.greaterThan=" + SMALLER_PRODUCT_ATTRIBUTE_SET_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeId equals to DEFAULT_PRODUCT_ATTRIBUTE_ID
        defaultStockItemTempShouldBeFound("productAttributeId.equals=" + DEFAULT_PRODUCT_ATTRIBUTE_ID);

        // Get all the stockItemTempList where productAttributeId equals to UPDATED_PRODUCT_ATTRIBUTE_ID
        defaultStockItemTempShouldNotBeFound("productAttributeId.equals=" + UPDATED_PRODUCT_ATTRIBUTE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeId not equals to DEFAULT_PRODUCT_ATTRIBUTE_ID
        defaultStockItemTempShouldNotBeFound("productAttributeId.notEquals=" + DEFAULT_PRODUCT_ATTRIBUTE_ID);

        // Get all the stockItemTempList where productAttributeId not equals to UPDATED_PRODUCT_ATTRIBUTE_ID
        defaultStockItemTempShouldBeFound("productAttributeId.notEquals=" + UPDATED_PRODUCT_ATTRIBUTE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeId in DEFAULT_PRODUCT_ATTRIBUTE_ID or UPDATED_PRODUCT_ATTRIBUTE_ID
        defaultStockItemTempShouldBeFound("productAttributeId.in=" + DEFAULT_PRODUCT_ATTRIBUTE_ID + "," + UPDATED_PRODUCT_ATTRIBUTE_ID);

        // Get all the stockItemTempList where productAttributeId equals to UPDATED_PRODUCT_ATTRIBUTE_ID
        defaultStockItemTempShouldNotBeFound("productAttributeId.in=" + UPDATED_PRODUCT_ATTRIBUTE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeId is not null
        defaultStockItemTempShouldBeFound("productAttributeId.specified=true");

        // Get all the stockItemTempList where productAttributeId is null
        defaultStockItemTempShouldNotBeFound("productAttributeId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeId is greater than or equal to DEFAULT_PRODUCT_ATTRIBUTE_ID
        defaultStockItemTempShouldBeFound("productAttributeId.greaterThanOrEqual=" + DEFAULT_PRODUCT_ATTRIBUTE_ID);

        // Get all the stockItemTempList where productAttributeId is greater than or equal to UPDATED_PRODUCT_ATTRIBUTE_ID
        defaultStockItemTempShouldNotBeFound("productAttributeId.greaterThanOrEqual=" + UPDATED_PRODUCT_ATTRIBUTE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeId is less than or equal to DEFAULT_PRODUCT_ATTRIBUTE_ID
        defaultStockItemTempShouldBeFound("productAttributeId.lessThanOrEqual=" + DEFAULT_PRODUCT_ATTRIBUTE_ID);

        // Get all the stockItemTempList where productAttributeId is less than or equal to SMALLER_PRODUCT_ATTRIBUTE_ID
        defaultStockItemTempShouldNotBeFound("productAttributeId.lessThanOrEqual=" + SMALLER_PRODUCT_ATTRIBUTE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeId is less than DEFAULT_PRODUCT_ATTRIBUTE_ID
        defaultStockItemTempShouldNotBeFound("productAttributeId.lessThan=" + DEFAULT_PRODUCT_ATTRIBUTE_ID);

        // Get all the stockItemTempList where productAttributeId is less than UPDATED_PRODUCT_ATTRIBUTE_ID
        defaultStockItemTempShouldBeFound("productAttributeId.lessThan=" + UPDATED_PRODUCT_ATTRIBUTE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeId is greater than DEFAULT_PRODUCT_ATTRIBUTE_ID
        defaultStockItemTempShouldNotBeFound("productAttributeId.greaterThan=" + DEFAULT_PRODUCT_ATTRIBUTE_ID);

        // Get all the stockItemTempList where productAttributeId is greater than SMALLER_PRODUCT_ATTRIBUTE_ID
        defaultStockItemTempShouldBeFound("productAttributeId.greaterThan=" + SMALLER_PRODUCT_ATTRIBUTE_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeValueIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeValue equals to DEFAULT_PRODUCT_ATTRIBUTE_VALUE
        defaultStockItemTempShouldBeFound("productAttributeValue.equals=" + DEFAULT_PRODUCT_ATTRIBUTE_VALUE);

        // Get all the stockItemTempList where productAttributeValue equals to UPDATED_PRODUCT_ATTRIBUTE_VALUE
        defaultStockItemTempShouldNotBeFound("productAttributeValue.equals=" + UPDATED_PRODUCT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeValue not equals to DEFAULT_PRODUCT_ATTRIBUTE_VALUE
        defaultStockItemTempShouldNotBeFound("productAttributeValue.notEquals=" + DEFAULT_PRODUCT_ATTRIBUTE_VALUE);

        // Get all the stockItemTempList where productAttributeValue not equals to UPDATED_PRODUCT_ATTRIBUTE_VALUE
        defaultStockItemTempShouldBeFound("productAttributeValue.notEquals=" + UPDATED_PRODUCT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeValueIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeValue in DEFAULT_PRODUCT_ATTRIBUTE_VALUE or UPDATED_PRODUCT_ATTRIBUTE_VALUE
        defaultStockItemTempShouldBeFound("productAttributeValue.in=" + DEFAULT_PRODUCT_ATTRIBUTE_VALUE + "," + UPDATED_PRODUCT_ATTRIBUTE_VALUE);

        // Get all the stockItemTempList where productAttributeValue equals to UPDATED_PRODUCT_ATTRIBUTE_VALUE
        defaultStockItemTempShouldNotBeFound("productAttributeValue.in=" + UPDATED_PRODUCT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeValue is not null
        defaultStockItemTempShouldBeFound("productAttributeValue.specified=true");

        // Get all the stockItemTempList where productAttributeValue is null
        defaultStockItemTempShouldNotBeFound("productAttributeValue.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeValueContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeValue contains DEFAULT_PRODUCT_ATTRIBUTE_VALUE
        defaultStockItemTempShouldBeFound("productAttributeValue.contains=" + DEFAULT_PRODUCT_ATTRIBUTE_VALUE);

        // Get all the stockItemTempList where productAttributeValue contains UPDATED_PRODUCT_ATTRIBUTE_VALUE
        defaultStockItemTempShouldNotBeFound("productAttributeValue.contains=" + UPDATED_PRODUCT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductAttributeValueNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productAttributeValue does not contain DEFAULT_PRODUCT_ATTRIBUTE_VALUE
        defaultStockItemTempShouldNotBeFound("productAttributeValue.doesNotContain=" + DEFAULT_PRODUCT_ATTRIBUTE_VALUE);

        // Get all the stockItemTempList where productAttributeValue does not contain UPDATED_PRODUCT_ATTRIBUTE_VALUE
        defaultStockItemTempShouldBeFound("productAttributeValue.doesNotContain=" + UPDATED_PRODUCT_ATTRIBUTE_VALUE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionSetIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionSetId equals to DEFAULT_PRODUCT_OPTION_SET_ID
        defaultStockItemTempShouldBeFound("productOptionSetId.equals=" + DEFAULT_PRODUCT_OPTION_SET_ID);

        // Get all the stockItemTempList where productOptionSetId equals to UPDATED_PRODUCT_OPTION_SET_ID
        defaultStockItemTempShouldNotBeFound("productOptionSetId.equals=" + UPDATED_PRODUCT_OPTION_SET_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionSetIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionSetId not equals to DEFAULT_PRODUCT_OPTION_SET_ID
        defaultStockItemTempShouldNotBeFound("productOptionSetId.notEquals=" + DEFAULT_PRODUCT_OPTION_SET_ID);

        // Get all the stockItemTempList where productOptionSetId not equals to UPDATED_PRODUCT_OPTION_SET_ID
        defaultStockItemTempShouldBeFound("productOptionSetId.notEquals=" + UPDATED_PRODUCT_OPTION_SET_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionSetIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionSetId in DEFAULT_PRODUCT_OPTION_SET_ID or UPDATED_PRODUCT_OPTION_SET_ID
        defaultStockItemTempShouldBeFound("productOptionSetId.in=" + DEFAULT_PRODUCT_OPTION_SET_ID + "," + UPDATED_PRODUCT_OPTION_SET_ID);

        // Get all the stockItemTempList where productOptionSetId equals to UPDATED_PRODUCT_OPTION_SET_ID
        defaultStockItemTempShouldNotBeFound("productOptionSetId.in=" + UPDATED_PRODUCT_OPTION_SET_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionSetIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionSetId is not null
        defaultStockItemTempShouldBeFound("productOptionSetId.specified=true");

        // Get all the stockItemTempList where productOptionSetId is null
        defaultStockItemTempShouldNotBeFound("productOptionSetId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionSetIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionSetId is greater than or equal to DEFAULT_PRODUCT_OPTION_SET_ID
        defaultStockItemTempShouldBeFound("productOptionSetId.greaterThanOrEqual=" + DEFAULT_PRODUCT_OPTION_SET_ID);

        // Get all the stockItemTempList where productOptionSetId is greater than or equal to UPDATED_PRODUCT_OPTION_SET_ID
        defaultStockItemTempShouldNotBeFound("productOptionSetId.greaterThanOrEqual=" + UPDATED_PRODUCT_OPTION_SET_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionSetIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionSetId is less than or equal to DEFAULT_PRODUCT_OPTION_SET_ID
        defaultStockItemTempShouldBeFound("productOptionSetId.lessThanOrEqual=" + DEFAULT_PRODUCT_OPTION_SET_ID);

        // Get all the stockItemTempList where productOptionSetId is less than or equal to SMALLER_PRODUCT_OPTION_SET_ID
        defaultStockItemTempShouldNotBeFound("productOptionSetId.lessThanOrEqual=" + SMALLER_PRODUCT_OPTION_SET_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionSetIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionSetId is less than DEFAULT_PRODUCT_OPTION_SET_ID
        defaultStockItemTempShouldNotBeFound("productOptionSetId.lessThan=" + DEFAULT_PRODUCT_OPTION_SET_ID);

        // Get all the stockItemTempList where productOptionSetId is less than UPDATED_PRODUCT_OPTION_SET_ID
        defaultStockItemTempShouldBeFound("productOptionSetId.lessThan=" + UPDATED_PRODUCT_OPTION_SET_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionSetIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionSetId is greater than DEFAULT_PRODUCT_OPTION_SET_ID
        defaultStockItemTempShouldNotBeFound("productOptionSetId.greaterThan=" + DEFAULT_PRODUCT_OPTION_SET_ID);

        // Get all the stockItemTempList where productOptionSetId is greater than SMALLER_PRODUCT_OPTION_SET_ID
        defaultStockItemTempShouldBeFound("productOptionSetId.greaterThan=" + SMALLER_PRODUCT_OPTION_SET_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionId equals to DEFAULT_PRODUCT_OPTION_ID
        defaultStockItemTempShouldBeFound("productOptionId.equals=" + DEFAULT_PRODUCT_OPTION_ID);

        // Get all the stockItemTempList where productOptionId equals to UPDATED_PRODUCT_OPTION_ID
        defaultStockItemTempShouldNotBeFound("productOptionId.equals=" + UPDATED_PRODUCT_OPTION_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionId not equals to DEFAULT_PRODUCT_OPTION_ID
        defaultStockItemTempShouldNotBeFound("productOptionId.notEquals=" + DEFAULT_PRODUCT_OPTION_ID);

        // Get all the stockItemTempList where productOptionId not equals to UPDATED_PRODUCT_OPTION_ID
        defaultStockItemTempShouldBeFound("productOptionId.notEquals=" + UPDATED_PRODUCT_OPTION_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionId in DEFAULT_PRODUCT_OPTION_ID or UPDATED_PRODUCT_OPTION_ID
        defaultStockItemTempShouldBeFound("productOptionId.in=" + DEFAULT_PRODUCT_OPTION_ID + "," + UPDATED_PRODUCT_OPTION_ID);

        // Get all the stockItemTempList where productOptionId equals to UPDATED_PRODUCT_OPTION_ID
        defaultStockItemTempShouldNotBeFound("productOptionId.in=" + UPDATED_PRODUCT_OPTION_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionId is not null
        defaultStockItemTempShouldBeFound("productOptionId.specified=true");

        // Get all the stockItemTempList where productOptionId is null
        defaultStockItemTempShouldNotBeFound("productOptionId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionId is greater than or equal to DEFAULT_PRODUCT_OPTION_ID
        defaultStockItemTempShouldBeFound("productOptionId.greaterThanOrEqual=" + DEFAULT_PRODUCT_OPTION_ID);

        // Get all the stockItemTempList where productOptionId is greater than or equal to UPDATED_PRODUCT_OPTION_ID
        defaultStockItemTempShouldNotBeFound("productOptionId.greaterThanOrEqual=" + UPDATED_PRODUCT_OPTION_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionId is less than or equal to DEFAULT_PRODUCT_OPTION_ID
        defaultStockItemTempShouldBeFound("productOptionId.lessThanOrEqual=" + DEFAULT_PRODUCT_OPTION_ID);

        // Get all the stockItemTempList where productOptionId is less than or equal to SMALLER_PRODUCT_OPTION_ID
        defaultStockItemTempShouldNotBeFound("productOptionId.lessThanOrEqual=" + SMALLER_PRODUCT_OPTION_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionId is less than DEFAULT_PRODUCT_OPTION_ID
        defaultStockItemTempShouldNotBeFound("productOptionId.lessThan=" + DEFAULT_PRODUCT_OPTION_ID);

        // Get all the stockItemTempList where productOptionId is less than UPDATED_PRODUCT_OPTION_ID
        defaultStockItemTempShouldBeFound("productOptionId.lessThan=" + UPDATED_PRODUCT_OPTION_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionId is greater than DEFAULT_PRODUCT_OPTION_ID
        defaultStockItemTempShouldNotBeFound("productOptionId.greaterThan=" + DEFAULT_PRODUCT_OPTION_ID);

        // Get all the stockItemTempList where productOptionId is greater than SMALLER_PRODUCT_OPTION_ID
        defaultStockItemTempShouldBeFound("productOptionId.greaterThan=" + SMALLER_PRODUCT_OPTION_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionValueIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionValue equals to DEFAULT_PRODUCT_OPTION_VALUE
        defaultStockItemTempShouldBeFound("productOptionValue.equals=" + DEFAULT_PRODUCT_OPTION_VALUE);

        // Get all the stockItemTempList where productOptionValue equals to UPDATED_PRODUCT_OPTION_VALUE
        defaultStockItemTempShouldNotBeFound("productOptionValue.equals=" + UPDATED_PRODUCT_OPTION_VALUE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionValue not equals to DEFAULT_PRODUCT_OPTION_VALUE
        defaultStockItemTempShouldNotBeFound("productOptionValue.notEquals=" + DEFAULT_PRODUCT_OPTION_VALUE);

        // Get all the stockItemTempList where productOptionValue not equals to UPDATED_PRODUCT_OPTION_VALUE
        defaultStockItemTempShouldBeFound("productOptionValue.notEquals=" + UPDATED_PRODUCT_OPTION_VALUE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionValueIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionValue in DEFAULT_PRODUCT_OPTION_VALUE or UPDATED_PRODUCT_OPTION_VALUE
        defaultStockItemTempShouldBeFound("productOptionValue.in=" + DEFAULT_PRODUCT_OPTION_VALUE + "," + UPDATED_PRODUCT_OPTION_VALUE);

        // Get all the stockItemTempList where productOptionValue equals to UPDATED_PRODUCT_OPTION_VALUE
        defaultStockItemTempShouldNotBeFound("productOptionValue.in=" + UPDATED_PRODUCT_OPTION_VALUE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionValue is not null
        defaultStockItemTempShouldBeFound("productOptionValue.specified=true");

        // Get all the stockItemTempList where productOptionValue is null
        defaultStockItemTempShouldNotBeFound("productOptionValue.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionValueContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionValue contains DEFAULT_PRODUCT_OPTION_VALUE
        defaultStockItemTempShouldBeFound("productOptionValue.contains=" + DEFAULT_PRODUCT_OPTION_VALUE);

        // Get all the stockItemTempList where productOptionValue contains UPDATED_PRODUCT_OPTION_VALUE
        defaultStockItemTempShouldNotBeFound("productOptionValue.contains=" + UPDATED_PRODUCT_OPTION_VALUE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductOptionValueNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productOptionValue does not contain DEFAULT_PRODUCT_OPTION_VALUE
        defaultStockItemTempShouldNotBeFound("productOptionValue.doesNotContain=" + DEFAULT_PRODUCT_OPTION_VALUE);

        // Get all the stockItemTempList where productOptionValue does not contain UPDATED_PRODUCT_OPTION_VALUE
        defaultStockItemTempShouldBeFound("productOptionValue.doesNotContain=" + UPDATED_PRODUCT_OPTION_VALUE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByModelNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where modelName equals to DEFAULT_MODEL_NAME
        defaultStockItemTempShouldBeFound("modelName.equals=" + DEFAULT_MODEL_NAME);

        // Get all the stockItemTempList where modelName equals to UPDATED_MODEL_NAME
        defaultStockItemTempShouldNotBeFound("modelName.equals=" + UPDATED_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByModelNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where modelName not equals to DEFAULT_MODEL_NAME
        defaultStockItemTempShouldNotBeFound("modelName.notEquals=" + DEFAULT_MODEL_NAME);

        // Get all the stockItemTempList where modelName not equals to UPDATED_MODEL_NAME
        defaultStockItemTempShouldBeFound("modelName.notEquals=" + UPDATED_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByModelNameIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where modelName in DEFAULT_MODEL_NAME or UPDATED_MODEL_NAME
        defaultStockItemTempShouldBeFound("modelName.in=" + DEFAULT_MODEL_NAME + "," + UPDATED_MODEL_NAME);

        // Get all the stockItemTempList where modelName equals to UPDATED_MODEL_NAME
        defaultStockItemTempShouldNotBeFound("modelName.in=" + UPDATED_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByModelNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where modelName is not null
        defaultStockItemTempShouldBeFound("modelName.specified=true");

        // Get all the stockItemTempList where modelName is null
        defaultStockItemTempShouldNotBeFound("modelName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByModelNameContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where modelName contains DEFAULT_MODEL_NAME
        defaultStockItemTempShouldBeFound("modelName.contains=" + DEFAULT_MODEL_NAME);

        // Get all the stockItemTempList where modelName contains UPDATED_MODEL_NAME
        defaultStockItemTempShouldNotBeFound("modelName.contains=" + UPDATED_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByModelNameNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where modelName does not contain DEFAULT_MODEL_NAME
        defaultStockItemTempShouldNotBeFound("modelName.doesNotContain=" + DEFAULT_MODEL_NAME);

        // Get all the stockItemTempList where modelName does not contain UPDATED_MODEL_NAME
        defaultStockItemTempShouldBeFound("modelName.doesNotContain=" + UPDATED_MODEL_NAME);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByModelNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where modelNumber equals to DEFAULT_MODEL_NUMBER
        defaultStockItemTempShouldBeFound("modelNumber.equals=" + DEFAULT_MODEL_NUMBER);

        // Get all the stockItemTempList where modelNumber equals to UPDATED_MODEL_NUMBER
        defaultStockItemTempShouldNotBeFound("modelNumber.equals=" + UPDATED_MODEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByModelNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where modelNumber not equals to DEFAULT_MODEL_NUMBER
        defaultStockItemTempShouldNotBeFound("modelNumber.notEquals=" + DEFAULT_MODEL_NUMBER);

        // Get all the stockItemTempList where modelNumber not equals to UPDATED_MODEL_NUMBER
        defaultStockItemTempShouldBeFound("modelNumber.notEquals=" + UPDATED_MODEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByModelNumberIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where modelNumber in DEFAULT_MODEL_NUMBER or UPDATED_MODEL_NUMBER
        defaultStockItemTempShouldBeFound("modelNumber.in=" + DEFAULT_MODEL_NUMBER + "," + UPDATED_MODEL_NUMBER);

        // Get all the stockItemTempList where modelNumber equals to UPDATED_MODEL_NUMBER
        defaultStockItemTempShouldNotBeFound("modelNumber.in=" + UPDATED_MODEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByModelNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where modelNumber is not null
        defaultStockItemTempShouldBeFound("modelNumber.specified=true");

        // Get all the stockItemTempList where modelNumber is null
        defaultStockItemTempShouldNotBeFound("modelNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByModelNumberContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where modelNumber contains DEFAULT_MODEL_NUMBER
        defaultStockItemTempShouldBeFound("modelNumber.contains=" + DEFAULT_MODEL_NUMBER);

        // Get all the stockItemTempList where modelNumber contains UPDATED_MODEL_NUMBER
        defaultStockItemTempShouldNotBeFound("modelNumber.contains=" + UPDATED_MODEL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByModelNumberNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where modelNumber does not contain DEFAULT_MODEL_NUMBER
        defaultStockItemTempShouldNotBeFound("modelNumber.doesNotContain=" + DEFAULT_MODEL_NUMBER);

        // Get all the stockItemTempList where modelNumber does not contain UPDATED_MODEL_NUMBER
        defaultStockItemTempShouldBeFound("modelNumber.doesNotContain=" + UPDATED_MODEL_NUMBER);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByMaterialIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where materialId equals to DEFAULT_MATERIAL_ID
        defaultStockItemTempShouldBeFound("materialId.equals=" + DEFAULT_MATERIAL_ID);

        // Get all the stockItemTempList where materialId equals to UPDATED_MATERIAL_ID
        defaultStockItemTempShouldNotBeFound("materialId.equals=" + UPDATED_MATERIAL_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByMaterialIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where materialId not equals to DEFAULT_MATERIAL_ID
        defaultStockItemTempShouldNotBeFound("materialId.notEquals=" + DEFAULT_MATERIAL_ID);

        // Get all the stockItemTempList where materialId not equals to UPDATED_MATERIAL_ID
        defaultStockItemTempShouldBeFound("materialId.notEquals=" + UPDATED_MATERIAL_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByMaterialIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where materialId in DEFAULT_MATERIAL_ID or UPDATED_MATERIAL_ID
        defaultStockItemTempShouldBeFound("materialId.in=" + DEFAULT_MATERIAL_ID + "," + UPDATED_MATERIAL_ID);

        // Get all the stockItemTempList where materialId equals to UPDATED_MATERIAL_ID
        defaultStockItemTempShouldNotBeFound("materialId.in=" + UPDATED_MATERIAL_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByMaterialIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where materialId is not null
        defaultStockItemTempShouldBeFound("materialId.specified=true");

        // Get all the stockItemTempList where materialId is null
        defaultStockItemTempShouldNotBeFound("materialId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByMaterialIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where materialId is greater than or equal to DEFAULT_MATERIAL_ID
        defaultStockItemTempShouldBeFound("materialId.greaterThanOrEqual=" + DEFAULT_MATERIAL_ID);

        // Get all the stockItemTempList where materialId is greater than or equal to UPDATED_MATERIAL_ID
        defaultStockItemTempShouldNotBeFound("materialId.greaterThanOrEqual=" + UPDATED_MATERIAL_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByMaterialIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where materialId is less than or equal to DEFAULT_MATERIAL_ID
        defaultStockItemTempShouldBeFound("materialId.lessThanOrEqual=" + DEFAULT_MATERIAL_ID);

        // Get all the stockItemTempList where materialId is less than or equal to SMALLER_MATERIAL_ID
        defaultStockItemTempShouldNotBeFound("materialId.lessThanOrEqual=" + SMALLER_MATERIAL_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByMaterialIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where materialId is less than DEFAULT_MATERIAL_ID
        defaultStockItemTempShouldNotBeFound("materialId.lessThan=" + DEFAULT_MATERIAL_ID);

        // Get all the stockItemTempList where materialId is less than UPDATED_MATERIAL_ID
        defaultStockItemTempShouldBeFound("materialId.lessThan=" + UPDATED_MATERIAL_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByMaterialIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where materialId is greater than DEFAULT_MATERIAL_ID
        defaultStockItemTempShouldNotBeFound("materialId.greaterThan=" + DEFAULT_MATERIAL_ID);

        // Get all the stockItemTempList where materialId is greater than SMALLER_MATERIAL_ID
        defaultStockItemTempShouldBeFound("materialId.greaterThan=" + SMALLER_MATERIAL_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByMaterialNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where materialName equals to DEFAULT_MATERIAL_NAME
        defaultStockItemTempShouldBeFound("materialName.equals=" + DEFAULT_MATERIAL_NAME);

        // Get all the stockItemTempList where materialName equals to UPDATED_MATERIAL_NAME
        defaultStockItemTempShouldNotBeFound("materialName.equals=" + UPDATED_MATERIAL_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByMaterialNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where materialName not equals to DEFAULT_MATERIAL_NAME
        defaultStockItemTempShouldNotBeFound("materialName.notEquals=" + DEFAULT_MATERIAL_NAME);

        // Get all the stockItemTempList where materialName not equals to UPDATED_MATERIAL_NAME
        defaultStockItemTempShouldBeFound("materialName.notEquals=" + UPDATED_MATERIAL_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByMaterialNameIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where materialName in DEFAULT_MATERIAL_NAME or UPDATED_MATERIAL_NAME
        defaultStockItemTempShouldBeFound("materialName.in=" + DEFAULT_MATERIAL_NAME + "," + UPDATED_MATERIAL_NAME);

        // Get all the stockItemTempList where materialName equals to UPDATED_MATERIAL_NAME
        defaultStockItemTempShouldNotBeFound("materialName.in=" + UPDATED_MATERIAL_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByMaterialNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where materialName is not null
        defaultStockItemTempShouldBeFound("materialName.specified=true");

        // Get all the stockItemTempList where materialName is null
        defaultStockItemTempShouldNotBeFound("materialName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByMaterialNameContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where materialName contains DEFAULT_MATERIAL_NAME
        defaultStockItemTempShouldBeFound("materialName.contains=" + DEFAULT_MATERIAL_NAME);

        // Get all the stockItemTempList where materialName contains UPDATED_MATERIAL_NAME
        defaultStockItemTempShouldNotBeFound("materialName.contains=" + UPDATED_MATERIAL_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByMaterialNameNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where materialName does not contain DEFAULT_MATERIAL_NAME
        defaultStockItemTempShouldNotBeFound("materialName.doesNotContain=" + DEFAULT_MATERIAL_NAME);

        // Get all the stockItemTempList where materialName does not contain UPDATED_MATERIAL_NAME
        defaultStockItemTempShouldBeFound("materialName.doesNotContain=" + UPDATED_MATERIAL_NAME);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByProductBrandIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productBrandId equals to DEFAULT_PRODUCT_BRAND_ID
        defaultStockItemTempShouldBeFound("productBrandId.equals=" + DEFAULT_PRODUCT_BRAND_ID);

        // Get all the stockItemTempList where productBrandId equals to UPDATED_PRODUCT_BRAND_ID
        defaultStockItemTempShouldNotBeFound("productBrandId.equals=" + UPDATED_PRODUCT_BRAND_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductBrandIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productBrandId not equals to DEFAULT_PRODUCT_BRAND_ID
        defaultStockItemTempShouldNotBeFound("productBrandId.notEquals=" + DEFAULT_PRODUCT_BRAND_ID);

        // Get all the stockItemTempList where productBrandId not equals to UPDATED_PRODUCT_BRAND_ID
        defaultStockItemTempShouldBeFound("productBrandId.notEquals=" + UPDATED_PRODUCT_BRAND_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductBrandIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productBrandId in DEFAULT_PRODUCT_BRAND_ID or UPDATED_PRODUCT_BRAND_ID
        defaultStockItemTempShouldBeFound("productBrandId.in=" + DEFAULT_PRODUCT_BRAND_ID + "," + UPDATED_PRODUCT_BRAND_ID);

        // Get all the stockItemTempList where productBrandId equals to UPDATED_PRODUCT_BRAND_ID
        defaultStockItemTempShouldNotBeFound("productBrandId.in=" + UPDATED_PRODUCT_BRAND_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductBrandIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productBrandId is not null
        defaultStockItemTempShouldBeFound("productBrandId.specified=true");

        // Get all the stockItemTempList where productBrandId is null
        defaultStockItemTempShouldNotBeFound("productBrandId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductBrandIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productBrandId is greater than or equal to DEFAULT_PRODUCT_BRAND_ID
        defaultStockItemTempShouldBeFound("productBrandId.greaterThanOrEqual=" + DEFAULT_PRODUCT_BRAND_ID);

        // Get all the stockItemTempList where productBrandId is greater than or equal to UPDATED_PRODUCT_BRAND_ID
        defaultStockItemTempShouldNotBeFound("productBrandId.greaterThanOrEqual=" + UPDATED_PRODUCT_BRAND_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductBrandIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productBrandId is less than or equal to DEFAULT_PRODUCT_BRAND_ID
        defaultStockItemTempShouldBeFound("productBrandId.lessThanOrEqual=" + DEFAULT_PRODUCT_BRAND_ID);

        // Get all the stockItemTempList where productBrandId is less than or equal to SMALLER_PRODUCT_BRAND_ID
        defaultStockItemTempShouldNotBeFound("productBrandId.lessThanOrEqual=" + SMALLER_PRODUCT_BRAND_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductBrandIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productBrandId is less than DEFAULT_PRODUCT_BRAND_ID
        defaultStockItemTempShouldNotBeFound("productBrandId.lessThan=" + DEFAULT_PRODUCT_BRAND_ID);

        // Get all the stockItemTempList where productBrandId is less than UPDATED_PRODUCT_BRAND_ID
        defaultStockItemTempShouldBeFound("productBrandId.lessThan=" + UPDATED_PRODUCT_BRAND_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductBrandIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productBrandId is greater than DEFAULT_PRODUCT_BRAND_ID
        defaultStockItemTempShouldNotBeFound("productBrandId.greaterThan=" + DEFAULT_PRODUCT_BRAND_ID);

        // Get all the stockItemTempList where productBrandId is greater than SMALLER_PRODUCT_BRAND_ID
        defaultStockItemTempShouldBeFound("productBrandId.greaterThan=" + SMALLER_PRODUCT_BRAND_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByProductBrandNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productBrandName equals to DEFAULT_PRODUCT_BRAND_NAME
        defaultStockItemTempShouldBeFound("productBrandName.equals=" + DEFAULT_PRODUCT_BRAND_NAME);

        // Get all the stockItemTempList where productBrandName equals to UPDATED_PRODUCT_BRAND_NAME
        defaultStockItemTempShouldNotBeFound("productBrandName.equals=" + UPDATED_PRODUCT_BRAND_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductBrandNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productBrandName not equals to DEFAULT_PRODUCT_BRAND_NAME
        defaultStockItemTempShouldNotBeFound("productBrandName.notEquals=" + DEFAULT_PRODUCT_BRAND_NAME);

        // Get all the stockItemTempList where productBrandName not equals to UPDATED_PRODUCT_BRAND_NAME
        defaultStockItemTempShouldBeFound("productBrandName.notEquals=" + UPDATED_PRODUCT_BRAND_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductBrandNameIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productBrandName in DEFAULT_PRODUCT_BRAND_NAME or UPDATED_PRODUCT_BRAND_NAME
        defaultStockItemTempShouldBeFound("productBrandName.in=" + DEFAULT_PRODUCT_BRAND_NAME + "," + UPDATED_PRODUCT_BRAND_NAME);

        // Get all the stockItemTempList where productBrandName equals to UPDATED_PRODUCT_BRAND_NAME
        defaultStockItemTempShouldNotBeFound("productBrandName.in=" + UPDATED_PRODUCT_BRAND_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductBrandNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productBrandName is not null
        defaultStockItemTempShouldBeFound("productBrandName.specified=true");

        // Get all the stockItemTempList where productBrandName is null
        defaultStockItemTempShouldNotBeFound("productBrandName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByProductBrandNameContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productBrandName contains DEFAULT_PRODUCT_BRAND_NAME
        defaultStockItemTempShouldBeFound("productBrandName.contains=" + DEFAULT_PRODUCT_BRAND_NAME);

        // Get all the stockItemTempList where productBrandName contains UPDATED_PRODUCT_BRAND_NAME
        defaultStockItemTempShouldNotBeFound("productBrandName.contains=" + UPDATED_PRODUCT_BRAND_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductBrandNameNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productBrandName does not contain DEFAULT_PRODUCT_BRAND_NAME
        defaultStockItemTempShouldNotBeFound("productBrandName.doesNotContain=" + DEFAULT_PRODUCT_BRAND_NAME);

        // Get all the stockItemTempList where productBrandName does not contain UPDATED_PRODUCT_BRAND_NAME
        defaultStockItemTempShouldBeFound("productBrandName.doesNotContain=" + UPDATED_PRODUCT_BRAND_NAME);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByDangerousGoodsIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where dangerousGoods equals to DEFAULT_DANGEROUS_GOODS
        defaultStockItemTempShouldBeFound("dangerousGoods.equals=" + DEFAULT_DANGEROUS_GOODS);

        // Get all the stockItemTempList where dangerousGoods equals to UPDATED_DANGEROUS_GOODS
        defaultStockItemTempShouldNotBeFound("dangerousGoods.equals=" + UPDATED_DANGEROUS_GOODS);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByDangerousGoodsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where dangerousGoods not equals to DEFAULT_DANGEROUS_GOODS
        defaultStockItemTempShouldNotBeFound("dangerousGoods.notEquals=" + DEFAULT_DANGEROUS_GOODS);

        // Get all the stockItemTempList where dangerousGoods not equals to UPDATED_DANGEROUS_GOODS
        defaultStockItemTempShouldBeFound("dangerousGoods.notEquals=" + UPDATED_DANGEROUS_GOODS);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByDangerousGoodsIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where dangerousGoods in DEFAULT_DANGEROUS_GOODS or UPDATED_DANGEROUS_GOODS
        defaultStockItemTempShouldBeFound("dangerousGoods.in=" + DEFAULT_DANGEROUS_GOODS + "," + UPDATED_DANGEROUS_GOODS);

        // Get all the stockItemTempList where dangerousGoods equals to UPDATED_DANGEROUS_GOODS
        defaultStockItemTempShouldNotBeFound("dangerousGoods.in=" + UPDATED_DANGEROUS_GOODS);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByDangerousGoodsIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where dangerousGoods is not null
        defaultStockItemTempShouldBeFound("dangerousGoods.specified=true");

        // Get all the stockItemTempList where dangerousGoods is null
        defaultStockItemTempShouldNotBeFound("dangerousGoods.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByDangerousGoodsContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where dangerousGoods contains DEFAULT_DANGEROUS_GOODS
        defaultStockItemTempShouldBeFound("dangerousGoods.contains=" + DEFAULT_DANGEROUS_GOODS);

        // Get all the stockItemTempList where dangerousGoods contains UPDATED_DANGEROUS_GOODS
        defaultStockItemTempShouldNotBeFound("dangerousGoods.contains=" + UPDATED_DANGEROUS_GOODS);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByDangerousGoodsNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where dangerousGoods does not contain DEFAULT_DANGEROUS_GOODS
        defaultStockItemTempShouldNotBeFound("dangerousGoods.doesNotContain=" + DEFAULT_DANGEROUS_GOODS);

        // Get all the stockItemTempList where dangerousGoods does not contain UPDATED_DANGEROUS_GOODS
        defaultStockItemTempShouldBeFound("dangerousGoods.doesNotContain=" + UPDATED_DANGEROUS_GOODS);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByVideoUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where videoUrl equals to DEFAULT_VIDEO_URL
        defaultStockItemTempShouldBeFound("videoUrl.equals=" + DEFAULT_VIDEO_URL);

        // Get all the stockItemTempList where videoUrl equals to UPDATED_VIDEO_URL
        defaultStockItemTempShouldNotBeFound("videoUrl.equals=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByVideoUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where videoUrl not equals to DEFAULT_VIDEO_URL
        defaultStockItemTempShouldNotBeFound("videoUrl.notEquals=" + DEFAULT_VIDEO_URL);

        // Get all the stockItemTempList where videoUrl not equals to UPDATED_VIDEO_URL
        defaultStockItemTempShouldBeFound("videoUrl.notEquals=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByVideoUrlIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where videoUrl in DEFAULT_VIDEO_URL or UPDATED_VIDEO_URL
        defaultStockItemTempShouldBeFound("videoUrl.in=" + DEFAULT_VIDEO_URL + "," + UPDATED_VIDEO_URL);

        // Get all the stockItemTempList where videoUrl equals to UPDATED_VIDEO_URL
        defaultStockItemTempShouldNotBeFound("videoUrl.in=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByVideoUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where videoUrl is not null
        defaultStockItemTempShouldBeFound("videoUrl.specified=true");

        // Get all the stockItemTempList where videoUrl is null
        defaultStockItemTempShouldNotBeFound("videoUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByVideoUrlContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where videoUrl contains DEFAULT_VIDEO_URL
        defaultStockItemTempShouldBeFound("videoUrl.contains=" + DEFAULT_VIDEO_URL);

        // Get all the stockItemTempList where videoUrl contains UPDATED_VIDEO_URL
        defaultStockItemTempShouldNotBeFound("videoUrl.contains=" + UPDATED_VIDEO_URL);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByVideoUrlNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where videoUrl does not contain DEFAULT_VIDEO_URL
        defaultStockItemTempShouldNotBeFound("videoUrl.doesNotContain=" + DEFAULT_VIDEO_URL);

        // Get all the stockItemTempList where videoUrl does not contain UPDATED_VIDEO_URL
        defaultStockItemTempShouldBeFound("videoUrl.doesNotContain=" + UPDATED_VIDEO_URL);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where unitPrice equals to DEFAULT_UNIT_PRICE
        defaultStockItemTempShouldBeFound("unitPrice.equals=" + DEFAULT_UNIT_PRICE);

        // Get all the stockItemTempList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultStockItemTempShouldNotBeFound("unitPrice.equals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByUnitPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where unitPrice not equals to DEFAULT_UNIT_PRICE
        defaultStockItemTempShouldNotBeFound("unitPrice.notEquals=" + DEFAULT_UNIT_PRICE);

        // Get all the stockItemTempList where unitPrice not equals to UPDATED_UNIT_PRICE
        defaultStockItemTempShouldBeFound("unitPrice.notEquals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where unitPrice in DEFAULT_UNIT_PRICE or UPDATED_UNIT_PRICE
        defaultStockItemTempShouldBeFound("unitPrice.in=" + DEFAULT_UNIT_PRICE + "," + UPDATED_UNIT_PRICE);

        // Get all the stockItemTempList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultStockItemTempShouldNotBeFound("unitPrice.in=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where unitPrice is not null
        defaultStockItemTempShouldBeFound("unitPrice.specified=true");

        // Get all the stockItemTempList where unitPrice is null
        defaultStockItemTempShouldNotBeFound("unitPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByUnitPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where unitPrice is greater than or equal to DEFAULT_UNIT_PRICE
        defaultStockItemTempShouldBeFound("unitPrice.greaterThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the stockItemTempList where unitPrice is greater than or equal to UPDATED_UNIT_PRICE
        defaultStockItemTempShouldNotBeFound("unitPrice.greaterThanOrEqual=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByUnitPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where unitPrice is less than or equal to DEFAULT_UNIT_PRICE
        defaultStockItemTempShouldBeFound("unitPrice.lessThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the stockItemTempList where unitPrice is less than or equal to SMALLER_UNIT_PRICE
        defaultStockItemTempShouldNotBeFound("unitPrice.lessThanOrEqual=" + SMALLER_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByUnitPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where unitPrice is less than DEFAULT_UNIT_PRICE
        defaultStockItemTempShouldNotBeFound("unitPrice.lessThan=" + DEFAULT_UNIT_PRICE);

        // Get all the stockItemTempList where unitPrice is less than UPDATED_UNIT_PRICE
        defaultStockItemTempShouldBeFound("unitPrice.lessThan=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByUnitPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where unitPrice is greater than DEFAULT_UNIT_PRICE
        defaultStockItemTempShouldNotBeFound("unitPrice.greaterThan=" + DEFAULT_UNIT_PRICE);

        // Get all the stockItemTempList where unitPrice is greater than SMALLER_UNIT_PRICE
        defaultStockItemTempShouldBeFound("unitPrice.greaterThan=" + SMALLER_UNIT_PRICE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByRemommendedRetailPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where remommendedRetailPrice equals to DEFAULT_REMOMMENDED_RETAIL_PRICE
        defaultStockItemTempShouldBeFound("remommendedRetailPrice.equals=" + DEFAULT_REMOMMENDED_RETAIL_PRICE);

        // Get all the stockItemTempList where remommendedRetailPrice equals to UPDATED_REMOMMENDED_RETAIL_PRICE
        defaultStockItemTempShouldNotBeFound("remommendedRetailPrice.equals=" + UPDATED_REMOMMENDED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByRemommendedRetailPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where remommendedRetailPrice not equals to DEFAULT_REMOMMENDED_RETAIL_PRICE
        defaultStockItemTempShouldNotBeFound("remommendedRetailPrice.notEquals=" + DEFAULT_REMOMMENDED_RETAIL_PRICE);

        // Get all the stockItemTempList where remommendedRetailPrice not equals to UPDATED_REMOMMENDED_RETAIL_PRICE
        defaultStockItemTempShouldBeFound("remommendedRetailPrice.notEquals=" + UPDATED_REMOMMENDED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByRemommendedRetailPriceIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where remommendedRetailPrice in DEFAULT_REMOMMENDED_RETAIL_PRICE or UPDATED_REMOMMENDED_RETAIL_PRICE
        defaultStockItemTempShouldBeFound("remommendedRetailPrice.in=" + DEFAULT_REMOMMENDED_RETAIL_PRICE + "," + UPDATED_REMOMMENDED_RETAIL_PRICE);

        // Get all the stockItemTempList where remommendedRetailPrice equals to UPDATED_REMOMMENDED_RETAIL_PRICE
        defaultStockItemTempShouldNotBeFound("remommendedRetailPrice.in=" + UPDATED_REMOMMENDED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByRemommendedRetailPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where remommendedRetailPrice is not null
        defaultStockItemTempShouldBeFound("remommendedRetailPrice.specified=true");

        // Get all the stockItemTempList where remommendedRetailPrice is null
        defaultStockItemTempShouldNotBeFound("remommendedRetailPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByRemommendedRetailPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where remommendedRetailPrice is greater than or equal to DEFAULT_REMOMMENDED_RETAIL_PRICE
        defaultStockItemTempShouldBeFound("remommendedRetailPrice.greaterThanOrEqual=" + DEFAULT_REMOMMENDED_RETAIL_PRICE);

        // Get all the stockItemTempList where remommendedRetailPrice is greater than or equal to UPDATED_REMOMMENDED_RETAIL_PRICE
        defaultStockItemTempShouldNotBeFound("remommendedRetailPrice.greaterThanOrEqual=" + UPDATED_REMOMMENDED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByRemommendedRetailPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where remommendedRetailPrice is less than or equal to DEFAULT_REMOMMENDED_RETAIL_PRICE
        defaultStockItemTempShouldBeFound("remommendedRetailPrice.lessThanOrEqual=" + DEFAULT_REMOMMENDED_RETAIL_PRICE);

        // Get all the stockItemTempList where remommendedRetailPrice is less than or equal to SMALLER_REMOMMENDED_RETAIL_PRICE
        defaultStockItemTempShouldNotBeFound("remommendedRetailPrice.lessThanOrEqual=" + SMALLER_REMOMMENDED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByRemommendedRetailPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where remommendedRetailPrice is less than DEFAULT_REMOMMENDED_RETAIL_PRICE
        defaultStockItemTempShouldNotBeFound("remommendedRetailPrice.lessThan=" + DEFAULT_REMOMMENDED_RETAIL_PRICE);

        // Get all the stockItemTempList where remommendedRetailPrice is less than UPDATED_REMOMMENDED_RETAIL_PRICE
        defaultStockItemTempShouldBeFound("remommendedRetailPrice.lessThan=" + UPDATED_REMOMMENDED_RETAIL_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByRemommendedRetailPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where remommendedRetailPrice is greater than DEFAULT_REMOMMENDED_RETAIL_PRICE
        defaultStockItemTempShouldNotBeFound("remommendedRetailPrice.greaterThan=" + DEFAULT_REMOMMENDED_RETAIL_PRICE);

        // Get all the stockItemTempList where remommendedRetailPrice is greater than SMALLER_REMOMMENDED_RETAIL_PRICE
        defaultStockItemTempShouldBeFound("remommendedRetailPrice.greaterThan=" + SMALLER_REMOMMENDED_RETAIL_PRICE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByCurrencyCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where currencyCode equals to DEFAULT_CURRENCY_CODE
        defaultStockItemTempShouldBeFound("currencyCode.equals=" + DEFAULT_CURRENCY_CODE);

        // Get all the stockItemTempList where currencyCode equals to UPDATED_CURRENCY_CODE
        defaultStockItemTempShouldNotBeFound("currencyCode.equals=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByCurrencyCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where currencyCode not equals to DEFAULT_CURRENCY_CODE
        defaultStockItemTempShouldNotBeFound("currencyCode.notEquals=" + DEFAULT_CURRENCY_CODE);

        // Get all the stockItemTempList where currencyCode not equals to UPDATED_CURRENCY_CODE
        defaultStockItemTempShouldBeFound("currencyCode.notEquals=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByCurrencyCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where currencyCode in DEFAULT_CURRENCY_CODE or UPDATED_CURRENCY_CODE
        defaultStockItemTempShouldBeFound("currencyCode.in=" + DEFAULT_CURRENCY_CODE + "," + UPDATED_CURRENCY_CODE);

        // Get all the stockItemTempList where currencyCode equals to UPDATED_CURRENCY_CODE
        defaultStockItemTempShouldNotBeFound("currencyCode.in=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByCurrencyCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where currencyCode is not null
        defaultStockItemTempShouldBeFound("currencyCode.specified=true");

        // Get all the stockItemTempList where currencyCode is null
        defaultStockItemTempShouldNotBeFound("currencyCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByCurrencyCodeContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where currencyCode contains DEFAULT_CURRENCY_CODE
        defaultStockItemTempShouldBeFound("currencyCode.contains=" + DEFAULT_CURRENCY_CODE);

        // Get all the stockItemTempList where currencyCode contains UPDATED_CURRENCY_CODE
        defaultStockItemTempShouldNotBeFound("currencyCode.contains=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByCurrencyCodeNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where currencyCode does not contain DEFAULT_CURRENCY_CODE
        defaultStockItemTempShouldNotBeFound("currencyCode.doesNotContain=" + DEFAULT_CURRENCY_CODE);

        // Get all the stockItemTempList where currencyCode does not contain UPDATED_CURRENCY_CODE
        defaultStockItemTempShouldBeFound("currencyCode.doesNotContain=" + UPDATED_CURRENCY_CODE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByQuantityOnHandIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where quantityOnHand equals to DEFAULT_QUANTITY_ON_HAND
        defaultStockItemTempShouldBeFound("quantityOnHand.equals=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemTempList where quantityOnHand equals to UPDATED_QUANTITY_ON_HAND
        defaultStockItemTempShouldNotBeFound("quantityOnHand.equals=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByQuantityOnHandIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where quantityOnHand not equals to DEFAULT_QUANTITY_ON_HAND
        defaultStockItemTempShouldNotBeFound("quantityOnHand.notEquals=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemTempList where quantityOnHand not equals to UPDATED_QUANTITY_ON_HAND
        defaultStockItemTempShouldBeFound("quantityOnHand.notEquals=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByQuantityOnHandIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where quantityOnHand in DEFAULT_QUANTITY_ON_HAND or UPDATED_QUANTITY_ON_HAND
        defaultStockItemTempShouldBeFound("quantityOnHand.in=" + DEFAULT_QUANTITY_ON_HAND + "," + UPDATED_QUANTITY_ON_HAND);

        // Get all the stockItemTempList where quantityOnHand equals to UPDATED_QUANTITY_ON_HAND
        defaultStockItemTempShouldNotBeFound("quantityOnHand.in=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByQuantityOnHandIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where quantityOnHand is not null
        defaultStockItemTempShouldBeFound("quantityOnHand.specified=true");

        // Get all the stockItemTempList where quantityOnHand is null
        defaultStockItemTempShouldNotBeFound("quantityOnHand.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByQuantityOnHandIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where quantityOnHand is greater than or equal to DEFAULT_QUANTITY_ON_HAND
        defaultStockItemTempShouldBeFound("quantityOnHand.greaterThanOrEqual=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemTempList where quantityOnHand is greater than or equal to UPDATED_QUANTITY_ON_HAND
        defaultStockItemTempShouldNotBeFound("quantityOnHand.greaterThanOrEqual=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByQuantityOnHandIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where quantityOnHand is less than or equal to DEFAULT_QUANTITY_ON_HAND
        defaultStockItemTempShouldBeFound("quantityOnHand.lessThanOrEqual=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemTempList where quantityOnHand is less than or equal to SMALLER_QUANTITY_ON_HAND
        defaultStockItemTempShouldNotBeFound("quantityOnHand.lessThanOrEqual=" + SMALLER_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByQuantityOnHandIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where quantityOnHand is less than DEFAULT_QUANTITY_ON_HAND
        defaultStockItemTempShouldNotBeFound("quantityOnHand.lessThan=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemTempList where quantityOnHand is less than UPDATED_QUANTITY_ON_HAND
        defaultStockItemTempShouldBeFound("quantityOnHand.lessThan=" + UPDATED_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByQuantityOnHandIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where quantityOnHand is greater than DEFAULT_QUANTITY_ON_HAND
        defaultStockItemTempShouldNotBeFound("quantityOnHand.greaterThan=" + DEFAULT_QUANTITY_ON_HAND);

        // Get all the stockItemTempList where quantityOnHand is greater than SMALLER_QUANTITY_ON_HAND
        defaultStockItemTempShouldBeFound("quantityOnHand.greaterThan=" + SMALLER_QUANTITY_ON_HAND);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyPeriod equals to DEFAULT_WARRANTY_PERIOD
        defaultStockItemTempShouldBeFound("warrantyPeriod.equals=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the stockItemTempList where warrantyPeriod equals to UPDATED_WARRANTY_PERIOD
        defaultStockItemTempShouldNotBeFound("warrantyPeriod.equals=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyPeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyPeriod not equals to DEFAULT_WARRANTY_PERIOD
        defaultStockItemTempShouldNotBeFound("warrantyPeriod.notEquals=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the stockItemTempList where warrantyPeriod not equals to UPDATED_WARRANTY_PERIOD
        defaultStockItemTempShouldBeFound("warrantyPeriod.notEquals=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyPeriod in DEFAULT_WARRANTY_PERIOD or UPDATED_WARRANTY_PERIOD
        defaultStockItemTempShouldBeFound("warrantyPeriod.in=" + DEFAULT_WARRANTY_PERIOD + "," + UPDATED_WARRANTY_PERIOD);

        // Get all the stockItemTempList where warrantyPeriod equals to UPDATED_WARRANTY_PERIOD
        defaultStockItemTempShouldNotBeFound("warrantyPeriod.in=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyPeriod is not null
        defaultStockItemTempShouldBeFound("warrantyPeriod.specified=true");

        // Get all the stockItemTempList where warrantyPeriod is null
        defaultStockItemTempShouldNotBeFound("warrantyPeriod.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyPeriodContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyPeriod contains DEFAULT_WARRANTY_PERIOD
        defaultStockItemTempShouldBeFound("warrantyPeriod.contains=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the stockItemTempList where warrantyPeriod contains UPDATED_WARRANTY_PERIOD
        defaultStockItemTempShouldNotBeFound("warrantyPeriod.contains=" + UPDATED_WARRANTY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyPeriodNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyPeriod does not contain DEFAULT_WARRANTY_PERIOD
        defaultStockItemTempShouldNotBeFound("warrantyPeriod.doesNotContain=" + DEFAULT_WARRANTY_PERIOD);

        // Get all the stockItemTempList where warrantyPeriod does not contain UPDATED_WARRANTY_PERIOD
        defaultStockItemTempShouldBeFound("warrantyPeriod.doesNotContain=" + UPDATED_WARRANTY_PERIOD);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyPolicyIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyPolicy equals to DEFAULT_WARRANTY_POLICY
        defaultStockItemTempShouldBeFound("warrantyPolicy.equals=" + DEFAULT_WARRANTY_POLICY);

        // Get all the stockItemTempList where warrantyPolicy equals to UPDATED_WARRANTY_POLICY
        defaultStockItemTempShouldNotBeFound("warrantyPolicy.equals=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyPolicyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyPolicy not equals to DEFAULT_WARRANTY_POLICY
        defaultStockItemTempShouldNotBeFound("warrantyPolicy.notEquals=" + DEFAULT_WARRANTY_POLICY);

        // Get all the stockItemTempList where warrantyPolicy not equals to UPDATED_WARRANTY_POLICY
        defaultStockItemTempShouldBeFound("warrantyPolicy.notEquals=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyPolicyIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyPolicy in DEFAULT_WARRANTY_POLICY or UPDATED_WARRANTY_POLICY
        defaultStockItemTempShouldBeFound("warrantyPolicy.in=" + DEFAULT_WARRANTY_POLICY + "," + UPDATED_WARRANTY_POLICY);

        // Get all the stockItemTempList where warrantyPolicy equals to UPDATED_WARRANTY_POLICY
        defaultStockItemTempShouldNotBeFound("warrantyPolicy.in=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyPolicyIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyPolicy is not null
        defaultStockItemTempShouldBeFound("warrantyPolicy.specified=true");

        // Get all the stockItemTempList where warrantyPolicy is null
        defaultStockItemTempShouldNotBeFound("warrantyPolicy.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyPolicyContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyPolicy contains DEFAULT_WARRANTY_POLICY
        defaultStockItemTempShouldBeFound("warrantyPolicy.contains=" + DEFAULT_WARRANTY_POLICY);

        // Get all the stockItemTempList where warrantyPolicy contains UPDATED_WARRANTY_POLICY
        defaultStockItemTempShouldNotBeFound("warrantyPolicy.contains=" + UPDATED_WARRANTY_POLICY);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyPolicyNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyPolicy does not contain DEFAULT_WARRANTY_POLICY
        defaultStockItemTempShouldNotBeFound("warrantyPolicy.doesNotContain=" + DEFAULT_WARRANTY_POLICY);

        // Get all the stockItemTempList where warrantyPolicy does not contain UPDATED_WARRANTY_POLICY
        defaultStockItemTempShouldBeFound("warrantyPolicy.doesNotContain=" + UPDATED_WARRANTY_POLICY);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyTypeId equals to DEFAULT_WARRANTY_TYPE_ID
        defaultStockItemTempShouldBeFound("warrantyTypeId.equals=" + DEFAULT_WARRANTY_TYPE_ID);

        // Get all the stockItemTempList where warrantyTypeId equals to UPDATED_WARRANTY_TYPE_ID
        defaultStockItemTempShouldNotBeFound("warrantyTypeId.equals=" + UPDATED_WARRANTY_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyTypeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyTypeId not equals to DEFAULT_WARRANTY_TYPE_ID
        defaultStockItemTempShouldNotBeFound("warrantyTypeId.notEquals=" + DEFAULT_WARRANTY_TYPE_ID);

        // Get all the stockItemTempList where warrantyTypeId not equals to UPDATED_WARRANTY_TYPE_ID
        defaultStockItemTempShouldBeFound("warrantyTypeId.notEquals=" + UPDATED_WARRANTY_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyTypeId in DEFAULT_WARRANTY_TYPE_ID or UPDATED_WARRANTY_TYPE_ID
        defaultStockItemTempShouldBeFound("warrantyTypeId.in=" + DEFAULT_WARRANTY_TYPE_ID + "," + UPDATED_WARRANTY_TYPE_ID);

        // Get all the stockItemTempList where warrantyTypeId equals to UPDATED_WARRANTY_TYPE_ID
        defaultStockItemTempShouldNotBeFound("warrantyTypeId.in=" + UPDATED_WARRANTY_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyTypeId is not null
        defaultStockItemTempShouldBeFound("warrantyTypeId.specified=true");

        // Get all the stockItemTempList where warrantyTypeId is null
        defaultStockItemTempShouldNotBeFound("warrantyTypeId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyTypeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyTypeId is greater than or equal to DEFAULT_WARRANTY_TYPE_ID
        defaultStockItemTempShouldBeFound("warrantyTypeId.greaterThanOrEqual=" + DEFAULT_WARRANTY_TYPE_ID);

        // Get all the stockItemTempList where warrantyTypeId is greater than or equal to UPDATED_WARRANTY_TYPE_ID
        defaultStockItemTempShouldNotBeFound("warrantyTypeId.greaterThanOrEqual=" + UPDATED_WARRANTY_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyTypeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyTypeId is less than or equal to DEFAULT_WARRANTY_TYPE_ID
        defaultStockItemTempShouldBeFound("warrantyTypeId.lessThanOrEqual=" + DEFAULT_WARRANTY_TYPE_ID);

        // Get all the stockItemTempList where warrantyTypeId is less than or equal to SMALLER_WARRANTY_TYPE_ID
        defaultStockItemTempShouldNotBeFound("warrantyTypeId.lessThanOrEqual=" + SMALLER_WARRANTY_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyTypeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyTypeId is less than DEFAULT_WARRANTY_TYPE_ID
        defaultStockItemTempShouldNotBeFound("warrantyTypeId.lessThan=" + DEFAULT_WARRANTY_TYPE_ID);

        // Get all the stockItemTempList where warrantyTypeId is less than UPDATED_WARRANTY_TYPE_ID
        defaultStockItemTempShouldBeFound("warrantyTypeId.lessThan=" + UPDATED_WARRANTY_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyTypeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyTypeId is greater than DEFAULT_WARRANTY_TYPE_ID
        defaultStockItemTempShouldNotBeFound("warrantyTypeId.greaterThan=" + DEFAULT_WARRANTY_TYPE_ID);

        // Get all the stockItemTempList where warrantyTypeId is greater than SMALLER_WARRANTY_TYPE_ID
        defaultStockItemTempShouldBeFound("warrantyTypeId.greaterThan=" + SMALLER_WARRANTY_TYPE_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyTypeName equals to DEFAULT_WARRANTY_TYPE_NAME
        defaultStockItemTempShouldBeFound("warrantyTypeName.equals=" + DEFAULT_WARRANTY_TYPE_NAME);

        // Get all the stockItemTempList where warrantyTypeName equals to UPDATED_WARRANTY_TYPE_NAME
        defaultStockItemTempShouldNotBeFound("warrantyTypeName.equals=" + UPDATED_WARRANTY_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyTypeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyTypeName not equals to DEFAULT_WARRANTY_TYPE_NAME
        defaultStockItemTempShouldNotBeFound("warrantyTypeName.notEquals=" + DEFAULT_WARRANTY_TYPE_NAME);

        // Get all the stockItemTempList where warrantyTypeName not equals to UPDATED_WARRANTY_TYPE_NAME
        defaultStockItemTempShouldBeFound("warrantyTypeName.notEquals=" + UPDATED_WARRANTY_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyTypeName in DEFAULT_WARRANTY_TYPE_NAME or UPDATED_WARRANTY_TYPE_NAME
        defaultStockItemTempShouldBeFound("warrantyTypeName.in=" + DEFAULT_WARRANTY_TYPE_NAME + "," + UPDATED_WARRANTY_TYPE_NAME);

        // Get all the stockItemTempList where warrantyTypeName equals to UPDATED_WARRANTY_TYPE_NAME
        defaultStockItemTempShouldNotBeFound("warrantyTypeName.in=" + UPDATED_WARRANTY_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyTypeName is not null
        defaultStockItemTempShouldBeFound("warrantyTypeName.specified=true");

        // Get all the stockItemTempList where warrantyTypeName is null
        defaultStockItemTempShouldNotBeFound("warrantyTypeName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyTypeNameContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyTypeName contains DEFAULT_WARRANTY_TYPE_NAME
        defaultStockItemTempShouldBeFound("warrantyTypeName.contains=" + DEFAULT_WARRANTY_TYPE_NAME);

        // Get all the stockItemTempList where warrantyTypeName contains UPDATED_WARRANTY_TYPE_NAME
        defaultStockItemTempShouldNotBeFound("warrantyTypeName.contains=" + UPDATED_WARRANTY_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByWarrantyTypeNameNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where warrantyTypeName does not contain DEFAULT_WARRANTY_TYPE_NAME
        defaultStockItemTempShouldNotBeFound("warrantyTypeName.doesNotContain=" + DEFAULT_WARRANTY_TYPE_NAME);

        // Get all the stockItemTempList where warrantyTypeName does not contain UPDATED_WARRANTY_TYPE_NAME
        defaultStockItemTempShouldBeFound("warrantyTypeName.doesNotContain=" + UPDATED_WARRANTY_TYPE_NAME);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLength equals to DEFAULT_ITEM_LENGTH
        defaultStockItemTempShouldBeFound("itemLength.equals=" + DEFAULT_ITEM_LENGTH);

        // Get all the stockItemTempList where itemLength equals to UPDATED_ITEM_LENGTH
        defaultStockItemTempShouldNotBeFound("itemLength.equals=" + UPDATED_ITEM_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLength not equals to DEFAULT_ITEM_LENGTH
        defaultStockItemTempShouldNotBeFound("itemLength.notEquals=" + DEFAULT_ITEM_LENGTH);

        // Get all the stockItemTempList where itemLength not equals to UPDATED_ITEM_LENGTH
        defaultStockItemTempShouldBeFound("itemLength.notEquals=" + UPDATED_ITEM_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLength in DEFAULT_ITEM_LENGTH or UPDATED_ITEM_LENGTH
        defaultStockItemTempShouldBeFound("itemLength.in=" + DEFAULT_ITEM_LENGTH + "," + UPDATED_ITEM_LENGTH);

        // Get all the stockItemTempList where itemLength equals to UPDATED_ITEM_LENGTH
        defaultStockItemTempShouldNotBeFound("itemLength.in=" + UPDATED_ITEM_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLength is not null
        defaultStockItemTempShouldBeFound("itemLength.specified=true");

        // Get all the stockItemTempList where itemLength is null
        defaultStockItemTempShouldNotBeFound("itemLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLength is greater than or equal to DEFAULT_ITEM_LENGTH
        defaultStockItemTempShouldBeFound("itemLength.greaterThanOrEqual=" + DEFAULT_ITEM_LENGTH);

        // Get all the stockItemTempList where itemLength is greater than or equal to UPDATED_ITEM_LENGTH
        defaultStockItemTempShouldNotBeFound("itemLength.greaterThanOrEqual=" + UPDATED_ITEM_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLength is less than or equal to DEFAULT_ITEM_LENGTH
        defaultStockItemTempShouldBeFound("itemLength.lessThanOrEqual=" + DEFAULT_ITEM_LENGTH);

        // Get all the stockItemTempList where itemLength is less than or equal to SMALLER_ITEM_LENGTH
        defaultStockItemTempShouldNotBeFound("itemLength.lessThanOrEqual=" + SMALLER_ITEM_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLength is less than DEFAULT_ITEM_LENGTH
        defaultStockItemTempShouldNotBeFound("itemLength.lessThan=" + DEFAULT_ITEM_LENGTH);

        // Get all the stockItemTempList where itemLength is less than UPDATED_ITEM_LENGTH
        defaultStockItemTempShouldBeFound("itemLength.lessThan=" + UPDATED_ITEM_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLength is greater than DEFAULT_ITEM_LENGTH
        defaultStockItemTempShouldNotBeFound("itemLength.greaterThan=" + DEFAULT_ITEM_LENGTH);

        // Get all the stockItemTempList where itemLength is greater than SMALLER_ITEM_LENGTH
        defaultStockItemTempShouldBeFound("itemLength.greaterThan=" + SMALLER_ITEM_LENGTH);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidth equals to DEFAULT_ITEM_WIDTH
        defaultStockItemTempShouldBeFound("itemWidth.equals=" + DEFAULT_ITEM_WIDTH);

        // Get all the stockItemTempList where itemWidth equals to UPDATED_ITEM_WIDTH
        defaultStockItemTempShouldNotBeFound("itemWidth.equals=" + UPDATED_ITEM_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidth not equals to DEFAULT_ITEM_WIDTH
        defaultStockItemTempShouldNotBeFound("itemWidth.notEquals=" + DEFAULT_ITEM_WIDTH);

        // Get all the stockItemTempList where itemWidth not equals to UPDATED_ITEM_WIDTH
        defaultStockItemTempShouldBeFound("itemWidth.notEquals=" + UPDATED_ITEM_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidth in DEFAULT_ITEM_WIDTH or UPDATED_ITEM_WIDTH
        defaultStockItemTempShouldBeFound("itemWidth.in=" + DEFAULT_ITEM_WIDTH + "," + UPDATED_ITEM_WIDTH);

        // Get all the stockItemTempList where itemWidth equals to UPDATED_ITEM_WIDTH
        defaultStockItemTempShouldNotBeFound("itemWidth.in=" + UPDATED_ITEM_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidth is not null
        defaultStockItemTempShouldBeFound("itemWidth.specified=true");

        // Get all the stockItemTempList where itemWidth is null
        defaultStockItemTempShouldNotBeFound("itemWidth.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidth is greater than or equal to DEFAULT_ITEM_WIDTH
        defaultStockItemTempShouldBeFound("itemWidth.greaterThanOrEqual=" + DEFAULT_ITEM_WIDTH);

        // Get all the stockItemTempList where itemWidth is greater than or equal to UPDATED_ITEM_WIDTH
        defaultStockItemTempShouldNotBeFound("itemWidth.greaterThanOrEqual=" + UPDATED_ITEM_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidth is less than or equal to DEFAULT_ITEM_WIDTH
        defaultStockItemTempShouldBeFound("itemWidth.lessThanOrEqual=" + DEFAULT_ITEM_WIDTH);

        // Get all the stockItemTempList where itemWidth is less than or equal to SMALLER_ITEM_WIDTH
        defaultStockItemTempShouldNotBeFound("itemWidth.lessThanOrEqual=" + SMALLER_ITEM_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidth is less than DEFAULT_ITEM_WIDTH
        defaultStockItemTempShouldNotBeFound("itemWidth.lessThan=" + DEFAULT_ITEM_WIDTH);

        // Get all the stockItemTempList where itemWidth is less than UPDATED_ITEM_WIDTH
        defaultStockItemTempShouldBeFound("itemWidth.lessThan=" + UPDATED_ITEM_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidth is greater than DEFAULT_ITEM_WIDTH
        defaultStockItemTempShouldNotBeFound("itemWidth.greaterThan=" + DEFAULT_ITEM_WIDTH);

        // Get all the stockItemTempList where itemWidth is greater than SMALLER_ITEM_WIDTH
        defaultStockItemTempShouldBeFound("itemWidth.greaterThan=" + SMALLER_ITEM_WIDTH);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeight equals to DEFAULT_ITEM_HEIGHT
        defaultStockItemTempShouldBeFound("itemHeight.equals=" + DEFAULT_ITEM_HEIGHT);

        // Get all the stockItemTempList where itemHeight equals to UPDATED_ITEM_HEIGHT
        defaultStockItemTempShouldNotBeFound("itemHeight.equals=" + UPDATED_ITEM_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeight not equals to DEFAULT_ITEM_HEIGHT
        defaultStockItemTempShouldNotBeFound("itemHeight.notEquals=" + DEFAULT_ITEM_HEIGHT);

        // Get all the stockItemTempList where itemHeight not equals to UPDATED_ITEM_HEIGHT
        defaultStockItemTempShouldBeFound("itemHeight.notEquals=" + UPDATED_ITEM_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeight in DEFAULT_ITEM_HEIGHT or UPDATED_ITEM_HEIGHT
        defaultStockItemTempShouldBeFound("itemHeight.in=" + DEFAULT_ITEM_HEIGHT + "," + UPDATED_ITEM_HEIGHT);

        // Get all the stockItemTempList where itemHeight equals to UPDATED_ITEM_HEIGHT
        defaultStockItemTempShouldNotBeFound("itemHeight.in=" + UPDATED_ITEM_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeight is not null
        defaultStockItemTempShouldBeFound("itemHeight.specified=true");

        // Get all the stockItemTempList where itemHeight is null
        defaultStockItemTempShouldNotBeFound("itemHeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeight is greater than or equal to DEFAULT_ITEM_HEIGHT
        defaultStockItemTempShouldBeFound("itemHeight.greaterThanOrEqual=" + DEFAULT_ITEM_HEIGHT);

        // Get all the stockItemTempList where itemHeight is greater than or equal to UPDATED_ITEM_HEIGHT
        defaultStockItemTempShouldNotBeFound("itemHeight.greaterThanOrEqual=" + UPDATED_ITEM_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeight is less than or equal to DEFAULT_ITEM_HEIGHT
        defaultStockItemTempShouldBeFound("itemHeight.lessThanOrEqual=" + DEFAULT_ITEM_HEIGHT);

        // Get all the stockItemTempList where itemHeight is less than or equal to SMALLER_ITEM_HEIGHT
        defaultStockItemTempShouldNotBeFound("itemHeight.lessThanOrEqual=" + SMALLER_ITEM_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeight is less than DEFAULT_ITEM_HEIGHT
        defaultStockItemTempShouldNotBeFound("itemHeight.lessThan=" + DEFAULT_ITEM_HEIGHT);

        // Get all the stockItemTempList where itemHeight is less than UPDATED_ITEM_HEIGHT
        defaultStockItemTempShouldBeFound("itemHeight.lessThan=" + UPDATED_ITEM_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeight is greater than DEFAULT_ITEM_HEIGHT
        defaultStockItemTempShouldNotBeFound("itemHeight.greaterThan=" + DEFAULT_ITEM_HEIGHT);

        // Get all the stockItemTempList where itemHeight is greater than SMALLER_ITEM_HEIGHT
        defaultStockItemTempShouldBeFound("itemHeight.greaterThan=" + SMALLER_ITEM_HEIGHT);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeight equals to DEFAULT_ITEM_WEIGHT
        defaultStockItemTempShouldBeFound("itemWeight.equals=" + DEFAULT_ITEM_WEIGHT);

        // Get all the stockItemTempList where itemWeight equals to UPDATED_ITEM_WEIGHT
        defaultStockItemTempShouldNotBeFound("itemWeight.equals=" + UPDATED_ITEM_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeight not equals to DEFAULT_ITEM_WEIGHT
        defaultStockItemTempShouldNotBeFound("itemWeight.notEquals=" + DEFAULT_ITEM_WEIGHT);

        // Get all the stockItemTempList where itemWeight not equals to UPDATED_ITEM_WEIGHT
        defaultStockItemTempShouldBeFound("itemWeight.notEquals=" + UPDATED_ITEM_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeight in DEFAULT_ITEM_WEIGHT or UPDATED_ITEM_WEIGHT
        defaultStockItemTempShouldBeFound("itemWeight.in=" + DEFAULT_ITEM_WEIGHT + "," + UPDATED_ITEM_WEIGHT);

        // Get all the stockItemTempList where itemWeight equals to UPDATED_ITEM_WEIGHT
        defaultStockItemTempShouldNotBeFound("itemWeight.in=" + UPDATED_ITEM_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeight is not null
        defaultStockItemTempShouldBeFound("itemWeight.specified=true");

        // Get all the stockItemTempList where itemWeight is null
        defaultStockItemTempShouldNotBeFound("itemWeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeight is greater than or equal to DEFAULT_ITEM_WEIGHT
        defaultStockItemTempShouldBeFound("itemWeight.greaterThanOrEqual=" + DEFAULT_ITEM_WEIGHT);

        // Get all the stockItemTempList where itemWeight is greater than or equal to UPDATED_ITEM_WEIGHT
        defaultStockItemTempShouldNotBeFound("itemWeight.greaterThanOrEqual=" + UPDATED_ITEM_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeight is less than or equal to DEFAULT_ITEM_WEIGHT
        defaultStockItemTempShouldBeFound("itemWeight.lessThanOrEqual=" + DEFAULT_ITEM_WEIGHT);

        // Get all the stockItemTempList where itemWeight is less than or equal to SMALLER_ITEM_WEIGHT
        defaultStockItemTempShouldNotBeFound("itemWeight.lessThanOrEqual=" + SMALLER_ITEM_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeight is less than DEFAULT_ITEM_WEIGHT
        defaultStockItemTempShouldNotBeFound("itemWeight.lessThan=" + DEFAULT_ITEM_WEIGHT);

        // Get all the stockItemTempList where itemWeight is less than UPDATED_ITEM_WEIGHT
        defaultStockItemTempShouldBeFound("itemWeight.lessThan=" + UPDATED_ITEM_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeight is greater than DEFAULT_ITEM_WEIGHT
        defaultStockItemTempShouldNotBeFound("itemWeight.greaterThan=" + DEFAULT_ITEM_WEIGHT);

        // Get all the stockItemTempList where itemWeight is greater than SMALLER_ITEM_WEIGHT
        defaultStockItemTempShouldBeFound("itemWeight.greaterThan=" + SMALLER_ITEM_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLength equals to DEFAULT_ITEM_PACKAGE_LENGTH
        defaultStockItemTempShouldBeFound("itemPackageLength.equals=" + DEFAULT_ITEM_PACKAGE_LENGTH);

        // Get all the stockItemTempList where itemPackageLength equals to UPDATED_ITEM_PACKAGE_LENGTH
        defaultStockItemTempShouldNotBeFound("itemPackageLength.equals=" + UPDATED_ITEM_PACKAGE_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLength not equals to DEFAULT_ITEM_PACKAGE_LENGTH
        defaultStockItemTempShouldNotBeFound("itemPackageLength.notEquals=" + DEFAULT_ITEM_PACKAGE_LENGTH);

        // Get all the stockItemTempList where itemPackageLength not equals to UPDATED_ITEM_PACKAGE_LENGTH
        defaultStockItemTempShouldBeFound("itemPackageLength.notEquals=" + UPDATED_ITEM_PACKAGE_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLength in DEFAULT_ITEM_PACKAGE_LENGTH or UPDATED_ITEM_PACKAGE_LENGTH
        defaultStockItemTempShouldBeFound("itemPackageLength.in=" + DEFAULT_ITEM_PACKAGE_LENGTH + "," + UPDATED_ITEM_PACKAGE_LENGTH);

        // Get all the stockItemTempList where itemPackageLength equals to UPDATED_ITEM_PACKAGE_LENGTH
        defaultStockItemTempShouldNotBeFound("itemPackageLength.in=" + UPDATED_ITEM_PACKAGE_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLength is not null
        defaultStockItemTempShouldBeFound("itemPackageLength.specified=true");

        // Get all the stockItemTempList where itemPackageLength is null
        defaultStockItemTempShouldNotBeFound("itemPackageLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLength is greater than or equal to DEFAULT_ITEM_PACKAGE_LENGTH
        defaultStockItemTempShouldBeFound("itemPackageLength.greaterThanOrEqual=" + DEFAULT_ITEM_PACKAGE_LENGTH);

        // Get all the stockItemTempList where itemPackageLength is greater than or equal to UPDATED_ITEM_PACKAGE_LENGTH
        defaultStockItemTempShouldNotBeFound("itemPackageLength.greaterThanOrEqual=" + UPDATED_ITEM_PACKAGE_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLength is less than or equal to DEFAULT_ITEM_PACKAGE_LENGTH
        defaultStockItemTempShouldBeFound("itemPackageLength.lessThanOrEqual=" + DEFAULT_ITEM_PACKAGE_LENGTH);

        // Get all the stockItemTempList where itemPackageLength is less than or equal to SMALLER_ITEM_PACKAGE_LENGTH
        defaultStockItemTempShouldNotBeFound("itemPackageLength.lessThanOrEqual=" + SMALLER_ITEM_PACKAGE_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLength is less than DEFAULT_ITEM_PACKAGE_LENGTH
        defaultStockItemTempShouldNotBeFound("itemPackageLength.lessThan=" + DEFAULT_ITEM_PACKAGE_LENGTH);

        // Get all the stockItemTempList where itemPackageLength is less than UPDATED_ITEM_PACKAGE_LENGTH
        defaultStockItemTempShouldBeFound("itemPackageLength.lessThan=" + UPDATED_ITEM_PACKAGE_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLength is greater than DEFAULT_ITEM_PACKAGE_LENGTH
        defaultStockItemTempShouldNotBeFound("itemPackageLength.greaterThan=" + DEFAULT_ITEM_PACKAGE_LENGTH);

        // Get all the stockItemTempList where itemPackageLength is greater than SMALLER_ITEM_PACKAGE_LENGTH
        defaultStockItemTempShouldBeFound("itemPackageLength.greaterThan=" + SMALLER_ITEM_PACKAGE_LENGTH);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidth equals to DEFAULT_ITEM_PACKAGE_WIDTH
        defaultStockItemTempShouldBeFound("itemPackageWidth.equals=" + DEFAULT_ITEM_PACKAGE_WIDTH);

        // Get all the stockItemTempList where itemPackageWidth equals to UPDATED_ITEM_PACKAGE_WIDTH
        defaultStockItemTempShouldNotBeFound("itemPackageWidth.equals=" + UPDATED_ITEM_PACKAGE_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidth not equals to DEFAULT_ITEM_PACKAGE_WIDTH
        defaultStockItemTempShouldNotBeFound("itemPackageWidth.notEquals=" + DEFAULT_ITEM_PACKAGE_WIDTH);

        // Get all the stockItemTempList where itemPackageWidth not equals to UPDATED_ITEM_PACKAGE_WIDTH
        defaultStockItemTempShouldBeFound("itemPackageWidth.notEquals=" + UPDATED_ITEM_PACKAGE_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidth in DEFAULT_ITEM_PACKAGE_WIDTH or UPDATED_ITEM_PACKAGE_WIDTH
        defaultStockItemTempShouldBeFound("itemPackageWidth.in=" + DEFAULT_ITEM_PACKAGE_WIDTH + "," + UPDATED_ITEM_PACKAGE_WIDTH);

        // Get all the stockItemTempList where itemPackageWidth equals to UPDATED_ITEM_PACKAGE_WIDTH
        defaultStockItemTempShouldNotBeFound("itemPackageWidth.in=" + UPDATED_ITEM_PACKAGE_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidth is not null
        defaultStockItemTempShouldBeFound("itemPackageWidth.specified=true");

        // Get all the stockItemTempList where itemPackageWidth is null
        defaultStockItemTempShouldNotBeFound("itemPackageWidth.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidth is greater than or equal to DEFAULT_ITEM_PACKAGE_WIDTH
        defaultStockItemTempShouldBeFound("itemPackageWidth.greaterThanOrEqual=" + DEFAULT_ITEM_PACKAGE_WIDTH);

        // Get all the stockItemTempList where itemPackageWidth is greater than or equal to UPDATED_ITEM_PACKAGE_WIDTH
        defaultStockItemTempShouldNotBeFound("itemPackageWidth.greaterThanOrEqual=" + UPDATED_ITEM_PACKAGE_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidth is less than or equal to DEFAULT_ITEM_PACKAGE_WIDTH
        defaultStockItemTempShouldBeFound("itemPackageWidth.lessThanOrEqual=" + DEFAULT_ITEM_PACKAGE_WIDTH);

        // Get all the stockItemTempList where itemPackageWidth is less than or equal to SMALLER_ITEM_PACKAGE_WIDTH
        defaultStockItemTempShouldNotBeFound("itemPackageWidth.lessThanOrEqual=" + SMALLER_ITEM_PACKAGE_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidth is less than DEFAULT_ITEM_PACKAGE_WIDTH
        defaultStockItemTempShouldNotBeFound("itemPackageWidth.lessThan=" + DEFAULT_ITEM_PACKAGE_WIDTH);

        // Get all the stockItemTempList where itemPackageWidth is less than UPDATED_ITEM_PACKAGE_WIDTH
        defaultStockItemTempShouldBeFound("itemPackageWidth.lessThan=" + UPDATED_ITEM_PACKAGE_WIDTH);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidth is greater than DEFAULT_ITEM_PACKAGE_WIDTH
        defaultStockItemTempShouldNotBeFound("itemPackageWidth.greaterThan=" + DEFAULT_ITEM_PACKAGE_WIDTH);

        // Get all the stockItemTempList where itemPackageWidth is greater than SMALLER_ITEM_PACKAGE_WIDTH
        defaultStockItemTempShouldBeFound("itemPackageWidth.greaterThan=" + SMALLER_ITEM_PACKAGE_WIDTH);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeight equals to DEFAULT_ITEM_PACKAGE_HEIGHT
        defaultStockItemTempShouldBeFound("itemPackageHeight.equals=" + DEFAULT_ITEM_PACKAGE_HEIGHT);

        // Get all the stockItemTempList where itemPackageHeight equals to UPDATED_ITEM_PACKAGE_HEIGHT
        defaultStockItemTempShouldNotBeFound("itemPackageHeight.equals=" + UPDATED_ITEM_PACKAGE_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeight not equals to DEFAULT_ITEM_PACKAGE_HEIGHT
        defaultStockItemTempShouldNotBeFound("itemPackageHeight.notEquals=" + DEFAULT_ITEM_PACKAGE_HEIGHT);

        // Get all the stockItemTempList where itemPackageHeight not equals to UPDATED_ITEM_PACKAGE_HEIGHT
        defaultStockItemTempShouldBeFound("itemPackageHeight.notEquals=" + UPDATED_ITEM_PACKAGE_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeight in DEFAULT_ITEM_PACKAGE_HEIGHT or UPDATED_ITEM_PACKAGE_HEIGHT
        defaultStockItemTempShouldBeFound("itemPackageHeight.in=" + DEFAULT_ITEM_PACKAGE_HEIGHT + "," + UPDATED_ITEM_PACKAGE_HEIGHT);

        // Get all the stockItemTempList where itemPackageHeight equals to UPDATED_ITEM_PACKAGE_HEIGHT
        defaultStockItemTempShouldNotBeFound("itemPackageHeight.in=" + UPDATED_ITEM_PACKAGE_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeight is not null
        defaultStockItemTempShouldBeFound("itemPackageHeight.specified=true");

        // Get all the stockItemTempList where itemPackageHeight is null
        defaultStockItemTempShouldNotBeFound("itemPackageHeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeight is greater than or equal to DEFAULT_ITEM_PACKAGE_HEIGHT
        defaultStockItemTempShouldBeFound("itemPackageHeight.greaterThanOrEqual=" + DEFAULT_ITEM_PACKAGE_HEIGHT);

        // Get all the stockItemTempList where itemPackageHeight is greater than or equal to UPDATED_ITEM_PACKAGE_HEIGHT
        defaultStockItemTempShouldNotBeFound("itemPackageHeight.greaterThanOrEqual=" + UPDATED_ITEM_PACKAGE_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeight is less than or equal to DEFAULT_ITEM_PACKAGE_HEIGHT
        defaultStockItemTempShouldBeFound("itemPackageHeight.lessThanOrEqual=" + DEFAULT_ITEM_PACKAGE_HEIGHT);

        // Get all the stockItemTempList where itemPackageHeight is less than or equal to SMALLER_ITEM_PACKAGE_HEIGHT
        defaultStockItemTempShouldNotBeFound("itemPackageHeight.lessThanOrEqual=" + SMALLER_ITEM_PACKAGE_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeight is less than DEFAULT_ITEM_PACKAGE_HEIGHT
        defaultStockItemTempShouldNotBeFound("itemPackageHeight.lessThan=" + DEFAULT_ITEM_PACKAGE_HEIGHT);

        // Get all the stockItemTempList where itemPackageHeight is less than UPDATED_ITEM_PACKAGE_HEIGHT
        defaultStockItemTempShouldBeFound("itemPackageHeight.lessThan=" + UPDATED_ITEM_PACKAGE_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeight is greater than DEFAULT_ITEM_PACKAGE_HEIGHT
        defaultStockItemTempShouldNotBeFound("itemPackageHeight.greaterThan=" + DEFAULT_ITEM_PACKAGE_HEIGHT);

        // Get all the stockItemTempList where itemPackageHeight is greater than SMALLER_ITEM_PACKAGE_HEIGHT
        defaultStockItemTempShouldBeFound("itemPackageHeight.greaterThan=" + SMALLER_ITEM_PACKAGE_HEIGHT);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeight equals to DEFAULT_ITEM_PACKAGE_WEIGHT
        defaultStockItemTempShouldBeFound("itemPackageWeight.equals=" + DEFAULT_ITEM_PACKAGE_WEIGHT);

        // Get all the stockItemTempList where itemPackageWeight equals to UPDATED_ITEM_PACKAGE_WEIGHT
        defaultStockItemTempShouldNotBeFound("itemPackageWeight.equals=" + UPDATED_ITEM_PACKAGE_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeight not equals to DEFAULT_ITEM_PACKAGE_WEIGHT
        defaultStockItemTempShouldNotBeFound("itemPackageWeight.notEquals=" + DEFAULT_ITEM_PACKAGE_WEIGHT);

        // Get all the stockItemTempList where itemPackageWeight not equals to UPDATED_ITEM_PACKAGE_WEIGHT
        defaultStockItemTempShouldBeFound("itemPackageWeight.notEquals=" + UPDATED_ITEM_PACKAGE_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeight in DEFAULT_ITEM_PACKAGE_WEIGHT or UPDATED_ITEM_PACKAGE_WEIGHT
        defaultStockItemTempShouldBeFound("itemPackageWeight.in=" + DEFAULT_ITEM_PACKAGE_WEIGHT + "," + UPDATED_ITEM_PACKAGE_WEIGHT);

        // Get all the stockItemTempList where itemPackageWeight equals to UPDATED_ITEM_PACKAGE_WEIGHT
        defaultStockItemTempShouldNotBeFound("itemPackageWeight.in=" + UPDATED_ITEM_PACKAGE_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeight is not null
        defaultStockItemTempShouldBeFound("itemPackageWeight.specified=true");

        // Get all the stockItemTempList where itemPackageWeight is null
        defaultStockItemTempShouldNotBeFound("itemPackageWeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeight is greater than or equal to DEFAULT_ITEM_PACKAGE_WEIGHT
        defaultStockItemTempShouldBeFound("itemPackageWeight.greaterThanOrEqual=" + DEFAULT_ITEM_PACKAGE_WEIGHT);

        // Get all the stockItemTempList where itemPackageWeight is greater than or equal to UPDATED_ITEM_PACKAGE_WEIGHT
        defaultStockItemTempShouldNotBeFound("itemPackageWeight.greaterThanOrEqual=" + UPDATED_ITEM_PACKAGE_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeight is less than or equal to DEFAULT_ITEM_PACKAGE_WEIGHT
        defaultStockItemTempShouldBeFound("itemPackageWeight.lessThanOrEqual=" + DEFAULT_ITEM_PACKAGE_WEIGHT);

        // Get all the stockItemTempList where itemPackageWeight is less than or equal to SMALLER_ITEM_PACKAGE_WEIGHT
        defaultStockItemTempShouldNotBeFound("itemPackageWeight.lessThanOrEqual=" + SMALLER_ITEM_PACKAGE_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeight is less than DEFAULT_ITEM_PACKAGE_WEIGHT
        defaultStockItemTempShouldNotBeFound("itemPackageWeight.lessThan=" + DEFAULT_ITEM_PACKAGE_WEIGHT);

        // Get all the stockItemTempList where itemPackageWeight is less than UPDATED_ITEM_PACKAGE_WEIGHT
        defaultStockItemTempShouldBeFound("itemPackageWeight.lessThan=" + UPDATED_ITEM_PACKAGE_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeight is greater than DEFAULT_ITEM_PACKAGE_WEIGHT
        defaultStockItemTempShouldNotBeFound("itemPackageWeight.greaterThan=" + DEFAULT_ITEM_PACKAGE_WEIGHT);

        // Get all the stockItemTempList where itemPackageWeight is greater than SMALLER_ITEM_PACKAGE_WEIGHT
        defaultStockItemTempShouldBeFound("itemPackageWeight.greaterThan=" + SMALLER_ITEM_PACKAGE_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthUnitMeasureIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLengthUnitMeasureId equals to DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemLengthUnitMeasureId.equals=" + DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemLengthUnitMeasureId equals to UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemLengthUnitMeasureId.equals=" + UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthUnitMeasureIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLengthUnitMeasureId not equals to DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemLengthUnitMeasureId.notEquals=" + DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemLengthUnitMeasureId not equals to UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemLengthUnitMeasureId.notEquals=" + UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthUnitMeasureIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLengthUnitMeasureId in DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID or UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemLengthUnitMeasureId.in=" + DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID + "," + UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemLengthUnitMeasureId equals to UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemLengthUnitMeasureId.in=" + UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthUnitMeasureIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLengthUnitMeasureId is not null
        defaultStockItemTempShouldBeFound("itemLengthUnitMeasureId.specified=true");

        // Get all the stockItemTempList where itemLengthUnitMeasureId is null
        defaultStockItemTempShouldNotBeFound("itemLengthUnitMeasureId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthUnitMeasureIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLengthUnitMeasureId is greater than or equal to DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemLengthUnitMeasureId.greaterThanOrEqual=" + DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemLengthUnitMeasureId is greater than or equal to UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemLengthUnitMeasureId.greaterThanOrEqual=" + UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthUnitMeasureIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLengthUnitMeasureId is less than or equal to DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemLengthUnitMeasureId.lessThanOrEqual=" + DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemLengthUnitMeasureId is less than or equal to SMALLER_ITEM_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemLengthUnitMeasureId.lessThanOrEqual=" + SMALLER_ITEM_LENGTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthUnitMeasureIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLengthUnitMeasureId is less than DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemLengthUnitMeasureId.lessThan=" + DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemLengthUnitMeasureId is less than UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemLengthUnitMeasureId.lessThan=" + UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthUnitMeasureIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLengthUnitMeasureId is greater than DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemLengthUnitMeasureId.greaterThan=" + DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemLengthUnitMeasureId is greater than SMALLER_ITEM_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemLengthUnitMeasureId.greaterThan=" + SMALLER_ITEM_LENGTH_UNIT_MEASURE_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthUnitMeasureCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLengthUnitMeasureCode equals to DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemLengthUnitMeasureCode.equals=" + DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemLengthUnitMeasureCode equals to UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemLengthUnitMeasureCode.equals=" + UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthUnitMeasureCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLengthUnitMeasureCode not equals to DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemLengthUnitMeasureCode.notEquals=" + DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemLengthUnitMeasureCode not equals to UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemLengthUnitMeasureCode.notEquals=" + UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthUnitMeasureCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLengthUnitMeasureCode in DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE or UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemLengthUnitMeasureCode.in=" + DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE + "," + UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemLengthUnitMeasureCode equals to UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemLengthUnitMeasureCode.in=" + UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthUnitMeasureCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLengthUnitMeasureCode is not null
        defaultStockItemTempShouldBeFound("itemLengthUnitMeasureCode.specified=true");

        // Get all the stockItemTempList where itemLengthUnitMeasureCode is null
        defaultStockItemTempShouldNotBeFound("itemLengthUnitMeasureCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthUnitMeasureCodeContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLengthUnitMeasureCode contains DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemLengthUnitMeasureCode.contains=" + DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemLengthUnitMeasureCode contains UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemLengthUnitMeasureCode.contains=" + UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemLengthUnitMeasureCodeNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemLengthUnitMeasureCode does not contain DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemLengthUnitMeasureCode.doesNotContain=" + DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemLengthUnitMeasureCode does not contain UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemLengthUnitMeasureCode.doesNotContain=" + UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthUnitMeasureIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidthUnitMeasureId equals to DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemWidthUnitMeasureId.equals=" + DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemWidthUnitMeasureId equals to UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemWidthUnitMeasureId.equals=" + UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthUnitMeasureIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidthUnitMeasureId not equals to DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemWidthUnitMeasureId.notEquals=" + DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemWidthUnitMeasureId not equals to UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemWidthUnitMeasureId.notEquals=" + UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthUnitMeasureIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidthUnitMeasureId in DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID or UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemWidthUnitMeasureId.in=" + DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID + "," + UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemWidthUnitMeasureId equals to UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemWidthUnitMeasureId.in=" + UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthUnitMeasureIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidthUnitMeasureId is not null
        defaultStockItemTempShouldBeFound("itemWidthUnitMeasureId.specified=true");

        // Get all the stockItemTempList where itemWidthUnitMeasureId is null
        defaultStockItemTempShouldNotBeFound("itemWidthUnitMeasureId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthUnitMeasureIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidthUnitMeasureId is greater than or equal to DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemWidthUnitMeasureId.greaterThanOrEqual=" + DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemWidthUnitMeasureId is greater than or equal to UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemWidthUnitMeasureId.greaterThanOrEqual=" + UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthUnitMeasureIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidthUnitMeasureId is less than or equal to DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemWidthUnitMeasureId.lessThanOrEqual=" + DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemWidthUnitMeasureId is less than or equal to SMALLER_ITEM_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemWidthUnitMeasureId.lessThanOrEqual=" + SMALLER_ITEM_WIDTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthUnitMeasureIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidthUnitMeasureId is less than DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemWidthUnitMeasureId.lessThan=" + DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemWidthUnitMeasureId is less than UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemWidthUnitMeasureId.lessThan=" + UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthUnitMeasureIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidthUnitMeasureId is greater than DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemWidthUnitMeasureId.greaterThan=" + DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemWidthUnitMeasureId is greater than SMALLER_ITEM_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemWidthUnitMeasureId.greaterThan=" + SMALLER_ITEM_WIDTH_UNIT_MEASURE_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthUnitMeasureCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidthUnitMeasureCode equals to DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemWidthUnitMeasureCode.equals=" + DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemWidthUnitMeasureCode equals to UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemWidthUnitMeasureCode.equals=" + UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthUnitMeasureCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidthUnitMeasureCode not equals to DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemWidthUnitMeasureCode.notEquals=" + DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemWidthUnitMeasureCode not equals to UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemWidthUnitMeasureCode.notEquals=" + UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthUnitMeasureCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidthUnitMeasureCode in DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE or UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemWidthUnitMeasureCode.in=" + DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE + "," + UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemWidthUnitMeasureCode equals to UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemWidthUnitMeasureCode.in=" + UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthUnitMeasureCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidthUnitMeasureCode is not null
        defaultStockItemTempShouldBeFound("itemWidthUnitMeasureCode.specified=true");

        // Get all the stockItemTempList where itemWidthUnitMeasureCode is null
        defaultStockItemTempShouldNotBeFound("itemWidthUnitMeasureCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthUnitMeasureCodeContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidthUnitMeasureCode contains DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemWidthUnitMeasureCode.contains=" + DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemWidthUnitMeasureCode contains UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemWidthUnitMeasureCode.contains=" + UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWidthUnitMeasureCodeNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWidthUnitMeasureCode does not contain DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemWidthUnitMeasureCode.doesNotContain=" + DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemWidthUnitMeasureCode does not contain UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemWidthUnitMeasureCode.doesNotContain=" + UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightUnitMeasureIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeightUnitMeasureId equals to DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemHeightUnitMeasureId.equals=" + DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemHeightUnitMeasureId equals to UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemHeightUnitMeasureId.equals=" + UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightUnitMeasureIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeightUnitMeasureId not equals to DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemHeightUnitMeasureId.notEquals=" + DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemHeightUnitMeasureId not equals to UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemHeightUnitMeasureId.notEquals=" + UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightUnitMeasureIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeightUnitMeasureId in DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID or UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemHeightUnitMeasureId.in=" + DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID + "," + UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemHeightUnitMeasureId equals to UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemHeightUnitMeasureId.in=" + UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightUnitMeasureIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeightUnitMeasureId is not null
        defaultStockItemTempShouldBeFound("itemHeightUnitMeasureId.specified=true");

        // Get all the stockItemTempList where itemHeightUnitMeasureId is null
        defaultStockItemTempShouldNotBeFound("itemHeightUnitMeasureId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightUnitMeasureIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeightUnitMeasureId is greater than or equal to DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemHeightUnitMeasureId.greaterThanOrEqual=" + DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemHeightUnitMeasureId is greater than or equal to UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemHeightUnitMeasureId.greaterThanOrEqual=" + UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightUnitMeasureIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeightUnitMeasureId is less than or equal to DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemHeightUnitMeasureId.lessThanOrEqual=" + DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemHeightUnitMeasureId is less than or equal to SMALLER_ITEM_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemHeightUnitMeasureId.lessThanOrEqual=" + SMALLER_ITEM_HEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightUnitMeasureIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeightUnitMeasureId is less than DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemHeightUnitMeasureId.lessThan=" + DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemHeightUnitMeasureId is less than UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemHeightUnitMeasureId.lessThan=" + UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightUnitMeasureIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeightUnitMeasureId is greater than DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemHeightUnitMeasureId.greaterThan=" + DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemHeightUnitMeasureId is greater than SMALLER_ITEM_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemHeightUnitMeasureId.greaterThan=" + SMALLER_ITEM_HEIGHT_UNIT_MEASURE_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightUnitMeasureCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeightUnitMeasureCode equals to DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemHeightUnitMeasureCode.equals=" + DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemHeightUnitMeasureCode equals to UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemHeightUnitMeasureCode.equals=" + UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightUnitMeasureCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeightUnitMeasureCode not equals to DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemHeightUnitMeasureCode.notEquals=" + DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemHeightUnitMeasureCode not equals to UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemHeightUnitMeasureCode.notEquals=" + UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightUnitMeasureCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeightUnitMeasureCode in DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE or UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemHeightUnitMeasureCode.in=" + DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE + "," + UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemHeightUnitMeasureCode equals to UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemHeightUnitMeasureCode.in=" + UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightUnitMeasureCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeightUnitMeasureCode is not null
        defaultStockItemTempShouldBeFound("itemHeightUnitMeasureCode.specified=true");

        // Get all the stockItemTempList where itemHeightUnitMeasureCode is null
        defaultStockItemTempShouldNotBeFound("itemHeightUnitMeasureCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightUnitMeasureCodeContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeightUnitMeasureCode contains DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemHeightUnitMeasureCode.contains=" + DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemHeightUnitMeasureCode contains UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemHeightUnitMeasureCode.contains=" + UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemHeightUnitMeasureCodeNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemHeightUnitMeasureCode does not contain DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemHeightUnitMeasureCode.doesNotContain=" + DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemHeightUnitMeasureCode does not contain UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemHeightUnitMeasureCode.doesNotContain=" + UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightUnitMeasureIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeightUnitMeasureId equals to DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemWeightUnitMeasureId.equals=" + DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemWeightUnitMeasureId equals to UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemWeightUnitMeasureId.equals=" + UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightUnitMeasureIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeightUnitMeasureId not equals to DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemWeightUnitMeasureId.notEquals=" + DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemWeightUnitMeasureId not equals to UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemWeightUnitMeasureId.notEquals=" + UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightUnitMeasureIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeightUnitMeasureId in DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID or UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemWeightUnitMeasureId.in=" + DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID + "," + UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemWeightUnitMeasureId equals to UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemWeightUnitMeasureId.in=" + UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightUnitMeasureIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeightUnitMeasureId is not null
        defaultStockItemTempShouldBeFound("itemWeightUnitMeasureId.specified=true");

        // Get all the stockItemTempList where itemWeightUnitMeasureId is null
        defaultStockItemTempShouldNotBeFound("itemWeightUnitMeasureId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightUnitMeasureIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeightUnitMeasureId is greater than or equal to DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemWeightUnitMeasureId.greaterThanOrEqual=" + DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemWeightUnitMeasureId is greater than or equal to UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemWeightUnitMeasureId.greaterThanOrEqual=" + UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightUnitMeasureIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeightUnitMeasureId is less than or equal to DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemWeightUnitMeasureId.lessThanOrEqual=" + DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemWeightUnitMeasureId is less than or equal to SMALLER_ITEM_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemWeightUnitMeasureId.lessThanOrEqual=" + SMALLER_ITEM_WEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightUnitMeasureIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeightUnitMeasureId is less than DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemWeightUnitMeasureId.lessThan=" + DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemWeightUnitMeasureId is less than UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemWeightUnitMeasureId.lessThan=" + UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightUnitMeasureIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeightUnitMeasureId is greater than DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemWeightUnitMeasureId.greaterThan=" + DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemWeightUnitMeasureId is greater than SMALLER_ITEM_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemWeightUnitMeasureId.greaterThan=" + SMALLER_ITEM_WEIGHT_UNIT_MEASURE_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightUnitMeasureCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeightUnitMeasureCode equals to DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemWeightUnitMeasureCode.equals=" + DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemWeightUnitMeasureCode equals to UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemWeightUnitMeasureCode.equals=" + UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightUnitMeasureCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeightUnitMeasureCode not equals to DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemWeightUnitMeasureCode.notEquals=" + DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemWeightUnitMeasureCode not equals to UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemWeightUnitMeasureCode.notEquals=" + UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightUnitMeasureCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeightUnitMeasureCode in DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE or UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemWeightUnitMeasureCode.in=" + DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE + "," + UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemWeightUnitMeasureCode equals to UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemWeightUnitMeasureCode.in=" + UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightUnitMeasureCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeightUnitMeasureCode is not null
        defaultStockItemTempShouldBeFound("itemWeightUnitMeasureCode.specified=true");

        // Get all the stockItemTempList where itemWeightUnitMeasureCode is null
        defaultStockItemTempShouldNotBeFound("itemWeightUnitMeasureCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightUnitMeasureCodeContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeightUnitMeasureCode contains DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemWeightUnitMeasureCode.contains=" + DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemWeightUnitMeasureCode contains UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemWeightUnitMeasureCode.contains=" + UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemWeightUnitMeasureCodeNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemWeightUnitMeasureCode does not contain DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemWeightUnitMeasureCode.doesNotContain=" + DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemWeightUnitMeasureCode does not contain UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemWeightUnitMeasureCode.doesNotContain=" + UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthUnitMeasureIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureId equals to DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageLengthUnitMeasureId.equals=" + DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureId equals to UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageLengthUnitMeasureId.equals=" + UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthUnitMeasureIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureId not equals to DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageLengthUnitMeasureId.notEquals=" + DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureId not equals to UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageLengthUnitMeasureId.notEquals=" + UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthUnitMeasureIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureId in DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID or UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageLengthUnitMeasureId.in=" + DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID + "," + UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureId equals to UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageLengthUnitMeasureId.in=" + UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthUnitMeasureIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureId is not null
        defaultStockItemTempShouldBeFound("itemPackageLengthUnitMeasureId.specified=true");

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureId is null
        defaultStockItemTempShouldNotBeFound("itemPackageLengthUnitMeasureId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthUnitMeasureIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureId is greater than or equal to DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageLengthUnitMeasureId.greaterThanOrEqual=" + DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureId is greater than or equal to UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageLengthUnitMeasureId.greaterThanOrEqual=" + UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthUnitMeasureIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureId is less than or equal to DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageLengthUnitMeasureId.lessThanOrEqual=" + DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureId is less than or equal to SMALLER_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageLengthUnitMeasureId.lessThanOrEqual=" + SMALLER_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthUnitMeasureIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureId is less than DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageLengthUnitMeasureId.lessThan=" + DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureId is less than UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageLengthUnitMeasureId.lessThan=" + UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthUnitMeasureIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureId is greater than DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageLengthUnitMeasureId.greaterThan=" + DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureId is greater than SMALLER_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageLengthUnitMeasureId.greaterThan=" + SMALLER_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthUnitMeasureCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureCode equals to DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageLengthUnitMeasureCode.equals=" + DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureCode equals to UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageLengthUnitMeasureCode.equals=" + UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthUnitMeasureCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureCode not equals to DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageLengthUnitMeasureCode.notEquals=" + DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureCode not equals to UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageLengthUnitMeasureCode.notEquals=" + UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthUnitMeasureCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureCode in DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE or UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageLengthUnitMeasureCode.in=" + DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE + "," + UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureCode equals to UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageLengthUnitMeasureCode.in=" + UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthUnitMeasureCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureCode is not null
        defaultStockItemTempShouldBeFound("itemPackageLengthUnitMeasureCode.specified=true");

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureCode is null
        defaultStockItemTempShouldNotBeFound("itemPackageLengthUnitMeasureCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthUnitMeasureCodeContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureCode contains DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageLengthUnitMeasureCode.contains=" + DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureCode contains UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageLengthUnitMeasureCode.contains=" + UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageLengthUnitMeasureCodeNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureCode does not contain DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageLengthUnitMeasureCode.doesNotContain=" + DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageLengthUnitMeasureCode does not contain UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageLengthUnitMeasureCode.doesNotContain=" + UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthUnitMeasureIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureId equals to DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageWidthUnitMeasureId.equals=" + DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureId equals to UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageWidthUnitMeasureId.equals=" + UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthUnitMeasureIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureId not equals to DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageWidthUnitMeasureId.notEquals=" + DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureId not equals to UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageWidthUnitMeasureId.notEquals=" + UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthUnitMeasureIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureId in DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID or UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageWidthUnitMeasureId.in=" + DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID + "," + UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureId equals to UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageWidthUnitMeasureId.in=" + UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthUnitMeasureIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureId is not null
        defaultStockItemTempShouldBeFound("itemPackageWidthUnitMeasureId.specified=true");

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureId is null
        defaultStockItemTempShouldNotBeFound("itemPackageWidthUnitMeasureId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthUnitMeasureIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureId is greater than or equal to DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageWidthUnitMeasureId.greaterThanOrEqual=" + DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureId is greater than or equal to UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageWidthUnitMeasureId.greaterThanOrEqual=" + UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthUnitMeasureIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureId is less than or equal to DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageWidthUnitMeasureId.lessThanOrEqual=" + DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureId is less than or equal to SMALLER_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageWidthUnitMeasureId.lessThanOrEqual=" + SMALLER_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthUnitMeasureIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureId is less than DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageWidthUnitMeasureId.lessThan=" + DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureId is less than UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageWidthUnitMeasureId.lessThan=" + UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthUnitMeasureIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureId is greater than DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageWidthUnitMeasureId.greaterThan=" + DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureId is greater than SMALLER_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageWidthUnitMeasureId.greaterThan=" + SMALLER_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthUnitMeasureCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureCode equals to DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageWidthUnitMeasureCode.equals=" + DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureCode equals to UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageWidthUnitMeasureCode.equals=" + UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthUnitMeasureCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureCode not equals to DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageWidthUnitMeasureCode.notEquals=" + DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureCode not equals to UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageWidthUnitMeasureCode.notEquals=" + UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthUnitMeasureCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureCode in DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE or UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageWidthUnitMeasureCode.in=" + DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE + "," + UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureCode equals to UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageWidthUnitMeasureCode.in=" + UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthUnitMeasureCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureCode is not null
        defaultStockItemTempShouldBeFound("itemPackageWidthUnitMeasureCode.specified=true");

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureCode is null
        defaultStockItemTempShouldNotBeFound("itemPackageWidthUnitMeasureCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthUnitMeasureCodeContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureCode contains DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageWidthUnitMeasureCode.contains=" + DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureCode contains UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageWidthUnitMeasureCode.contains=" + UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWidthUnitMeasureCodeNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureCode does not contain DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageWidthUnitMeasureCode.doesNotContain=" + DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageWidthUnitMeasureCode does not contain UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageWidthUnitMeasureCode.doesNotContain=" + UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightUnitMeasureIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureId equals to DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageHeightUnitMeasureId.equals=" + DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureId equals to UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageHeightUnitMeasureId.equals=" + UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightUnitMeasureIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureId not equals to DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageHeightUnitMeasureId.notEquals=" + DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureId not equals to UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageHeightUnitMeasureId.notEquals=" + UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightUnitMeasureIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureId in DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID or UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageHeightUnitMeasureId.in=" + DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID + "," + UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureId equals to UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageHeightUnitMeasureId.in=" + UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightUnitMeasureIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureId is not null
        defaultStockItemTempShouldBeFound("itemPackageHeightUnitMeasureId.specified=true");

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureId is null
        defaultStockItemTempShouldNotBeFound("itemPackageHeightUnitMeasureId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightUnitMeasureIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureId is greater than or equal to DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageHeightUnitMeasureId.greaterThanOrEqual=" + DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureId is greater than or equal to UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageHeightUnitMeasureId.greaterThanOrEqual=" + UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightUnitMeasureIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureId is less than or equal to DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageHeightUnitMeasureId.lessThanOrEqual=" + DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureId is less than or equal to SMALLER_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageHeightUnitMeasureId.lessThanOrEqual=" + SMALLER_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightUnitMeasureIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureId is less than DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageHeightUnitMeasureId.lessThan=" + DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureId is less than UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageHeightUnitMeasureId.lessThan=" + UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightUnitMeasureIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureId is greater than DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageHeightUnitMeasureId.greaterThan=" + DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureId is greater than SMALLER_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageHeightUnitMeasureId.greaterThan=" + SMALLER_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightUnitMeasureCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureCode equals to DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageHeightUnitMeasureCode.equals=" + DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureCode equals to UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageHeightUnitMeasureCode.equals=" + UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightUnitMeasureCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureCode not equals to DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageHeightUnitMeasureCode.notEquals=" + DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureCode not equals to UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageHeightUnitMeasureCode.notEquals=" + UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightUnitMeasureCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureCode in DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE or UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageHeightUnitMeasureCode.in=" + DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE + "," + UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureCode equals to UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageHeightUnitMeasureCode.in=" + UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightUnitMeasureCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureCode is not null
        defaultStockItemTempShouldBeFound("itemPackageHeightUnitMeasureCode.specified=true");

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureCode is null
        defaultStockItemTempShouldNotBeFound("itemPackageHeightUnitMeasureCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightUnitMeasureCodeContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureCode contains DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageHeightUnitMeasureCode.contains=" + DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureCode contains UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageHeightUnitMeasureCode.contains=" + UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageHeightUnitMeasureCodeNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureCode does not contain DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageHeightUnitMeasureCode.doesNotContain=" + DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageHeightUnitMeasureCode does not contain UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageHeightUnitMeasureCode.doesNotContain=" + UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightUnitMeasureIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureId equals to DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageWeightUnitMeasureId.equals=" + DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureId equals to UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageWeightUnitMeasureId.equals=" + UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightUnitMeasureIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureId not equals to DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageWeightUnitMeasureId.notEquals=" + DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureId not equals to UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageWeightUnitMeasureId.notEquals=" + UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightUnitMeasureIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureId in DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID or UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageWeightUnitMeasureId.in=" + DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID + "," + UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureId equals to UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageWeightUnitMeasureId.in=" + UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightUnitMeasureIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureId is not null
        defaultStockItemTempShouldBeFound("itemPackageWeightUnitMeasureId.specified=true");

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureId is null
        defaultStockItemTempShouldNotBeFound("itemPackageWeightUnitMeasureId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightUnitMeasureIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureId is greater than or equal to DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageWeightUnitMeasureId.greaterThanOrEqual=" + DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureId is greater than or equal to UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageWeightUnitMeasureId.greaterThanOrEqual=" + UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightUnitMeasureIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureId is less than or equal to DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageWeightUnitMeasureId.lessThanOrEqual=" + DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureId is less than or equal to SMALLER_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageWeightUnitMeasureId.lessThanOrEqual=" + SMALLER_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightUnitMeasureIdIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureId is less than DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageWeightUnitMeasureId.lessThan=" + DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureId is less than UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageWeightUnitMeasureId.lessThan=" + UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightUnitMeasureIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureId is greater than DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldNotBeFound("itemPackageWeightUnitMeasureId.greaterThan=" + DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureId is greater than SMALLER_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID
        defaultStockItemTempShouldBeFound("itemPackageWeightUnitMeasureId.greaterThan=" + SMALLER_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightUnitMeasureCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureCode equals to DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageWeightUnitMeasureCode.equals=" + DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureCode equals to UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageWeightUnitMeasureCode.equals=" + UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightUnitMeasureCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureCode not equals to DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageWeightUnitMeasureCode.notEquals=" + DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureCode not equals to UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageWeightUnitMeasureCode.notEquals=" + UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightUnitMeasureCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureCode in DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE or UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageWeightUnitMeasureCode.in=" + DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE + "," + UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureCode equals to UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageWeightUnitMeasureCode.in=" + UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightUnitMeasureCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureCode is not null
        defaultStockItemTempShouldBeFound("itemPackageWeightUnitMeasureCode.specified=true");

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureCode is null
        defaultStockItemTempShouldNotBeFound("itemPackageWeightUnitMeasureCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightUnitMeasureCodeContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureCode contains DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageWeightUnitMeasureCode.contains=" + DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureCode contains UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageWeightUnitMeasureCode.contains=" + UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByItemPackageWeightUnitMeasureCodeNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureCode does not contain DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldNotBeFound("itemPackageWeightUnitMeasureCode.doesNotContain=" + DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE);

        // Get all the stockItemTempList where itemPackageWeightUnitMeasureCode does not contain UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE
        defaultStockItemTempShouldBeFound("itemPackageWeightUnitMeasureCode.doesNotContain=" + UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByNoOfPiecesIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where noOfPieces equals to DEFAULT_NO_OF_PIECES
        defaultStockItemTempShouldBeFound("noOfPieces.equals=" + DEFAULT_NO_OF_PIECES);

        // Get all the stockItemTempList where noOfPieces equals to UPDATED_NO_OF_PIECES
        defaultStockItemTempShouldNotBeFound("noOfPieces.equals=" + UPDATED_NO_OF_PIECES);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByNoOfPiecesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where noOfPieces not equals to DEFAULT_NO_OF_PIECES
        defaultStockItemTempShouldNotBeFound("noOfPieces.notEquals=" + DEFAULT_NO_OF_PIECES);

        // Get all the stockItemTempList where noOfPieces not equals to UPDATED_NO_OF_PIECES
        defaultStockItemTempShouldBeFound("noOfPieces.notEquals=" + UPDATED_NO_OF_PIECES);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByNoOfPiecesIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where noOfPieces in DEFAULT_NO_OF_PIECES or UPDATED_NO_OF_PIECES
        defaultStockItemTempShouldBeFound("noOfPieces.in=" + DEFAULT_NO_OF_PIECES + "," + UPDATED_NO_OF_PIECES);

        // Get all the stockItemTempList where noOfPieces equals to UPDATED_NO_OF_PIECES
        defaultStockItemTempShouldNotBeFound("noOfPieces.in=" + UPDATED_NO_OF_PIECES);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByNoOfPiecesIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where noOfPieces is not null
        defaultStockItemTempShouldBeFound("noOfPieces.specified=true");

        // Get all the stockItemTempList where noOfPieces is null
        defaultStockItemTempShouldNotBeFound("noOfPieces.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByNoOfPiecesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where noOfPieces is greater than or equal to DEFAULT_NO_OF_PIECES
        defaultStockItemTempShouldBeFound("noOfPieces.greaterThanOrEqual=" + DEFAULT_NO_OF_PIECES);

        // Get all the stockItemTempList where noOfPieces is greater than or equal to UPDATED_NO_OF_PIECES
        defaultStockItemTempShouldNotBeFound("noOfPieces.greaterThanOrEqual=" + UPDATED_NO_OF_PIECES);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByNoOfPiecesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where noOfPieces is less than or equal to DEFAULT_NO_OF_PIECES
        defaultStockItemTempShouldBeFound("noOfPieces.lessThanOrEqual=" + DEFAULT_NO_OF_PIECES);

        // Get all the stockItemTempList where noOfPieces is less than or equal to SMALLER_NO_OF_PIECES
        defaultStockItemTempShouldNotBeFound("noOfPieces.lessThanOrEqual=" + SMALLER_NO_OF_PIECES);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByNoOfPiecesIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where noOfPieces is less than DEFAULT_NO_OF_PIECES
        defaultStockItemTempShouldNotBeFound("noOfPieces.lessThan=" + DEFAULT_NO_OF_PIECES);

        // Get all the stockItemTempList where noOfPieces is less than UPDATED_NO_OF_PIECES
        defaultStockItemTempShouldBeFound("noOfPieces.lessThan=" + UPDATED_NO_OF_PIECES);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByNoOfPiecesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where noOfPieces is greater than DEFAULT_NO_OF_PIECES
        defaultStockItemTempShouldNotBeFound("noOfPieces.greaterThan=" + DEFAULT_NO_OF_PIECES);

        // Get all the stockItemTempList where noOfPieces is greater than SMALLER_NO_OF_PIECES
        defaultStockItemTempShouldBeFound("noOfPieces.greaterThan=" + SMALLER_NO_OF_PIECES);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByNoOfItemsIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where noOfItems equals to DEFAULT_NO_OF_ITEMS
        defaultStockItemTempShouldBeFound("noOfItems.equals=" + DEFAULT_NO_OF_ITEMS);

        // Get all the stockItemTempList where noOfItems equals to UPDATED_NO_OF_ITEMS
        defaultStockItemTempShouldNotBeFound("noOfItems.equals=" + UPDATED_NO_OF_ITEMS);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByNoOfItemsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where noOfItems not equals to DEFAULT_NO_OF_ITEMS
        defaultStockItemTempShouldNotBeFound("noOfItems.notEquals=" + DEFAULT_NO_OF_ITEMS);

        // Get all the stockItemTempList where noOfItems not equals to UPDATED_NO_OF_ITEMS
        defaultStockItemTempShouldBeFound("noOfItems.notEquals=" + UPDATED_NO_OF_ITEMS);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByNoOfItemsIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where noOfItems in DEFAULT_NO_OF_ITEMS or UPDATED_NO_OF_ITEMS
        defaultStockItemTempShouldBeFound("noOfItems.in=" + DEFAULT_NO_OF_ITEMS + "," + UPDATED_NO_OF_ITEMS);

        // Get all the stockItemTempList where noOfItems equals to UPDATED_NO_OF_ITEMS
        defaultStockItemTempShouldNotBeFound("noOfItems.in=" + UPDATED_NO_OF_ITEMS);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByNoOfItemsIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where noOfItems is not null
        defaultStockItemTempShouldBeFound("noOfItems.specified=true");

        // Get all the stockItemTempList where noOfItems is null
        defaultStockItemTempShouldNotBeFound("noOfItems.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByNoOfItemsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where noOfItems is greater than or equal to DEFAULT_NO_OF_ITEMS
        defaultStockItemTempShouldBeFound("noOfItems.greaterThanOrEqual=" + DEFAULT_NO_OF_ITEMS);

        // Get all the stockItemTempList where noOfItems is greater than or equal to UPDATED_NO_OF_ITEMS
        defaultStockItemTempShouldNotBeFound("noOfItems.greaterThanOrEqual=" + UPDATED_NO_OF_ITEMS);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByNoOfItemsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where noOfItems is less than or equal to DEFAULT_NO_OF_ITEMS
        defaultStockItemTempShouldBeFound("noOfItems.lessThanOrEqual=" + DEFAULT_NO_OF_ITEMS);

        // Get all the stockItemTempList where noOfItems is less than or equal to SMALLER_NO_OF_ITEMS
        defaultStockItemTempShouldNotBeFound("noOfItems.lessThanOrEqual=" + SMALLER_NO_OF_ITEMS);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByNoOfItemsIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where noOfItems is less than DEFAULT_NO_OF_ITEMS
        defaultStockItemTempShouldNotBeFound("noOfItems.lessThan=" + DEFAULT_NO_OF_ITEMS);

        // Get all the stockItemTempList where noOfItems is less than UPDATED_NO_OF_ITEMS
        defaultStockItemTempShouldBeFound("noOfItems.lessThan=" + UPDATED_NO_OF_ITEMS);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByNoOfItemsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where noOfItems is greater than DEFAULT_NO_OF_ITEMS
        defaultStockItemTempShouldNotBeFound("noOfItems.greaterThan=" + DEFAULT_NO_OF_ITEMS);

        // Get all the stockItemTempList where noOfItems is greater than SMALLER_NO_OF_ITEMS
        defaultStockItemTempShouldBeFound("noOfItems.greaterThan=" + SMALLER_NO_OF_ITEMS);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByManufactureIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where manufacture equals to DEFAULT_MANUFACTURE
        defaultStockItemTempShouldBeFound("manufacture.equals=" + DEFAULT_MANUFACTURE);

        // Get all the stockItemTempList where manufacture equals to UPDATED_MANUFACTURE
        defaultStockItemTempShouldNotBeFound("manufacture.equals=" + UPDATED_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByManufactureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where manufacture not equals to DEFAULT_MANUFACTURE
        defaultStockItemTempShouldNotBeFound("manufacture.notEquals=" + DEFAULT_MANUFACTURE);

        // Get all the stockItemTempList where manufacture not equals to UPDATED_MANUFACTURE
        defaultStockItemTempShouldBeFound("manufacture.notEquals=" + UPDATED_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByManufactureIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where manufacture in DEFAULT_MANUFACTURE or UPDATED_MANUFACTURE
        defaultStockItemTempShouldBeFound("manufacture.in=" + DEFAULT_MANUFACTURE + "," + UPDATED_MANUFACTURE);

        // Get all the stockItemTempList where manufacture equals to UPDATED_MANUFACTURE
        defaultStockItemTempShouldNotBeFound("manufacture.in=" + UPDATED_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByManufactureIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where manufacture is not null
        defaultStockItemTempShouldBeFound("manufacture.specified=true");

        // Get all the stockItemTempList where manufacture is null
        defaultStockItemTempShouldNotBeFound("manufacture.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByManufactureContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where manufacture contains DEFAULT_MANUFACTURE
        defaultStockItemTempShouldBeFound("manufacture.contains=" + DEFAULT_MANUFACTURE);

        // Get all the stockItemTempList where manufacture contains UPDATED_MANUFACTURE
        defaultStockItemTempShouldNotBeFound("manufacture.contains=" + UPDATED_MANUFACTURE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByManufactureNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where manufacture does not contain DEFAULT_MANUFACTURE
        defaultStockItemTempShouldNotBeFound("manufacture.doesNotContain=" + DEFAULT_MANUFACTURE);

        // Get all the stockItemTempList where manufacture does not contain UPDATED_MANUFACTURE
        defaultStockItemTempShouldBeFound("manufacture.doesNotContain=" + UPDATED_MANUFACTURE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByProductComplianceCertificateIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productComplianceCertificate equals to DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultStockItemTempShouldBeFound("productComplianceCertificate.equals=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the stockItemTempList where productComplianceCertificate equals to UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultStockItemTempShouldNotBeFound("productComplianceCertificate.equals=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductComplianceCertificateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productComplianceCertificate not equals to DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultStockItemTempShouldNotBeFound("productComplianceCertificate.notEquals=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the stockItemTempList where productComplianceCertificate not equals to UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultStockItemTempShouldBeFound("productComplianceCertificate.notEquals=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductComplianceCertificateIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productComplianceCertificate in DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE or UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultStockItemTempShouldBeFound("productComplianceCertificate.in=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE + "," + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the stockItemTempList where productComplianceCertificate equals to UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultStockItemTempShouldNotBeFound("productComplianceCertificate.in=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductComplianceCertificateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productComplianceCertificate is not null
        defaultStockItemTempShouldBeFound("productComplianceCertificate.specified=true");

        // Get all the stockItemTempList where productComplianceCertificate is null
        defaultStockItemTempShouldNotBeFound("productComplianceCertificate.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByProductComplianceCertificateContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productComplianceCertificate contains DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultStockItemTempShouldBeFound("productComplianceCertificate.contains=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the stockItemTempList where productComplianceCertificate contains UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultStockItemTempShouldNotBeFound("productComplianceCertificate.contains=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByProductComplianceCertificateNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where productComplianceCertificate does not contain DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultStockItemTempShouldNotBeFound("productComplianceCertificate.doesNotContain=" + DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE);

        // Get all the stockItemTempList where productComplianceCertificate does not contain UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE
        defaultStockItemTempShouldBeFound("productComplianceCertificate.doesNotContain=" + UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByGenuineAndLegalIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where genuineAndLegal equals to DEFAULT_GENUINE_AND_LEGAL
        defaultStockItemTempShouldBeFound("genuineAndLegal.equals=" + DEFAULT_GENUINE_AND_LEGAL);

        // Get all the stockItemTempList where genuineAndLegal equals to UPDATED_GENUINE_AND_LEGAL
        defaultStockItemTempShouldNotBeFound("genuineAndLegal.equals=" + UPDATED_GENUINE_AND_LEGAL);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByGenuineAndLegalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where genuineAndLegal not equals to DEFAULT_GENUINE_AND_LEGAL
        defaultStockItemTempShouldNotBeFound("genuineAndLegal.notEquals=" + DEFAULT_GENUINE_AND_LEGAL);

        // Get all the stockItemTempList where genuineAndLegal not equals to UPDATED_GENUINE_AND_LEGAL
        defaultStockItemTempShouldBeFound("genuineAndLegal.notEquals=" + UPDATED_GENUINE_AND_LEGAL);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByGenuineAndLegalIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where genuineAndLegal in DEFAULT_GENUINE_AND_LEGAL or UPDATED_GENUINE_AND_LEGAL
        defaultStockItemTempShouldBeFound("genuineAndLegal.in=" + DEFAULT_GENUINE_AND_LEGAL + "," + UPDATED_GENUINE_AND_LEGAL);

        // Get all the stockItemTempList where genuineAndLegal equals to UPDATED_GENUINE_AND_LEGAL
        defaultStockItemTempShouldNotBeFound("genuineAndLegal.in=" + UPDATED_GENUINE_AND_LEGAL);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByGenuineAndLegalIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where genuineAndLegal is not null
        defaultStockItemTempShouldBeFound("genuineAndLegal.specified=true");

        // Get all the stockItemTempList where genuineAndLegal is null
        defaultStockItemTempShouldNotBeFound("genuineAndLegal.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByCountryOfOriginIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where countryOfOrigin equals to DEFAULT_COUNTRY_OF_ORIGIN
        defaultStockItemTempShouldBeFound("countryOfOrigin.equals=" + DEFAULT_COUNTRY_OF_ORIGIN);

        // Get all the stockItemTempList where countryOfOrigin equals to UPDATED_COUNTRY_OF_ORIGIN
        defaultStockItemTempShouldNotBeFound("countryOfOrigin.equals=" + UPDATED_COUNTRY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByCountryOfOriginIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where countryOfOrigin not equals to DEFAULT_COUNTRY_OF_ORIGIN
        defaultStockItemTempShouldNotBeFound("countryOfOrigin.notEquals=" + DEFAULT_COUNTRY_OF_ORIGIN);

        // Get all the stockItemTempList where countryOfOrigin not equals to UPDATED_COUNTRY_OF_ORIGIN
        defaultStockItemTempShouldBeFound("countryOfOrigin.notEquals=" + UPDATED_COUNTRY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByCountryOfOriginIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where countryOfOrigin in DEFAULT_COUNTRY_OF_ORIGIN or UPDATED_COUNTRY_OF_ORIGIN
        defaultStockItemTempShouldBeFound("countryOfOrigin.in=" + DEFAULT_COUNTRY_OF_ORIGIN + "," + UPDATED_COUNTRY_OF_ORIGIN);

        // Get all the stockItemTempList where countryOfOrigin equals to UPDATED_COUNTRY_OF_ORIGIN
        defaultStockItemTempShouldNotBeFound("countryOfOrigin.in=" + UPDATED_COUNTRY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByCountryOfOriginIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where countryOfOrigin is not null
        defaultStockItemTempShouldBeFound("countryOfOrigin.specified=true");

        // Get all the stockItemTempList where countryOfOrigin is null
        defaultStockItemTempShouldNotBeFound("countryOfOrigin.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByCountryOfOriginContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where countryOfOrigin contains DEFAULT_COUNTRY_OF_ORIGIN
        defaultStockItemTempShouldBeFound("countryOfOrigin.contains=" + DEFAULT_COUNTRY_OF_ORIGIN);

        // Get all the stockItemTempList where countryOfOrigin contains UPDATED_COUNTRY_OF_ORIGIN
        defaultStockItemTempShouldNotBeFound("countryOfOrigin.contains=" + UPDATED_COUNTRY_OF_ORIGIN);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByCountryOfOriginNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where countryOfOrigin does not contain DEFAULT_COUNTRY_OF_ORIGIN
        defaultStockItemTempShouldNotBeFound("countryOfOrigin.doesNotContain=" + DEFAULT_COUNTRY_OF_ORIGIN);

        // Get all the stockItemTempList where countryOfOrigin does not contain UPDATED_COUNTRY_OF_ORIGIN
        defaultStockItemTempShouldBeFound("countryOfOrigin.doesNotContain=" + UPDATED_COUNTRY_OF_ORIGIN);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsBySellStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where sellStartDate equals to DEFAULT_SELL_START_DATE
        defaultStockItemTempShouldBeFound("sellStartDate.equals=" + DEFAULT_SELL_START_DATE);

        // Get all the stockItemTempList where sellStartDate equals to UPDATED_SELL_START_DATE
        defaultStockItemTempShouldNotBeFound("sellStartDate.equals=" + UPDATED_SELL_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsBySellStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where sellStartDate not equals to DEFAULT_SELL_START_DATE
        defaultStockItemTempShouldNotBeFound("sellStartDate.notEquals=" + DEFAULT_SELL_START_DATE);

        // Get all the stockItemTempList where sellStartDate not equals to UPDATED_SELL_START_DATE
        defaultStockItemTempShouldBeFound("sellStartDate.notEquals=" + UPDATED_SELL_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsBySellStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where sellStartDate in DEFAULT_SELL_START_DATE or UPDATED_SELL_START_DATE
        defaultStockItemTempShouldBeFound("sellStartDate.in=" + DEFAULT_SELL_START_DATE + "," + UPDATED_SELL_START_DATE);

        // Get all the stockItemTempList where sellStartDate equals to UPDATED_SELL_START_DATE
        defaultStockItemTempShouldNotBeFound("sellStartDate.in=" + UPDATED_SELL_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsBySellStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where sellStartDate is not null
        defaultStockItemTempShouldBeFound("sellStartDate.specified=true");

        // Get all the stockItemTempList where sellStartDate is null
        defaultStockItemTempShouldNotBeFound("sellStartDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsBySellEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where sellEndDate equals to DEFAULT_SELL_END_DATE
        defaultStockItemTempShouldBeFound("sellEndDate.equals=" + DEFAULT_SELL_END_DATE);

        // Get all the stockItemTempList where sellEndDate equals to UPDATED_SELL_END_DATE
        defaultStockItemTempShouldNotBeFound("sellEndDate.equals=" + UPDATED_SELL_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsBySellEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where sellEndDate not equals to DEFAULT_SELL_END_DATE
        defaultStockItemTempShouldNotBeFound("sellEndDate.notEquals=" + DEFAULT_SELL_END_DATE);

        // Get all the stockItemTempList where sellEndDate not equals to UPDATED_SELL_END_DATE
        defaultStockItemTempShouldBeFound("sellEndDate.notEquals=" + UPDATED_SELL_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsBySellEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where sellEndDate in DEFAULT_SELL_END_DATE or UPDATED_SELL_END_DATE
        defaultStockItemTempShouldBeFound("sellEndDate.in=" + DEFAULT_SELL_END_DATE + "," + UPDATED_SELL_END_DATE);

        // Get all the stockItemTempList where sellEndDate equals to UPDATED_SELL_END_DATE
        defaultStockItemTempShouldNotBeFound("sellEndDate.in=" + UPDATED_SELL_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsBySellEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where sellEndDate is not null
        defaultStockItemTempShouldBeFound("sellEndDate.specified=true");

        // Get all the stockItemTempList where sellEndDate is null
        defaultStockItemTempShouldNotBeFound("sellEndDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where status equals to DEFAULT_STATUS
        defaultStockItemTempShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the stockItemTempList where status equals to UPDATED_STATUS
        defaultStockItemTempShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where status not equals to DEFAULT_STATUS
        defaultStockItemTempShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the stockItemTempList where status not equals to UPDATED_STATUS
        defaultStockItemTempShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultStockItemTempShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the stockItemTempList where status equals to UPDATED_STATUS
        defaultStockItemTempShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where status is not null
        defaultStockItemTempShouldBeFound("status.specified=true");

        // Get all the stockItemTempList where status is null
        defaultStockItemTempShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where status is greater than or equal to DEFAULT_STATUS
        defaultStockItemTempShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the stockItemTempList where status is greater than or equal to UPDATED_STATUS
        defaultStockItemTempShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where status is less than or equal to DEFAULT_STATUS
        defaultStockItemTempShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the stockItemTempList where status is less than or equal to SMALLER_STATUS
        defaultStockItemTempShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where status is less than DEFAULT_STATUS
        defaultStockItemTempShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the stockItemTempList where status is less than UPDATED_STATUS
        defaultStockItemTempShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where status is greater than DEFAULT_STATUS
        defaultStockItemTempShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the stockItemTempList where status is greater than SMALLER_STATUS
        defaultStockItemTempShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultStockItemTempShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the stockItemTempList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultStockItemTempShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultStockItemTempShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the stockItemTempList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultStockItemTempShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultStockItemTempShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the stockItemTempList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultStockItemTempShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where lastEditedBy is not null
        defaultStockItemTempShouldBeFound("lastEditedBy.specified=true");

        // Get all the stockItemTempList where lastEditedBy is null
        defaultStockItemTempShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllStockItemTempsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultStockItemTempShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the stockItemTempList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultStockItemTempShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultStockItemTempShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the stockItemTempList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultStockItemTempShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllStockItemTempsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultStockItemTempShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the stockItemTempList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultStockItemTempShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultStockItemTempShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the stockItemTempList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultStockItemTempShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultStockItemTempShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the stockItemTempList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultStockItemTempShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        // Get all the stockItemTempList where lastEditedWhen is not null
        defaultStockItemTempShouldBeFound("lastEditedWhen.specified=true");

        // Get all the stockItemTempList where lastEditedWhen is null
        defaultStockItemTempShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockItemTempsByUploadTransactionIsEqualToSomething() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);
        UploadTransactions uploadTransaction = UploadTransactionsResourceIT.createEntity(em);
        em.persist(uploadTransaction);
        em.flush();
        stockItemTemp.setUploadTransaction(uploadTransaction);
        stockItemTempRepository.saveAndFlush(stockItemTemp);
        Long uploadTransactionId = uploadTransaction.getId();

        // Get all the stockItemTempList where uploadTransaction equals to uploadTransactionId
        defaultStockItemTempShouldBeFound("uploadTransactionId.equals=" + uploadTransactionId);

        // Get all the stockItemTempList where uploadTransaction equals to uploadTransactionId + 1
        defaultStockItemTempShouldNotBeFound("uploadTransactionId.equals=" + (uploadTransactionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStockItemTempShouldBeFound(String filter) throws Exception {
        restStockItemTempMockMvc.perform(get("/api/stock-item-temps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItemTemp.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockItemName").value(hasItem(DEFAULT_STOCK_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].vendorCode").value(hasItem(DEFAULT_VENDOR_CODE)))
            .andExpect(jsonPath("$.[*].vendorSKU").value(hasItem(DEFAULT_VENDOR_SKU)))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE)))
            .andExpect(jsonPath("$.[*].barcodeTypeId").value(hasItem(DEFAULT_BARCODE_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].barcodeTypeName").value(hasItem(DEFAULT_BARCODE_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE)))
            .andExpect(jsonPath("$.[*].productCategoryId").value(hasItem(DEFAULT_PRODUCT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].productCategoryName").value(hasItem(DEFAULT_PRODUCT_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].productAttributeSetId").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_SET_ID.intValue())))
            .andExpect(jsonPath("$.[*].productAttributeId").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_ID.intValue())))
            .andExpect(jsonPath("$.[*].productAttributeValue").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_VALUE)))
            .andExpect(jsonPath("$.[*].productOptionSetId").value(hasItem(DEFAULT_PRODUCT_OPTION_SET_ID.intValue())))
            .andExpect(jsonPath("$.[*].productOptionId").value(hasItem(DEFAULT_PRODUCT_OPTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].productOptionValue").value(hasItem(DEFAULT_PRODUCT_OPTION_VALUE)))
            .andExpect(jsonPath("$.[*].modelName").value(hasItem(DEFAULT_MODEL_NAME)))
            .andExpect(jsonPath("$.[*].modelNumber").value(hasItem(DEFAULT_MODEL_NUMBER)))
            .andExpect(jsonPath("$.[*].materialId").value(hasItem(DEFAULT_MATERIAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].materialName").value(hasItem(DEFAULT_MATERIAL_NAME)))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].productBrandId").value(hasItem(DEFAULT_PRODUCT_BRAND_ID.intValue())))
            .andExpect(jsonPath("$.[*].productBrandName").value(hasItem(DEFAULT_PRODUCT_BRAND_NAME)))
            .andExpect(jsonPath("$.[*].highlights").value(hasItem(DEFAULT_HIGHLIGHTS.toString())))
            .andExpect(jsonPath("$.[*].searchDetails").value(hasItem(DEFAULT_SEARCH_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].careInstructions").value(hasItem(DEFAULT_CARE_INSTRUCTIONS.toString())))
            .andExpect(jsonPath("$.[*].dangerousGoods").value(hasItem(DEFAULT_DANGEROUS_GOODS)))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].remommendedRetailPrice").value(hasItem(DEFAULT_REMOMMENDED_RETAIL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].quantityOnHand").value(hasItem(DEFAULT_QUANTITY_ON_HAND)))
            .andExpect(jsonPath("$.[*].warrantyPeriod").value(hasItem(DEFAULT_WARRANTY_PERIOD)))
            .andExpect(jsonPath("$.[*].warrantyPolicy").value(hasItem(DEFAULT_WARRANTY_POLICY)))
            .andExpect(jsonPath("$.[*].warrantyTypeId").value(hasItem(DEFAULT_WARRANTY_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].warrantyTypeName").value(hasItem(DEFAULT_WARRANTY_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].whatInTheBox").value(hasItem(DEFAULT_WHAT_IN_THE_BOX.toString())))
            .andExpect(jsonPath("$.[*].itemLength").value(hasItem(DEFAULT_ITEM_LENGTH)))
            .andExpect(jsonPath("$.[*].itemWidth").value(hasItem(DEFAULT_ITEM_WIDTH)))
            .andExpect(jsonPath("$.[*].itemHeight").value(hasItem(DEFAULT_ITEM_HEIGHT)))
            .andExpect(jsonPath("$.[*].itemWeight").value(hasItem(DEFAULT_ITEM_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].itemPackageLength").value(hasItem(DEFAULT_ITEM_PACKAGE_LENGTH)))
            .andExpect(jsonPath("$.[*].itemPackageWidth").value(hasItem(DEFAULT_ITEM_PACKAGE_WIDTH)))
            .andExpect(jsonPath("$.[*].itemPackageHeight").value(hasItem(DEFAULT_ITEM_PACKAGE_HEIGHT)))
            .andExpect(jsonPath("$.[*].itemPackageWeight").value(hasItem(DEFAULT_ITEM_PACKAGE_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].itemLengthUnitMeasureId").value(hasItem(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemLengthUnitMeasureCode").value(hasItem(DEFAULT_ITEM_LENGTH_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].itemWidthUnitMeasureId").value(hasItem(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemWidthUnitMeasureCode").value(hasItem(DEFAULT_ITEM_WIDTH_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].itemHeightUnitMeasureId").value(hasItem(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemHeightUnitMeasureCode").value(hasItem(DEFAULT_ITEM_HEIGHT_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].itemWeightUnitMeasureId").value(hasItem(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemWeightUnitMeasureCode").value(hasItem(DEFAULT_ITEM_WEIGHT_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].itemPackageLengthUnitMeasureId").value(hasItem(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemPackageLengthUnitMeasureCode").value(hasItem(DEFAULT_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].itemPackageWidthUnitMeasureId").value(hasItem(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemPackageWidthUnitMeasureCode").value(hasItem(DEFAULT_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].itemPackageHeightUnitMeasureId").value(hasItem(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemPackageHeightUnitMeasureCode").value(hasItem(DEFAULT_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].itemPackageWeightUnitMeasureId").value(hasItem(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemPackageWeightUnitMeasureCode").value(hasItem(DEFAULT_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].noOfPieces").value(hasItem(DEFAULT_NO_OF_PIECES)))
            .andExpect(jsonPath("$.[*].noOfItems").value(hasItem(DEFAULT_NO_OF_ITEMS)))
            .andExpect(jsonPath("$.[*].manufacture").value(hasItem(DEFAULT_MANUFACTURE)))
            .andExpect(jsonPath("$.[*].specialFeactures").value(hasItem(DEFAULT_SPECIAL_FEACTURES.toString())))
            .andExpect(jsonPath("$.[*].productComplianceCertificate").value(hasItem(DEFAULT_PRODUCT_COMPLIANCE_CERTIFICATE)))
            .andExpect(jsonPath("$.[*].genuineAndLegal").value(hasItem(DEFAULT_GENUINE_AND_LEGAL.booleanValue())))
            .andExpect(jsonPath("$.[*].countryOfOrigin").value(hasItem(DEFAULT_COUNTRY_OF_ORIGIN)))
            .andExpect(jsonPath("$.[*].usageAndSideEffects").value(hasItem(DEFAULT_USAGE_AND_SIDE_EFFECTS.toString())))
            .andExpect(jsonPath("$.[*].safetyWarnning").value(hasItem(DEFAULT_SAFETY_WARNNING.toString())))
            .andExpect(jsonPath("$.[*].sellStartDate").value(hasItem(DEFAULT_SELL_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].sellEndDate").value(hasItem(DEFAULT_SELL_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restStockItemTempMockMvc.perform(get("/api/stock-item-temps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStockItemTempShouldNotBeFound(String filter) throws Exception {
        restStockItemTempMockMvc.perform(get("/api/stock-item-temps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockItemTempMockMvc.perform(get("/api/stock-item-temps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStockItemTemp() throws Exception {
        // Get the stockItemTemp
        restStockItemTempMockMvc.perform(get("/api/stock-item-temps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockItemTemp() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        int databaseSizeBeforeUpdate = stockItemTempRepository.findAll().size();

        // Update the stockItemTemp
        StockItemTemp updatedStockItemTemp = stockItemTempRepository.findById(stockItemTemp.getId()).get();
        // Disconnect from session so that the updates on updatedStockItemTemp are not directly saved in db
        em.detach(updatedStockItemTemp);
        updatedStockItemTemp
            .stockItemName(UPDATED_STOCK_ITEM_NAME)
            .vendorCode(UPDATED_VENDOR_CODE)
            .vendorSKU(UPDATED_VENDOR_SKU)
            .barcode(UPDATED_BARCODE)
            .barcodeTypeId(UPDATED_BARCODE_TYPE_ID)
            .barcodeTypeName(UPDATED_BARCODE_TYPE_NAME)
            .productType(UPDATED_PRODUCT_TYPE)
            .productCategoryId(UPDATED_PRODUCT_CATEGORY_ID)
            .productCategoryName(UPDATED_PRODUCT_CATEGORY_NAME)
            .productAttributeSetId(UPDATED_PRODUCT_ATTRIBUTE_SET_ID)
            .productAttributeId(UPDATED_PRODUCT_ATTRIBUTE_ID)
            .productAttributeValue(UPDATED_PRODUCT_ATTRIBUTE_VALUE)
            .productOptionSetId(UPDATED_PRODUCT_OPTION_SET_ID)
            .productOptionId(UPDATED_PRODUCT_OPTION_ID)
            .productOptionValue(UPDATED_PRODUCT_OPTION_VALUE)
            .modelName(UPDATED_MODEL_NAME)
            .modelNumber(UPDATED_MODEL_NUMBER)
            .materialId(UPDATED_MATERIAL_ID)
            .materialName(UPDATED_MATERIAL_NAME)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .description(UPDATED_DESCRIPTION)
            .productBrandId(UPDATED_PRODUCT_BRAND_ID)
            .productBrandName(UPDATED_PRODUCT_BRAND_NAME)
            .highlights(UPDATED_HIGHLIGHTS)
            .searchDetails(UPDATED_SEARCH_DETAILS)
            .careInstructions(UPDATED_CARE_INSTRUCTIONS)
            .dangerousGoods(UPDATED_DANGEROUS_GOODS)
            .videoUrl(UPDATED_VIDEO_URL)
            .unitPrice(UPDATED_UNIT_PRICE)
            .remommendedRetailPrice(UPDATED_REMOMMENDED_RETAIL_PRICE)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .quantityOnHand(UPDATED_QUANTITY_ON_HAND)
            .warrantyPeriod(UPDATED_WARRANTY_PERIOD)
            .warrantyPolicy(UPDATED_WARRANTY_POLICY)
            .warrantyTypeId(UPDATED_WARRANTY_TYPE_ID)
            .warrantyTypeName(UPDATED_WARRANTY_TYPE_NAME)
            .whatInTheBox(UPDATED_WHAT_IN_THE_BOX)
            .itemLength(UPDATED_ITEM_LENGTH)
            .itemWidth(UPDATED_ITEM_WIDTH)
            .itemHeight(UPDATED_ITEM_HEIGHT)
            .itemWeight(UPDATED_ITEM_WEIGHT)
            .itemPackageLength(UPDATED_ITEM_PACKAGE_LENGTH)
            .itemPackageWidth(UPDATED_ITEM_PACKAGE_WIDTH)
            .itemPackageHeight(UPDATED_ITEM_PACKAGE_HEIGHT)
            .itemPackageWeight(UPDATED_ITEM_PACKAGE_WEIGHT)
            .itemLengthUnitMeasureId(UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID)
            .itemLengthUnitMeasureCode(UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE)
            .itemWidthUnitMeasureId(UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID)
            .itemWidthUnitMeasureCode(UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE)
            .itemHeightUnitMeasureId(UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID)
            .itemHeightUnitMeasureCode(UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE)
            .itemWeightUnitMeasureId(UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID)
            .itemWeightUnitMeasureCode(UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE)
            .itemPackageLengthUnitMeasureId(UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID)
            .itemPackageLengthUnitMeasureCode(UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE)
            .itemPackageWidthUnitMeasureId(UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID)
            .itemPackageWidthUnitMeasureCode(UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE)
            .itemPackageHeightUnitMeasureId(UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID)
            .itemPackageHeightUnitMeasureCode(UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE)
            .itemPackageWeightUnitMeasureId(UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID)
            .itemPackageWeightUnitMeasureCode(UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE)
            .noOfPieces(UPDATED_NO_OF_PIECES)
            .noOfItems(UPDATED_NO_OF_ITEMS)
            .manufacture(UPDATED_MANUFACTURE)
            .specialFeactures(UPDATED_SPECIAL_FEACTURES)
            .productComplianceCertificate(UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE)
            .genuineAndLegal(UPDATED_GENUINE_AND_LEGAL)
            .countryOfOrigin(UPDATED_COUNTRY_OF_ORIGIN)
            .usageAndSideEffects(UPDATED_USAGE_AND_SIDE_EFFECTS)
            .safetyWarnning(UPDATED_SAFETY_WARNNING)
            .sellStartDate(UPDATED_SELL_START_DATE)
            .sellEndDate(UPDATED_SELL_END_DATE)
            .status(UPDATED_STATUS)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        StockItemTempDTO stockItemTempDTO = stockItemTempMapper.toDto(updatedStockItemTemp);

        restStockItemTempMockMvc.perform(put("/api/stock-item-temps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTempDTO)))
            .andExpect(status().isOk());

        // Validate the StockItemTemp in the database
        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeUpdate);
        StockItemTemp testStockItemTemp = stockItemTempList.get(stockItemTempList.size() - 1);
        assertThat(testStockItemTemp.getStockItemName()).isEqualTo(UPDATED_STOCK_ITEM_NAME);
        assertThat(testStockItemTemp.getVendorCode()).isEqualTo(UPDATED_VENDOR_CODE);
        assertThat(testStockItemTemp.getVendorSKU()).isEqualTo(UPDATED_VENDOR_SKU);
        assertThat(testStockItemTemp.getBarcode()).isEqualTo(UPDATED_BARCODE);
        assertThat(testStockItemTemp.getBarcodeTypeId()).isEqualTo(UPDATED_BARCODE_TYPE_ID);
        assertThat(testStockItemTemp.getBarcodeTypeName()).isEqualTo(UPDATED_BARCODE_TYPE_NAME);
        assertThat(testStockItemTemp.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testStockItemTemp.getProductCategoryId()).isEqualTo(UPDATED_PRODUCT_CATEGORY_ID);
        assertThat(testStockItemTemp.getProductCategoryName()).isEqualTo(UPDATED_PRODUCT_CATEGORY_NAME);
        assertThat(testStockItemTemp.getProductAttributeSetId()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_SET_ID);
        assertThat(testStockItemTemp.getProductAttributeId()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_ID);
        assertThat(testStockItemTemp.getProductAttributeValue()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_VALUE);
        assertThat(testStockItemTemp.getProductOptionSetId()).isEqualTo(UPDATED_PRODUCT_OPTION_SET_ID);
        assertThat(testStockItemTemp.getProductOptionId()).isEqualTo(UPDATED_PRODUCT_OPTION_ID);
        assertThat(testStockItemTemp.getProductOptionValue()).isEqualTo(UPDATED_PRODUCT_OPTION_VALUE);
        assertThat(testStockItemTemp.getModelName()).isEqualTo(UPDATED_MODEL_NAME);
        assertThat(testStockItemTemp.getModelNumber()).isEqualTo(UPDATED_MODEL_NUMBER);
        assertThat(testStockItemTemp.getMaterialId()).isEqualTo(UPDATED_MATERIAL_ID);
        assertThat(testStockItemTemp.getMaterialName()).isEqualTo(UPDATED_MATERIAL_NAME);
        assertThat(testStockItemTemp.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testStockItemTemp.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStockItemTemp.getProductBrandId()).isEqualTo(UPDATED_PRODUCT_BRAND_ID);
        assertThat(testStockItemTemp.getProductBrandName()).isEqualTo(UPDATED_PRODUCT_BRAND_NAME);
        assertThat(testStockItemTemp.getHighlights()).isEqualTo(UPDATED_HIGHLIGHTS);
        assertThat(testStockItemTemp.getSearchDetails()).isEqualTo(UPDATED_SEARCH_DETAILS);
        assertThat(testStockItemTemp.getCareInstructions()).isEqualTo(UPDATED_CARE_INSTRUCTIONS);
        assertThat(testStockItemTemp.getDangerousGoods()).isEqualTo(UPDATED_DANGEROUS_GOODS);
        assertThat(testStockItemTemp.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testStockItemTemp.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testStockItemTemp.getRemommendedRetailPrice()).isEqualTo(UPDATED_REMOMMENDED_RETAIL_PRICE);
        assertThat(testStockItemTemp.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testStockItemTemp.getQuantityOnHand()).isEqualTo(UPDATED_QUANTITY_ON_HAND);
        assertThat(testStockItemTemp.getWarrantyPeriod()).isEqualTo(UPDATED_WARRANTY_PERIOD);
        assertThat(testStockItemTemp.getWarrantyPolicy()).isEqualTo(UPDATED_WARRANTY_POLICY);
        assertThat(testStockItemTemp.getWarrantyTypeId()).isEqualTo(UPDATED_WARRANTY_TYPE_ID);
        assertThat(testStockItemTemp.getWarrantyTypeName()).isEqualTo(UPDATED_WARRANTY_TYPE_NAME);
        assertThat(testStockItemTemp.getWhatInTheBox()).isEqualTo(UPDATED_WHAT_IN_THE_BOX);
        assertThat(testStockItemTemp.getItemLength()).isEqualTo(UPDATED_ITEM_LENGTH);
        assertThat(testStockItemTemp.getItemWidth()).isEqualTo(UPDATED_ITEM_WIDTH);
        assertThat(testStockItemTemp.getItemHeight()).isEqualTo(UPDATED_ITEM_HEIGHT);
        assertThat(testStockItemTemp.getItemWeight()).isEqualTo(UPDATED_ITEM_WEIGHT);
        assertThat(testStockItemTemp.getItemPackageLength()).isEqualTo(UPDATED_ITEM_PACKAGE_LENGTH);
        assertThat(testStockItemTemp.getItemPackageWidth()).isEqualTo(UPDATED_ITEM_PACKAGE_WIDTH);
        assertThat(testStockItemTemp.getItemPackageHeight()).isEqualTo(UPDATED_ITEM_PACKAGE_HEIGHT);
        assertThat(testStockItemTemp.getItemPackageWeight()).isEqualTo(UPDATED_ITEM_PACKAGE_WEIGHT);
        assertThat(testStockItemTemp.getItemLengthUnitMeasureId()).isEqualTo(UPDATED_ITEM_LENGTH_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemLengthUnitMeasureCode()).isEqualTo(UPDATED_ITEM_LENGTH_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemWidthUnitMeasureId()).isEqualTo(UPDATED_ITEM_WIDTH_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemWidthUnitMeasureCode()).isEqualTo(UPDATED_ITEM_WIDTH_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemHeightUnitMeasureId()).isEqualTo(UPDATED_ITEM_HEIGHT_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemHeightUnitMeasureCode()).isEqualTo(UPDATED_ITEM_HEIGHT_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemWeightUnitMeasureId()).isEqualTo(UPDATED_ITEM_WEIGHT_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemWeightUnitMeasureCode()).isEqualTo(UPDATED_ITEM_WEIGHT_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemPackageLengthUnitMeasureId()).isEqualTo(UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemPackageLengthUnitMeasureCode()).isEqualTo(UPDATED_ITEM_PACKAGE_LENGTH_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemPackageWidthUnitMeasureId()).isEqualTo(UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemPackageWidthUnitMeasureCode()).isEqualTo(UPDATED_ITEM_PACKAGE_WIDTH_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemPackageHeightUnitMeasureId()).isEqualTo(UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemPackageHeightUnitMeasureCode()).isEqualTo(UPDATED_ITEM_PACKAGE_HEIGHT_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getItemPackageWeightUnitMeasureId()).isEqualTo(UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_ID);
        assertThat(testStockItemTemp.getItemPackageWeightUnitMeasureCode()).isEqualTo(UPDATED_ITEM_PACKAGE_WEIGHT_UNIT_MEASURE_CODE);
        assertThat(testStockItemTemp.getNoOfPieces()).isEqualTo(UPDATED_NO_OF_PIECES);
        assertThat(testStockItemTemp.getNoOfItems()).isEqualTo(UPDATED_NO_OF_ITEMS);
        assertThat(testStockItemTemp.getManufacture()).isEqualTo(UPDATED_MANUFACTURE);
        assertThat(testStockItemTemp.getSpecialFeactures()).isEqualTo(UPDATED_SPECIAL_FEACTURES);
        assertThat(testStockItemTemp.getProductComplianceCertificate()).isEqualTo(UPDATED_PRODUCT_COMPLIANCE_CERTIFICATE);
        assertThat(testStockItemTemp.isGenuineAndLegal()).isEqualTo(UPDATED_GENUINE_AND_LEGAL);
        assertThat(testStockItemTemp.getCountryOfOrigin()).isEqualTo(UPDATED_COUNTRY_OF_ORIGIN);
        assertThat(testStockItemTemp.getUsageAndSideEffects()).isEqualTo(UPDATED_USAGE_AND_SIDE_EFFECTS);
        assertThat(testStockItemTemp.getSafetyWarnning()).isEqualTo(UPDATED_SAFETY_WARNNING);
        assertThat(testStockItemTemp.getSellStartDate()).isEqualTo(UPDATED_SELL_START_DATE);
        assertThat(testStockItemTemp.getSellEndDate()).isEqualTo(UPDATED_SELL_END_DATE);
        assertThat(testStockItemTemp.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testStockItemTemp.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testStockItemTemp.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingStockItemTemp() throws Exception {
        int databaseSizeBeforeUpdate = stockItemTempRepository.findAll().size();

        // Create the StockItemTemp
        StockItemTempDTO stockItemTempDTO = stockItemTempMapper.toDto(stockItemTemp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockItemTempMockMvc.perform(put("/api/stock-item-temps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemTempDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItemTemp in the database
        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStockItemTemp() throws Exception {
        // Initialize the database
        stockItemTempRepository.saveAndFlush(stockItemTemp);

        int databaseSizeBeforeDelete = stockItemTempRepository.findAll().size();

        // Delete the stockItemTemp
        restStockItemTempMockMvc.perform(delete("/api/stock-item-temps/{id}", stockItemTemp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockItemTemp> stockItemTempList = stockItemTempRepository.findAll();
        assertThat(stockItemTempList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
