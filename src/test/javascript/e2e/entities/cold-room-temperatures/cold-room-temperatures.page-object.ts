import { element, by, ElementFinder } from 'protractor';

export class ColdRoomTemperaturesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cold-room-temperatures div table .btn-danger'));
  title = element.all(by.css('jhi-cold-room-temperatures div h2#page-heading span')).first();

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

export class ColdRoomTemperaturesUpdatePage {
  pageTitle = element(by.id('jhi-cold-room-temperatures-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  coldRoomSensorNumberInput = element(by.id('field_coldRoomSensorNumber'));
  recordedWhenInput = element(by.id('field_recordedWhen'));
  temperatureInput = element(by.id('field_temperature'));
  validFromInput = element(by.id('field_validFrom'));
  validToInput = element(by.id('field_validTo'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setColdRoomSensorNumberInput(coldRoomSensorNumber) {
    await this.coldRoomSensorNumberInput.sendKeys(coldRoomSensorNumber);
  }

  async getColdRoomSensorNumberInput() {
    return await this.coldRoomSensorNumberInput.getAttribute('value');
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

export class ColdRoomTemperaturesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-coldRoomTemperatures-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-coldRoomTemperatures'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
