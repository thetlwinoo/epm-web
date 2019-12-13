import { element, by, ElementFinder } from 'protractor';

export class ProductAttributeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-attribute div table .btn-danger'));
  title = element.all(by.css('jhi-product-attribute div h2#page-heading span')).first();

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

export class ProductAttributeUpdatePage {
  pageTitle = element(by.id('jhi-product-attribute-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  valueInput = element(by.id('field_value'));
  productAttributeSetSelect = element(by.id('field_productAttributeSet'));
  supplierSelect = element(by.id('field_supplier'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setValueInput(value) {
    await this.valueInput.sendKeys(value);
  }

  async getValueInput() {
    return await this.valueInput.getAttribute('value');
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

export class ProductAttributeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productAttribute-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productAttribute'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
