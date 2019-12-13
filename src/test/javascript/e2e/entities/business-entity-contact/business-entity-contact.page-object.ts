import { element, by, ElementFinder } from 'protractor';

export class BusinessEntityContactComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-business-entity-contact div table .btn-danger'));
  title = element.all(by.css('jhi-business-entity-contact div h2#page-heading span')).first();

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

export class BusinessEntityContactUpdatePage {
  pageTitle = element(by.id('jhi-business-entity-contact-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  personSelect = element(by.id('field_person'));
  contactTypeSelect = element(by.id('field_contactType'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
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

  async contactTypeSelectLastOption() {
    await this.contactTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async contactTypeSelectOption(option) {
    await this.contactTypeSelect.sendKeys(option);
  }

  getContactTypeSelect(): ElementFinder {
    return this.contactTypeSelect;
  }

  async getContactTypeSelectedOption() {
    return await this.contactTypeSelect.element(by.css('option:checked')).getText();
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

export class BusinessEntityContactDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-businessEntityContact-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-businessEntityContact'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
