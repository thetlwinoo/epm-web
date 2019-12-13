import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SystemParametersComponentsPage, SystemParametersDeleteDialog, SystemParametersUpdatePage } from './system-parameters.page-object';

const expect = chai.expect;

describe('SystemParameters e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let systemParametersComponentsPage: SystemParametersComponentsPage;
  let systemParametersUpdatePage: SystemParametersUpdatePage;
  let systemParametersDeleteDialog: SystemParametersDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SystemParameters', async () => {
    await navBarPage.goToEntity('system-parameters');
    systemParametersComponentsPage = new SystemParametersComponentsPage();
    await browser.wait(ec.visibilityOf(systemParametersComponentsPage.title), 5000);
    expect(await systemParametersComponentsPage.getTitle()).to.eq('epmwebApp.systemParameters.home.title');
  });

  it('should load create SystemParameters page', async () => {
    await systemParametersComponentsPage.clickOnCreateButton();
    systemParametersUpdatePage = new SystemParametersUpdatePage();
    expect(await systemParametersUpdatePage.getPageTitle()).to.eq('epmwebApp.systemParameters.home.createOrEditLabel');
    await systemParametersUpdatePage.cancel();
  });

  it('should create and save SystemParameters', async () => {
    const nbButtonsBeforeCreate = await systemParametersComponentsPage.countDeleteButtons();

    await systemParametersComponentsPage.clickOnCreateButton();
    await promise.all([
      systemParametersUpdatePage.setApplicationSettingsInput('applicationSettings'),
      systemParametersUpdatePage.deliveryCitySelectLastOption(),
      systemParametersUpdatePage.postalCitySelectLastOption()
    ]);
    expect(await systemParametersUpdatePage.getApplicationSettingsInput()).to.eq(
      'applicationSettings',
      'Expected ApplicationSettings value to be equals to applicationSettings'
    );
    await systemParametersUpdatePage.save();
    expect(await systemParametersUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await systemParametersComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last SystemParameters', async () => {
    const nbButtonsBeforeDelete = await systemParametersComponentsPage.countDeleteButtons();
    await systemParametersComponentsPage.clickOnLastDeleteButton();

    systemParametersDeleteDialog = new SystemParametersDeleteDialog();
    expect(await systemParametersDeleteDialog.getDialogTitle()).to.eq('epmwebApp.systemParameters.delete.question');
    await systemParametersDeleteDialog.clickOnConfirmButton();

    expect(await systemParametersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
