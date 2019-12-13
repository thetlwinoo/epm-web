import { element, by, ElementFinder } from 'protractor';

export class PaymentTransactionsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-payment-transactions div table .btn-danger'));
  title = element.all(by.css('jhi-payment-transactions div h2#page-heading span')).first();

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

export class PaymentTransactionsUpdatePage {
  pageTitle = element(by.id('jhi-payment-transactions-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  returnedCompletedPaymentDataInput = element(by.id('field_returnedCompletedPaymentData'));
  lastEditedByInput = element(by.id('field_lastEditedBy'));
  lastEditedWhenInput = element(by.id('field_lastEditedWhen'));
  paymentOnOrderSelect = element(by.id('field_paymentOnOrder'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setReturnedCompletedPaymentDataInput(returnedCompletedPaymentData) {
    await this.returnedCompletedPaymentDataInput.sendKeys(returnedCompletedPaymentData);
  }

  async getReturnedCompletedPaymentDataInput() {
    return await this.returnedCompletedPaymentDataInput.getAttribute('value');
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

  async paymentOnOrderSelectLastOption() {
    await this.paymentOnOrderSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async paymentOnOrderSelectOption(option) {
    await this.paymentOnOrderSelect.sendKeys(option);
  }

  getPaymentOnOrderSelect(): ElementFinder {
    return this.paymentOnOrderSelect;
  }

  async getPaymentOnOrderSelectedOption() {
    return await this.paymentOnOrderSelect.element(by.css('option:checked')).getText();
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

export class PaymentTransactionsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-paymentTransactions-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-paymentTransactions'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
