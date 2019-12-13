import { element, by, ElementFinder } from 'protractor';

export class WishlistsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-wishlists div table .btn-danger'));
  title = element.all(by.css('jhi-wishlists div h2#page-heading span')).first();

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

export class WishlistsUpdatePage {
  pageTitle = element(by.id('jhi-wishlists-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  wishlistUserSelect = element(by.id('field_wishlistUser'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async wishlistUserSelectLastOption() {
    await this.wishlistUserSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async wishlistUserSelectOption(option) {
    await this.wishlistUserSelect.sendKeys(option);
  }

  getWishlistUserSelect(): ElementFinder {
    return this.wishlistUserSelect;
  }

  async getWishlistUserSelectedOption() {
    return await this.wishlistUserSelect.element(by.css('option:checked')).getText();
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

export class WishlistsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-wishlists-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-wishlists'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
