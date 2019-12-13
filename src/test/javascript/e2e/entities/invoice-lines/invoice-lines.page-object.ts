import { element, by, ElementFinder } from 'protractor';

export class InvoiceLinesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-invoice-lines div table .btn-danger'));
  title = element.all(by.css('jhi-invoice-lines div h2#page-heading span')).first();

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

export class InvoiceLinesUpdatePage {
  pageTitle = element(by.id('jhi-invoice-lines-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descriptionInput = element(by.id('field_description'));
  quantityInput = element(by.id('field_quantity'));
  unitPriceInput = element(by.id('field_unitPrice'));
  taxRateInput = element(by.id('field_taxRate'));
  taxAmountInput = element(by.id('field_taxAmount'));
  lineProfitInput = element(by.id('field_lineProfit'));
  extendedPriceInput = element(by.id('field_extendedPrice'));
  lastEditedByInput = element(by.id('field_lastEditedBy'));
  lastEditedWhenInput = element(by.id('field_lastEditedWhen'));
  packageTypeSelect = element(by.id('field_packageType'));
  stockItemSelect = element(by.id('field_stockItem'));
  invoiceSelect = element(by.id('field_invoice'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
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

  async setTaxRateInput(taxRate) {
    await this.taxRateInput.sendKeys(taxRate);
  }

  async getTaxRateInput() {
    return await this.taxRateInput.getAttribute('value');
  }

  async setTaxAmountInput(taxAmount) {
    await this.taxAmountInput.sendKeys(taxAmount);
  }

  async getTaxAmountInput() {
    return await this.taxAmountInput.getAttribute('value');
  }

  async setLineProfitInput(lineProfit) {
    await this.lineProfitInput.sendKeys(lineProfit);
  }

  async getLineProfitInput() {
    return await this.lineProfitInput.getAttribute('value');
  }

  async setExtendedPriceInput(extendedPrice) {
    await this.extendedPriceInput.sendKeys(extendedPrice);
  }

  async getExtendedPriceInput() {
    return await this.extendedPriceInput.getAttribute('value');
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

  async invoiceSelectLastOption() {
    await this.invoiceSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async invoiceSelectOption(option) {
    await this.invoiceSelect.sendKeys(option);
  }

  getInvoiceSelect(): ElementFinder {
    return this.invoiceSelect;
  }

  async getInvoiceSelectedOption() {
    return await this.invoiceSelect.element(by.css('option:checked')).getText();
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

export class InvoiceLinesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-invoiceLines-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-invoiceLines'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
