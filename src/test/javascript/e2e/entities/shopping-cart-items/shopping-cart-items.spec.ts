import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ShoppingCartItemsComponentsPage,
  ShoppingCartItemsDeleteDialog,
  ShoppingCartItemsUpdatePage
} from './shopping-cart-items.page-object';

const expect = chai.expect;

describe('ShoppingCartItems e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let shoppingCartItemsComponentsPage: ShoppingCartItemsComponentsPage;
  let shoppingCartItemsUpdatePage: ShoppingCartItemsUpdatePage;
  let shoppingCartItemsDeleteDialog: ShoppingCartItemsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ShoppingCartItems', async () => {
    await navBarPage.goToEntity('shopping-cart-items');
    shoppingCartItemsComponentsPage = new ShoppingCartItemsComponentsPage();
    await browser.wait(ec.visibilityOf(shoppingCartItemsComponentsPage.title), 5000);
    expect(await shoppingCartItemsComponentsPage.getTitle()).to.eq('epmwebApp.shoppingCartItems.home.title');
  });

  it('should load create ShoppingCartItems page', async () => {
    await shoppingCartItemsComponentsPage.clickOnCreateButton();
    shoppingCartItemsUpdatePage = new ShoppingCartItemsUpdatePage();
    expect(await shoppingCartItemsUpdatePage.getPageTitle()).to.eq('epmwebApp.shoppingCartItems.home.createOrEditLabel');
    await shoppingCartItemsUpdatePage.cancel();
  });

  it('should create and save ShoppingCartItems', async () => {
    const nbButtonsBeforeCreate = await shoppingCartItemsComponentsPage.countDeleteButtons();

    await shoppingCartItemsComponentsPage.clickOnCreateButton();
    await promise.all([
      shoppingCartItemsUpdatePage.setQuantityInput('5'),
      shoppingCartItemsUpdatePage.setLastEditedByInput('lastEditedBy'),
      shoppingCartItemsUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      shoppingCartItemsUpdatePage.stockItemSelectLastOption(),
      shoppingCartItemsUpdatePage.cartSelectLastOption()
    ]);
    expect(await shoppingCartItemsUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await shoppingCartItemsUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await shoppingCartItemsUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await shoppingCartItemsUpdatePage.save();
    expect(await shoppingCartItemsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await shoppingCartItemsComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ShoppingCartItems', async () => {
    const nbButtonsBeforeDelete = await shoppingCartItemsComponentsPage.countDeleteButtons();
    await shoppingCartItemsComponentsPage.clickOnLastDeleteButton();

    shoppingCartItemsDeleteDialog = new ShoppingCartItemsDeleteDialog();
    expect(await shoppingCartItemsDeleteDialog.getDialogTitle()).to.eq('epmwebApp.shoppingCartItems.delete.question');
    await shoppingCartItemsDeleteDialog.clickOnConfirmButton();

    expect(await shoppingCartItemsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
