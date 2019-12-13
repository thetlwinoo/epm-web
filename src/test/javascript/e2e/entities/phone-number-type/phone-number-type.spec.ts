import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PhoneNumberTypeComponentsPage, PhoneNumberTypeDeleteDialog, PhoneNumberTypeUpdatePage } from './phone-number-type.page-object';

const expect = chai.expect;

describe('PhoneNumberType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let phoneNumberTypeComponentsPage: PhoneNumberTypeComponentsPage;
  let phoneNumberTypeUpdatePage: PhoneNumberTypeUpdatePage;
  let phoneNumberTypeDeleteDialog: PhoneNumberTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PhoneNumberTypes', async () => {
    await navBarPage.goToEntity('phone-number-type');
    phoneNumberTypeComponentsPage = new PhoneNumberTypeComponentsPage();
    await browser.wait(ec.visibilityOf(phoneNumberTypeComponentsPage.title), 5000);
    expect(await phoneNumberTypeComponentsPage.getTitle()).to.eq('epmwebApp.phoneNumberType.home.title');
  });

  it('should load create PhoneNumberType page', async () => {
    await phoneNumberTypeComponentsPage.clickOnCreateButton();
    phoneNumberTypeUpdatePage = new PhoneNumberTypeUpdatePage();
    expect(await phoneNumberTypeUpdatePage.getPageTitle()).to.eq('epmwebApp.phoneNumberType.home.createOrEditLabel');
    await phoneNumberTypeUpdatePage.cancel();
  });

  it('should create and save PhoneNumberTypes', async () => {
    const nbButtonsBeforeCreate = await phoneNumberTypeComponentsPage.countDeleteButtons();

    await phoneNumberTypeComponentsPage.clickOnCreateButton();
    await promise.all([phoneNumberTypeUpdatePage.setNameInput('name')]);
    expect(await phoneNumberTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    await phoneNumberTypeUpdatePage.save();
    expect(await phoneNumberTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await phoneNumberTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PhoneNumberType', async () => {
    const nbButtonsBeforeDelete = await phoneNumberTypeComponentsPage.countDeleteButtons();
    await phoneNumberTypeComponentsPage.clickOnLastDeleteButton();

    phoneNumberTypeDeleteDialog = new PhoneNumberTypeDeleteDialog();
    expect(await phoneNumberTypeDeleteDialog.getDialogTitle()).to.eq('epmwebApp.phoneNumberType.delete.question');
    await phoneNumberTypeDeleteDialog.clickOnConfirmButton();

    expect(await phoneNumberTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
