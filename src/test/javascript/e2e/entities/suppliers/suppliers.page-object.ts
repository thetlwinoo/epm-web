import { element, by, ElementFinder } from 'protractor';

export class SuppliersComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-suppliers div table .btn-danger'));
  title = element.all(by.css('jhi-suppliers div h2#page-heading span')).first();

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

export class SuppliersUpdatePage {
  pageTitle = element(by.id('jhi-suppliers-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  supplierReferenceInput = element(by.id('field_supplierReference'));
  bankAccountNameInput = element(by.id('field_bankAccountName'));
  bankAccountBranchInput = element(by.id('field_bankAccountBranch'));
  bankAccountCodeInput = element(by.id('field_bankAccountCode'));
  bankAccountNumberInput = element(by.id('field_bankAccountNumber'));
  bankInternationalCodeInput = element(by.id('field_bankInternationalCode'));
  paymentDaysInput = element(by.id('field_paymentDays'));
  internalCommentsInput = element(by.id('field_internalComments'));
  phoneNumberInput = element(by.id('field_phoneNumber'));
  faxNumberInput = element(by.id('field_faxNumber'));
  websiteURLInput = element(by.id('field_websiteURL'));
  webServiceUrlInput = element(by.id('field_webServiceUrl'));
  creditRatingInput = element(by.id('field_creditRating'));
  activeFlagInput = element(by.id('field_activeFlag'));
  thumbnailUrlInput = element(by.id('field_thumbnailUrl'));
  validFromInput = element(by.id('field_validFrom'));
  validToInput = element(by.id('field_validTo'));
  userSelect = element(by.id('field_user'));
  supplierCategorySelect = element(by.id('field_supplierCategory'));
  deliveryMethodSelect = element(by.id('field_deliveryMethod'));
  deliveryCitySelect = element(by.id('field_deliveryCity'));
  postalCitySelect = element(by.id('field_postalCity'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setSupplierReferenceInput(supplierReference) {
    await this.supplierReferenceInput.sendKeys(supplierReference);
  }

  async getSupplierReferenceInput() {
    return await this.supplierReferenceInput.getAttribute('value');
  }

  async setBankAccountNameInput(bankAccountName) {
    await this.bankAccountNameInput.sendKeys(bankAccountName);
  }

  async getBankAccountNameInput() {
    return await this.bankAccountNameInput.getAttribute('value');
  }

  async setBankAccountBranchInput(bankAccountBranch) {
    await this.bankAccountBranchInput.sendKeys(bankAccountBranch);
  }

  async getBankAccountBranchInput() {
    return await this.bankAccountBranchInput.getAttribute('value');
  }

  async setBankAccountCodeInput(bankAccountCode) {
    await this.bankAccountCodeInput.sendKeys(bankAccountCode);
  }

  async getBankAccountCodeInput() {
    return await this.bankAccountCodeInput.getAttribute('value');
  }

  async setBankAccountNumberInput(bankAccountNumber) {
    await this.bankAccountNumberInput.sendKeys(bankAccountNumber);
  }

  async getBankAccountNumberInput() {
    return await this.bankAccountNumberInput.getAttribute('value');
  }

  async setBankInternationalCodeInput(bankInternationalCode) {
    await this.bankInternationalCodeInput.sendKeys(bankInternationalCode);
  }

  async getBankInternationalCodeInput() {
    return await this.bankInternationalCodeInput.getAttribute('value');
  }

  async setPaymentDaysInput(paymentDays) {
    await this.paymentDaysInput.sendKeys(paymentDays);
  }

  async getPaymentDaysInput() {
    return await this.paymentDaysInput.getAttribute('value');
  }

  async setInternalCommentsInput(internalComments) {
    await this.internalCommentsInput.sendKeys(internalComments);
  }

  async getInternalCommentsInput() {
    return await this.internalCommentsInput.getAttribute('value');
  }

  async setPhoneNumberInput(phoneNumber) {
    await this.phoneNumberInput.sendKeys(phoneNumber);
  }

  async getPhoneNumberInput() {
    return await this.phoneNumberInput.getAttribute('value');
  }

  async setFaxNumberInput(faxNumber) {
    await this.faxNumberInput.sendKeys(faxNumber);
  }

  async getFaxNumberInput() {
    return await this.faxNumberInput.getAttribute('value');
  }

  async setWebsiteURLInput(websiteURL) {
    await this.websiteURLInput.sendKeys(websiteURL);
  }

  async getWebsiteURLInput() {
    return await this.websiteURLInput.getAttribute('value');
  }

  async setWebServiceUrlInput(webServiceUrl) {
    await this.webServiceUrlInput.sendKeys(webServiceUrl);
  }

  async getWebServiceUrlInput() {
    return await this.webServiceUrlInput.getAttribute('value');
  }

  async setCreditRatingInput(creditRating) {
    await this.creditRatingInput.sendKeys(creditRating);
  }

  async getCreditRatingInput() {
    return await this.creditRatingInput.getAttribute('value');
  }

  getActiveFlagInput() {
    return this.activeFlagInput;
  }
  async setThumbnailUrlInput(thumbnailUrl) {
    await this.thumbnailUrlInput.sendKeys(thumbnailUrl);
  }

  async getThumbnailUrlInput() {
    return await this.thumbnailUrlInput.getAttribute('value');
  }

  async setValidFromInput(validFrom) {
    await this.validFromInput.sendKeys(validFrom);
  }

  async getValidFromInput() {
    return await this.validFromInput.getAttribute('value');
  }

  async setValidToInput(validTo) {
    await this.validToInput.sendKeys(validTo);
  }

  async getValidToInput() {
    return await this.validToInput.getAttribute('value');
  }

  async userSelectLastOption() {
    await this.userSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async userSelectOption(option) {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption() {
    return await this.userSelect.element(by.css('option:checked')).getText();
  }

  async supplierCategorySelectLastOption() {
    await this.supplierCategorySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async supplierCategorySelectOption(option) {
    await this.supplierCategorySelect.sendKeys(option);
  }

  getSupplierCategorySelect(): ElementFinder {
    return this.supplierCategorySelect;
  }

  async getSupplierCategorySelectedOption() {
    return await this.supplierCategorySelect.element(by.css('option:checked')).getText();
  }

  async deliveryMethodSelectLastOption() {
    await this.deliveryMethodSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async deliveryMethodSelectOption(option) {
    await this.deliveryMethodSelect.sendKeys(option);
  }

  getDeliveryMethodSelect(): ElementFinder {
    return this.deliveryMethodSelect;
  }

  async getDeliveryMethodSelectedOption() {
    return await this.deliveryMethodSelect.element(by.css('option:checked')).getText();
  }

  async deliveryCitySelectLastOption() {
    await this.deliveryCitySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async deliveryCitySelectOption(option) {
    await this.deliveryCitySelect.sendKeys(option);
  }

  getDeliveryCitySelect(): ElementFinder {
    return this.deliveryCitySelect;
  }

  async getDeliveryCitySelectedOption() {
    return await this.deliveryCitySelect.element(by.css('option:checked')).getText();
  }

  async postalCitySelectLastOption() {
    await this.postalCitySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async postalCitySelectOption(option) {
    await this.postalCitySelect.sendKeys(option);
  }

  getPostalCitySelect(): ElementFinder {
    return this.postalCitySelect;
  }

  async getPostalCitySelectedOption() {
    return await this.postalCitySelect.element(by.css('option:checked')).getText();
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

export class SuppliersDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-suppliers-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-suppliers'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
