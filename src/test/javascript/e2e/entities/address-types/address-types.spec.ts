import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AddressTypesComponentsPage, AddressTypesDeleteDialog, AddressTypesUpdatePage } from './address-types.page-object';

const expect = chai.expect;

describe('AddressTypes e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let addressTypesComponentsPage: AddressTypesComponentsPage;
  let addressTypesUpdatePage: AddressTypesUpdatePage;
  let addressTypesDeleteDialog: AddressTypesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AddressTypes', async () => {
    await navBarPage.goToEntity('address-types');
    addressTypesComponentsPage = new AddressTypesComponentsPage();
    await browser.wait(ec.visibilityOf(addressTypesComponentsPage.title), 5000);
    expect(await addressTypesComponentsPage.getTitle()).to.eq('epmwebApp.addressTypes.home.title');
  });

  it('should load create AddressTypes page', async () => {
    await addressTypesComponentsPage.clickOnCreateButton();
    addressTypesUpdatePage = new AddressTypesUpdatePage();
    expect(await addressTypesUpdatePage.getPageTitle()).to.eq('epmwebApp.addressTypes.home.createOrEditLabel');
    await addressTypesUpdatePage.cancel();
  });

  it('should create and save AddressTypes', async () => {
    const nbButtonsBeforeCreate = await addressTypesComponentsPage.countDeleteButtons();

    await addressTypesComponentsPage.clickOnCreateButton();
    await promise.all([addressTypesUpdatePage.setNameInput('name'), addressTypesUpdatePage.setReferInput('refer')]);
    expect(await addressTypesUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await addressTypesUpdatePage.getReferInput()).to.eq('refer', 'Expected Refer value to be equals to refer');
    await addressTypesUpdatePage.save();
    expect(await addressTypesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await addressTypesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last AddressTypes', async () => {
    const nbButtonsBeforeDelete = await addressTypesComponentsPage.countDeleteButtons();
    await addressTypesComponentsPage.clickOnLastDeleteButton();

    addressTypesDeleteDialog = new AddressTypesDeleteDialog();
    expect(await addressTypesDeleteDialog.getDialogTitle()).to.eq('epmwebApp.addressTypes.delete.question');
    await addressTypesDeleteDialog.clickOnConfirmButton();

    expect(await addressTypesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
