import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductChoiceComponentsPage, ProductChoiceDeleteDialog, ProductChoiceUpdatePage } from './product-choice.page-object';

const expect = chai.expect;

describe('ProductChoice e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productChoiceComponentsPage: ProductChoiceComponentsPage;
  let productChoiceUpdatePage: ProductChoiceUpdatePage;
  let productChoiceDeleteDialog: ProductChoiceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductChoices', async () => {
    await navBarPage.goToEntity('product-choice');
    productChoiceComponentsPage = new ProductChoiceComponentsPage();
    await browser.wait(ec.visibilityOf(productChoiceComponentsPage.title), 5000);
    expect(await productChoiceComponentsPage.getTitle()).to.eq('epmwebApp.productChoice.home.title');
  });

  it('should load create ProductChoice page', async () => {
    await productChoiceComponentsPage.clickOnCreateButton();
    productChoiceUpdatePage = new ProductChoiceUpdatePage();
    expect(await productChoiceUpdatePage.getPageTitle()).to.eq('epmwebApp.productChoice.home.createOrEditLabel');
    await productChoiceUpdatePage.cancel();
  });

  it('should create and save ProductChoices', async () => {
    const nbButtonsBeforeCreate = await productChoiceComponentsPage.countDeleteButtons();

    await productChoiceComponentsPage.clickOnCreateButton();
    await promise.all([
      productChoiceUpdatePage.productCategorySelectLastOption(),
      productChoiceUpdatePage.productAttributeSetSelectLastOption(),
      productChoiceUpdatePage.productOptionSetSelectLastOption()
    ]);
    const selectedIsMultiply = productChoiceUpdatePage.getIsMultiplyInput();
    if (await selectedIsMultiply.isSelected()) {
      await productChoiceUpdatePage.getIsMultiplyInput().click();
      expect(await productChoiceUpdatePage.getIsMultiplyInput().isSelected(), 'Expected isMultiply not to be selected').to.be.false;
    } else {
      await productChoiceUpdatePage.getIsMultiplyInput().click();
      expect(await productChoiceUpdatePage.getIsMultiplyInput().isSelected(), 'Expected isMultiply to be selected').to.be.true;
    }
    await productChoiceUpdatePage.save();
    expect(await productChoiceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productChoiceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ProductChoice', async () => {
    const nbButtonsBeforeDelete = await productChoiceComponentsPage.countDeleteButtons();
    await productChoiceComponentsPage.clickOnLastDeleteButton();

    productChoiceDeleteDialog = new ProductChoiceDeleteDialog();
    expect(await productChoiceDeleteDialog.getDialogTitle()).to.eq('epmwebApp.productChoice.delete.question');
    await productChoiceDeleteDialog.clickOnConfirmButton();

    expect(await productChoiceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
