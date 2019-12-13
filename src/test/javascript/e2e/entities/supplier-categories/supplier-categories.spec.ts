import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  SupplierCategoriesComponentsPage,
  SupplierCategoriesDeleteDialog,
  SupplierCategoriesUpdatePage
} from './supplier-categories.page-object';

const expect = chai.expect;

describe('SupplierCategories e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let supplierCategoriesComponentsPage: SupplierCategoriesComponentsPage;
  let supplierCategoriesUpdatePage: SupplierCategoriesUpdatePage;
  let supplierCategoriesDeleteDialog: SupplierCategoriesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SupplierCategories', async () => {
    await navBarPage.goToEntity('supplier-categories');
    supplierCategoriesComponentsPage = new SupplierCategoriesComponentsPage();
    await browser.wait(ec.visibilityOf(supplierCategoriesComponentsPage.title), 5000);
    expect(await supplierCategoriesComponentsPage.getTitle()).to.eq('epmwebApp.supplierCategories.home.title');
  });

  it('should load create SupplierCategories page', async () => {
    await supplierCategoriesComponentsPage.clickOnCreateButton();
    supplierCategoriesUpdatePage = new SupplierCategoriesUpdatePage();
    expect(await supplierCategoriesUpdatePage.getPageTitle()).to.eq('epmwebApp.supplierCategories.home.createOrEditLabel');
    await supplierCategoriesUpdatePage.cancel();
  });

  it('should create and save SupplierCategories', async () => {
    const nbButtonsBeforeCreate = await supplierCategoriesComponentsPage.countDeleteButtons();

    await supplierCategoriesComponentsPage.clickOnCreateButton();
    await promise.all([
      supplierCategoriesUpdatePage.setNameInput('name'),
      supplierCategoriesUpdatePage.setValidFromInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      supplierCategoriesUpdatePage.setValidToInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
    ]);
    expect(await supplierCategoriesUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await supplierCategoriesUpdatePage.getValidFromInput()).to.contain(
      '2001-01-01T02:30',
      'Expected validFrom value to be equals to 2000-12-31'
    );
    expect(await supplierCategoriesUpdatePage.getValidToInput()).to.contain(
      '2001-01-01T02:30',
      'Expected validTo value to be equals to 2000-12-31'
    );
    await supplierCategoriesUpdatePage.save();
    expect(await supplierCategoriesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await supplierCategoriesComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last SupplierCategories', async () => {
    const nbButtonsBeforeDelete = await supplierCategoriesComponentsPage.countDeleteButtons();
    await supplierCategoriesComponentsPage.clickOnLastDeleteButton();

    supplierCategoriesDeleteDialog = new SupplierCategoriesDeleteDialog();
    expect(await supplierCategoriesDeleteDialog.getDialogTitle()).to.eq('epmwebApp.supplierCategories.delete.question');
    await supplierCategoriesDeleteDialog.clickOnConfirmButton();

    expect(await supplierCategoriesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
