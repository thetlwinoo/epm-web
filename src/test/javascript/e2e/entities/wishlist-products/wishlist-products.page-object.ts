import { element, by, ElementFinder } from 'protractor';

export class WishlistProductsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-wishlist-products div table .btn-danger'));
  title = element.all(by.css('jhi-wishlist-products div h2#page-heading span')).first();

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

export class WishlistProductsUpdatePage {
  pageTitle = element(by.id('jhi-wishlist-products-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  productSelect = element(by.id('field_product'));
  wishlistSelect = element(by.id('field_wishlist'));

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

  async wishlistSelectLastOption() {
    await this.wishlistSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async wishlistSelectOption(option) {
    await this.wishlistSelect.sendKeys(option);
  }

  getWishlistSelect(): ElementFinder {
    return this.wishlistSelect;
  }

  async getWishlistSelectedOption() {
    return await this.wishlistSelect.element(by.css('option:checked')).getText();
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

export class WishlistProductsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-wishlistProducts-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-wishlistProducts'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
