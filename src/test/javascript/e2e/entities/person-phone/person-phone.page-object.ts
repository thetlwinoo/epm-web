import { element, by, ElementFinder } from 'protractor';

export class PersonPhoneComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-person-phone div table .btn-danger'));
  title = element.all(by.css('jhi-person-phone div h2#page-heading span')).first();

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

export class PersonPhoneUpdatePage {
  pageTitle = element(by.id('jhi-person-phone-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  phoneNumberInput = element(by.id('field_phoneNumber'));
  defaultIndInput = element(by.id('field_defaultInd'));
  activeIndInput = element(by.id('field_activeInd'));
  personSelect = element(by.id('field_person'));
  phoneNumberTypeSelect = element(by.id('field_phoneNumberType'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setPhoneNumberInput(phoneNumber) {
    await this.phoneNumberInput.sendKeys(phoneNumber);
  }

  async getPhoneNumberInput() {
    return await this.phoneNumberInput.getAttribute('value');
  }

  getDefaultIndInput() {
    return this.defaultIndInput;
  }
  getActiveIndInput() {
    return this.activeIndInput;
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

  async phoneNumberTypeSelectLastOption() {
    await this.phoneNumberTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async phoneNumberTypeSelectOption(option) {
    await this.phoneNumberTypeSelect.sendKeys(option);
  }

  getPhoneNumberTypeSelect(): ElementFinder {
    return this.phoneNumberTypeSelect;
  }

  async getPhoneNumberTypeSelectedOption() {
    return await this.phoneNumberTypeSelect.element(by.css('option:checked')).getText();
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

export class PersonPhoneDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-personPhone-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-personPhone'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
