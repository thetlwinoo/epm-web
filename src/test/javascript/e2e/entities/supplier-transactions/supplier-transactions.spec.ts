import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  SupplierTransactionsComponentsPage,
  SupplierTransactionsDeleteDialog,
  SupplierTransactionsUpdatePage
} from './supplier-transactions.page-object';

const expect = chai.expect;

describe('SupplierTransactions e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let supplierTransactionsComponentsPage: SupplierTransactionsComponentsPage;
  let supplierTransactionsUpdatePage: SupplierTransactionsUpdatePage;
  let supplierTransactionsDeleteDialog: SupplierTransactionsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SupplierTransactions', async () => {
    await navBarPage.goToEntity('supplier-transactions');
    supplierTransactionsComponentsPage = new SupplierTransactionsComponentsPage();
    await browser.wait(ec.visibilityOf(supplierTransactionsComponentsPage.title), 5000);
    expect(await supplierTransactionsComponentsPage.getTitle()).to.eq('epmwebApp.supplierTransactions.home.title');
  });

  it('should load create SupplierTransactions page', async () => {
    await supplierTransactionsComponentsPage.clickOnCreateButton();
    supplierTransactionsUpdatePage = new SupplierTransactionsUpdatePage();
    expect(await supplierTransactionsUpdatePage.getPageTitle()).to.eq('epmwebApp.supplierTransactions.home.createOrEditLabel');
    await supplierTransactionsUpdatePage.cancel();
  });

  it('should create and save SupplierTransactions', async () => {
    const nbButtonsBeforeCreate = await supplierTransactionsComponentsPage.countDeleteButtons();

    await supplierTransactionsComponentsPage.clickOnCreateButton();
    await promise.all([
      supplierTransactionsUpdatePage.setSupplierInvoiceNumberInput('supplierInvoiceNumber'),
      supplierTransactionsUpdatePage.setTransactionDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      supplierTransactionsUpdatePage.setAmountExcludingTaxInput('5'),
      supplierTransactionsUpdatePage.setTaxAmountInput('5'),
      supplierTransactionsUpdatePage.setTransactionAmountInput('5'),
      supplierTransactionsUpdatePage.setOutstandingBalanceInput('5'),
      supplierTransactionsUpdatePage.setFinalizationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      supplierTransactionsUpdatePage.setLastEditedByInput('lastEditedBy'),
      supplierTransactionsUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      supplierTransactionsUpdatePage.supplierSelectLastOption(),
      supplierTransactionsUpdatePage.transactionTypeSelectLastOption(),
      supplierTransactionsUpdatePage.purchaseOrderSelectLastOption()
    ]);
    expect(await supplierTransactionsUpdatePage.getSupplierInvoiceNumberInput()).to.eq(
      'supplierInvoiceNumber',
      'Expected SupplierInvoiceNumber value to be equals to supplierInvoiceNumber'
    );
    expect(await supplierTransactionsUpdatePage.getTransactionDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected transactionDate value to be equals to 2000-12-31'
    );
    expect(await supplierTransactionsUpdatePage.getAmountExcludingTaxInput()).to.eq(
      '5',
      'Expected amountExcludingTax value to be equals to 5'
    );
    expect(await supplierTransactionsUpdatePage.getTaxAmountInput()).to.eq('5', 'Expected taxAmount value to be equals to 5');
    expect(await supplierTransactionsUpdatePage.getTransactionAmountInput()).to.eq(
      '5',
      'Expected transactionAmount value to be equals to 5'
    );
    expect(await supplierTransactionsUpdatePage.getOutstandingBalanceInput()).to.eq(
      '5',
      'Expected outstandingBalance value to be equals to 5'
    );
    expect(await supplierTransactionsUpdatePage.getFinalizationDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected finalizationDate value to be equals to 2000-12-31'
    );
    const selectedIsFinalized = supplierTransactionsUpdatePage.getIsFinalizedInput();
    if (await selectedIsFinalized.isSelected()) {
      await supplierTransactionsUpdatePage.getIsFinalizedInput().click();
      expect(await supplierTransactionsUpdatePage.getIsFinalizedInput().isSelected(), 'Expected isFinalized not to be selected').to.be
        .false;
    } else {
      await supplierTransactionsUpdatePage.getIsFinalizedInput().click();
      expect(await supplierTransactionsUpdatePage.getIsFinalizedInput().isSelected(), 'Expected isFinalized to be selected').to.be.true;
    }
    expect(await supplierTransactionsUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await supplierTransactionsUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await supplierTransactionsUpdatePage.save();
    expect(await supplierTransactionsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await supplierTransactionsComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last SupplierTransactions', async () => {
    const nbButtonsBeforeDelete = await supplierTransactionsComponentsPage.countDeleteButtons();
    await supplierTransactionsComponentsPage.clickOnLastDeleteButton();

    supplierTransactionsDeleteDialog = new SupplierTransactionsDeleteDialog();
    expect(await supplierTransactionsDeleteDialog.getDialogTitle()).to.eq('epmwebApp.supplierTransactions.delete.question');
    await supplierTransactionsDeleteDialog.clickOnConfirmButton();

    expect(await supplierTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
