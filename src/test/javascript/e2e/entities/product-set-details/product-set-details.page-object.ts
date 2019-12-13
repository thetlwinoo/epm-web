import { element, by, ElementFinder } from 'protractor';

export class ProductSetDetailsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-set-details div table .btn-danger'));
  title = element.all(by.css('jhi-product-set-details div h2#page-heading span')).first();

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

export class ProductSetDetailsUpdatePage {
  pageTitle = element(by.id('jhi-product-set-details-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  subGroupNoInput = element(by.id('field_subGroupNo'));
  subGroupMinCountInput = element(by.id('field_subGroupMinCount'));
  subGroupMinTotalInput = element(by.id('field_subGroupMinTotal'));
  minCountInput = element(by.id('field_minCount'));
  maxCountInput = element(by.id('field_maxCount'));
  isOptionalInput = element(by.id('field_isOptional'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSubGroupNoInput(subGroupNo) {
    await this.subGroupNoInput.sendKeys(subGroupNo);
  }

  async getSubGroupNoInput() {
    return await this.subGroupNoInput.getAttribute('value');
  }

  async setSubGroupMinCountInput(subGroupMinCount) {
    await this.subGroupMinCountInput.sendKeys(subGroupMinCount);
  }

  async getSubGroupMinCountInput() {
    return await this.subGroupMinCountInput.getAttribute('value');
  }

  async setSubGroupMinTotalInput(subGroupMinTotal) {
    await this.subGroupMinTotalInput.sendKeys(subGroupMinTotal);
  }

  async getSubGroupMinTotalInput() {
    return await this.subGroupMinTotalInput.getAttribute('value');
  }

  async setMinCountInput(minCount) {
    await this.minCountInput.sendKeys(minCount);
  }

  async getMinCountInput() {
    return await this.minCountInput.getAttribute('value');
  }

  async setMaxCountInput(maxCount) {
    await this.maxCountInput.sendKeys(maxCount);
  }

  async getMaxCountInput() {
    return await this.maxCountInput.getAttribute('value');
  }

  getIsOptionalInput() {
    return this.isOptionalInput;
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

export class ProductSetDetailsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productSetDetails-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productSetDetails'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
