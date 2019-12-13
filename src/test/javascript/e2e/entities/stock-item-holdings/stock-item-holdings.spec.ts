import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  StockItemHoldingsComponentsPage,
  StockItemHoldingsDeleteDialog,
  StockItemHoldingsUpdatePage
} from './stock-item-holdings.page-object';

const expect = chai.expect;

describe('StockItemHoldings e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let stockItemHoldingsComponentsPage: StockItemHoldingsComponentsPage;
  let stockItemHoldingsUpdatePage: StockItemHoldingsUpdatePage;
  let stockItemHoldingsDeleteDialog: StockItemHoldingsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load StockItemHoldings', async () => {
    await navBarPage.goToEntity('stock-item-holdings');
    stockItemHoldingsComponentsPage = new StockItemHoldingsComponentsPage();
    await browser.wait(ec.visibilityOf(stockItemHoldingsComponentsPage.title), 5000);
    expect(await stockItemHoldingsComponentsPage.getTitle()).to.eq('epmwebApp.stockItemHoldings.home.title');
  });

  it('should load create StockItemHoldings page', async () => {
    await stockItemHoldingsComponentsPage.clickOnCreateButton();
    stockItemHoldingsUpdatePage = new StockItemHoldingsUpdatePage();
    expect(await stockItemHoldingsUpdatePage.getPageTitle()).to.eq('epmwebApp.stockItemHoldings.home.createOrEditLabel');
    await stockItemHoldingsUpdatePage.cancel();
  });

  it('should create and save StockItemHoldings', async () => {
    const nbButtonsBeforeCreate = await stockItemHoldingsComponentsPage.countDeleteButtons();

    await stockItemHoldingsComponentsPage.clickOnCreateButton();
    await promise.all([
      stockItemHoldingsUpdatePage.setQuantityOnHandInput('5'),
      stockItemHoldingsUpdatePage.setBinLocationInput('binLocation'),
      stockItemHoldingsUpdatePage.setLastStocktakeQuantityInput('5'),
      stockItemHoldingsUpdatePage.setLastCostPriceInput('5'),
      stockItemHoldingsUpdatePage.setReorderLevelInput('5'),
      stockItemHoldingsUpdatePage.setTargerStockLevelInput('5'),
      stockItemHoldingsUpdatePage.stockItemHoldingOnStockItemSelectLastOption()
    ]);
    expect(await stockItemHoldingsUpdatePage.getQuantityOnHandInput()).to.eq('5', 'Expected quantityOnHand value to be equals to 5');
    expect(await stockItemHoldingsUpdatePage.getBinLocationInput()).to.eq(
      'binLocation',
      'Expected BinLocation value to be equals to binLocation'
    );
    expect(await stockItemHoldingsUpdatePage.getLastStocktakeQuantityInput()).to.eq(
      '5',
      'Expected lastStocktakeQuantity value to be equals to 5'
    );
    expect(await stockItemHoldingsUpdatePage.getLastCostPriceInput()).to.eq('5', 'Expected lastCostPrice value to be equals to 5');
    expect(await stockItemHoldingsUpdatePage.getReorderLevelInput()).to.eq('5', 'Expected reorderLevel value to be equals to 5');
    expect(await stockItemHoldingsUpdatePage.getTargerStockLevelInput()).to.eq('5', 'Expected targerStockLevel value to be equals to 5');
    await stockItemHoldingsUpdatePage.save();
    expect(await stockItemHoldingsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await stockItemHoldingsComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last StockItemHoldings', async () => {
    const nbButtonsBeforeDelete = await stockItemHoldingsComponentsPage.countDeleteButtons();
    await stockItemHoldingsComponentsPage.clickOnLastDeleteButton();

    stockItemHoldingsDeleteDialog = new StockItemHoldingsDeleteDialog();
    expect(await stockItemHoldingsDeleteDialog.getDialogTitle()).to.eq('epmwebApp.stockItemHoldings.delete.question');
    await stockItemHoldingsDeleteDialog.clickOnConfirmButton();

    expect(await stockItemHoldingsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
