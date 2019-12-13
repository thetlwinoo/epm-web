import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductSetComponentsPage, ProductSetDeleteDialog, ProductSetUpdatePage } from './product-set.page-object';

const expect = chai.expect;

describe('ProductSet e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productSetComponentsPage: ProductSetComponentsPage;
  let productSetUpdatePage: ProductSetUpdatePage;
  let productSetDeleteDialog: ProductSetDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductSets', async () => {
    await navBarPage.goToEntity('product-set');
    productSetComponentsPage = new ProductSetComponentsPage();
    await browser.wait(ec.visibilityOf(productSetComponentsPage.title), 5000);
    expect(await productSetComponentsPage.getTitle()).to.eq('epmwebApp.productSet.home.title');
  });

  it('should load create ProductSet page', async () => {
    await productSetComponentsPage.clickOnCreateButton();
    productSetUpdatePage = new ProductSetUpdatePage();
    expect(await productSetUpdatePage.getPageTitle()).to.eq('epmwebApp.productSet.home.createOrEditLabel');
    await productSetUpdatePage.cancel();
  });

  it('should create and save ProductSets', async () => {
    const nbButtonsBeforeCreate = await productSetComponentsPage.countDeleteButtons();

    await productSetComponentsPage.clickOnCreateButton();
    await promise.all([productSetUpdatePage.setNameInput('name'), productSetUpdatePage.setNoOfPersonInput('5')]);
    expect(await productSetUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await productSetUpdatePage.getNoOfPersonInput()).to.eq('5', 'Expected noOfPerson value to be equals to 5');
    const selectedIsExclusive = productSetUpdatePage.getIsExclusiveInput();
    if (await selectedIsExclusive.isSelected()) {
      await productSetUpdatePage.getIsExclusiveInput().click();
      expect(await productSetUpdatePage.getIsExclusiveInput().isSelected(), 'Expected isExclusive not to be selected').to.be.false;
    } else {
      await productSetUpdatePage.getIsExclusiveInput().click();
      expect(await productSetUpdatePage.getIsExclusiveInput().isSelected(), 'Expected isExclusive to be selected').to.be.true;
    }
    await productSetUpdatePage.save();
    expect(await productSetUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productSetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ProductSet', async () => {
    const nbButtonsBeforeDelete = await productSetComponentsPage.countDeleteButtons();
    await productSetComponentsPage.clickOnLastDeleteButton();

    productSetDeleteDialog = new ProductSetDeleteDialog();
    expect(await productSetDeleteDialog.getDialogTitle()).to.eq('epmwebApp.productSet.delete.question');
    await productSetDeleteDialog.clickOnConfirmButton();

    expect(await productSetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
