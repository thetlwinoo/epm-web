import { element, by, ElementFinder } from 'protractor';

export class PersonEmailAddressComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-person-email-address div table .btn-danger'));
  title = element.all(by.css('jhi-person-email-address div h2#page-heading span')).first();

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

export class PersonEmailAddressUpdatePage {
  pageTitle = element(by.id('jhi-person-email-address-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  emailAddressInput = element(by.id('field_emailAddress'));
  defaultIndInput = element(by.id('field_defaultInd'));
  activeIndInput = element(by.id('field_activeInd'));
  personSelect = element(by.id('field_person'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setEmailAddressInput(emailAddress) {
    await this.emailAddressInput.sendKeys(emailAddress);
  }

  async getEmailAddressInput() {
    return await this.emailAddressInput.getAttribute('value');
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

export class PersonEmailAddressDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-personEmailAddress-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-personEmailAddress'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
