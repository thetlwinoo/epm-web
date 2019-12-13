import { element, by, ElementFinder } from 'protractor';

export class OrderLinesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-order-lines div table .btn-danger'));
  title = element.all(by.css('jhi-order-lines div h2#page-heading span')).first();

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

export class OrderLinesUpdatePage {
  pageTitle = element(by.id('jhi-order-lines-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  carrierTrackingNumberInput = element(by.id('field_carrierTrackingNumber'));
  quantityInput = element(by.id('field_quantity'));
  unitPriceInput = element(by.id('field_unitPrice'));
  unitPriceDiscountInput = element(by.id('field_unitPriceDiscount'));
  lineTotalInput = element(by.id('field_lineTotal'));
  taxRateInput = element(by.id('field_taxRate'));
  pickedQuantityInput = element(by.id('field_pickedQuantity'));
  pickingCompletedWhenInput = element(by.id('field_pickingCompletedWhen'));
  statusSelect = element(by.id('field_status'));
  lastEditedByInput = element(by.id('field_lastEditedBy'));
  lastEditedWhenInput = element(by.id('field_lastEditedWhen'));
  stockItemSelect = element(by.id('field_stockItem'));
  packageTypeSelect = element(by.id('field_packageType'));
  orderSelect = element(by.id('field_order'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCarrierTrackingNumberInput(carrierTrackingNumber) {
    await this.carrierTrackingNumberInput.sendKeys(carrierTrackingNumber);
  }

  async getCarrierTrackingNumberInput() {
    return await this.carrierTrackingNumberInput.getAttribute('value');
  }

  async setQuantityInput(quantity) {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput() {
    return await this.quantityInput.getAttribute('value');
  }

  async setUnitPriceInput(unitPrice) {
    await this.unitPriceInput.sendKeys(unitPrice);
  }

  async getUnitPriceInput() {
    return await this.unitPriceInput.getAttribute('value');
  }

  async setUnitPriceDiscountInput(unitPriceDiscount) {
    await this.unitPriceDiscountInput.sendKeys(unitPriceDiscount);
  }

  async getUnitPriceDiscountInput() {
    return await this.unitPriceDiscountInput.getAttribute('value');
  }

  async setLineTotalInput(lineTotal) {
    await this.lineTotalInput.sendKeys(lineTotal);
  }

  async getLineTotalInput() {
    return await this.lineTotalInput.getAttribute('value');
  }

  async setTaxRateInput(taxRate) {
    await this.taxRateInput.sendKeys(taxRate);
  }

  async getTaxRateInput() {
    return await this.taxRateInput.getAttribute('value');
  }

  async setPickedQuantityInput(pickedQuantity) {
    await this.pickedQuantityInput.sendKeys(pickedQuantity);
  }

  async getPickedQuantityInput() {
    return await this.pickedQuantityInput.getAttribute('value');
  }

  async setPickingCompletedWhenInput(pickingCompletedWhen) {
    await this.pickingCompletedWhenInput.sendKeys(pickingCompletedWhen);
  }

  async getPickingCompletedWhenInput() {
    return await this.pickingCompletedWhenInput.getAttribute('value');
  }

  async setStatusSelect(status) {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect() {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption() {
    await this.statusSelect
      .all(by.tagName('option'))
      .last()
      .click();
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

  async stockItemSelectLastOption() {
    await this.stockItemSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async stockItemSelectOption(option) {
    await this.stockItemSelect.sendKeys(option);
  }

  getStockItemSelect(): ElementFinder {
    return this.stockItemSelect;
  }

  async getStockItemSelectedOption() {
    return await this.stockItemSelect.element(by.css('option:checked')).getText();
  }

  async packageTypeSelectLastOption() {
    await this.packageTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async packageTypeSelectOption(option) {
    await this.packageTypeSelect.sendKeys(option);
  }

  getPackageTypeSelect(): ElementFinder {
    return this.packageTypeSelect;
  }

  async getPackageTypeSelectedOption() {
    return await this.packageTypeSelect.element(by.css('option:checked')).getText();
  }

  async orderSelectLastOption() {
    await this.orderSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async orderSelectOption(option) {
    await this.orderSelect.sendKeys(option);
  }

  getOrderSelect(): ElementFinder {
    return this.orderSelect;
  }

  async getOrderSelectedOption() {
    return await this.orderSelect.element(by.css('option:checked')).getText();
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

export class OrderLinesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-orderLines-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-orderLines'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
