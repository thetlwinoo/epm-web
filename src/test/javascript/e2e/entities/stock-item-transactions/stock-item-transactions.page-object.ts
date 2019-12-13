import { element, by, ElementFinder } from 'protractor';

export class StockItemTransactionsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-stock-item-transactions div table .btn-danger'));
  title = element.all(by.css('jhi-stock-item-transactions div h2#page-heading span')).first();

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

export class StockItemTransactionsUpdatePage {
  pageTitle = element(by.id('jhi-stock-item-transactions-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  transactionOccuredWhenInput = element(by.id('field_transactionOccuredWhen'));
  quantityInput = element(by.id('field_quantity'));
  lastEditedByInput = element(by.id('field_lastEditedBy'));
  lastEditedWhenInput = element(by.id('field_lastEditedWhen'));
  stockItemSelect = element(by.id('field_stockItem'));
  customerSelect = element(by.id('field_customer'));
  invoiceSelect = element(by.id('field_invoice'));
  supplierSelect = element(by.id('field_supplier'));
  transactionTypeSelect = element(by.id('field_transactionType'));
  purchaseOrderSelect = element(by.id('field_purchaseOrder'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTransactionOccuredWhenInput(transactionOccuredWhen) {
    await this.transactionOccuredWhenInput.sendKeys(transactionOccuredWhen);
  }

  async getTransactionOccuredWhenInput() {
    return await this.transactionOccuredWhenInput.getAttribute('value');
  }

  async setQuantityInput(quantity) {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput() {
    return await this.quantityInput.getAttribute('value');
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

  async customerSelectLastOption() {
    await this.customerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async customerSelectOption(option) {
    await this.customerSelect.sendKeys(option);
  }

  getCustomerSelect(): ElementFinder {
    return this.customerSelect;
  }

  async getCustomerSelectedOption() {
    return await this.customerSelect.element(by.css('option:checked')).getText();
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

  async transactionTypeSelectLastOption() {
    await this.transactionTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async transactionTypeSelectOption(option) {
    await this.transactionTypeSelect.sendKeys(option);
  }

  getTransactionTypeSelect(): ElementFinder {
    return this.transactionTypeSelect;
  }

  async getTransactionTypeSelectedOption() {
    return await this.transactionTypeSelect.element(by.css('option:checked')).getText();
  }

  async purchaseOrderSelectLastOption() {
    await this.purchaseOrderSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async purchaseOrderSelectOption(option) {
    await this.purchaseOrderSelect.sendKeys(option);
  }

  getPurchaseOrderSelect(): ElementFinder {
    return this.purchaseOrderSelect;
  }

  async getPurchaseOrderSelectedOption() {
    return await this.purchaseOrderSelect.element(by.css('option:checked')).getText();
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

export class StockItemTransactionsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-stockItemTransactions-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-stockItemTransactions'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
