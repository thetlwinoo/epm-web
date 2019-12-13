import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CustomerCategoriesComponentsPage,
  CustomerCategoriesDeleteDialog,
  CustomerCategoriesUpdatePage
} from './customer-categories.page-object';

const expect = chai.expect;

describe('CustomerCategories e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let customerCategoriesComponentsPage: CustomerCategoriesComponentsPage;
  let customerCategoriesUpdatePage: CustomerCategoriesUpdatePage;
  let customerCategoriesDeleteDialog: CustomerCategoriesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CustomerCategories', async () => {
    await navBarPage.goToEntity('customer-categories');
    customerCategoriesComponentsPage = new CustomerCategoriesComponentsPage();
    await browser.wait(ec.visibilityOf(customerCategoriesComponentsPage.title), 5000);
    expect(await customerCategoriesComponentsPage.getTitle()).to.eq('epmwebApp.customerCategories.home.title');
  });

  it('should load create CustomerCategories page', async () => {
    await customerCategoriesComponentsPage.clickOnCreateButton();
    customerCategoriesUpdatePage = new CustomerCategoriesUpdatePage();
    expect(await customerCategoriesUpdatePage.getPageTitle()).to.eq('epmwebApp.customerCategories.home.createOrEditLabel');
    await customerCategoriesUpdatePage.cancel();
  });

  it('should create and save CustomerCategories', async () => {
    const nbButtonsBeforeCreate = await customerCategoriesComponentsPage.countDeleteButtons();

    await customerCategoriesComponentsPage.clickOnCreateButton();
    await promise.all([
      customerCategoriesUpdatePage.setNameInput('name'),
      customerCategoriesUpdatePage.setValidFromInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      customerCategoriesUpdatePage.setValidToInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
    ]);
    expect(await customerCategoriesUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await customerCategoriesUpdatePage.getValidFromInput()).to.contain(
      '2001-01-01T02:30',
      'Expected validFrom value to be equals to 2000-12-31'
    );
    expect(await customerCategoriesUpdatePage.getValidToInput()).to.contain(
      '2001-01-01T02:30',
      'Expected validTo value to be equals to 2000-12-31'
    );
    await customerCategoriesUpdatePage.save();
    expect(await customerCategoriesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await customerCategoriesComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CustomerCategories', async () => {
    const nbButtonsBeforeDelete = await customerCategoriesComponentsPage.countDeleteButtons();
    await customerCategoriesComponentsPage.clickOnLastDeleteButton();

    customerCategoriesDeleteDialog = new CustomerCategoriesDeleteDialog();
    expect(await customerCategoriesDeleteDialog.getDialogTitle()).to.eq('epmwebApp.customerCategories.delete.question');
    await customerCategoriesDeleteDialog.clickOnConfirmButton();

    expect(await customerCategoriesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
