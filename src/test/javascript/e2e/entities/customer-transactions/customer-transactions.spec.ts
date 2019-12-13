import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CustomerTransactionsComponentsPage,
  CustomerTransactionsDeleteDialog,
  CustomerTransactionsUpdatePage
} from './customer-transactions.page-object';

const expect = chai.expect;

describe('CustomerTransactions e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let customerTransactionsComponentsPage: CustomerTransactionsComponentsPage;
  let customerTransactionsUpdatePage: CustomerTransactionsUpdatePage;
  let customerTransactionsDeleteDialog: CustomerTransactionsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CustomerTransactions', async () => {
    await navBarPage.goToEntity('customer-transactions');
    customerTransactionsComponentsPage = new CustomerTransactionsComponentsPage();
    await browser.wait(ec.visibilityOf(customerTransactionsComponentsPage.title), 5000);
    expect(await customerTransactionsComponentsPage.getTitle()).to.eq('epmwebApp.customerTransactions.home.title');
  });

  it('should load create CustomerTransactions page', async () => {
    await customerTransactionsComponentsPage.clickOnCreateButton();
    customerTransactionsUpdatePage = new CustomerTransactionsUpdatePage();
    expect(await customerTransactionsUpdatePage.getPageTitle()).to.eq('epmwebApp.customerTransactions.home.createOrEditLabel');
    await customerTransactionsUpdatePage.cancel();
  });

  it('should create and save CustomerTransactions', async () => {
    const nbButtonsBeforeCreate = await customerTransactionsComponentsPage.countDeleteButtons();

    await customerTransactionsComponentsPage.clickOnCreateButton();
    await promise.all([
      customerTransactionsUpdatePage.setTransactionDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      customerTransactionsUpdatePage.setAmountExcludingTaxInput('5'),
      customerTransactionsUpdatePage.setTaxAmountInput('5'),
      customerTransactionsUpdatePage.setTransactionAmountInput('5'),
      customerTransactionsUpdatePage.setOutstandingBalanceInput('5'),
      customerTransactionsUpdatePage.setFinalizationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      customerTransactionsUpdatePage.setLastEditedByInput('lastEditedBy'),
      customerTransactionsUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      customerTransactionsUpdatePage.customerSelectLastOption(),
      customerTransactionsUpdatePage.paymentTransactionSelectLastOption(),
      customerTransactionsUpdatePage.transactionTypeSelectLastOption(),
      customerTransactionsUpdatePage.invoiceSelectLastOption()
    ]);
    expect(await customerTransactionsUpdatePage.getTransactionDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected transactionDate value to be equals to 2000-12-31'
    );
    expect(await customerTransactionsUpdatePage.getAmountExcludingTaxInput()).to.eq(
      '5',
      'Expected amountExcludingTax value to be equals to 5'
    );
    expect(await customerTransactionsUpdatePage.getTaxAmountInput()).to.eq('5', 'Expected taxAmount value to be equals to 5');
    expect(await customerTransactionsUpdatePage.getTransactionAmountInput()).to.eq(
      '5',
      'Expected transactionAmount value to be equals to 5'
    );
    expect(await customerTransactionsUpdatePage.getOutstandingBalanceInput()).to.eq(
      '5',
      'Expected outstandingBalance value to be equals to 5'
    );
    expect(await customerTransactionsUpdatePage.getFinalizationDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected finalizationDate value to be equals to 2000-12-31'
    );
    const selectedIsFinalized = customerTransactionsUpdatePage.getIsFinalizedInput();
    if (await selectedIsFinalized.isSelected()) {
      await customerTransactionsUpdatePage.getIsFinalizedInput().click();
      expect(await customerTransactionsUpdatePage.getIsFinalizedInput().isSelected(), 'Expected isFinalized not to be selected').to.be
        .false;
    } else {
      await customerTransactionsUpdatePage.getIsFinalizedInput().click();
      expect(await customerTransactionsUpdatePage.getIsFinalizedInput().isSelected(), 'Expected isFinalized to be selected').to.be.true;
    }
    expect(await customerTransactionsUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await customerTransactionsUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await customerTransactionsUpdatePage.save();
    expect(await customerTransactionsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await customerTransactionsComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CustomerTransactions', async () => {
    const nbButtonsBeforeDelete = await customerTransactionsComponentsPage.countDeleteButtons();
    await customerTransactionsComponentsPage.clickOnLastDeleteButton();

    customerTransactionsDeleteDialog = new CustomerTransactionsDeleteDialog();
    expect(await customerTransactionsDeleteDialog.getDialogTitle()).to.eq('epmwebApp.customerTransactions.delete.question');
    await customerTransactionsDeleteDialog.clickOnConfirmButton();

    expect(await customerTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
