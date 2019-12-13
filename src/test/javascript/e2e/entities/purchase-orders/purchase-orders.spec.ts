import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PurchaseOrdersComponentsPage, PurchaseOrdersDeleteDialog, PurchaseOrdersUpdatePage } from './purchase-orders.page-object';

const expect = chai.expect;

describe('PurchaseOrders e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let purchaseOrdersComponentsPage: PurchaseOrdersComponentsPage;
  let purchaseOrdersUpdatePage: PurchaseOrdersUpdatePage;
  let purchaseOrdersDeleteDialog: PurchaseOrdersDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PurchaseOrders', async () => {
    await navBarPage.goToEntity('purchase-orders');
    purchaseOrdersComponentsPage = new PurchaseOrdersComponentsPage();
    await browser.wait(ec.visibilityOf(purchaseOrdersComponentsPage.title), 5000);
    expect(await purchaseOrdersComponentsPage.getTitle()).to.eq('epmwebApp.purchaseOrders.home.title');
  });

  it('should load create PurchaseOrders page', async () => {
    await purchaseOrdersComponentsPage.clickOnCreateButton();
    purchaseOrdersUpdatePage = new PurchaseOrdersUpdatePage();
    expect(await purchaseOrdersUpdatePage.getPageTitle()).to.eq('epmwebApp.purchaseOrders.home.createOrEditLabel');
    await purchaseOrdersUpdatePage.cancel();
  });

  it('should create and save PurchaseOrders', async () => {
    const nbButtonsBeforeCreate = await purchaseOrdersComponentsPage.countDeleteButtons();

    await purchaseOrdersComponentsPage.clickOnCreateButton();
    await promise.all([
      purchaseOrdersUpdatePage.setOrderDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      purchaseOrdersUpdatePage.setExpectedDeliveryDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      purchaseOrdersUpdatePage.setSupplierReferenceInput('supplierReference'),
      purchaseOrdersUpdatePage.setIsOrderFinalizedInput('5'),
      purchaseOrdersUpdatePage.setCommentsInput('comments'),
      purchaseOrdersUpdatePage.setInternalCommentsInput('internalComments'),
      purchaseOrdersUpdatePage.setLastEditedByInput('lastEditedBy'),
      purchaseOrdersUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      purchaseOrdersUpdatePage.contactPersonSelectLastOption(),
      purchaseOrdersUpdatePage.supplierSelectLastOption(),
      purchaseOrdersUpdatePage.deliveryMethodSelectLastOption()
    ]);
    expect(await purchaseOrdersUpdatePage.getOrderDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected orderDate value to be equals to 2000-12-31'
    );
    expect(await purchaseOrdersUpdatePage.getExpectedDeliveryDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected expectedDeliveryDate value to be equals to 2000-12-31'
    );
    expect(await purchaseOrdersUpdatePage.getSupplierReferenceInput()).to.eq(
      'supplierReference',
      'Expected SupplierReference value to be equals to supplierReference'
    );
    expect(await purchaseOrdersUpdatePage.getIsOrderFinalizedInput()).to.eq('5', 'Expected isOrderFinalized value to be equals to 5');
    expect(await purchaseOrdersUpdatePage.getCommentsInput()).to.eq('comments', 'Expected Comments value to be equals to comments');
    expect(await purchaseOrdersUpdatePage.getInternalCommentsInput()).to.eq(
      'internalComments',
      'Expected InternalComments value to be equals to internalComments'
    );
    expect(await purchaseOrdersUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await purchaseOrdersUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await purchaseOrdersUpdatePage.save();
    expect(await purchaseOrdersUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await purchaseOrdersComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PurchaseOrders', async () => {
    const nbButtonsBeforeDelete = await purchaseOrdersComponentsPage.countDeleteButtons();
    await purchaseOrdersComponentsPage.clickOnLastDeleteButton();

    purchaseOrdersDeleteDialog = new PurchaseOrdersDeleteDialog();
    expect(await purchaseOrdersDeleteDialog.getDialogTitle()).to.eq('epmwebApp.purchaseOrders.delete.question');
    await purchaseOrdersDeleteDialog.clickOnConfirmButton();

    expect(await purchaseOrdersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
