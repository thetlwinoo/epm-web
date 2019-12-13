import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InvoicesComponentsPage, InvoicesDeleteDialog, InvoicesUpdatePage } from './invoices.page-object';

const expect = chai.expect;

describe('Invoices e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let invoicesComponentsPage: InvoicesComponentsPage;
  let invoicesUpdatePage: InvoicesUpdatePage;
  let invoicesDeleteDialog: InvoicesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Invoices', async () => {
    await navBarPage.goToEntity('invoices');
    invoicesComponentsPage = new InvoicesComponentsPage();
    await browser.wait(ec.visibilityOf(invoicesComponentsPage.title), 5000);
    expect(await invoicesComponentsPage.getTitle()).to.eq('epmwebApp.invoices.home.title');
  });

  it('should load create Invoices page', async () => {
    await invoicesComponentsPage.clickOnCreateButton();
    invoicesUpdatePage = new InvoicesUpdatePage();
    expect(await invoicesUpdatePage.getPageTitle()).to.eq('epmwebApp.invoices.home.createOrEditLabel');
    await invoicesUpdatePage.cancel();
  });

  it('should create and save Invoices', async () => {
    const nbButtonsBeforeCreate = await invoicesComponentsPage.countDeleteButtons();

    await invoicesComponentsPage.clickOnCreateButton();
    await promise.all([
      invoicesUpdatePage.setInvoiceDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      invoicesUpdatePage.setCustomerPurchaseOrderNumberInput('customerPurchaseOrderNumber'),
      invoicesUpdatePage.setCreditNoteReasonInput('creditNoteReason'),
      invoicesUpdatePage.setCommentsInput('comments'),
      invoicesUpdatePage.setDeliveryInstructionsInput('deliveryInstructions'),
      invoicesUpdatePage.setInternalCommentsInput('internalComments'),
      invoicesUpdatePage.setTotalDryItemsInput('5'),
      invoicesUpdatePage.setTotalChillerItemsInput('5'),
      invoicesUpdatePage.setDeliveryRunInput('deliveryRun'),
      invoicesUpdatePage.setRunPositionInput('runPosition'),
      invoicesUpdatePage.setReturnedDeliveryDataInput('returnedDeliveryData'),
      invoicesUpdatePage.setConfirmedDeliveryTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      invoicesUpdatePage.setConfirmedReceivedByInput('confirmedReceivedBy'),
      invoicesUpdatePage.paymentMethodSelectLastOption(),
      invoicesUpdatePage.statusSelectLastOption(),
      invoicesUpdatePage.setLastEditedByInput('lastEditedBy'),
      invoicesUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      invoicesUpdatePage.contactPersonSelectLastOption(),
      invoicesUpdatePage.salespersonPersonSelectLastOption(),
      invoicesUpdatePage.packedByPersonSelectLastOption(),
      invoicesUpdatePage.accountsPersonSelectLastOption(),
      invoicesUpdatePage.customerSelectLastOption(),
      invoicesUpdatePage.billToCustomerSelectLastOption(),
      invoicesUpdatePage.deliveryMethodSelectLastOption(),
      invoicesUpdatePage.orderSelectLastOption()
    ]);
    expect(await invoicesUpdatePage.getInvoiceDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected invoiceDate value to be equals to 2000-12-31'
    );
    expect(await invoicesUpdatePage.getCustomerPurchaseOrderNumberInput()).to.eq(
      'customerPurchaseOrderNumber',
      'Expected CustomerPurchaseOrderNumber value to be equals to customerPurchaseOrderNumber'
    );
    const selectedIsCreditNote = invoicesUpdatePage.getIsCreditNoteInput();
    if (await selectedIsCreditNote.isSelected()) {
      await invoicesUpdatePage.getIsCreditNoteInput().click();
      expect(await invoicesUpdatePage.getIsCreditNoteInput().isSelected(), 'Expected isCreditNote not to be selected').to.be.false;
    } else {
      await invoicesUpdatePage.getIsCreditNoteInput().click();
      expect(await invoicesUpdatePage.getIsCreditNoteInput().isSelected(), 'Expected isCreditNote to be selected').to.be.true;
    }
    expect(await invoicesUpdatePage.getCreditNoteReasonInput()).to.eq(
      'creditNoteReason',
      'Expected CreditNoteReason value to be equals to creditNoteReason'
    );
    expect(await invoicesUpdatePage.getCommentsInput()).to.eq('comments', 'Expected Comments value to be equals to comments');
    expect(await invoicesUpdatePage.getDeliveryInstructionsInput()).to.eq(
      'deliveryInstructions',
      'Expected DeliveryInstructions value to be equals to deliveryInstructions'
    );
    expect(await invoicesUpdatePage.getInternalCommentsInput()).to.eq(
      'internalComments',
      'Expected InternalComments value to be equals to internalComments'
    );
    expect(await invoicesUpdatePage.getTotalDryItemsInput()).to.eq('5', 'Expected totalDryItems value to be equals to 5');
    expect(await invoicesUpdatePage.getTotalChillerItemsInput()).to.eq('5', 'Expected totalChillerItems value to be equals to 5');
    expect(await invoicesUpdatePage.getDeliveryRunInput()).to.eq('deliveryRun', 'Expected DeliveryRun value to be equals to deliveryRun');
    expect(await invoicesUpdatePage.getRunPositionInput()).to.eq('runPosition', 'Expected RunPosition value to be equals to runPosition');
    expect(await invoicesUpdatePage.getReturnedDeliveryDataInput()).to.eq(
      'returnedDeliveryData',
      'Expected ReturnedDeliveryData value to be equals to returnedDeliveryData'
    );
    expect(await invoicesUpdatePage.getConfirmedDeliveryTimeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected confirmedDeliveryTime value to be equals to 2000-12-31'
    );
    expect(await invoicesUpdatePage.getConfirmedReceivedByInput()).to.eq(
      'confirmedReceivedBy',
      'Expected ConfirmedReceivedBy value to be equals to confirmedReceivedBy'
    );
    expect(await invoicesUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await invoicesUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await invoicesUpdatePage.save();
    expect(await invoicesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await invoicesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Invoices', async () => {
    const nbButtonsBeforeDelete = await invoicesComponentsPage.countDeleteButtons();
    await invoicesComponentsPage.clickOnLastDeleteButton();

    invoicesDeleteDialog = new InvoicesDeleteDialog();
    expect(await invoicesDeleteDialog.getDialogTitle()).to.eq('epmwebApp.invoices.delete.question');
    await invoicesDeleteDialog.clickOnConfirmButton();

    expect(await invoicesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
