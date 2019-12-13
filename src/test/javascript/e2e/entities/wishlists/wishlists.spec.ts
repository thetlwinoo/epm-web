import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { WishlistsComponentsPage, WishlistsDeleteDialog, WishlistsUpdatePage } from './wishlists.page-object';

const expect = chai.expect;

describe('Wishlists e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let wishlistsComponentsPage: WishlistsComponentsPage;
  let wishlistsUpdatePage: WishlistsUpdatePage;
  let wishlistsDeleteDialog: WishlistsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Wishlists', async () => {
    await navBarPage.goToEntity('wishlists');
    wishlistsComponentsPage = new WishlistsComponentsPage();
    await browser.wait(ec.visibilityOf(wishlistsComponentsPage.title), 5000);
    expect(await wishlistsComponentsPage.getTitle()).to.eq('epmwebApp.wishlists.home.title');
  });

  it('should load create Wishlists page', async () => {
    await wishlistsComponentsPage.clickOnCreateButton();
    wishlistsUpdatePage = new WishlistsUpdatePage();
    expect(await wishlistsUpdatePage.getPageTitle()).to.eq('epmwebApp.wishlists.home.createOrEditLabel');
    await wishlistsUpdatePage.cancel();
  });

  it('should create and save Wishlists', async () => {
    const nbButtonsBeforeCreate = await wishlistsComponentsPage.countDeleteButtons();

    await wishlistsComponentsPage.clickOnCreateButton();
    await promise.all([wishlistsUpdatePage.wishlistUserSelectLastOption()]);
    await wishlistsUpdatePage.save();
    expect(await wishlistsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await wishlistsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Wishlists', async () => {
    const nbButtonsBeforeDelete = await wishlistsComponentsPage.countDeleteButtons();
    await wishlistsComponentsPage.clickOnLastDeleteButton();

    wishlistsDeleteDialog = new WishlistsDeleteDialog();
    expect(await wishlistsDeleteDialog.getDialogTitle()).to.eq('epmwebApp.wishlists.delete.question');
    await wishlistsDeleteDialog.clickOnConfirmButton();

    expect(await wishlistsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
