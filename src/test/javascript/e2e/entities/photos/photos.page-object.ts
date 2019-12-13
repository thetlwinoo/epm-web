import { element, by, ElementFinder } from 'protractor';

export class PhotosComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-photos div table .btn-danger'));
  title = element.all(by.css('jhi-photos div h2#page-heading span')).first();

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

export class PhotosUpdatePage {
  pageTitle = element(by.id('jhi-photos-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  thumbnailPhotoInput = element(by.id('field_thumbnailPhoto'));
  originalPhotoInput = element(by.id('field_originalPhoto'));
  bannerTallPhotoInput = element(by.id('field_bannerTallPhoto'));
  bannerWidePhotoInput = element(by.id('field_bannerWidePhoto'));
  circlePhotoInput = element(by.id('field_circlePhoto'));
  sharpenedPhotoInput = element(by.id('field_sharpenedPhoto'));
  squarePhotoInput = element(by.id('field_squarePhoto'));
  watermarkPhotoInput = element(by.id('field_watermarkPhoto'));
  thumbnailPhotoBlobInput = element(by.id('file_thumbnailPhotoBlob'));
  originalPhotoBlobInput = element(by.id('file_originalPhotoBlob'));
  bannerTallPhotoBlobInput = element(by.id('file_bannerTallPhotoBlob'));
  bannerWidePhotoBlobInput = element(by.id('file_bannerWidePhotoBlob'));
  circlePhotoBlobInput = element(by.id('file_circlePhotoBlob'));
  sharpenedPhotoBlobInput = element(by.id('file_sharpenedPhotoBlob'));
  squarePhotoBlobInput = element(by.id('file_squarePhotoBlob'));
  watermarkPhotoBlobInput = element(by.id('file_watermarkPhotoBlob'));
  priorityInput = element(by.id('field_priority'));
  defaultIndInput = element(by.id('field_defaultInd'));
  stockItemSelect = element(by.id('field_stockItem'));
  productCategorySelect = element(by.id('field_productCategory'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setThumbnailPhotoInput(thumbnailPhoto) {
    await this.thumbnailPhotoInput.sendKeys(thumbnailPhoto);
  }

  async getThumbnailPhotoInput() {
    return await this.thumbnailPhotoInput.getAttribute('value');
  }

  async setOriginalPhotoInput(originalPhoto) {
    await this.originalPhotoInput.sendKeys(originalPhoto);
  }

  async getOriginalPhotoInput() {
    return await this.originalPhotoInput.getAttribute('value');
  }

  async setBannerTallPhotoInput(bannerTallPhoto) {
    await this.bannerTallPhotoInput.sendKeys(bannerTallPhoto);
  }

  async getBannerTallPhotoInput() {
    return await this.bannerTallPhotoInput.getAttribute('value');
  }

  async setBannerWidePhotoInput(bannerWidePhoto) {
    await this.bannerWidePhotoInput.sendKeys(bannerWidePhoto);
  }

  async getBannerWidePhotoInput() {
    return await this.bannerWidePhotoInput.getAttribute('value');
  }

  async setCirclePhotoInput(circlePhoto) {
    await this.circlePhotoInput.sendKeys(circlePhoto);
  }

  async getCirclePhotoInput() {
    return await this.circlePhotoInput.getAttribute('value');
  }

  async setSharpenedPhotoInput(sharpenedPhoto) {
    await this.sharpenedPhotoInput.sendKeys(sharpenedPhoto);
  }

  async getSharpenedPhotoInput() {
    return await this.sharpenedPhotoInput.getAttribute('value');
  }

  async setSquarePhotoInput(squarePhoto) {
    await this.squarePhotoInput.sendKeys(squarePhoto);
  }

  async getSquarePhotoInput() {
    return await this.squarePhotoInput.getAttribute('value');
  }

  async setWatermarkPhotoInput(watermarkPhoto) {
    await this.watermarkPhotoInput.sendKeys(watermarkPhoto);
  }

  async getWatermarkPhotoInput() {
    return await this.watermarkPhotoInput.getAttribute('value');
  }

  async setThumbnailPhotoBlobInput(thumbnailPhotoBlob) {
    await this.thumbnailPhotoBlobInput.sendKeys(thumbnailPhotoBlob);
  }

  async getThumbnailPhotoBlobInput() {
    return await this.thumbnailPhotoBlobInput.getAttribute('value');
  }

  async setOriginalPhotoBlobInput(originalPhotoBlob) {
    await this.originalPhotoBlobInput.sendKeys(originalPhotoBlob);
  }

  async getOriginalPhotoBlobInput() {
    return await this.originalPhotoBlobInput.getAttribute('value');
  }

  async setBannerTallPhotoBlobInput(bannerTallPhotoBlob) {
    await this.bannerTallPhotoBlobInput.sendKeys(bannerTallPhotoBlob);
  }

  async getBannerTallPhotoBlobInput() {
    return await this.bannerTallPhotoBlobInput.getAttribute('value');
  }

  async setBannerWidePhotoBlobInput(bannerWidePhotoBlob) {
    await this.bannerWidePhotoBlobInput.sendKeys(bannerWidePhotoBlob);
  }

  async getBannerWidePhotoBlobInput() {
    return await this.bannerWidePhotoBlobInput.getAttribute('value');
  }

  async setCirclePhotoBlobInput(circlePhotoBlob) {
    await this.circlePhotoBlobInput.sendKeys(circlePhotoBlob);
  }

  async getCirclePhotoBlobInput() {
    return await this.circlePhotoBlobInput.getAttribute('value');
  }

  async setSharpenedPhotoBlobInput(sharpenedPhotoBlob) {
    await this.sharpenedPhotoBlobInput.sendKeys(sharpenedPhotoBlob);
  }

  async getSharpenedPhotoBlobInput() {
    return await this.sharpenedPhotoBlobInput.getAttribute('value');
  }

  async setSquarePhotoBlobInput(squarePhotoBlob) {
    await this.squarePhotoBlobInput.sendKeys(squarePhotoBlob);
  }

  async getSquarePhotoBlobInput() {
    return await this.squarePhotoBlobInput.getAttribute('value');
  }

  async setWatermarkPhotoBlobInput(watermarkPhotoBlob) {
    await this.watermarkPhotoBlobInput.sendKeys(watermarkPhotoBlob);
  }

  async getWatermarkPhotoBlobInput() {
    return await this.watermarkPhotoBlobInput.getAttribute('value');
  }

  async setPriorityInput(priority) {
    await this.priorityInput.sendKeys(priority);
  }

  async getPriorityInput() {
    return await this.priorityInput.getAttribute('value');
  }

  getDefaultIndInput() {
    return this.defaultIndInput;
  }

  async stockItemSelectLastOption() {
    await this.stockItemSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async stockItemSelectOption(option) {
    await this.stockItemSelect.sendKeys(option);
  }

  getStockItemSelect(): ElementFinder {
    return this.stockItemSelect;
  }

  async getStockItemSelectedOption() {
    return await this.stockItemSelect.element(by.css('option:checked')).getText();
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

export class PhotosDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-photos-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-photos'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
