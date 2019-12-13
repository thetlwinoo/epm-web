import { element, by, ElementFinder } from 'protractor';

export class ProductSetComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-set div table .btn-danger'));
  title = element.all(by.css('jhi-product-set div h2#page-heading span')).first();

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

export class ProductSetUpdatePage {
  pageTitle = element(by.id('jhi-product-set-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  noOfPersonInput = element(by.id('field_noOfPerson'));
  isExclusiveInput = element(by.id('field_isExclusive'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setNoOfPersonInput(noOfPerson) {
    await this.noOfPersonInput.sendKeys(noOfPerson);
  }

  async getNoOfPersonInput() {
    return await this.noOfPersonInput.getAttribute('value');
  }

  getIsExclusiveInput() {
    return this.isExclusiveInput;
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

export class ProductSetDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productSet-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productSet'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
