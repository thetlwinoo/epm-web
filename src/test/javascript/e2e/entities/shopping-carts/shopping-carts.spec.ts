import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ShoppingCartsComponentsPage, ShoppingCartsDeleteDialog, ShoppingCartsUpdatePage } from './shopping-carts.page-object';

const expect = chai.expect;

describe('ShoppingCarts e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let shoppingCartsComponentsPage: ShoppingCartsComponentsPage;
  let shoppingCartsUpdatePage: ShoppingCartsUpdatePage;
  let shoppingCartsDeleteDialog: ShoppingCartsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ShoppingCarts', async () => {
    await navBarPage.goToEntity('shopping-carts');
    shoppingCartsComponentsPage = new ShoppingCartsComponentsPage();
    await browser.wait(ec.visibilityOf(shoppingCartsComponentsPage.title), 5000);
    expect(await shoppingCartsComponentsPage.getTitle()).to.eq('epmwebApp.shoppingCarts.home.title');
  });

  it('should load create ShoppingCarts page', async () => {
    await shoppingCartsComponentsPage.clickOnCreateButton();
    shoppingCartsUpdatePage = new ShoppingCartsUpdatePage();
    expect(await shoppingCartsUpdatePage.getPageTitle()).to.eq('epmwebApp.shoppingCarts.home.createOrEditLabel');
    await shoppingCartsUpdatePage.cancel();
  });

  it('should create and save ShoppingCarts', async () => {
    const nbButtonsBeforeCreate = await shoppingCartsComponentsPage.countDeleteButtons();

    await shoppingCartsComponentsPage.clickOnCreateButton();
    await promise.all([
      shoppingCartsUpdatePage.setTotalPriceInput('5'),
      shoppingCartsUpdatePage.setTotalCargoPriceInput('5'),
      shoppingCartsUpdatePage.setLastEditedByInput('lastEditedBy'),
      shoppingCartsUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      shoppingCartsUpdatePage.cartUserSelectLastOption(),
      shoppingCartsUpdatePage.customerSelectLastOption(),
      shoppingCartsUpdatePage.specialDealsSelectLastOption()
    ]);
    expect(await shoppingCartsUpdatePage.getTotalPriceInput()).to.eq('5', 'Expected totalPrice value to be equals to 5');
    expect(await shoppingCartsUpdatePage.getTotalCargoPriceInput()).to.eq('5', 'Expected totalCargoPrice value to be equals to 5');
    expect(await shoppingCartsUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await shoppingCartsUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await shoppingCartsUpdatePage.save();
    expect(await shoppingCartsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await shoppingCartsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ShoppingCarts', async () => {
    const nbButtonsBeforeDelete = await shoppingCartsComponentsPage.countDeleteButtons();
    await shoppingCartsComponentsPage.clickOnLastDeleteButton();

    shoppingCartsDeleteDialog = new ShoppingCartsDeleteDialog();
    expect(await shoppingCartsDeleteDialog.getDialogTitle()).to.eq('epmwebApp.shoppingCarts.delete.question');
    await shoppingCartsDeleteDialog.clickOnConfirmButton();

    expect(await shoppingCartsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
