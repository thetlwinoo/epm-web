import { element, by, ElementFinder } from 'protractor';

export class VehicleTemperaturesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-vehicle-temperatures div table .btn-danger'));
  title = element.all(by.css('jhi-vehicle-temperatures div h2#page-heading span')).first();

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

export class VehicleTemperaturesUpdatePage {
  pageTitle = element(by.id('jhi-vehicle-temperatures-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  vehicleRegistrationInput = element(by.id('field_vehicleRegistration'));
  chillerSensorNumberInput = element(by.id('field_chillerSensorNumber'));
  recordedWhenInput = element(by.id('field_recordedWhen'));
  temperatureInput = element(by.id('field_temperature'));
  isCompressedInput = element(by.id('field_isCompressed'));
  fullSensorDataInput = element(by.id('field_fullSensorData'));
  compressedSensorDataInput = element(by.id('field_compressedSensorData'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setVehicleRegistrationInput(vehicleRegistration) {
    await this.vehicleRegistrationInput.sendKeys(vehicleRegistration);
  }

  async getVehicleRegistrationInput() {
    return await this.vehicleRegistrationInput.getAttribute('value');
  }

  async setChillerSensorNumberInput(chillerSensorNumber) {
    await this.chillerSensorNumberInput.sendKeys(chillerSensorNumber);
  }

  async getChillerSensorNumberInput() {
    return await this.chillerSensorNumberInput.getAttribute('value');
  }

  async setRecordedWhenInput(recordedWhen) {
    await this.recordedWhenInput.sendKeys(recordedWhen);
  }

  async getRecordedWhenInput() {
    return await this.recordedWhenInput.getAttribute('value');
  }

  async setTemperatureInput(temperature) {
    await this.temperatureInput.sendKeys(temperature);
  }

  async getTemperatureInput() {
    return await this.temperatureInput.getAttribute('value');
  }

  getIsCompressedInput() {
    return this.isCompressedInput;
  }
  async setFullSensorDataInput(fullSensorData) {
    await this.fullSensorDataInput.sendKeys(fullSensorData);
  }

  async getFullSensorDataInput() {
    return await this.fullSensorDataInput.getAttribute('value');
  }

  async setCompressedSensorDataInput(compressedSensorData) {
    await this.compressedSensorDataInput.sendKeys(compressedSensorData);
  }

  async getCompressedSensorDataInput() {
    return await this.compressedSensorDataInput.getAttribute('value');
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

export class VehicleTemperaturesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-vehicleTemperatures-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-vehicleTemperatures'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
