import { element, by, ElementFinder } from 'protractor';

export class BusinessEntityAddressComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-business-entity-address div table .btn-danger'));
  title = element.all(by.css('jhi-business-entity-address div h2#page-heading span')).first();

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

export class BusinessEntityAddressUpdatePage {
  pageTitle = element(by.id('jhi-business-entity-address-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  addressSelect = element(by.id('field_address'));
  personSelect = element(by.id('field_person'));
  addressTypeSelect = element(by.id('field_addressType'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async addressSelectLastOption() {
    await this.addressSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async addressSelectOption(option) {
    await this.addressSelect.sendKeys(option);
  }

  getAddressSelect(): ElementFinder {
    return this.addressSelect;
  }

  async getAddressSelectedOption() {
    return await this.addressSelect.element(by.css('option:checked')).getText();
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

export class BusinessEntityAddressDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-businessEntityAddress-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-businessEntityAddress'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
