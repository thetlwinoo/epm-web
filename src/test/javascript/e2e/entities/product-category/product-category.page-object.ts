import { element, by, ElementFinder } from 'protractor';

export class ProductCategoryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-category div table .btn-danger'));
  title = element.all(by.css('jhi-product-category div h2#page-heading span')).first();

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

export class ProductCategoryUpdatePage {
  pageTitle = element(by.id('jhi-product-category-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  shortLabelInput = element(by.id('field_shortLabel'));
  thumbnailUrlInput = element(by.id('field_thumbnailUrl'));
  parentSelect = element(by.id('field_parent'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setShortLabelInput(shortLabel) {
    await this.shortLabelInput.sendKeys(shortLabel);
  }

  async getShortLabelInput() {
    return await this.shortLabelInput.getAttribute('value');
  }

  async setThumbnailUrlInput(thumbnailUrl) {
    await this.thumbnailUrlInput.sendKeys(thumbnailUrl);
  }

  async getThumbnailUrlInput() {
    return await this.thumbnailUrlInput.getAttribute('value');
  }

  async parentSelectLastOption() {
    await this.parentSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async parentSelectOption(option) {
    await this.parentSelect.sendKeys(option);
  }

  getParentSelect(): ElementFinder {
    return this.parentSelect;
  }

  async getParentSelectedOption() {
    return await this.parentSelect.element(by.css('option:checked')).getText();
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

export class ProductCategoryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productCategory-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productCategory'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
