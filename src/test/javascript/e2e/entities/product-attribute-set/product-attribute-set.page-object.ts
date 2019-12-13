import { element, by, ElementFinder } from 'protractor';

export class ProductAttributeSetComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-attribute-set div table .btn-danger'));
  title = element.all(by.css('jhi-product-attribute-set div h2#page-heading span')).first();

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

export class ProductAttributeSetUpdatePage {
  pageTitle = element(by.id('jhi-product-attribute-set-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  productOptionSetSelect = element(by.id('field_productOptionSet'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async productOptionSetSelectLastOption() {
    await this.productOptionSetSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productOptionSetSelectOption(option) {
    await this.productOptionSetSelect.sendKeys(option);
  }

  getProductOptionSetSelect(): ElementFinder {
    return this.productOptionSetSelect;
  }

  async getProductOptionSetSelectedOption() {
    return await this.productOptionSetSelect.element(by.css('option:checked')).getText();
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

export class ProductAttributeSetDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productAttributeSet-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productAttributeSet'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
