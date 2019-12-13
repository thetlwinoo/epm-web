import { element, by, ElementFinder } from 'protractor';

export class ProductsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-products div table .btn-danger'));
  title = element.all(by.css('jhi-products div h2#page-heading span')).first();

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

export class ProductsUpdatePage {
  pageTitle = element(by.id('jhi-products-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  handleInput = element(by.id('field_handle'));
  productNumberInput = element(by.id('field_productNumber'));
  searchDetailsInput = element(by.id('field_searchDetails'));
  sellCountInput = element(by.id('field_sellCount'));
  thumbnailListInput = element(by.id('field_thumbnailList'));
  activeIndInput = element(by.id('field_activeInd'));
  lastEditedByInput = element(by.id('field_lastEditedBy'));
  lastEditedWhenInput = element(by.id('field_lastEditedWhen'));
  productDocumentSelect = element(by.id('field_productDocument'));
  supplierSelect = element(by.id('field_supplier'));
  productCategorySelect = element(by.id('field_productCategory'));
  productBrandSelect = element(by.id('field_productBrand'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setHandleInput(handle) {
    await this.handleInput.sendKeys(handle);
  }

  async getHandleInput() {
    return await this.handleInput.getAttribute('value');
  }

  async setProductNumberInput(productNumber) {
    await this.productNumberInput.sendKeys(productNumber);
  }

  async getProductNumberInput() {
    return await this.productNumberInput.getAttribute('value');
  }

  async setSearchDetailsInput(searchDetails) {
    await this.searchDetailsInput.sendKeys(searchDetails);
  }

  async getSearchDetailsInput() {
    return await this.searchDetailsInput.getAttribute('value');
  }

  async setSellCountInput(sellCount) {
    await this.sellCountInput.sendKeys(sellCount);
  }

  async getSellCountInput() {
    return await this.sellCountInput.getAttribute('value');
  }

  async setThumbnailListInput(thumbnailList) {
    await this.thumbnailListInput.sendKeys(thumbnailList);
  }

  async getThumbnailListInput() {
    return await this.thumbnailListInput.getAttribute('value');
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

  async productDocumentSelectLastOption() {
    await this.productDocumentSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productDocumentSelectOption(option) {
    await this.productDocumentSelect.sendKeys(option);
  }

  getProductDocumentSelect(): ElementFinder {
    return this.productDocumentSelect;
  }

  async getProductDocumentSelectedOption() {
    return await this.productDocumentSelect.element(by.css('option:checked')).getText();
  }

  async supplierSelectLastOption() {
    await this.supplierSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async supplierSelectOption(option) {
    await this.supplierSelect.sendKeys(option);
  }

  getSupplierSelect(): ElementFinder {
    return this.supplierSelect;
  }

  async getSupplierSelectedOption() {
    return await this.supplierSelect.element(by.css('option:checked')).getText();
  }

  async productCategorySelectLastOption() {
    await this.productCategorySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productCategorySelectOption(option) {
    await this.productCategorySelect.sendKeys(option);
  }

  getProductCategorySelect(): ElementFinder {
    return this.productCategorySelect;
  }

  async getProductCategorySelectedOption() {
    return await this.productCategorySelect.element(by.css('option:checked')).getText();
  }

  async productBrandSelectLastOption() {
    await this.productBrandSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productBrandSelectOption(option) {
    await this.productBrandSelect.sendKeys(option);
  }

  getProductBrandSelect(): ElementFinder {
    return this.productBrandSelect;
  }

  async getProductBrandSelectedOption() {
    return await this.productBrandSelect.element(by.css('option:checked')).getText();
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

export class ProductsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-products-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-products'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
