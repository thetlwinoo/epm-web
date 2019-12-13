import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrderLinesComponentsPage, OrderLinesDeleteDialog, OrderLinesUpdatePage } from './order-lines.page-object';

const expect = chai.expect;

describe('OrderLines e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let orderLinesComponentsPage: OrderLinesComponentsPage;
  let orderLinesUpdatePage: OrderLinesUpdatePage;
  let orderLinesDeleteDialog: OrderLinesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OrderLines', async () => {
    await navBarPage.goToEntity('order-lines');
    orderLinesComponentsPage = new OrderLinesComponentsPage();
    await browser.wait(ec.visibilityOf(orderLinesComponentsPage.title), 5000);
    expect(await orderLinesComponentsPage.getTitle()).to.eq('epmwebApp.orderLines.home.title');
  });

  it('should load create OrderLines page', async () => {
    await orderLinesComponentsPage.clickOnCreateButton();
    orderLinesUpdatePage = new OrderLinesUpdatePage();
    expect(await orderLinesUpdatePage.getPageTitle()).to.eq('epmwebApp.orderLines.home.createOrEditLabel');
    await orderLinesUpdatePage.cancel();
  });

  it('should create and save OrderLines', async () => {
    const nbButtonsBeforeCreate = await orderLinesComponentsPage.countDeleteButtons();

    await orderLinesComponentsPage.clickOnCreateButton();
    await promise.all([
      orderLinesUpdatePage.setCarrierTrackingNumberInput('carrierTrackingNumber'),
      orderLinesUpdatePage.setQuantityInput('5'),
      orderLinesUpdatePage.setUnitPriceInput('5'),
      orderLinesUpdatePage.setUnitPriceDiscountInput('5'),
      orderLinesUpdatePage.setLineTotalInput('5'),
      orderLinesUpdatePage.setTaxRateInput('5'),
      orderLinesUpdatePage.setPickedQuantityInput('5'),
      orderLinesUpdatePage.setPickingCompletedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      orderLinesUpdatePage.statusSelectLastOption(),
      orderLinesUpdatePage.setLastEditedByInput('lastEditedBy'),
      orderLinesUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      orderLinesUpdatePage.stockItemSelectLastOption(),
      orderLinesUpdatePage.packageTypeSelectLastOption(),
      orderLinesUpdatePage.orderSelectLastOption()
    ]);
    expect(await orderLinesUpdatePage.getCarrierTrackingNumberInput()).to.eq(
      'carrierTrackingNumber',
      'Expected CarrierTrackingNumber value to be equals to carrierTrackingNumber'
    );
    expect(await orderLinesUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await orderLinesUpdatePage.getUnitPriceInput()).to.eq('5', 'Expected unitPrice value to be equals to 5');
    expect(await orderLinesUpdatePage.getUnitPriceDiscountInput()).to.eq('5', 'Expected unitPriceDiscount value to be equals to 5');
    expect(await orderLinesUpdatePage.getLineTotalInput()).to.eq('5', 'Expected lineTotal value to be equals to 5');
    expect(await orderLinesUpdatePage.getTaxRateInput()).to.eq('5', 'Expected taxRate value to be equals to 5');
    expect(await orderLinesUpdatePage.getPickedQuantityInput()).to.eq('5', 'Expected pickedQuantity value to be equals to 5');
    expect(await orderLinesUpdatePage.getPickingCompletedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected pickingCompletedWhen value to be equals to 2000-12-31'
    );
    expect(await orderLinesUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await orderLinesUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await orderLinesUpdatePage.save();
    expect(await orderLinesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await orderLinesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last OrderLines', async () => {
    const nbButtonsBeforeDelete = await orderLinesComponentsPage.countDeleteButtons();
    await orderLinesComponentsPage.clickOnLastDeleteButton();

    orderLinesDeleteDialog = new OrderLinesDeleteDialog();
    expect(await orderLinesDeleteDialog.getDialogTitle()).to.eq('epmwebApp.orderLines.delete.question');
    await orderLinesDeleteDialog.clickOnConfirmButton();

    expect(await orderLinesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
