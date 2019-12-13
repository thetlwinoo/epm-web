import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ColdRoomTemperaturesComponentsPage,
  ColdRoomTemperaturesDeleteDialog,
  ColdRoomTemperaturesUpdatePage
} from './cold-room-temperatures.page-object';

const expect = chai.expect;

describe('ColdRoomTemperatures e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let coldRoomTemperaturesComponentsPage: ColdRoomTemperaturesComponentsPage;
  let coldRoomTemperaturesUpdatePage: ColdRoomTemperaturesUpdatePage;
  let coldRoomTemperaturesDeleteDialog: ColdRoomTemperaturesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ColdRoomTemperatures', async () => {
    await navBarPage.goToEntity('cold-room-temperatures');
    coldRoomTemperaturesComponentsPage = new ColdRoomTemperaturesComponentsPage();
    await browser.wait(ec.visibilityOf(coldRoomTemperaturesComponentsPage.title), 5000);
    expect(await coldRoomTemperaturesComponentsPage.getTitle()).to.eq('epmwebApp.coldRoomTemperatures.home.title');
  });

  it('should load create ColdRoomTemperatures page', async () => {
    await coldRoomTemperaturesComponentsPage.clickOnCreateButton();
    coldRoomTemperaturesUpdatePage = new ColdRoomTemperaturesUpdatePage();
    expect(await coldRoomTemperaturesUpdatePage.getPageTitle()).to.eq('epmwebApp.coldRoomTemperatures.home.createOrEditLabel');
    await coldRoomTemperaturesUpdatePage.cancel();
  });

  it('should create and save ColdRoomTemperatures', async () => {
    const nbButtonsBeforeCreate = await coldRoomTemperaturesComponentsPage.countDeleteButtons();

    await coldRoomTemperaturesComponentsPage.clickOnCreateButton();
    await promise.all([
      coldRoomTemperaturesUpdatePage.setColdRoomSensorNumberInput('5'),
      coldRoomTemperaturesUpdatePage.setRecordedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      coldRoomTemperaturesUpdatePage.setTemperatureInput('5'),
      coldRoomTemperaturesUpdatePage.setValidFromInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      coldRoomTemperaturesUpdatePage.setValidToInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
    ]);
    expect(await coldRoomTemperaturesUpdatePage.getColdRoomSensorNumberInput()).to.eq(
      '5',
      'Expected coldRoomSensorNumber value to be equals to 5'
    );
    expect(await coldRoomTemperaturesUpdatePage.getRecordedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected recordedWhen value to be equals to 2000-12-31'
    );
    expect(await coldRoomTemperaturesUpdatePage.getTemperatureInput()).to.eq('5', 'Expected temperature value to be equals to 5');
    expect(await coldRoomTemperaturesUpdatePage.getValidFromInput()).to.contain(
      '2001-01-01T02:30',
      'Expected validFrom value to be equals to 2000-12-31'
    );
    expect(await coldRoomTemperaturesUpdatePage.getValidToInput()).to.contain(
      '2001-01-01T02:30',
      'Expected validTo value to be equals to 2000-12-31'
    );
    await coldRoomTemperaturesUpdatePage.save();
    expect(await coldRoomTemperaturesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await coldRoomTemperaturesComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ColdRoomTemperatures', async () => {
    const nbButtonsBeforeDelete = await coldRoomTemperaturesComponentsPage.countDeleteButtons();
    await coldRoomTemperaturesComponentsPage.clickOnLastDeleteButton();

    coldRoomTemperaturesDeleteDialog = new ColdRoomTemperaturesDeleteDialog();
    expect(await coldRoomTemperaturesDeleteDialog.getDialogTitle()).to.eq('epmwebApp.coldRoomTemperatures.delete.question');
    await coldRoomTemperaturesDeleteDialog.clickOnConfirmButton();

    expect(await coldRoomTemperaturesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
