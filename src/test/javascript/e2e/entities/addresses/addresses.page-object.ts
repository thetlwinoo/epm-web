import { element, by, ElementFinder } from 'protractor';

export class AddressesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-addresses div table .btn-danger'));
  title = element.all(by.css('jhi-addresses div h2#page-heading span')).first();

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

export class AddressesUpdatePage {
  pageTitle = element(by.id('jhi-addresses-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  contactPersonInput = element(by.id('field_contactPerson'));
  contactNumberInput = element(by.id('field_contactNumber'));
  contactEmailAddressInput = element(by.id('field_contactEmailAddress'));
  addressLine1Input = element(by.id('field_addressLine1'));
  addressLine2Input = element(by.id('field_addressLine2'));
  cityInput = element(by.id('field_city'));
  postalCodeInput = element(by.id('field_postalCode'));
  defaultIndInput = element(by.id('field_defaultInd'));
  activeIndInput = element(by.id('field_activeInd'));
  stateProvinceSelect = element(by.id('field_stateProvince'));
  addressTypeSelect = element(by.id('field_addressType'));
  personSelect = element(by.id('field_person'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setContactPersonInput(contactPerson) {
    await this.contactPersonInput.sendKeys(contactPerson);
  }

  async getContactPersonInput() {
    return await this.contactPersonInput.getAttribute('value');
  }

  async setContactNumberInput(contactNumber) {
    await this.contactNumberInput.sendKeys(contactNumber);
  }

  async getContactNumberInput() {
    return await this.contactNumberInput.getAttribute('value');
  }

  async setContactEmailAddressInput(contactEmailAddress) {
    await this.contactEmailAddressInput.sendKeys(contactEmailAddress);
  }

  async getContactEmailAddressInput() {
    return await this.contactEmailAddressInput.getAttribute('value');
  }

  async setAddressLine1Input(addressLine1) {
    await this.addressLine1Input.sendKeys(addressLine1);
  }

  async getAddressLine1Input() {
    return await this.addressLine1Input.getAttribute('value');
  }

  async setAddressLine2Input(addressLine2) {
    await this.addressLine2Input.sendKeys(addressLine2);
  }

  async getAddressLine2Input() {
    return await this.addressLine2Input.getAttribute('value');
  }

  async setCityInput(city) {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput() {
    return await this.cityInput.getAttribute('value');
  }

  async setPostalCodeInput(postalCode) {
    await this.postalCodeInput.sendKeys(postalCode);
  }

  async getPostalCodeInput() {
    return await this.postalCodeInput.getAttribute('value');
  }

  getDefaultIndInput() {
    return this.defaultIndInput;
  }
  getActiveIndInput() {
    return this.activeIndInput;
  }

  async stateProvinceSelectLastOption() {
    await this.stateProvinceSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async stateProvinceSelectOption(option) {
    await this.stateProvinceSelect.sendKeys(option);
  }

  getStateProvinceSelect(): ElementFinder {
    return this.stateProvinceSelect;
  }

  async getStateProvinceSelectedOption() {
    return await this.stateProvinceSelect.element(by.css('option:checked')).getText();
  }

  async addressTypeSelectLastOption() {
    await this.addressTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async addressTypeSelectOption(option) {
    await this.addressTypeSelect.sendKeys(option);
  }

  getAddressTypeSelect(): ElementFinder {
    return this.addressTypeSelect;
  }

  async getAddressTypeSelectedOption() {
    return await this.addressTypeSelect.element(by.css('option:checked')).getText();
  }

  async personSelectLastOption() {
    await this.personSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async personSelectOption(option) {
    await this.personSelect.sendKeys(option);
  }

  getPersonSelect(): ElementFinder {
    return this.personSelect;
  }

  async getPersonSelectedOption() {
    return await this.personSelect.element(by.css('option:checked')).getText();
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

export class AddressesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-addresses-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-addresses'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
