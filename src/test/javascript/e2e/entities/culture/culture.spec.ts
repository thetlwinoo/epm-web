import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CultureComponentsPage, CultureDeleteDialog, CultureUpdatePage } from './culture.page-object';

const expect = chai.expect;

describe('Culture e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cultureComponentsPage: CultureComponentsPage;
  let cultureUpdatePage: CultureUpdatePage;
  let cultureDeleteDialog: CultureDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Cultures', async () => {
    await navBarPage.goToEntity('culture');
    cultureComponentsPage = new CultureComponentsPage();
    await browser.wait(ec.visibilityOf(cultureComponentsPage.title), 5000);
    expect(await cultureComponentsPage.getTitle()).to.eq('epmwebApp.culture.home.title');
  });

  it('should load create Culture page', async () => {
    await cultureComponentsPage.clickOnCreateButton();
    cultureUpdatePage = new CultureUpdatePage();
    expect(await cultureUpdatePage.getPageTitle()).to.eq('epmwebApp.culture.home.createOrEditLabel');
    await cultureUpdatePage.cancel();
  });

  it('should create and save Cultures', async () => {
    const nbButtonsBeforeCreate = await cultureComponentsPage.countDeleteButtons();

    await cultureComponentsPage.clickOnCreateButton();
    await promise.all([cultureUpdatePage.setCodeInput('code'), cultureUpdatePage.setNameInput('name')]);
    expect(await cultureUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await cultureUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    await cultureUpdatePage.save();
    expect(await cultureUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await cultureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Culture', async () => {
    const nbButtonsBeforeDelete = await cultureComponentsPage.countDeleteButtons();
    await cultureComponentsPage.clickOnLastDeleteButton();

    cultureDeleteDialog = new CultureDeleteDialog();
    expect(await cultureDeleteDialog.getDialogTitle()).to.eq('epmwebApp.culture.delete.question');
    await cultureDeleteDialog.clickOnConfirmButton();

    expect(await cultureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
