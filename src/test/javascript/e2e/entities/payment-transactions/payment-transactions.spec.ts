import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PaymentTransactionsComponentsPage,
  PaymentTransactionsDeleteDialog,
  PaymentTransactionsUpdatePage
} from './payment-transactions.page-object';

const expect = chai.expect;

describe('PaymentTransactions e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paymentTransactionsComponentsPage: PaymentTransactionsComponentsPage;
  let paymentTransactionsUpdatePage: PaymentTransactionsUpdatePage;
  let paymentTransactionsDeleteDialog: PaymentTransactionsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PaymentTransactions', async () => {
    await navBarPage.goToEntity('payment-transactions');
    paymentTransactionsComponentsPage = new PaymentTransactionsComponentsPage();
    await browser.wait(ec.visibilityOf(paymentTransactionsComponentsPage.title), 5000);
    expect(await paymentTransactionsComponentsPage.getTitle()).to.eq('epmwebApp.paymentTransactions.home.title');
  });

  it('should load create PaymentTransactions page', async () => {
    await paymentTransactionsComponentsPage.clickOnCreateButton();
    paymentTransactionsUpdatePage = new PaymentTransactionsUpdatePage();
    expect(await paymentTransactionsUpdatePage.getPageTitle()).to.eq('epmwebApp.paymentTransactions.home.createOrEditLabel');
    await paymentTransactionsUpdatePage.cancel();
  });

  it('should create and save PaymentTransactions', async () => {
    const nbButtonsBeforeCreate = await paymentTransactionsComponentsPage.countDeleteButtons();

    await paymentTransactionsComponentsPage.clickOnCreateButton();
    await promise.all([
      paymentTransactionsUpdatePage.setReturnedCompletedPaymentDataInput('returnedCompletedPaymentData'),
      paymentTransactionsUpdatePage.setLastEditedByInput('lastEditedBy'),
      paymentTransactionsUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      paymentTransactionsUpdatePage.paymentOnOrderSelectLastOption()
    ]);
    expect(await paymentTransactionsUpdatePage.getReturnedCompletedPaymentDataInput()).to.eq(
      'returnedCompletedPaymentData',
      'Expected ReturnedCompletedPaymentData value to be equals to returnedCompletedPaymentData'
    );
    expect(await paymentTransactionsUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await paymentTransactionsUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await paymentTransactionsUpdatePage.save();
    expect(await paymentTransactionsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await paymentTransactionsComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PaymentTransactions', async () => {
    const nbButtonsBeforeDelete = await paymentTransactionsComponentsPage.countDeleteButtons();
    await paymentTransactionsComponentsPage.clickOnLastDeleteButton();

    paymentTransactionsDeleteDialog = new PaymentTransactionsDeleteDialog();
    expect(await paymentTransactionsDeleteDialog.getDialogTitle()).to.eq('epmwebApp.paymentTransactions.delete.question');
    await paymentTransactionsDeleteDialog.clickOnConfirmButton();

    expect(await paymentTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
