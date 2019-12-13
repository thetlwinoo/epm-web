import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrdersComponentsPage, OrdersDeleteDialog, OrdersUpdatePage } from './orders.page-object';

const expect = chai.expect;

describe('Orders e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let ordersComponentsPage: OrdersComponentsPage;
  let ordersUpdatePage: OrdersUpdatePage;
  let ordersDeleteDialog: OrdersDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Orders', async () => {
    await navBarPage.goToEntity('orders');
    ordersComponentsPage = new OrdersComponentsPage();
    await browser.wait(ec.visibilityOf(ordersComponentsPage.title), 5000);
    expect(await ordersComponentsPage.getTitle()).to.eq('epmwebApp.orders.home.title');
  });

  it('should load create Orders page', async () => {
    await ordersComponentsPage.clickOnCreateButton();
    ordersUpdatePage = new OrdersUpdatePage();
    expect(await ordersUpdatePage.getPageTitle()).to.eq('epmwebApp.orders.home.createOrEditLabel');
    await ordersUpdatePage.cancel();
  });

  it('should create and save Orders', async () => {
    const nbButtonsBeforeCreate = await ordersComponentsPage.countDeleteButtons();

    await ordersComponentsPage.clickOnCreateButton();
    await promise.all([
      ordersUpdatePage.setOrderDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      ordersUpdatePage.setDueDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      ordersUpdatePage.setShipDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      ordersUpdatePage.setPaymentStatusInput('5'),
      ordersUpdatePage.setOrderFlagInput('5'),
      ordersUpdatePage.setOrderNumberInput('orderNumber'),
      ordersUpdatePage.setSubTotalInput('5'),
      ordersUpdatePage.setTaxAmountInput('5'),
      ordersUpdatePage.setFrieightInput('5'),
      ordersUpdatePage.setTotalDueInput('5'),
      ordersUpdatePage.setCommentsInput('comments'),
      ordersUpdatePage.setDeliveryInstructionsInput('deliveryInstructions'),
      ordersUpdatePage.setInternalCommentsInput('internalComments'),
      ordersUpdatePage.setPickingCompletedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      ordersUpdatePage.statusSelectLastOption(),
      ordersUpdatePage.setLastEditedByInput('lastEditedBy'),
      ordersUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      ordersUpdatePage.orderOnReviewSelectLastOption(),
      ordersUpdatePage.customerSelectLastOption(),
      ordersUpdatePage.shipToAddressSelectLastOption(),
      ordersUpdatePage.billToAddressSelectLastOption(),
      ordersUpdatePage.shipMethodSelectLastOption(),
      ordersUpdatePage.currencyRateSelectLastOption(),
      ordersUpdatePage.specialDealsSelectLastOption()
    ]);
    expect(await ordersUpdatePage.getOrderDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected orderDate value to be equals to 2000-12-31'
    );
    expect(await ordersUpdatePage.getDueDateInput()).to.contain('2001-01-01T02:30', 'Expected dueDate value to be equals to 2000-12-31');
    expect(await ordersUpdatePage.getShipDateInput()).to.contain('2001-01-01T02:30', 'Expected shipDate value to be equals to 2000-12-31');
    expect(await ordersUpdatePage.getPaymentStatusInput()).to.eq('5', 'Expected paymentStatus value to be equals to 5');
    expect(await ordersUpdatePage.getOrderFlagInput()).to.eq('5', 'Expected orderFlag value to be equals to 5');
    expect(await ordersUpdatePage.getOrderNumberInput()).to.eq('orderNumber', 'Expected OrderNumber value to be equals to orderNumber');
    expect(await ordersUpdatePage.getSubTotalInput()).to.eq('5', 'Expected subTotal value to be equals to 5');
    expect(await ordersUpdatePage.getTaxAmountInput()).to.eq('5', 'Expected taxAmount value to be equals to 5');
    expect(await ordersUpdatePage.getFrieightInput()).to.eq('5', 'Expected frieight value to be equals to 5');
    expect(await ordersUpdatePage.getTotalDueInput()).to.eq('5', 'Expected totalDue value to be equals to 5');
    expect(await ordersUpdatePage.getCommentsInput()).to.eq('comments', 'Expected Comments value to be equals to comments');
    expect(await ordersUpdatePage.getDeliveryInstructionsInput()).to.eq(
      'deliveryInstructions',
      'Expected DeliveryInstructions value to be equals to deliveryInstructions'
    );
    expect(await ordersUpdatePage.getInternalCommentsInput()).to.eq(
      'internalComments',
      'Expected InternalComments value to be equals to internalComments'
    );
    expect(await ordersUpdatePage.getPickingCompletedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected pickingCompletedWhen value to be equals to 2000-12-31'
    );
    expect(await ordersUpdatePage.getLastEditedByInput()).to.eq('lastEditedBy', 'Expected LastEditedBy value to be equals to lastEditedBy');
    expect(await ordersUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await ordersUpdatePage.save();
    expect(await ordersUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await ordersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Orders', async () => {
    const nbButtonsBeforeDelete = await ordersComponentsPage.countDeleteButtons();
    await ordersComponentsPage.clickOnLastDeleteButton();

    ordersDeleteDialog = new OrdersDeleteDialog();
    expect(await ordersDeleteDialog.getDialogTitle()).to.eq('epmwebApp.orders.delete.question');
    await ordersDeleteDialog.clickOnConfirmButton();

    expect(await ordersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
