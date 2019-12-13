import { element, by, ElementFinder } from 'protractor';

export class CompareProductsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-compare-products div table .btn-danger'));
  title = element.all(by.css('jhi-compare-products div h2#page-heading span')).first();

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

export class CompareProductsUpdatePage {
  pageTitle = element(by.id('jhi-compare-products-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  productSelect = element(by.id('field_product'));
  compareSelect = element(by.id('field_compare'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async productSelectLastOption() {
    await this.productSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productSelectOption(option) {
    await this.productSelect.sendKeys(option);
  }

  getProductSelect(): ElementFinder {
    return this.productSelect;
  }

  async getProductSelectedOption() {
    return await this.productSelect.element(by.css('option:checked')).getText();
  }

  async compareSelectLastOption() {
    await this.compareSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async compareSelectOption(option) {
    await this.compareSelect.sendKeys(option);
  }

  getCompareSelect(): ElementFinder {
    return this.compareSelect;
  }

  async getCompareSelectedOption() {
    return await this.compareSelect.element(by.css('option:checked')).getText();
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

export class CompareProductsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-compareProducts-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-compareProducts'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
