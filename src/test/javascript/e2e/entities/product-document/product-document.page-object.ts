import { element, by, ElementFinder } from 'protractor';

export class ProductDocumentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-document div table .btn-danger'));
  title = element.all(by.css('jhi-product-document div h2#page-heading span')).first();

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

export class ProductDocumentUpdatePage {
  pageTitle = element(by.id('jhi-product-document-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  videoUrlInput = element(by.id('field_videoUrl'));
  highlightsInput = element(by.id('field_highlights'));
  longDescriptionInput = element(by.id('field_longDescription'));
  shortDescriptionInput = element(by.id('field_shortDescription'));
  descriptionInput = element(by.id('field_description'));
  careInstructionsInput = element(by.id('field_careInstructions'));
  productTypeInput = element(by.id('field_productType'));
  modelNameInput = element(by.id('field_modelName'));
  modelNumberInput = element(by.id('field_modelNumber'));
  fabricTypeInput = element(by.id('field_fabricType'));
  specialFeaturesInput = element(by.id('field_specialFeatures'));
  productComplianceCertificateInput = element(by.id('field_productComplianceCertificate'));
  genuineAndLegalInput = element(by.id('field_genuineAndLegal'));
  countryOfOriginInput = element(by.id('field_countryOfOrigin'));
  usageAndSideEffectsInput = element(by.id('field_usageAndSideEffects'));
  safetyWarnningInput = element(by.id('field_safetyWarnning'));
  warrantyPeriodInput = element(by.id('field_warrantyPeriod'));
  warrantyPolicyInput = element(by.id('field_warrantyPolicy'));
  warrantyTypeSelect = element(by.id('field_warrantyType'));
  cultureSelect = element(by.id('field_culture'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setVideoUrlInput(videoUrl) {
    await this.videoUrlInput.sendKeys(videoUrl);
  }

  async getVideoUrlInput() {
    return await this.videoUrlInput.getAttribute('value');
  }

  async setHighlightsInput(highlights) {
    await this.highlightsInput.sendKeys(highlights);
  }

  async getHighlightsInput() {
    return await this.highlightsInput.getAttribute('value');
  }

  async setLongDescriptionInput(longDescription) {
    await this.longDescriptionInput.sendKeys(longDescription);
  }

  async getLongDescriptionInput() {
    return await this.longDescriptionInput.getAttribute('value');
  }

  async setShortDescriptionInput(shortDescription) {
    await this.shortDescriptionInput.sendKeys(shortDescription);
  }

  async getShortDescriptionInput() {
    return await this.shortDescriptionInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setCareInstructionsInput(careInstructions) {
    await this.careInstructionsInput.sendKeys(careInstructions);
  }

  async getCareInstructionsInput() {
    return await this.careInstructionsInput.getAttribute('value');
  }

  async setProductTypeInput(productType) {
    await this.productTypeInput.sendKeys(productType);
  }

  async getProductTypeInput() {
    return await this.productTypeInput.getAttribute('value');
  }

  async setModelNameInput(modelName) {
    await this.modelNameInput.sendKeys(modelName);
  }

  async getModelNameInput() {
    return await this.modelNameInput.getAttribute('value');
  }

  async setModelNumberInput(modelNumber) {
    await this.modelNumberInput.sendKeys(modelNumber);
  }

  async getModelNumberInput() {
    return await this.modelNumberInput.getAttribute('value');
  }

  async setFabricTypeInput(fabricType) {
    await this.fabricTypeInput.sendKeys(fabricType);
  }

  async getFabricTypeInput() {
    return await this.fabricTypeInput.getAttribute('value');
  }

  async setSpecialFeaturesInput(specialFeatures) {
    await this.specialFeaturesInput.sendKeys(specialFeatures);
  }

  async getSpecialFeaturesInput() {
    return await this.specialFeaturesInput.getAttribute('value');
  }

  async setProductComplianceCertificateInput(productComplianceCertificate) {
    await this.productComplianceCertificateInput.sendKeys(productComplianceCertificate);
  }

  async getProductComplianceCertificateInput() {
    return await this.productComplianceCertificateInput.getAttribute('value');
  }

  getGenuineAndLegalInput() {
    return this.genuineAndLegalInput;
  }
  async setCountryOfOriginInput(countryOfOrigin) {
    await this.countryOfOriginInput.sendKeys(countryOfOrigin);
  }

  async getCountryOfOriginInput() {
    return await this.countryOfOriginInput.getAttribute('value');
  }

  async setUsageAndSideEffectsInput(usageAndSideEffects) {
    await this.usageAndSideEffectsInput.sendKeys(usageAndSideEffects);
  }

  async getUsageAndSideEffectsInput() {
    return await this.usageAndSideEffectsInput.getAttribute('value');
  }

  async setSafetyWarnningInput(safetyWarnning) {
    await this.safetyWarnningInput.sendKeys(safetyWarnning);
  }

  async getSafetyWarnningInput() {
    return await this.safetyWarnningInput.getAttribute('value');
  }

  async setWarrantyPeriodInput(warrantyPeriod) {
    await this.warrantyPeriodInput.sendKeys(warrantyPeriod);
  }

  async getWarrantyPeriodInput() {
    return await this.warrantyPeriodInput.getAttribute('value');
  }

  async setWarrantyPolicyInput(warrantyPolicy) {
    await this.warrantyPolicyInput.sendKeys(warrantyPolicy);
  }

  async getWarrantyPolicyInput() {
    return await this.warrantyPolicyInput.getAttribute('value');
  }

  async warrantyTypeSelectLastOption() {
    await this.warrantyTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async warrantyTypeSelectOption(option) {
    await this.warrantyTypeSelect.sendKeys(option);
  }

  getWarrantyTypeSelect(): ElementFinder {
    return this.warrantyTypeSelect;
  }

  async getWarrantyTypeSelectedOption() {
    return await this.warrantyTypeSelect.element(by.css('option:checked')).getText();
  }

  async cultureSelectLastOption() {
    await this.cultureSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async cultureSelectOption(option) {
    await this.cultureSelect.sendKeys(option);
  }

  getCultureSelect(): ElementFinder {
    return this.cultureSelect;
  }

  async getCultureSelectedOption() {
    return await this.cultureSelect.element(by.css('option:checked')).getText();
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

export class ProductDocumentDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productDocument-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productDocument'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
