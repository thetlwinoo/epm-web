import { element, by, ElementFinder } from 'protractor';

export class CountriesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-countries div table .btn-danger'));
  title = element.all(by.css('jhi-countries div h2#page-heading span')).first();

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

export class CountriesUpdatePage {
  pageTitle = element(by.id('jhi-countries-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  formalNameInput = element(by.id('field_formalName'));
  isoAplha3CodeInput = element(by.id('field_isoAplha3Code'));
  isoNumericCodeInput = element(by.id('field_isoNumericCode'));
  countryTypeInput = element(by.id('field_countryType'));
  latestRecordedPopulationInput = element(by.id('field_latestRecordedPopulation'));
  continentInput = element(by.id('field_continent'));
  regionInput = element(by.id('field_region'));
  subregionInput = element(by.id('field_subregion'));
  borderInput = element(by.id('field_border'));
  validFromInput = element(by.id('field_validFrom'));
  validToInput = element(by.id('field_validTo'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setFormalNameInput(formalName) {
    await this.formalNameInput.sendKeys(formalName);
  }

  async getFormalNameInput() {
    return await this.formalNameInput.getAttribute('value');
  }

  async setIsoAplha3CodeInput(isoAplha3Code) {
    await this.isoAplha3CodeInput.sendKeys(isoAplha3Code);
  }

  async getIsoAplha3CodeInput() {
    return await this.isoAplha3CodeInput.getAttribute('value');
  }

  async setIsoNumericCodeInput(isoNumericCode) {
    await this.isoNumericCodeInput.sendKeys(isoNumericCode);
  }

  async getIsoNumericCodeInput() {
    return await this.isoNumericCodeInput.getAttribute('value');
  }

  async setCountryTypeInput(countryType) {
    await this.countryTypeInput.sendKeys(countryType);
  }

  async getCountryTypeInput() {
    return await this.countryTypeInput.getAttribute('value');
  }

  async setLatestRecordedPopulationInput(latestRecordedPopulation) {
    await this.latestRecordedPopulationInput.sendKeys(latestRecordedPopulation);
  }

  async getLatestRecordedPopulationInput() {
    return await this.latestRecordedPopulationInput.getAttribute('value');
  }

  async setContinentInput(continent) {
    await this.continentInput.sendKeys(continent);
  }

  async getContinentInput() {
    return await this.continentInput.getAttribute('value');
  }

  async setRegionInput(region) {
    await this.regionInput.sendKeys(region);
  }

  async getRegionInput() {
    return await this.regionInput.getAttribute('value');
  }

  async setSubregionInput(subregion) {
    await this.subregionInput.sendKeys(subregion);
  }

  async getSubregionInput() {
    return await this.subregionInput.getAttribute('value');
  }

  async setBorderInput(border) {
    await this.borderInput.sendKeys(border);
  }

  async getBorderInput() {
    return await this.borderInput.getAttribute('value');
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

export class CountriesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-countries-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-countries'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
