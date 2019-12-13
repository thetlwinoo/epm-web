import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PackageTypesComponentsPage, PackageTypesDeleteDialog, PackageTypesUpdatePage } from './package-types.page-object';

const expect = chai.expect;

describe('PackageTypes e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let packageTypesComponentsPage: PackageTypesComponentsPage;
  let packageTypesUpdatePage: PackageTypesUpdatePage;
  let packageTypesDeleteDialog: PackageTypesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PackageTypes', async () => {
    await navBarPage.goToEntity('package-types');
    packageTypesComponentsPage = new PackageTypesComponentsPage();
    await browser.wait(ec.visibilityOf(packageTypesComponentsPage.title), 5000);
    expect(await packageTypesComponentsPage.getTitle()).to.eq('epmwebApp.packageTypes.home.title');
  });

  it('should load create PackageTypes page', async () => {
    await packageTypesComponentsPage.clickOnCreateButton();
    packageTypesUpdatePage = new PackageTypesUpdatePage();
    expect(await packageTypesUpdatePage.getPageTitle()).to.eq('epmwebApp.packageTypes.home.createOrEditLabel');
    await packageTypesUpdatePage.cancel();
  });

  it('should create and save PackageTypes', async () => {
    const nbButtonsBeforeCreate = await packageTypesComponentsPage.countDeleteButtons();

    await packageTypesComponentsPage.clickOnCreateButton();
    await promise.all([
      packageTypesUpdatePage.setNameInput('name'),
      packageTypesUpdatePage.setValidFromInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      packageTypesUpdatePage.setValidToInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
    ]);
    expect(await packageTypesUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await packageTypesUpdatePage.getValidFromInput()).to.contain(
      '2001-01-01T02:30',
      'Expected validFrom value to be equals to 2000-12-31'
    );
    expect(await packageTypesUpdatePage.getValidToInput()).to.contain(
      '2001-01-01T02:30',
      'Expected validTo value to be equals to 2000-12-31'
    );
    await packageTypesUpdatePage.save();
    expect(await packageTypesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await packageTypesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PackageTypes', async () => {
    const nbButtonsBeforeDelete = await packageTypesComponentsPage.countDeleteButtons();
    await packageTypesComponentsPage.clickOnLastDeleteButton();

    packageTypesDeleteDialog = new PackageTypesDeleteDialog();
    expect(await packageTypesDeleteDialog.getDialogTitle()).to.eq('epmwebApp.packageTypes.delete.question');
    await packageTypesDeleteDialog.clickOnConfirmButton();

    expect(await packageTypesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
