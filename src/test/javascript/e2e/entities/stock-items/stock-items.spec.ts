import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StockItemsComponentsPage, StockItemsDeleteDialog, StockItemsUpdatePage } from './stock-items.page-object';

const expect = chai.expect;

describe('StockItems e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let stockItemsComponentsPage: StockItemsComponentsPage;
  let stockItemsUpdatePage: StockItemsUpdatePage;
  let stockItemsDeleteDialog: StockItemsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load StockItems', async () => {
    await navBarPage.goToEntity('stock-items');
    stockItemsComponentsPage = new StockItemsComponentsPage();
    await browser.wait(ec.visibilityOf(stockItemsComponentsPage.title), 5000);
    expect(await stockItemsComponentsPage.getTitle()).to.eq('epmwebApp.stockItems.home.title');
  });

  it('should load create StockItems page', async () => {
    await stockItemsComponentsPage.clickOnCreateButton();
    stockItemsUpdatePage = new StockItemsUpdatePage();
    expect(await stockItemsUpdatePage.getPageTitle()).to.eq('epmwebApp.stockItems.home.createOrEditLabel');
    await stockItemsUpdatePage.cancel();
  });

  it('should create and save StockItems', async () => {
    const nbButtonsBeforeCreate = await stockItemsComponentsPage.countDeleteButtons();

    await stockItemsComponentsPage.clickOnCreateButton();
    await promise.all([
      stockItemsUpdatePage.setNameInput('name'),
      stockItemsUpdatePage.setVendorCodeInput('vendorCode'),
      stockItemsUpdatePage.setVendorSKUInput('vendorSKU'),
      stockItemsUpdatePage.setGeneratedSKUInput('generatedSKU'),
      stockItemsUpdatePage.setBarcodeInput('barcode'),
      stockItemsUpdatePage.setUnitPriceInput('5'),
      stockItemsUpdatePage.setRecommendedRetailPriceInput('5'),
      stockItemsUpdatePage.setQuantityOnHandInput('5'),
      stockItemsUpdatePage.setItemLengthInput('5'),
      stockItemsUpdatePage.setItemWidthInput('5'),
      stockItemsUpdatePage.setItemHeightInput('5'),
      stockItemsUpdatePage.setItemWeightInput('5'),
      stockItemsUpdatePage.setItemPackageLengthInput('5'),
      stockItemsUpdatePage.setItemPackageWidthInput('5'),
      stockItemsUpdatePage.setItemPackageHeightInput('5'),
      stockItemsUpdatePage.setItemPackageWeightInput('5'),
      stockItemsUpdatePage.setNoOfPiecesInput('5'),
      stockItemsUpdatePage.setNoOfItemsInput('5'),
      stockItemsUpdatePage.setManufactureInput('manufacture'),
      stockItemsUpdatePage.setMarketingCommentsInput('marketingComments'),
      stockItemsUpdatePage.setInternalCommentsInput('internalComments'),
      stockItemsUpdatePage.setSellStartDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      stockItemsUpdatePage.setSellEndDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      stockItemsUpdatePage.setSellCountInput('5'),
      stockItemsUpdatePage.setCustomFieldsInput('customFields'),
      stockItemsUpdatePage.setThumbnailUrlInput('thumbnailUrl'),
      stockItemsUpdatePage.setLastEditedByInput('lastEditedBy'),
      stockItemsUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      stockItemsUpdatePage.stockItemOnReviewLineSelectLastOption(),
      stockItemsUpdatePage.itemLengthUnitSelectLastOption(),
      stockItemsUpdatePage.itemWidthUnitSelectLastOption(),
      stockItemsUpdatePage.itemHeightUnitSelectLastOption(),
      stockItemsUpdatePage.packageLengthUnitSelectLastOption(),
      stockItemsUpdatePage.packageWidthUnitSelectLastOption(),
      stockItemsUpdatePage.packageHeightUnitSelectLastOption(),
      stockItemsUpdatePage.itemPackageWeightUnitSelectLastOption(),
      stockItemsUpdatePage.productAttributeSelectLastOption(),
      stockItemsUpdatePage.productOptionSelectLastOption(),
      stockItemsUpdatePage.materialSelectLastOption(),
      stockItemsUpdatePage.currencySelectLastOption(),
      stockItemsUpdatePage.barcodeTypeSelectLastOption(),
      stockItemsUpdatePage.productSelectLastOption()
    ]);
    expect(await stockItemsUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await stockItemsUpdatePage.getVendorCodeInput()).to.eq('vendorCode', 'Expected VendorCode value to be equals to vendorCode');
    expect(await stockItemsUpdatePage.getVendorSKUInput()).to.eq('vendorSKU', 'Expected VendorSKU value to be equals to vendorSKU');
    expect(await stockItemsUpdatePage.getGeneratedSKUInput()).to.eq(
      'generatedSKU',
      'Expected GeneratedSKU value to be equals to generatedSKU'
    );
    expect(await stockItemsUpdatePage.getBarcodeInput()).to.eq('barcode', 'Expected Barcode value to be equals to barcode');
    expect(await stockItemsUpdatePage.getUnitPriceInput()).to.eq('5', 'Expected unitPrice value to be equals to 5');
    expect(await stockItemsUpdatePage.getRecommendedRetailPriceInput()).to.eq(
      '5',
      'Expected recommendedRetailPrice value to be equals to 5'
    );
    expect(await stockItemsUpdatePage.getQuantityOnHandInput()).to.eq('5', 'Expected quantityOnHand value to be equals to 5');
    expect(await stockItemsUpdatePage.getItemLengthInput()).to.eq('5', 'Expected itemLength value to be equals to 5');
    expect(await stockItemsUpdatePage.getItemWidthInput()).to.eq('5', 'Expected itemWidth value to be equals to 5');
    expect(await stockItemsUpdatePage.getItemHeightInput()).to.eq('5', 'Expected itemHeight value to be equals to 5');
    expect(await stockItemsUpdatePage.getItemWeightInput()).to.eq('5', 'Expected itemWeight value to be equals to 5');
    expect(await stockItemsUpdatePage.getItemPackageLengthInput()).to.eq('5', 'Expected itemPackageLength value to be equals to 5');
    expect(await stockItemsUpdatePage.getItemPackageWidthInput()).to.eq('5', 'Expected itemPackageWidth value to be equals to 5');
    expect(await stockItemsUpdatePage.getItemPackageHeightInput()).to.eq('5', 'Expected itemPackageHeight value to be equals to 5');
    expect(await stockItemsUpdatePage.getItemPackageWeightInput()).to.eq('5', 'Expected itemPackageWeight value to be equals to 5');
    expect(await stockItemsUpdatePage.getNoOfPiecesInput()).to.eq('5', 'Expected noOfPieces value to be equals to 5');
    expect(await stockItemsUpdatePage.getNoOfItemsInput()).to.eq('5', 'Expected noOfItems value to be equals to 5');
    expect(await stockItemsUpdatePage.getManufactureInput()).to.eq('manufacture', 'Expected Manufacture value to be equals to manufacture');
    expect(await stockItemsUpdatePage.getMarketingCommentsInput()).to.eq(
      'marketingComments',
      'Expected MarketingComments value to be equals to marketingComments'
    );
    expect(await stockItemsUpdatePage.getInternalCommentsInput()).to.eq(
      'internalComments',
      'Expected InternalComments value to be equals to internalComments'
    );
    expect(await stockItemsUpdatePage.getSellStartDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected sellStartDate value to be equals to 2000-12-31'
    );
    expect(await stockItemsUpdatePage.getSellEndDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected sellEndDate value to be equals to 2000-12-31'
    );
    expect(await stockItemsUpdatePage.getSellCountInput()).to.eq('5', 'Expected sellCount value to be equals to 5');
    expect(await stockItemsUpdatePage.getCustomFieldsInput()).to.eq(
      'customFields',
      'Expected CustomFields value to be equals to customFields'
    );
    expect(await stockItemsUpdatePage.getThumbnailUrlInput()).to.eq(
      'thumbnailUrl',
      'Expected ThumbnailUrl value to be equals to thumbnailUrl'
    );
    const selectedActiveInd = stockItemsUpdatePage.getActiveIndInput();
    if (await selectedActiveInd.isSelected()) {
      await stockItemsUpdatePage.getActiveIndInput().click();
      expect(await stockItemsUpdatePage.getActiveIndInput().isSelected(), 'Expected activeInd not to be selected').to.be.false;
    } else {
      await stockItemsUpdatePage.getActiveIndInput().click();
      expect(await stockItemsUpdatePage.getActiveIndInput().isSelected(), 'Expected activeInd to be selected').to.be.true;
    }
    expect(await stockItemsUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await stockItemsUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await stockItemsUpdatePage.save();
    expect(await stockItemsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await stockItemsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last StockItems', async () => {
    const nbButtonsBeforeDelete = await stockItemsComponentsPage.countDeleteButtons();
    await stockItemsComponentsPage.clickOnLastDeleteButton();

    stockItemsDeleteDialog = new StockItemsDeleteDialog();
    expect(await stockItemsDeleteDialog.getDialogTitle()).to.eq('epmwebApp.stockItems.delete.question');
    await stockItemsDeleteDialog.clickOnConfirmButton();

    expect(await stockItemsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
