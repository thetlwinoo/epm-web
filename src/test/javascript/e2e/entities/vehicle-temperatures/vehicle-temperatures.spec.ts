import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  VehicleTemperaturesComponentsPage,
  VehicleTemperaturesDeleteDialog,
  VehicleTemperaturesUpdatePage
} from './vehicle-temperatures.page-object';

const expect = chai.expect;

describe('VehicleTemperatures e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let vehicleTemperaturesComponentsPage: VehicleTemperaturesComponentsPage;
  let vehicleTemperaturesUpdatePage: VehicleTemperaturesUpdatePage;
  let vehicleTemperaturesDeleteDialog: VehicleTemperaturesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load VehicleTemperatures', async () => {
    await navBarPage.goToEntity('vehicle-temperatures');
    vehicleTemperaturesComponentsPage = new VehicleTemperaturesComponentsPage();
    await browser.wait(ec.visibilityOf(vehicleTemperaturesComponentsPage.title), 5000);
    expect(await vehicleTemperaturesComponentsPage.getTitle()).to.eq('epmwebApp.vehicleTemperatures.home.title');
  });

  it('should load create VehicleTemperatures page', async () => {
    await vehicleTemperaturesComponentsPage.clickOnCreateButton();
    vehicleTemperaturesUpdatePage = new VehicleTemperaturesUpdatePage();
    expect(await vehicleTemperaturesUpdatePage.getPageTitle()).to.eq('epmwebApp.vehicleTemperatures.home.createOrEditLabel');
    await vehicleTemperaturesUpdatePage.cancel();
  });

  it('should create and save VehicleTemperatures', async () => {
    const nbButtonsBeforeCreate = await vehicleTemperaturesComponentsPage.countDeleteButtons();

    await vehicleTemperaturesComponentsPage.clickOnCreateButton();
    await promise.all([
      vehicleTemperaturesUpdatePage.setVehicleRegistrationInput('5'),
      vehicleTemperaturesUpdatePage.setChillerSensorNumberInput('chillerSensorNumber'),
      vehicleTemperaturesUpdatePage.setRecordedWhenInput('5'),
      vehicleTemperaturesUpdatePage.setTemperatureInput('5'),
      vehicleTemperaturesUpdatePage.setFullSensorDataInput('fullSensorData'),
      vehicleTemperaturesUpdatePage.setCompressedSensorDataInput('compressedSensorData')
    ]);
    expect(await vehicleTemperaturesUpdatePage.getVehicleRegistrationInput()).to.eq(
      '5',
      'Expected vehicleRegistration value to be equals to 5'
    );
    expect(await vehicleTemperaturesUpdatePage.getChillerSensorNumberInput()).to.eq(
      'chillerSensorNumber',
      'Expected ChillerSensorNumber value to be equals to chillerSensorNumber'
    );
    expect(await vehicleTemperaturesUpdatePage.getRecordedWhenInput()).to.eq('5', 'Expected recordedWhen value to be equals to 5');
    expect(await vehicleTemperaturesUpdatePage.getTemperatureInput()).to.eq('5', 'Expected temperature value to be equals to 5');
    const selectedIsCompressed = vehicleTemperaturesUpdatePage.getIsCompressedInput();
    if (await selectedIsCompressed.isSelected()) {
      await vehicleTemperaturesUpdatePage.getIsCompressedInput().click();
      expect(await vehicleTemperaturesUpdatePage.getIsCompressedInput().isSelected(), 'Expected isCompressed not to be selected').to.be
        .false;
    } else {
      await vehicleTemperaturesUpdatePage.getIsCompressedInput().click();
      expect(await vehicleTemperaturesUpdatePage.getIsCompressedInput().isSelected(), 'Expected isCompressed to be selected').to.be.true;
    }
    expect(await vehicleTemperaturesUpdatePage.getFullSensorDataInput()).to.eq(
      'fullSensorData',
      'Expected FullSensorData value to be equals to fullSensorData'
    );
    expect(await vehicleTemperaturesUpdatePage.getCompressedSensorDataInput()).to.eq(
      'compressedSensorData',
      'Expected CompressedSensorData value to be equals to compressedSensorData'
    );
    await vehicleTemperaturesUpdatePage.save();
    expect(await vehicleTemperaturesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await vehicleTemperaturesComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last VehicleTemperatures', async () => {
    const nbButtonsBeforeDelete = await vehicleTemperaturesComponentsPage.countDeleteButtons();
    await vehicleTemperaturesComponentsPage.clickOnLastDeleteButton();

    vehicleTemperaturesDeleteDialog = new VehicleTemperaturesDeleteDialog();
    expect(await vehicleTemperaturesDeleteDialog.getDialogTitle()).to.eq('epmwebApp.vehicleTemperatures.delete.question');
    await vehicleTemperaturesDeleteDialog.clickOnConfirmButton();

    expect(await vehicleTemperaturesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
