import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  BusinessEntityAddressComponentsPage,
  BusinessEntityAddressDeleteDialog,
  BusinessEntityAddressUpdatePage
} from './business-entity-address.page-object';

const expect = chai.expect;

describe('BusinessEntityAddress e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let businessEntityAddressComponentsPage: BusinessEntityAddressComponentsPage;
  let businessEntityAddressUpdatePage: BusinessEntityAddressUpdatePage;
  let businessEntityAddressDeleteDialog: BusinessEntityAddressDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BusinessEntityAddresses', async () => {
    await navBarPage.goToEntity('business-entity-address');
    businessEntityAddressComponentsPage = new BusinessEntityAddressComponentsPage();
    await browser.wait(ec.visibilityOf(businessEntityAddressComponentsPage.title), 5000);
    expect(await businessEntityAddressComponentsPage.getTitle()).to.eq('epmwebApp.businessEntityAddress.home.title');
  });

  it('should load create BusinessEntityAddress page', async () => {
    await businessEntityAddressComponentsPage.clickOnCreateButton();
    businessEntityAddressUpdatePage = new BusinessEntityAddressUpdatePage();
    expect(await businessEntityAddressUpdatePage.getPageTitle()).to.eq('epmwebApp.businessEntityAddress.home.createOrEditLabel');
    await businessEntityAddressUpdatePage.cancel();
  });

  it('should create and save BusinessEntityAddresses', async () => {
    const nbButtonsBeforeCreate = await businessEntityAddressComponentsPage.countDeleteButtons();

    await businessEntityAddressComponentsPage.clickOnCreateButton();
    await promise.all([
      businessEntityAddressUpdatePage.addressSelectLastOption(),
      businessEntityAddressUpdatePage.personSelectLastOption(),
      businessEntityAddressUpdatePage.addressTypeSelectLastOption()
    ]);
    await businessEntityAddressUpdatePage.save();
    expect(await businessEntityAddressUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await businessEntityAddressComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last BusinessEntityAddress', async () => {
    const nbButtonsBeforeDelete = await businessEntityAddressComponentsPage.countDeleteButtons();
    await businessEntityAddressComponentsPage.clickOnLastDeleteButton();

    businessEntityAddressDeleteDialog = new BusinessEntityAddressDeleteDialog();
    expect(await businessEntityAddressDeleteDialog.getDialogTitle()).to.eq('epmwebApp.businessEntityAddress.delete.question');
    await businessEntityAddressDeleteDialog.clickOnConfirmButton();

    expect(await businessEntityAddressComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
