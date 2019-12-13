import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CurrencyComponentsPage, CurrencyDeleteDialog, CurrencyUpdatePage } from './currency.page-object';

const expect = chai.expect;

describe('Currency e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let currencyComponentsPage: CurrencyComponentsPage;
  let currencyUpdatePage: CurrencyUpdatePage;
  let currencyDeleteDialog: CurrencyDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Currencies', async () => {
    await navBarPage.goToEntity('currency');
    currencyComponentsPage = new CurrencyComponentsPage();
    await browser.wait(ec.visibilityOf(currencyComponentsPage.title), 5000);
    expect(await currencyComponentsPage.getTitle()).to.eq('epmwebApp.currency.home.title');
  });

  it('should load create Currency page', async () => {
    await currencyComponentsPage.clickOnCreateButton();
    currencyUpdatePage = new CurrencyUpdatePage();
    expect(await currencyUpdatePage.getPageTitle()).to.eq('epmwebApp.currency.home.createOrEditLabel');
    await currencyUpdatePage.cancel();
  });

  it('should create and save Currencies', async () => {
    const nbButtonsBeforeCreate = await currencyComponentsPage.countDeleteButtons();

    await currencyComponentsPage.clickOnCreateButton();
    await promise.all([currencyUpdatePage.setCodeInput('code'), currencyUpdatePage.setNameInput('name')]);
    expect(await currencyUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await currencyUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    await currencyUpdatePage.save();
    expect(await currencyUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await currencyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Currency', async () => {
    const nbButtonsBeforeDelete = await currencyComponentsPage.countDeleteButtons();
    await currencyComponentsPage.clickOnLastDeleteButton();

    currencyDeleteDialog = new CurrencyDeleteDialog();
    expect(await currencyDeleteDialog.getDialogTitle()).to.eq('epmwebApp.currency.delete.question');
    await currencyDeleteDialog.clickOnConfirmButton();

    expect(await currencyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
