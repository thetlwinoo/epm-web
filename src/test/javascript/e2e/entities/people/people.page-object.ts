import { element, by, ElementFinder } from 'protractor';

export class PeopleComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-people div table .btn-danger'));
  title = element.all(by.css('jhi-people div h2#page-heading span')).first();

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

export class PeopleUpdatePage {
  pageTitle = element(by.id('jhi-people-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  fullNameInput = element(by.id('field_fullName'));
  preferredNameInput = element(by.id('field_preferredName'));
  searchNameInput = element(by.id('field_searchName'));
  genderSelect = element(by.id('field_gender'));
  isPermittedToLogonInput = element(by.id('field_isPermittedToLogon'));
  logonNameInput = element(by.id('field_logonName'));
  isExternalLogonProviderInput = element(by.id('field_isExternalLogonProvider'));
  isSystemUserInput = element(by.id('field_isSystemUser'));
  isEmployeeInput = element(by.id('field_isEmployee'));
  isSalesPersonInput = element(by.id('field_isSalesPerson'));
  isGuestUserInput = element(by.id('field_isGuestUser'));
  emailPromotionInput = element(by.id('field_emailPromotion'));
  userPreferencesInput = element(by.id('field_userPreferences'));
  phoneNumberInput = element(by.id('field_phoneNumber'));
  emailAddressInput = element(by.id('field_emailAddress'));
  photoInput = element(by.id('field_photo'));
  customFieldsInput = element(by.id('field_customFields'));
  otherLanguagesInput = element(by.id('field_otherLanguages'));
  validFromInput = element(by.id('field_validFrom'));
  validToInput = element(by.id('field_validTo'));
  userSelect = element(by.id('field_user'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFullNameInput(fullName) {
    await this.fullNameInput.sendKeys(fullName);
  }

  async getFullNameInput() {
    return await this.fullNameInput.getAttribute('value');
  }

  async setPreferredNameInput(preferredName) {
    await this.preferredNameInput.sendKeys(preferredName);
  }

  async getPreferredNameInput() {
    return await this.preferredNameInput.getAttribute('value');
  }

  async setSearchNameInput(searchName) {
    await this.searchNameInput.sendKeys(searchName);
  }

  async getSearchNameInput() {
    return await this.searchNameInput.getAttribute('value');
  }

  async setGenderSelect(gender) {
    await this.genderSelect.sendKeys(gender);
  }

  async getGenderSelect() {
    return await this.genderSelect.element(by.css('option:checked')).getText();
  }

  async genderSelectLastOption() {
    await this.genderSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  getIsPermittedToLogonInput() {
    return this.isPermittedToLogonInput;
  }
  async setLogonNameInput(logonName) {
    await this.logonNameInput.sendKeys(logonName);
  }

  async getLogonNameInput() {
    return await this.logonNameInput.getAttribute('value');
  }

  getIsExternalLogonProviderInput() {
    return this.isExternalLogonProviderInput;
  }
  getIsSystemUserInput() {
    return this.isSystemUserInput;
  }
  getIsEmployeeInput() {
    return this.isEmployeeInput;
  }
  getIsSalesPersonInput() {
    return this.isSalesPersonInput;
  }
  getIsGuestUserInput() {
    return this.isGuestUserInput;
  }
  async setEmailPromotionInput(emailPromotion) {
    await this.emailPromotionInput.sendKeys(emailPromotion);
  }

  async getEmailPromotionInput() {
    return await this.emailPromotionInput.getAttribute('value');
  }

  async setUserPreferencesInput(userPreferences) {
    await this.userPreferencesInput.sendKeys(userPreferences);
  }

  async getUserPreferencesInput() {
    return await this.userPreferencesInput.getAttribute('value');
  }

  async setPhoneNumberInput(phoneNumber) {
    await this.phoneNumberInput.sendKeys(phoneNumber);
  }

  async getPhoneNumberInput() {
    return await this.phoneNumberInput.getAttribute('value');
  }

  async setEmailAddressInput(emailAddress) {
    await this.emailAddressInput.sendKeys(emailAddress);
  }

  async getEmailAddressInput() {
    return await this.emailAddressInput.getAttribute('value');
  }

  async setPhotoInput(photo) {
    await this.photoInput.sendKeys(photo);
  }

  async getPhotoInput() {
    return await this.photoInput.getAttribute('value');
  }

  async setCustomFieldsInput(customFields) {
    await this.customFieldsInput.sendKeys(customFields);
  }

  async getCustomFieldsInput() {
    return await this.customFieldsInput.getAttribute('value');
  }

  async setOtherLanguagesInput(otherLanguages) {
    await this.otherLanguagesInput.sendKeys(otherLanguages);
  }

  async getOtherLanguagesInput() {
    return await this.otherLanguagesInput.getAttribute('value');
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

export class PeopleDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-people-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-people'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
