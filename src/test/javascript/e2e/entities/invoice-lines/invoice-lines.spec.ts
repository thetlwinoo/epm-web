import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InvoiceLinesComponentsPage, InvoiceLinesDeleteDialog, InvoiceLinesUpdatePage } from './invoice-lines.page-object';

const expect = chai.expect;

describe('InvoiceLines e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let invoiceLinesComponentsPage: InvoiceLinesComponentsPage;
  let invoiceLinesUpdatePage: InvoiceLinesUpdatePage;
  let invoiceLinesDeleteDialog: InvoiceLinesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load InvoiceLines', async () => {
    await navBarPage.goToEntity('invoice-lines');
    invoiceLinesComponentsPage = new InvoiceLinesComponentsPage();
    await browser.wait(ec.visibilityOf(invoiceLinesComponentsPage.title), 5000);
    expect(await invoiceLinesComponentsPage.getTitle()).to.eq('epmwebApp.invoiceLines.home.title');
  });

  it('should load create InvoiceLines page', async () => {
    await invoiceLinesComponentsPage.clickOnCreateButton();
    invoiceLinesUpdatePage = new InvoiceLinesUpdatePage();
    expect(await invoiceLinesUpdatePage.getPageTitle()).to.eq('epmwebApp.invoiceLines.home.createOrEditLabel');
    await invoiceLinesUpdatePage.cancel();
  });

  it('should create and save InvoiceLines', async () => {
    const nbButtonsBeforeCreate = await invoiceLinesComponentsPage.countDeleteButtons();

    await invoiceLinesComponentsPage.clickOnCreateButton();
    await promise.all([
      invoiceLinesUpdatePage.setDescriptionInput('description'),
      invoiceLinesUpdatePage.setQuantityInput('5'),
      invoiceLinesUpdatePage.setUnitPriceInput('5'),
      invoiceLinesUpdatePage.setTaxRateInput('5'),
      invoiceLinesUpdatePage.setTaxAmountInput('5'),
      invoiceLinesUpdatePage.setLineProfitInput('5'),
      invoiceLinesUpdatePage.setExtendedPriceInput('5'),
      invoiceLinesUpdatePage.setLastEditedByInput('lastEditedBy'),
      invoiceLinesUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      invoiceLinesUpdatePage.packageTypeSelectLastOption(),
      invoiceLinesUpdatePage.stockItemSelectLastOption(),
      invoiceLinesUpdatePage.invoiceSelectLastOption()
    ]);
    expect(await invoiceLinesUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await invoiceLinesUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await invoiceLinesUpdatePage.getUnitPriceInput()).to.eq('5', 'Expected unitPrice value to be equals to 5');
    expect(await invoiceLinesUpdatePage.getTaxRateInput()).to.eq('5', 'Expected taxRate value to be equals to 5');
    expect(await invoiceLinesUpdatePage.getTaxAmountInput()).to.eq('5', 'Expected taxAmount value to be equals to 5');
    expect(await invoiceLinesUpdatePage.getLineProfitInput()).to.eq('5', 'Expected lineProfit value to be equals to 5');
    expect(await invoiceLinesUpdatePage.getExtendedPriceInput()).to.eq('5', 'Expected extendedPrice value to be equals to 5');
    expect(await invoiceLinesUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await invoiceLinesUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await invoiceLinesUpdatePage.save();
    expect(await invoiceLinesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await invoiceLinesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last InvoiceLines', async () => {
    const nbButtonsBeforeDelete = await invoiceLinesComponentsPage.countDeleteButtons();
    await invoiceLinesComponentsPage.clickOnLastDeleteButton();

    invoiceLinesDeleteDialog = new InvoiceLinesDeleteDialog();
    expect(await invoiceLinesDeleteDialog.getDialogTitle()).to.eq('epmwebApp.invoiceLines.delete.question');
    await invoiceLinesDeleteDialog.clickOnConfirmButton();

    expect(await invoiceLinesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
