import { element, by, ElementFinder } from 'protractor';

export class ReviewsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-reviews div table .btn-danger'));
  title = element.all(by.css('jhi-reviews div h2#page-heading span')).first();

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

export class ReviewsUpdatePage {
  pageTitle = element(by.id('jhi-reviews-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  emailAddressInput = element(by.id('field_emailAddress'));
  reviewDateInput = element(by.id('field_reviewDate'));
  overAllSellerRatingInput = element(by.id('field_overAllSellerRating'));
  overAllSellerReviewInput = element(by.id('field_overAllSellerReview'));
  overAllDeliveryRatingInput = element(by.id('field_overAllDeliveryRating'));
  overAllDeliveryReviewInput = element(by.id('field_overAllDeliveryReview'));
  reviewAsAnonymousInput = element(by.id('field_reviewAsAnonymous'));
  completedReviewInput = element(by.id('field_completedReview'));
  lastEditedByInput = element(by.id('field_lastEditedBy'));
  lastEditedWhenInput = element(by.id('field_lastEditedWhen'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setEmailAddressInput(emailAddress) {
    await this.emailAddressInput.sendKeys(emailAddress);
  }

  async getEmailAddressInput() {
    return await this.emailAddressInput.getAttribute('value');
  }

  async setReviewDateInput(reviewDate) {
    await this.reviewDateInput.sendKeys(reviewDate);
  }

  async getReviewDateInput() {
    return await this.reviewDateInput.getAttribute('value');
  }

  async setOverAllSellerRatingInput(overAllSellerRating) {
    await this.overAllSellerRatingInput.sendKeys(overAllSellerRating);
  }

  async getOverAllSellerRatingInput() {
    return await this.overAllSellerRatingInput.getAttribute('value');
  }

  async setOverAllSellerReviewInput(overAllSellerReview) {
    await this.overAllSellerReviewInput.sendKeys(overAllSellerReview);
  }

  async getOverAllSellerReviewInput() {
    return await this.overAllSellerReviewInput.getAttribute('value');
  }

  async setOverAllDeliveryRatingInput(overAllDeliveryRating) {
    await this.overAllDeliveryRatingInput.sendKeys(overAllDeliveryRating);
  }

  async getOverAllDeliveryRatingInput() {
    return await this.overAllDeliveryRatingInput.getAttribute('value');
  }

  async setOverAllDeliveryReviewInput(overAllDeliveryReview) {
    await this.overAllDeliveryReviewInput.sendKeys(overAllDeliveryReview);
  }

  async getOverAllDeliveryReviewInput() {
    return await this.overAllDeliveryReviewInput.getAttribute('value');
  }

  getReviewAsAnonymousInput() {
    return this.reviewAsAnonymousInput;
  }
  getCompletedReviewInput() {
    return this.completedReviewInput;
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

export class ReviewsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-reviews-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-reviews'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
