import { element, by, ElementFinder } from 'protractor';

export class CitiesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cities div table .btn-danger'));
  title = element.all(by.css('jhi-cities div h2#page-heading span')).first();

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

export class CitiesUpdatePage {
  pageTitle = element(by.id('jhi-cities-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  locationInput = element(by.id('field_location'));
  latestRecordedPopulationInput = element(by.id('field_latestRecordedPopulation'));
  validFromInput = element(by.id('field_validFrom'));
  validToInput = element(by.id('field_validTo'));
  stateProvinceSelect = element(by.id('field_stateProvince'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setLocationInput(location) {
    await this.locationInput.sendKeys(location);
  }

  async getLocationInput() {
    return await this.locationInput.getAttribute('value');
  }

  async setLatestRecordedPopulationInput(latestRecordedPopulation) {
    await this.latestRecordedPopulationInput.sendKeys(latestRecordedPopulation);
  }

  async getLatestRecordedPopulationInput() {
    return await this.latestRecordedPopulationInput.getAttribute('value');
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

export class CitiesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-cities-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-cities'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
