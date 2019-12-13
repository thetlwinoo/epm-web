import { element, by, ElementFinder } from 'protractor';

export class UploadTransactionsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-upload-transactions div table .btn-danger'));
  title = element.all(by.css('jhi-upload-transactions div h2#page-heading span')).first();

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

export class UploadTransactionsUpdatePage {
  pageTitle = element(by.id('jhi-upload-transactions-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  fileNameInput = element(by.id('field_fileName'));
  templateUrlInput = element(by.id('field_templateUrl'));
  statusInput = element(by.id('field_status'));
  generatedCodeInput = element(by.id('field_generatedCode'));
  lastEditedByInput = element(by.id('field_lastEditedBy'));
  lastEditedWhenInput = element(by.id('field_lastEditedWhen'));
  supplierSelect = element(by.id('field_supplier'));
  actionTypeSelect = element(by.id('field_actionType'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFileNameInput(fileName) {
    await this.fileNameInput.sendKeys(fileName);
  }

  async getFileNameInput() {
    return await this.fileNameInput.getAttribute('value');
  }

  async setTemplateUrlInput(templateUrl) {
    await this.templateUrlInput.sendKeys(templateUrl);
  }

  async getTemplateUrlInput() {
    return await this.templateUrlInput.getAttribute('value');
  }

  async setStatusInput(status) {
    await this.statusInput.sendKeys(status);
  }

  async getStatusInput() {
    return await this.statusInput.getAttribute('value');
  }

  async setGeneratedCodeInput(generatedCode) {
    await this.generatedCodeInput.sendKeys(generatedCode);
  }

  async getGeneratedCodeInput() {
    return await this.generatedCodeInput.getAttribute('value');
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

  async actionTypeSelectLastOption() {
    await this.actionTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async actionTypeSelectOption(option) {
    await this.actionTypeSelect.sendKeys(option);
  }

  getActionTypeSelect(): ElementFinder {
    return this.actionTypeSelect;
  }

  async getActionTypeSelectedOption() {
    return await this.actionTypeSelect.element(by.css('option:checked')).getText();
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

export class UploadTransactionsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-uploadTransactions-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-uploadTransactions'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
