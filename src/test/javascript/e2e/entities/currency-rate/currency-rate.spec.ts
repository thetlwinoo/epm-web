import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CurrencyRateComponentsPage, CurrencyRateDeleteDialog, CurrencyRateUpdatePage } from './currency-rate.page-object';

const expect = chai.expect;

describe('CurrencyRate e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let currencyRateComponentsPage: CurrencyRateComponentsPage;
  let currencyRateUpdatePage: CurrencyRateUpdatePage;
  let currencyRateDeleteDialog: CurrencyRateDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CurrencyRates', async () => {
    await navBarPage.goToEntity('currency-rate');
    currencyRateComponentsPage = new CurrencyRateComponentsPage();
    await browser.wait(ec.visibilityOf(currencyRateComponentsPage.title), 5000);
    expect(await currencyRateComponentsPage.getTitle()).to.eq('epmwebApp.currencyRate.home.title');
  });

  it('should load create CurrencyRate page', async () => {
    await currencyRateComponentsPage.clickOnCreateButton();
    currencyRateUpdatePage = new CurrencyRateUpdatePage();
    expect(await currencyRateUpdatePage.getPageTitle()).to.eq('epmwebApp.currencyRate.home.createOrEditLabel');
    await currencyRateUpdatePage.cancel();
  });

  it('should create and save CurrencyRates', async () => {
    const nbButtonsBeforeCreate = await currencyRateComponentsPage.countDeleteButtons();

    await currencyRateComponentsPage.clickOnCreateButton();
    await promise.all([
      currencyRateUpdatePage.setCurrencyRateDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      currencyRateUpdatePage.setFromcodeInput('fromcode'),
      currencyRateUpdatePage.setTocodeInput('tocode'),
      currencyRateUpdatePage.setAverageRateInput('5'),
      currencyRateUpdatePage.setEndOfDayRateInput('5'),
      currencyRateUpdatePage.setLastEditedByInput('lastEditedBy'),
      currencyRateUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
    ]);
    expect(await currencyRateUpdatePage.getCurrencyRateDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected currencyRateDate value to be equals to 2000-12-31'
    );
    expect(await currencyRateUpdatePage.getFromcodeInput()).to.eq('fromcode', 'Expected Fromcode value to be equals to fromcode');
    expect(await currencyRateUpdatePage.getTocodeInput()).to.eq('tocode', 'Expected Tocode value to be equals to tocode');
    expect(await currencyRateUpdatePage.getAverageRateInput()).to.eq('5', 'Expected averageRate value to be equals to 5');
    expect(await currencyRateUpdatePage.getEndOfDayRateInput()).to.eq('5', 'Expected endOfDayRate value to be equals to 5');
    expect(await currencyRateUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await currencyRateUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await currencyRateUpdatePage.save();
    expect(await currencyRateUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await currencyRateComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last CurrencyRate', async () => {
    const nbButtonsBeforeDelete = await currencyRateComponentsPage.countDeleteButtons();
    await currencyRateComponentsPage.clickOnLastDeleteButton();

    currencyRateDeleteDialog = new CurrencyRateDeleteDialog();
    expect(await currencyRateDeleteDialog.getDialogTitle()).to.eq('epmwebApp.currencyRate.delete.question');
    await currencyRateDeleteDialog.clickOnConfirmButton();

    expect(await currencyRateComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
