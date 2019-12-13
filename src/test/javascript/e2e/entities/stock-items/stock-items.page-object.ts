import { element, by, ElementFinder } from 'protractor';

export class StockItemsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-stock-items div table .btn-danger'));
  title = element.all(by.css('jhi-stock-items div h2#page-heading span')).first();

  async clickOnCreateButton() {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton() {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class StockItemsUpdatePage {
  pageTitle = element(by.id('jhi-stock-items-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  vendorCodeInput = element(by.id('field_vendorCode'));
  vendorSKUInput = element(by.id('field_vendorSKU'));
  generatedSKUInput = element(by.id('field_generatedSKU'));
  barcodeInput = element(by.id('field_barcode'));
  unitPriceInput = element(by.id('field_unitPrice'));
  recommendedRetailPriceInput = element(by.id('field_recommendedRetailPrice'));
  quantityOnHandInput = element(by.id('field_quantityOnHand'));
  itemLengthInput = element(by.id('field_itemLength'));
  itemWidthInput = element(by.id('field_itemWidth'));
  itemHeightInput = element(by.id('field_itemHeight'));
  itemWeightInput = element(by.id('field_itemWeight'));
  itemPackageLengthInput = element(by.id('field_itemPackageLength'));
  itemPackageWidthInput = element(by.id('field_itemPackageWidth'));
  itemPackageHeightInput = element(by.id('field_itemPackageHeight'));
  itemPackageWeightInput = element(by.id('field_itemPackageWeight'));
  noOfPiecesInput = element(by.id('field_noOfPieces'));
  noOfItemsInput = element(by.id('field_noOfItems'));
  manufactureInput = element(by.id('field_manufacture'));
  marketingCommentsInput = element(by.id('field_marketingComments'));
  internalCommentsInput = element(by.id('field_internalComments'));
  sellStartDateInput = element(by.id('field_sellStartDate'));
  sellEndDateInput = element(by.id('field_sellEndDate'));
  sellCountInput = element(by.id('field_sellCount'));
  customFieldsInput = element(by.id('field_customFields'));
  thumbnailUrlInput = element(by.id('field_thumbnailUrl'));
  activeIndInput = element(by.id('field_activeInd'));
  lastEditedByInput = element(by.id('field_lastEditedBy'));
  lastEditedWhenInput = element(by.id('field_lastEditedWhen'));
  stockItemOnReviewLineSelect = element(by.id('field_stockItemOnReviewLine'));
  itemLengthUnitSelect = element(by.id('field_itemLengthUnit'));
  itemWidthUnitSelect = element(by.id('field_itemWidthUnit'));
  itemHeightUnitSelect = element(by.id('field_itemHeightUnit'));
  packageLengthUnitSelect = element(by.id('field_packageLengthUnit'));
  packageWidthUnitSelect = element(by.id('field_packageWidthUnit'));
  packageHeightUnitSelect = element(by.id('field_packageHeightUnit'));
  itemPackageWeightUnitSelect = element(by.id('field_itemPackageWeightUnit'));
  productAttributeSelect = element(by.id('field_productAttribute'));
  productOptionSelect = element(by.id('field_productOption'));
  materialSelect = element(by.id('field_material'));
  currencySelect = element(by.id('field_currency'));
  barcodeTypeSelect = element(by.id('field_barcodeType'));
  productSelect = element(by.id('field_product'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setVendorCodeInput(vendorCode) {
    await this.vendorCodeInput.sendKeys(vendorCode);
  }

  async getVendorCodeInput() {
    return await this.vendorCodeInput.getAttribute('value');
  }

  async setVendorSKUInput(vendorSKU) {
    await this.vendorSKUInput.sendKeys(vendorSKU);
  }

  async getVendorSKUInput() {
    return await this.vendorSKUInput.getAttribute('value');
  }

  async setGeneratedSKUInput(generatedSKU) {
    await this.generatedSKUInput.sendKeys(generatedSKU);
  }

  async getGeneratedSKUInput() {
    return await this.generatedSKUInput.getAttribute('value');
  }

  async setBarcodeInput(barcode) {
    await this.barcodeInput.sendKeys(barcode);
  }

  async getBarcodeInput() {
    return await this.barcodeInput.getAttribute('value');
  }

  async setUnitPriceInput(unitPrice) {
    await this.unitPriceInput.sendKeys(unitPrice);
  }

  async getUnitPriceInput() {
    return await this.unitPriceInput.getAttribute('value');
  }

  async setRecommendedRetailPriceInput(recommendedRetailPrice) {
    await this.recommendedRetailPriceInput.sendKeys(recommendedRetailPrice);
  }

  async getRecommendedRetailPriceInput() {
    return await this.recommendedRetailPriceInput.getAttribute('value');
  }

  async setQuantityOnHandInput(quantityOnHand) {
    await this.quantityOnHandInput.sendKeys(quantityOnHand);
  }

  async getQuantityOnHandInput() {
    return await this.quantityOnHandInput.getAttribute('value');
  }

  async setItemLengthInput(itemLength) {
    await this.itemLengthInput.sendKeys(itemLength);
  }

  async getItemLengthInput() {
    return await this.itemLengthInput.getAttribute('value');
  }

  async setItemWidthInput(itemWidth) {
    await this.itemWidthInput.sendKeys(itemWidth);
  }

  async getItemWidthInput() {
    return await this.itemWidthInput.getAttribute('value');
  }

  async setItemHeightInput(itemHeight) {
    await this.itemHeightInput.sendKeys(itemHeight);
  }

  async getItemHeightInput() {
    return await this.itemHeightInput.getAttribute('value');
  }

  async setItemWeightInput(itemWeight) {
    await this.itemWeightInput.sendKeys(itemWeight);
  }

  async getItemWeightInput() {
    return await this.itemWeightInput.getAttribute('value');
  }

  async setItemPackageLengthInput(itemPackageLength) {
    await this.itemPackageLengthInput.sendKeys(itemPackageLength);
  }

  async getItemPackageLengthInput() {
    return await this.itemPackageLengthInput.getAttribute('value');
  }

  async setItemPackageWidthInput(itemPackageWidth) {
    await this.itemPackageWidthInput.sendKeys(itemPackageWidth);
  }

  async getItemPackageWidthInput() {
    return await this.itemPackageWidthInput.getAttribute('value');
  }

  async setItemPackageHeightInput(itemPackageHeight) {
    await this.itemPackageHeightInput.sendKeys(itemPackageHeight);
  }

  async getItemPackageHeightInput() {
    return await this.itemPackageHeightInput.getAttribute('value');
  }

  async setItemPackageWeightInput(itemPackageWeight) {
    await this.itemPackageWeightInput.sendKeys(itemPackageWeight);
  }

  async getItemPackageWeightInput() {
    return await this.itemPackageWeightInput.getAttribute('value');
  }

  async setNoOfPiecesInput(noOfPieces) {
    await this.noOfPiecesInput.sendKeys(noOfPieces);
  }

  async getNoOfPiecesInput() {
    return await this.noOfPiecesInput.getAttribute('value');
  }

  async setNoOfItemsInput(noOfItems) {
    await this.noOfItemsInput.sendKeys(noOfItems);
  }

  async getNoOfItemsInput() {
    return await this.noOfItemsInput.getAttribute('value');
  }

  async setManufactureInput(manufacture) {
    await this.manufactureInput.sendKeys(manufacture);
  }

  async getManufactureInput() {
    return await this.manufactureInput.getAttribute('value');
  }

  async setMarketingCommentsInput(marketingComments) {
    await this.marketingCommentsInput.sendKeys(marketingComments);
  }

  async getMarketingCommentsInput() {
    return await this.marketingCommentsInput.getAttribute('value');
  }

  async setInternalCommentsInput(internalComments) {
    await this.internalCommentsInput.sendKeys(internalComments);
  }

  async getInternalCommentsInput() {
    return await this.internalCommentsInput.getAttribute('value');
  }

  async setSellStartDateInput(sellStartDate) {
    await this.sellStartDateInput.sendKeys(sellStartDate);
  }

  async getSellStartDateInput() {
    return await this.sellStartDateInput.getAttribute('value');
  }

  async setSellEndDateInput(sellEndDate) {
    await this.sellEndDateInput.sendKeys(sellEndDate);
  }

  async getSellEndDateInput() {
    return await this.sellEndDateInput.getAttribute('value');
  }

  async setSellCountInput(sellCount) {
    await this.sellCountInput.sendKeys(sellCount);
  }

  async getSellCountInput() {
    return await this.sellCountInput.getAttribute('value');
  }

  async setCustomFieldsInput(customFields) {
    await this.customFieldsInput.sendKeys(customFields);
  }

  async getCustomFieldsInput() {
    return await this.customFieldsInput.getAttribute('value');
  }

  async setThumbnailUrlInput(thumbnailUrl) {
    await this.thumbnailUrlInput.sendKeys(thumbnailUrl);
  }

  async getThumbnailUrlInput() {
    return await this.thumbnailUrlInput.getAttribute('value');
  }

  getActiveIndInput() {
    return this.activeIndInput;
  }
  async setLastEditedByInput(lastEditedBy) {
    await this.lastEditedByInput.sendKeys(lastEditedBy);
  }

  async getLastEditedByInput() {
    return await this.lastEditedByInput.getAttribute('value');
  }

  async setLastEditedWhenInput(lastEditedWhen) {
    await this.lastEditedWhenInput.sendKeys(lastEditedWhen);
  }

  async getLastEditedWhenInput() {
    return await this.lastEditedWhenInput.getAttribute('value');
  }

  async stockItemOnReviewLineSelectLastOption() {
    await this.stockItemOnReviewLineSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async stockItemOnReviewLineSelectOption(option) {
    await this.stockItemOnReviewLineSelect.sendKeys(option);
  }

  getStockItemOnReviewLineSelect(): ElementFinder {
    return this.stockItemOnReviewLineSelect;
  }

  async getStockItemOnReviewLineSelectedOption() {
    return await this.stockItemOnReviewLineSelect.element(by.css('option:checked')).getText();
  }

  async itemLengthUnitSelectLastOption() {
    await this.itemLengthUnitSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async itemLengthUnitSelectOption(option) {
    await this.itemLengthUnitSelect.sendKeys(option);
  }

  getItemLengthUnitSelect(): ElementFinder {
    return this.itemLengthUnitSelect;
  }

  async getItemLengthUnitSelectedOption() {
    return await this.itemLengthUnitSelect.element(by.css('option:checked')).getText();
  }

  async itemWidthUnitSelectLastOption() {
    await this.itemWidthUnitSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async itemWidthUnitSelectOption(option) {
    await this.itemWidthUnitSelect.sendKeys(option);
  }

  getItemWidthUnitSelect(): ElementFinder {
    return this.itemWidthUnitSelect;
  }

  async getItemWidthUnitSelectedOption() {
    return await this.itemWidthUnitSelect.element(by.css('option:checked')).getText();
  }

  async itemHeightUnitSelectLastOption() {
    await this.itemHeightUnitSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async itemHeightUnitSelectOption(option) {
    await this.itemHeightUnitSelect.sendKeys(option);
  }

  getItemHeightUnitSelect(): ElementFinder {
    return this.itemHeightUnitSelect;
  }

  async getItemHeightUnitSelectedOption() {
    return await this.itemHeightUnitSelect.element(by.css('option:checked')).getText();
  }

  async packageLengthUnitSelectLastOption() {
    await this.packageLengthUnitSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async packageLengthUnitSelectOption(option) {
    await this.packageLengthUnitSelect.sendKeys(option);
  }

  getPackageLengthUnitSelect(): ElementFinder {
    return this.packageLengthUnitSelect;
  }

  async getPackageLengthUnitSelectedOption() {
    return await this.packageLengthUnitSelect.element(by.css('option:checked')).getText();
  }

  async packageWidthUnitSelectLastOption() {
    await this.packageWidthUnitSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async packageWidthUnitSelectOption(option) {
    await this.packageWidthUnitSelect.sendKeys(option);
  }

  getPackageWidthUnitSelect(): ElementFinder {
    return this.packageWidthUnitSelect;
  }

  async getPackageWidthUnitSelectedOption() {
    return await this.packageWidthUnitSelect.element(by.css('option:checked')).getText();
  }

  async packageHeightUnitSelectLastOption() {
    await this.packageHeightUnitSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async packageHeightUnitSelectOption(option) {
    await this.packageHeightUnitSelect.sendKeys(option);
  }

  getPackageHeightUnitSelect(): ElementFinder {
    return this.packageHeightUnitSelect;
  }

  async getPackageHeightUnitSelectedOption() {
    return await this.packageHeightUnitSelect.element(by.css('option:checked')).getText();
  }

  async itemPackageWeightUnitSelectLastOption() {
    await this.itemPackageWeightUnitSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async itemPackageWeightUnitSelectOption(option) {
    await this.itemPackageWeightUnitSelect.sendKeys(option);
  }

  getItemPackageWeightUnitSelect(): ElementFinder {
    return this.itemPackageWeightUnitSelect;
  }

  async getItemPackageWeightUnitSelectedOption() {
    return await this.itemPackageWeightUnitSelect.element(by.css('option:checked')).getText();
  }

  async productAttributeSelectLastOption() {
    await this.productAttributeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productAttributeSelectOption(option) {
    await this.productAttributeSelect.sendKeys(option);
  }

  getProductAttributeSelect(): ElementFinder {
    return this.productAttributeSelect;
  }

  async getProductAttributeSelectedOption() {
    return await this.productAttributeSelect.element(by.css('option:checked')).getText();
  }

  async productOptionSelectLastOption() {
    await this.productOptionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productOptionSelectOption(option) {
    await this.productOptionSelect.sendKeys(option);
  }

  getProductOptionSelect(): ElementFinder {
    return this.productOptionSelect;
  }

  async getProductOptionSelectedOption() {
    return await this.productOptionSelect.element(by.css('option:checked')).getText();
  }

  async materialSelectLastOption() {
    await this.materialSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async materialSelectOption(option) {
    await this.materialSelect.sendKeys(option);
  }

  getMaterialSelect(): ElementFinder {
    return this.materialSelect;
  }

  async getMaterialSelectedOption() {
    return await this.materialSelect.element(by.css('option:checked')).getText();
  }

  async currencySelectLastOption() {
    await this.currencySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async currencySelectOption(option) {
    await this.currencySelect.sendKeys(option);
  }

  getCurrencySelect(): ElementFinder {
    return this.currencySelect;
  }

  async getCurrencySelectedOption() {
    return await this.currencySelect.element(by.css('option:checked')).getText();
  }

  async barcodeTypeSelectLastOption() {
    await this.barcodeTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async barcodeTypeSelectOption(option) {
    await this.barcodeTypeSelect.sendKeys(option);
  }

  getBarcodeTypeSelect(): ElementFinder {
    return this.barcodeTypeSelect;
  }

  async getBarcodeTypeSelectedOption() {
    return await this.barcodeTypeSelect.element(by.css('option:checked')).getText();
  }

  async productSelectLastOption() {
    await this.productSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productSelectOption(option) {
    await this.productSelect.sendKeys(option);
  }

  getProductSelect(): ElementFinder {
    return this.productSelect;
  }

  async getProductSelectedOption() {
    return await this.productSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class StockItemsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-stockItems-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-stockItems'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
