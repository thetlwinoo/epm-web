import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductOptionComponentsPage, ProductOptionDeleteDialog, ProductOptionUpdatePage } from './product-option.page-object';

const expect = chai.expect;

describe('ProductOption e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productOptionComponentsPage: ProductOptionComponentsPage;
  let productOptionUpdatePage: ProductOptionUpdatePage;
  let productOptionDeleteDialog: ProductOptionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductOptions', async () => {
    await navBarPage.goToEntity('product-option');
    productOptionComponentsPage = new ProductOptionComponentsPage();
    await browser.wait(ec.visibilityOf(productOptionComponentsPage.title), 5000);
    expect(await productOptionComponentsPage.getTitle()).to.eq('epmwebApp.productOption.home.title');
  });

  it('should load create ProductOption page', async () => {
    await productOptionComponentsPage.clickOnCreateButton();
    productOptionUpdatePage = new ProductOptionUpdatePage();
    expect(await productOptionUpdatePage.getPageTitle()).to.eq('epmwebApp.productOption.home.createOrEditLabel');
    await productOptionUpdatePage.cancel();
  });

  it('should create and save ProductOptions', async () => {
    const nbButtonsBeforeCreate = await productOptionComponentsPage.countDeleteButtons();

    await productOptionComponentsPage.clickOnCreateButton();
    await promise.all([
      productOptionUpdatePage.setValueInput('value'),
      productOptionUpdatePage.productOptionSetSelectLastOption(),
      productOptionUpdatePage.supplierSelectLastOption()
    ]);
    expect(await productOptionUpdatePage.getValueInput()).to.eq('value', 'Expected Value value to be equals to value');
    await productOptionUpdatePage.save();
    expect(await productOptionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productOptionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ProductOption', async () => {
    const nbButtonsBeforeDelete = await productOptionComponentsPage.countDeleteButtons();
    await productOptionComponentsPage.clickOnLastDeleteButton();

    productOptionDeleteDialog = new ProductOptionDeleteDialog();
    expect(await productOptionDeleteDialog.getDialogTitle()).to.eq('epmwebApp.productOption.delete.question');
    await productOptionDeleteDialog.clickOnConfirmButton();

    expect(await productOptionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
