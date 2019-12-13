import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StockItemTempComponentsPage, StockItemTempDeleteDialog, StockItemTempUpdatePage } from './stock-item-temp.page-object';

const expect = chai.expect;

describe('StockItemTemp e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let stockItemTempComponentsPage: StockItemTempComponentsPage;
  let stockItemTempUpdatePage: StockItemTempUpdatePage;
  let stockItemTempDeleteDialog: StockItemTempDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load StockItemTemps', async () => {
    await navBarPage.goToEntity('stock-item-temp');
    stockItemTempComponentsPage = new StockItemTempComponentsPage();
    await browser.wait(ec.visibilityOf(stockItemTempComponentsPage.title), 5000);
    expect(await stockItemTempComponentsPage.getTitle()).to.eq('epmwebApp.stockItemTemp.home.title');
  });

  it('should load create StockItemTemp page', async () => {
    await stockItemTempComponentsPage.clickOnCreateButton();
    stockItemTempUpdatePage = new StockItemTempUpdatePage();
    expect(await stockItemTempUpdatePage.getPageTitle()).to.eq('epmwebApp.stockItemTemp.home.createOrEditLabel');
    await stockItemTempUpdatePage.cancel();
  });

  it('should create and save StockItemTemps', async () => {
    const nbButtonsBeforeCreate = await stockItemTempComponentsPage.countDeleteButtons();

    await stockItemTempComponentsPage.clickOnCreateButton();
    await promise.all([
      stockItemTempUpdatePage.setStockItemNameInput('stockItemName'),
      stockItemTempUpdatePage.setVendorCodeInput('vendorCode'),
      stockItemTempUpdatePage.setVendorSKUInput('vendorSKU'),
      stockItemTempUpdatePage.setBarcodeInput('barcode'),
      stockItemTempUpdatePage.setBarcodeTypeIdInput('5'),
      stockItemTempUpdatePage.setBarcodeTypeNameInput('barcodeTypeName'),
      stockItemTempUpdatePage.setProductTypeInput('productType'),
      stockItemTempUpdatePage.setProductCategoryIdInput('5'),
      stockItemTempUpdatePage.setProductCategoryNameInput('productCategoryName'),
      stockItemTempUpdatePage.setProductAttributeSetIdInput('5'),
      stockItemTempUpdatePage.setProductAttributeIdInput('5'),
      stockItemTempUpdatePage.setProductAttributeValueInput('productAttributeValue'),
      stockItemTempUpdatePage.setProductOptionSetIdInput('5'),
      stockItemTempUpdatePage.setProductOptionIdInput('5'),
      stockItemTempUpdatePage.setProductOptionValueInput('productOptionValue'),
      stockItemTempUpdatePage.setModelNameInput('modelName'),
      stockItemTempUpdatePage.setModelNumberInput('modelNumber'),
      stockItemTempUpdatePage.setMaterialIdInput('5'),
      stockItemTempUpdatePage.setMaterialNameInput('materialName'),
      stockItemTempUpdatePage.setShortDescriptionInput('shortDescription'),
      stockItemTempUpdatePage.setDescriptionInput('description'),
      stockItemTempUpdatePage.setProductBrandIdInput('5'),
      stockItemTempUpdatePage.setProductBrandNameInput('productBrandName'),
      stockItemTempUpdatePage.setHighlightsInput('highlights'),
      stockItemTempUpdatePage.setSearchDetailsInput('searchDetails'),
      stockItemTempUpdatePage.setCareInstructionsInput('careInstructions'),
      stockItemTempUpdatePage.setDangerousGoodsInput('dangerousGoods'),
      stockItemTempUpdatePage.setVideoUrlInput('videoUrl'),
      stockItemTempUpdatePage.setUnitPriceInput('5'),
      stockItemTempUpdatePage.setRemommendedRetailPriceInput('5'),
      stockItemTempUpdatePage.setCurrencyCodeInput('currencyCode'),
      stockItemTempUpdatePage.setQuantityOnHandInput('5'),
      stockItemTempUpdatePage.setWarrantyPeriodInput('warrantyPeriod'),
      stockItemTempUpdatePage.setWarrantyPolicyInput('warrantyPolicy'),
      stockItemTempUpdatePage.setWarrantyTypeIdInput('5'),
      stockItemTempUpdatePage.setWarrantyTypeNameInput('warrantyTypeName'),
      stockItemTempUpdatePage.setWhatInTheBoxInput('whatInTheBox'),
      stockItemTempUpdatePage.setItemLengthInput('5'),
      stockItemTempUpdatePage.setItemWidthInput('5'),
      stockItemTempUpdatePage.setItemHeightInput('5'),
      stockItemTempUpdatePage.setItemWeightInput('5'),
      stockItemTempUpdatePage.setItemPackageLengthInput('5'),
      stockItemTempUpdatePage.setItemPackageWidthInput('5'),
      stockItemTempUpdatePage.setItemPackageHeightInput('5'),
      stockItemTempUpdatePage.setItemPackageWeightInput('5'),
      stockItemTempUpdatePage.setItemLengthUnitMeasureIdInput('5'),
      stockItemTempUpdatePage.setItemLengthUnitMeasureCodeInput('itemLengthUnitMeasureCode'),
      stockItemTempUpdatePage.setItemWidthUnitMeasureIdInput('5'),
      stockItemTempUpdatePage.setItemWidthUnitMeasureCodeInput('itemWidthUnitMeasureCode'),
      stockItemTempUpdatePage.setItemHeightUnitMeasureIdInput('5'),
      stockItemTempUpdatePage.setItemHeightUnitMeasureCodeInput('itemHeightUnitMeasureCode'),
      stockItemTempUpdatePage.setItemWeightUnitMeasureIdInput('5'),
      stockItemTempUpdatePage.setItemWeightUnitMeasureCodeInput('itemWeightUnitMeasureCode'),
      stockItemTempUpdatePage.setItemPackageLengthUnitMeasureIdInput('5'),
      stockItemTempUpdatePage.setItemPackageLengthUnitMeasureCodeInput('itemPackageLengthUnitMeasureCode'),
      stockItemTempUpdatePage.setItemPackageWidthUnitMeasureIdInput('5'),
      stockItemTempUpdatePage.setItemPackageWidthUnitMeasureCodeInput('itemPackageWidthUnitMeasureCode'),
      stockItemTempUpdatePage.setItemPackageHeightUnitMeasureIdInput('5'),
      stockItemTempUpdatePage.setItemPackageHeightUnitMeasureCodeInput('itemPackageHeightUnitMeasureCode'),
      stockItemTempUpdatePage.setItemPackageWeightUnitMeasureIdInput('5'),
      stockItemTempUpdatePage.setItemPackageWeightUnitMeasureCodeInput('itemPackageWeightUnitMeasureCode'),
      stockItemTempUpdatePage.setNoOfPiecesInput('5'),
      stockItemTempUpdatePage.setNoOfItemsInput('5'),
      stockItemTempUpdatePage.setManufactureInput('manufacture'),
      stockItemTempUpdatePage.setSpecialFeacturesInput('specialFeactures'),
      stockItemTempUpdatePage.setProductComplianceCertificateInput('productComplianceCertificate'),
      stockItemTempUpdatePage.setCountryOfOriginInput('countryOfOrigin'),
      stockItemTempUpdatePage.setUsageAndSideEffectsInput('usageAndSideEffects'),
      stockItemTempUpdatePage.setSafetyWarnningInput('safetyWarnning'),
      stockItemTempUpdatePage.setSellStartDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      stockItemTempUpdatePage.setSellEndDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      stockItemTempUpdatePage.setStatusInput('5'),
      stockItemTempUpdatePage.setLastEditedByInput('lastEditedBy'),
      stockItemTempUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      stockItemTempUpdatePage.uploadTransactionSelectLastOption()
    ]);
    expect(await stockItemTempUpdatePage.getStockItemNameInput()).to.eq(
      'stockItemName',
      'Expected StockItemName value to be equals to stockItemName'
    );
    expect(await stockItemTempUpdatePage.getVendorCodeInput()).to.eq('vendorCode', 'Expected VendorCode value to be equals to vendorCode');
    expect(await stockItemTempUpdatePage.getVendorSKUInput()).to.eq('vendorSKU', 'Expected VendorSKU value to be equals to vendorSKU');
    expect(await stockItemTempUpdatePage.getBarcodeInput()).to.eq('barcode', 'Expected Barcode value to be equals to barcode');
    expect(await stockItemTempUpdatePage.getBarcodeTypeIdInput()).to.eq('5', 'Expected barcodeTypeId value to be equals to 5');
    expect(await stockItemTempUpdatePage.getBarcodeTypeNameInput()).to.eq(
      'barcodeTypeName',
      'Expected BarcodeTypeName value to be equals to barcodeTypeName'
    );
    expect(await stockItemTempUpdatePage.getProductTypeInput()).to.eq(
      'productType',
      'Expected ProductType value to be equals to productType'
    );
    expect(await stockItemTempUpdatePage.getProductCategoryIdInput()).to.eq('5', 'Expected productCategoryId value to be equals to 5');
    expect(await stockItemTempUpdatePage.getProductCategoryNameInput()).to.eq(
      'productCategoryName',
      'Expected ProductCategoryName value to be equals to productCategoryName'
    );
    expect(await stockItemTempUpdatePage.getProductAttributeSetIdInput()).to.eq(
      '5',
      'Expected productAttributeSetId value to be equals to 5'
    );
    expect(await stockItemTempUpdatePage.getProductAttributeIdInput()).to.eq('5', 'Expected productAttributeId value to be equals to 5');
    expect(await stockItemTempUpdatePage.getProductAttributeValueInput()).to.eq(
      'productAttributeValue',
      'Expected ProductAttributeValue value to be equals to productAttributeValue'
    );
    expect(await stockItemTempUpdatePage.getProductOptionSetIdInput()).to.eq('5', 'Expected productOptionSetId value to be equals to 5');
    expect(await stockItemTempUpdatePage.getProductOptionIdInput()).to.eq('5', 'Expected productOptionId value to be equals to 5');
    expect(await stockItemTempUpdatePage.getProductOptionValueInput()).to.eq(
      'productOptionValue',
      'Expected ProductOptionValue value to be equals to productOptionValue'
    );
    expect(await stockItemTempUpdatePage.getModelNameInput()).to.eq('modelName', 'Expected ModelName value to be equals to modelName');
    expect(await stockItemTempUpdatePage.getModelNumberInput()).to.eq(
      'modelNumber',
      'Expected ModelNumber value to be equals to modelNumber'
    );
    expect(await stockItemTempUpdatePage.getMaterialIdInput()).to.eq('5', 'Expected materialId value to be equals to 5');
    expect(await stockItemTempUpdatePage.getMaterialNameInput()).to.eq(
      'materialName',
      'Expected MaterialName value to be equals to materialName'
    );
    expect(await stockItemTempUpdatePage.getShortDescriptionInput()).to.eq(
      'shortDescription',
      'Expected ShortDescription value to be equals to shortDescription'
    );
    expect(await stockItemTempUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await stockItemTempUpdatePage.getProductBrandIdInput()).to.eq('5', 'Expected productBrandId value to be equals to 5');
    expect(await stockItemTempUpdatePage.getProductBrandNameInput()).to.eq(
      'productBrandName',
      'Expected ProductBrandName value to be equals to productBrandName'
    );
    expect(await stockItemTempUpdatePage.getHighlightsInput()).to.eq('highlights', 'Expected Highlights value to be equals to highlights');
    expect(await stockItemTempUpdatePage.getSearchDetailsInput()).to.eq(
      'searchDetails',
      'Expected SearchDetails value to be equals to searchDetails'
    );
    expect(await stockItemTempUpdatePage.getCareInstructionsInput()).to.eq(
      'careInstructions',
      'Expected CareInstructions value to be equals to careInstructions'
    );
    expect(await stockItemTempUpdatePage.getDangerousGoodsInput()).to.eq(
      'dangerousGoods',
      'Expected DangerousGoods value to be equals to dangerousGoods'
    );
    expect(await stockItemTempUpdatePage.getVideoUrlInput()).to.eq('videoUrl', 'Expected VideoUrl value to be equals to videoUrl');
    expect(await stockItemTempUpdatePage.getUnitPriceInput()).to.eq('5', 'Expected unitPrice value to be equals to 5');
    expect(await stockItemTempUpdatePage.getRemommendedRetailPriceInput()).to.eq(
      '5',
      'Expected remommendedRetailPrice value to be equals to 5'
    );
    expect(await stockItemTempUpdatePage.getCurrencyCodeInput()).to.eq(
      'currencyCode',
      'Expected CurrencyCode value to be equals to currencyCode'
    );
    expect(await stockItemTempUpdatePage.getQuantityOnHandInput()).to.eq('5', 'Expected quantityOnHand value to be equals to 5');
    expect(await stockItemTempUpdatePage.getWarrantyPeriodInput()).to.eq(
      'warrantyPeriod',
      'Expected WarrantyPeriod value to be equals to warrantyPeriod'
    );
    expect(await stockItemTempUpdatePage.getWarrantyPolicyInput()).to.eq(
      'warrantyPolicy',
      'Expected WarrantyPolicy value to be equals to warrantyPolicy'
    );
    expect(await stockItemTempUpdatePage.getWarrantyTypeIdInput()).to.eq('5', 'Expected warrantyTypeId value to be equals to 5');
    expect(await stockItemTempUpdatePage.getWarrantyTypeNameInput()).to.eq(
      'warrantyTypeName',
      'Expected WarrantyTypeName value to be equals to warrantyTypeName'
    );
    expect(await stockItemTempUpdatePage.getWhatInTheBoxInput()).to.eq(
      'whatInTheBox',
      'Expected WhatInTheBox value to be equals to whatInTheBox'
    );
    expect(await stockItemTempUpdatePage.getItemLengthInput()).to.eq('5', 'Expected itemLength value to be equals to 5');
    expect(await stockItemTempUpdatePage.getItemWidthInput()).to.eq('5', 'Expected itemWidth value to be equals to 5');
    expect(await stockItemTempUpdatePage.getItemHeightInput()).to.eq('5', 'Expected itemHeight value to be equals to 5');
    expect(await stockItemTempUpdatePage.getItemWeightInput()).to.eq('5', 'Expected itemWeight value to be equals to 5');
    expect(await stockItemTempUpdatePage.getItemPackageLengthInput()).to.eq('5', 'Expected itemPackageLength value to be equals to 5');
    expect(await stockItemTempUpdatePage.getItemPackageWidthInput()).to.eq('5', 'Expected itemPackageWidth value to be equals to 5');
    expect(await stockItemTempUpdatePage.getItemPackageHeightInput()).to.eq('5', 'Expected itemPackageHeight value to be equals to 5');
    expect(await stockItemTempUpdatePage.getItemPackageWeightInput()).to.eq('5', 'Expected itemPackageWeight value to be equals to 5');
    expect(await stockItemTempUpdatePage.getItemLengthUnitMeasureIdInput()).to.eq(
      '5',
      'Expected itemLengthUnitMeasureId value to be equals to 5'
    );
    expect(await stockItemTempUpdatePage.getItemLengthUnitMeasureCodeInput()).to.eq(
      'itemLengthUnitMeasureCode',
      'Expected ItemLengthUnitMeasureCode value to be equals to itemLengthUnitMeasureCode'
    );
    expect(await stockItemTempUpdatePage.getItemWidthUnitMeasureIdInput()).to.eq(
      '5',
      'Expected itemWidthUnitMeasureId value to be equals to 5'
    );
    expect(await stockItemTempUpdatePage.getItemWidthUnitMeasureCodeInput()).to.eq(
      'itemWidthUnitMeasureCode',
      'Expected ItemWidthUnitMeasureCode value to be equals to itemWidthUnitMeasureCode'
    );
    expect(await stockItemTempUpdatePage.getItemHeightUnitMeasureIdInput()).to.eq(
      '5',
      'Expected itemHeightUnitMeasureId value to be equals to 5'
    );
    expect(await stockItemTempUpdatePage.getItemHeightUnitMeasureCodeInput()).to.eq(
      'itemHeightUnitMeasureCode',
      'Expected ItemHeightUnitMeasureCode value to be equals to itemHeightUnitMeasureCode'
    );
    expect(await stockItemTempUpdatePage.getItemWeightUnitMeasureIdInput()).to.eq(
      '5',
      'Expected itemWeightUnitMeasureId value to be equals to 5'
    );
    expect(await stockItemTempUpdatePage.getItemWeightUnitMeasureCodeInput()).to.eq(
      'itemWeightUnitMeasureCode',
      'Expected ItemWeightUnitMeasureCode value to be equals to itemWeightUnitMeasureCode'
    );
    expect(await stockItemTempUpdatePage.getItemPackageLengthUnitMeasureIdInput()).to.eq(
      '5',
      'Expected itemPackageLengthUnitMeasureId value to be equals to 5'
    );
    expect(await stockItemTempUpdatePage.getItemPackageLengthUnitMeasureCodeInput()).to.eq(
      'itemPackageLengthUnitMeasureCode',
      'Expected ItemPackageLengthUnitMeasureCode value to be equals to itemPackageLengthUnitMeasureCode'
    );
    expect(await stockItemTempUpdatePage.getItemPackageWidthUnitMeasureIdInput()).to.eq(
      '5',
      'Expected itemPackageWidthUnitMeasureId value to be equals to 5'
    );
    expect(await stockItemTempUpdatePage.getItemPackageWidthUnitMeasureCodeInput()).to.eq(
      'itemPackageWidthUnitMeasureCode',
      'Expected ItemPackageWidthUnitMeasureCode value to be equals to itemPackageWidthUnitMeasureCode'
    );
    expect(await stockItemTempUpdatePage.getItemPackageHeightUnitMeasureIdInput()).to.eq(
      '5',
      'Expected itemPackageHeightUnitMeasureId value to be equals to 5'
    );
    expect(await stockItemTempUpdatePage.getItemPackageHeightUnitMeasureCodeInput()).to.eq(
      'itemPackageHeightUnitMeasureCode',
      'Expected ItemPackageHeightUnitMeasureCode value to be equals to itemPackageHeightUnitMeasureCode'
    );
    expect(await stockItemTempUpdatePage.getItemPackageWeightUnitMeasureIdInput()).to.eq(
      '5',
      'Expected itemPackageWeightUnitMeasureId value to be equals to 5'
    );
    expect(await stockItemTempUpdatePage.getItemPackageWeightUnitMeasureCodeInput()).to.eq(
      'itemPackageWeightUnitMeasureCode',
      'Expected ItemPackageWeightUnitMeasureCode value to be equals to itemPackageWeightUnitMeasureCode'
    );
    expect(await stockItemTempUpdatePage.getNoOfPiecesInput()).to.eq('5', 'Expected noOfPieces value to be equals to 5');
    expect(await stockItemTempUpdatePage.getNoOfItemsInput()).to.eq('5', 'Expected noOfItems value to be equals to 5');
    expect(await stockItemTempUpdatePage.getManufactureInput()).to.eq(
      'manufacture',
      'Expected Manufacture value to be equals to manufacture'
    );
    expect(await stockItemTempUpdatePage.getSpecialFeacturesInput()).to.eq(
      'specialFeactures',
      'Expected SpecialFeactures value to be equals to specialFeactures'
    );
    expect(await stockItemTempUpdatePage.getProductComplianceCertificateInput()).to.eq(
      'productComplianceCertificate',
      'Expected ProductComplianceCertificate value to be equals to productComplianceCertificate'
    );
    const selectedGenuineAndLegal = stockItemTempUpdatePage.getGenuineAndLegalInput();
    if (await selectedGenuineAndLegal.isSelected()) {
      await stockItemTempUpdatePage.getGenuineAndLegalInput().click();
      expect(await stockItemTempUpdatePage.getGenuineAndLegalInput().isSelected(), 'Expected genuineAndLegal not to be selected').to.be
        .false;
    } else {
      await stockItemTempUpdatePage.getGenuineAndLegalInput().click();
      expect(await stockItemTempUpdatePage.getGenuineAndLegalInput().isSelected(), 'Expected genuineAndLegal to be selected').to.be.true;
    }
    expect(await stockItemTempUpdatePage.getCountryOfOriginInput()).to.eq(
      'countryOfOrigin',
      'Expected CountryOfOrigin value to be equals to countryOfOrigin'
    );
    expect(await stockItemTempUpdatePage.getUsageAndSideEffectsInput()).to.eq(
      'usageAndSideEffects',
      'Expected UsageAndSideEffects value to be equals to usageAndSideEffects'
    );
    expect(await stockItemTempUpdatePage.getSafetyWarnningInput()).to.eq(
      'safetyWarnning',
      'Expected SafetyWarnning value to be equals to safetyWarnning'
    );
    expect(await stockItemTempUpdatePage.getSellStartDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected sellStartDate value to be equals to 2000-12-31'
    );
    expect(await stockItemTempUpdatePage.getSellEndDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected sellEndDate value to be equals to 2000-12-31'
    );
    expect(await stockItemTempUpdatePage.getStatusInput()).to.eq('5', 'Expected status value to be equals to 5');
    expect(await stockItemTempUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await stockItemTempUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await stockItemTempUpdatePage.save();
    expect(await stockItemTempUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await stockItemTempComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last StockItemTemp', async () => {
    const nbButtonsBeforeDelete = await stockItemTempComponentsPage.countDeleteButtons();
    await stockItemTempComponentsPage.clickOnLastDeleteButton();

    stockItemTempDeleteDialog = new StockItemTempDeleteDialog();
    expect(await stockItemTempDeleteDialog.getDialogTitle()).to.eq('epmwebApp.stockItemTemp.delete.question');
    await stockItemTempDeleteDialog.clickOnConfirmButton();

    expect(await stockItemTempComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
