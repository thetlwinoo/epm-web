import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PurchaseOrderLinesComponentsPage,
  PurchaseOrderLinesDeleteDialog,
  PurchaseOrderLinesUpdatePage
} from './purchase-order-lines.page-object';

const expect = chai.expect;

describe('PurchaseOrderLines e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let purchaseOrderLinesComponentsPage: PurchaseOrderLinesComponentsPage;
  let purchaseOrderLinesUpdatePage: PurchaseOrderLinesUpdatePage;
  let purchaseOrderLinesDeleteDialog: PurchaseOrderLinesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PurchaseOrderLines', async () => {
    await navBarPage.goToEntity('purchase-order-lines');
    purchaseOrderLinesComponentsPage = new PurchaseOrderLinesComponentsPage();
    await browser.wait(ec.visibilityOf(purchaseOrderLinesComponentsPage.title), 5000);
    expect(await purchaseOrderLinesComponentsPage.getTitle()).to.eq('epmwebApp.purchaseOrderLines.home.title');
  });

  it('should load create PurchaseOrderLines page', async () => {
    await purchaseOrderLinesComponentsPage.clickOnCreateButton();
    purchaseOrderLinesUpdatePage = new PurchaseOrderLinesUpdatePage();
    expect(await purchaseOrderLinesUpdatePage.getPageTitle()).to.eq('epmwebApp.purchaseOrderLines.home.createOrEditLabel');
    await purchaseOrderLinesUpdatePage.cancel();
  });

  it('should create and save PurchaseOrderLines', async () => {
    const nbButtonsBeforeCreate = await purchaseOrderLinesComponentsPage.countDeleteButtons();

    await purchaseOrderLinesComponentsPage.clickOnCreateButton();
    await promise.all([
      purchaseOrderLinesUpdatePage.setOrderedOutersInput('5'),
      purchaseOrderLinesUpdatePage.setDescriptionInput('description'),
      purchaseOrderLinesUpdatePage.setReceivedOutersInput('5'),
      purchaseOrderLinesUpdatePage.setExpectedUnitPricePerOuterInput('5'),
      purchaseOrderLinesUpdatePage.setLastReceiptDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      purchaseOrderLinesUpdatePage.setLastEditedByInput('lastEditedBy'),
      purchaseOrderLinesUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      purchaseOrderLinesUpdatePage.packageTypeSelectLastOption(),
      purchaseOrderLinesUpdatePage.stockItemSelectLastOption(),
      purchaseOrderLinesUpdatePage.purchaseOrderSelectLastOption()
    ]);
    expect(await purchaseOrderLinesUpdatePage.getOrderedOutersInput()).to.eq('5', 'Expected orderedOuters value to be equals to 5');
    expect(await purchaseOrderLinesUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await purchaseOrderLinesUpdatePage.getReceivedOutersInput()).to.eq('5', 'Expected receivedOuters value to be equals to 5');
    expect(await purchaseOrderLinesUpdatePage.getExpectedUnitPricePerOuterInput()).to.eq(
      '5',
      'Expected expectedUnitPricePerOuter value to be equals to 5'
    );
    expect(await purchaseOrderLinesUpdatePage.getLastReceiptDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastReceiptDate value to be equals to 2000-12-31'
    );
    const selectedIsOrderLineFinalized = purchaseOrderLinesUpdatePage.getIsOrderLineFinalizedInput();
    if (await selectedIsOrderLineFinalized.isSelected()) {
      await purchaseOrderLinesUpdatePage.getIsOrderLineFinalizedInput().click();
      expect(
        await purchaseOrderLinesUpdatePage.getIsOrderLineFinalizedInput().isSelected(),
        'Expected isOrderLineFinalized not to be selected'
      ).to.be.false;
    } else {
      await purchaseOrderLinesUpdatePage.getIsOrderLineFinalizedInput().click();
      expect(await purchaseOrderLinesUpdatePage.getIsOrderLineFinalizedInput().isSelected(), 'Expected isOrderLineFinalized to be selected')
        .to.be.true;
    }
    expect(await purchaseOrderLinesUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await purchaseOrderLinesUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await purchaseOrderLinesUpdatePage.save();
    expect(await purchaseOrderLinesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await purchaseOrderLinesComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PurchaseOrderLines', async () => {
    const nbButtonsBeforeDelete = await purchaseOrderLinesComponentsPage.countDeleteButtons();
    await purchaseOrderLinesComponentsPage.clickOnLastDeleteButton();

    purchaseOrderLinesDeleteDialog = new PurchaseOrderLinesDeleteDialog();
    expect(await purchaseOrderLinesDeleteDialog.getDialogTitle()).to.eq('epmwebApp.purchaseOrderLines.delete.question');
    await purchaseOrderLinesDeleteDialog.clickOnConfirmButton();

    expect(await purchaseOrderLinesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
