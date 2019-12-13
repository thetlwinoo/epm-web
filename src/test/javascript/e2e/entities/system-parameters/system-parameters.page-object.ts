import { element, by, ElementFinder } from 'protractor';

export class SystemParametersComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-system-parameters div table .btn-danger'));
  title = element.all(by.css('jhi-system-parameters div h2#page-heading span')).first();

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

export class SystemParametersUpdatePage {
  pageTitle = element(by.id('jhi-system-parameters-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  applicationSettingsInput = element(by.id('field_applicationSettings'));
  deliveryCitySelect = element(by.id('field_deliveryCity'));
  postalCitySelect = element(by.id('field_postalCity'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setApplicationSettingsInput(applicationSettings) {
    await this.applicationSettingsInput.sendKeys(applicationSettings);
  }

  async getApplicationSettingsInput() {
    return await this.applicationSettingsInput.getAttribute('value');
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

export class SystemParametersDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-systemParameters-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-systemParameters'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
