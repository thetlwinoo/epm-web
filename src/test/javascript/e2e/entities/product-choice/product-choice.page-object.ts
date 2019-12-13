import { element, by, ElementFinder } from 'protractor';

export class ProductChoiceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-choice div table .btn-danger'));
  title = element.all(by.css('jhi-product-choice div h2#page-heading span')).first();

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

export class ProductChoiceUpdatePage {
  pageTitle = element(by.id('jhi-product-choice-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  isMultiplyInput = element(by.id('field_isMultiply'));
  productCategorySelect = element(by.id('field_productCategory'));
  productAttributeSetSelect = element(by.id('field_productAttributeSet'));
  productOptionSetSelect = element(by.id('field_productOptionSet'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  getIsMultiplyInput() {
    return this.isMultiplyInput;
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

  async productAttributeSetSelectLastOption() {
    await this.productAttributeSetSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productAttributeSetSelectOption(option) {
    await this.productAttributeSetSelect.sendKeys(option);
  }

  getProductAttributeSetSelect(): ElementFinder {
    return this.productAttributeSetSelect;
  }

  async getProductAttributeSetSelectedOption() {
    return await this.productAttributeSetSelect.element(by.css('option:checked')).getText();
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

export class ProductChoiceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productChoice-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productChoice'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
