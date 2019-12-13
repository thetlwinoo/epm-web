import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ProductSetDetailsComponentsPage,
  ProductSetDetailsDeleteDialog,
  ProductSetDetailsUpdatePage
} from './product-set-details.page-object';

const expect = chai.expect;

describe('ProductSetDetails e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productSetDetailsComponentsPage: ProductSetDetailsComponentsPage;
  let productSetDetailsUpdatePage: ProductSetDetailsUpdatePage;
  let productSetDetailsDeleteDialog: ProductSetDetailsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductSetDetails', async () => {
    await navBarPage.goToEntity('product-set-details');
    productSetDetailsComponentsPage = new ProductSetDetailsComponentsPage();
    await browser.wait(ec.visibilityOf(productSetDetailsComponentsPage.title), 5000);
    expect(await productSetDetailsComponentsPage.getTitle()).to.eq('epmwebApp.productSetDetails.home.title');
  });

  it('should load create ProductSetDetails page', async () => {
    await productSetDetailsComponentsPage.clickOnCreateButton();
    productSetDetailsUpdatePage = new ProductSetDetailsUpdatePage();
    expect(await productSetDetailsUpdatePage.getPageTitle()).to.eq('epmwebApp.productSetDetails.home.createOrEditLabel');
    await productSetDetailsUpdatePage.cancel();
  });

  it('should create and save ProductSetDetails', async () => {
    const nbButtonsBeforeCreate = await productSetDetailsComponentsPage.countDeleteButtons();

    await productSetDetailsComponentsPage.clickOnCreateButton();
    await promise.all([
      productSetDetailsUpdatePage.setSubGroupNoInput('5'),
      productSetDetailsUpdatePage.setSubGroupMinCountInput('5'),
      productSetDetailsUpdatePage.setSubGroupMinTotalInput('5'),
      productSetDetailsUpdatePage.setMinCountInput('5'),
      productSetDetailsUpdatePage.setMaxCountInput('5')
    ]);
    expect(await productSetDetailsUpdatePage.getSubGroupNoInput()).to.eq('5', 'Expected subGroupNo value to be equals to 5');
    expect(await productSetDetailsUpdatePage.getSubGroupMinCountInput()).to.eq('5', 'Expected subGroupMinCount value to be equals to 5');
    expect(await productSetDetailsUpdatePage.getSubGroupMinTotalInput()).to.eq('5', 'Expected subGroupMinTotal value to be equals to 5');
    expect(await productSetDetailsUpdatePage.getMinCountInput()).to.eq('5', 'Expected minCount value to be equals to 5');
    expect(await productSetDetailsUpdatePage.getMaxCountInput()).to.eq('5', 'Expected maxCount value to be equals to 5');
    const selectedIsOptional = productSetDetailsUpdatePage.getIsOptionalInput();
    if (await selectedIsOptional.isSelected()) {
      await productSetDetailsUpdatePage.getIsOptionalInput().click();
      expect(await productSetDetailsUpdatePage.getIsOptionalInput().isSelected(), 'Expected isOptional not to be selected').to.be.false;
    } else {
      await productSetDetailsUpdatePage.getIsOptionalInput().click();
      expect(await productSetDetailsUpdatePage.getIsOptionalInput().isSelected(), 'Expected isOptional to be selected').to.be.true;
    }
    await productSetDetailsUpdatePage.save();
    expect(await productSetDetailsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productSetDetailsComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProductSetDetails', async () => {
    const nbButtonsBeforeDelete = await productSetDetailsComponentsPage.countDeleteButtons();
    await productSetDetailsComponentsPage.clickOnLastDeleteButton();

    productSetDetailsDeleteDialog = new ProductSetDetailsDeleteDialog();
    expect(await productSetDetailsDeleteDialog.getDialogTitle()).to.eq('epmwebApp.productSetDetails.delete.question');
    await productSetDetailsDeleteDialog.clickOnConfirmButton();

    expect(await productSetDetailsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
