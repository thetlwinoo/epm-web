import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DeliveryMethodsComponentsPage, DeliveryMethodsDeleteDialog, DeliveryMethodsUpdatePage } from './delivery-methods.page-object';

const expect = chai.expect;

describe('DeliveryMethods e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let deliveryMethodsComponentsPage: DeliveryMethodsComponentsPage;
  let deliveryMethodsUpdatePage: DeliveryMethodsUpdatePage;
  let deliveryMethodsDeleteDialog: DeliveryMethodsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DeliveryMethods', async () => {
    await navBarPage.goToEntity('delivery-methods');
    deliveryMethodsComponentsPage = new DeliveryMethodsComponentsPage();
    await browser.wait(ec.visibilityOf(deliveryMethodsComponentsPage.title), 5000);
    expect(await deliveryMethodsComponentsPage.getTitle()).to.eq('epmwebApp.deliveryMethods.home.title');
  });

  it('should load create DeliveryMethods page', async () => {
    await deliveryMethodsComponentsPage.clickOnCreateButton();
    deliveryMethodsUpdatePage = new DeliveryMethodsUpdatePage();
    expect(await deliveryMethodsUpdatePage.getPageTitle()).to.eq('epmwebApp.deliveryMethods.home.createOrEditLabel');
    await deliveryMethodsUpdatePage.cancel();
  });

  it('should create and save DeliveryMethods', async () => {
    const nbButtonsBeforeCreate = await deliveryMethodsComponentsPage.countDeleteButtons();

    await deliveryMethodsComponentsPage.clickOnCreateButton();
    await promise.all([
      deliveryMethodsUpdatePage.setNameInput('name'),
      deliveryMethodsUpdatePage.setValidFromInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      deliveryMethodsUpdatePage.setValidToInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
    ]);
    expect(await deliveryMethodsUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await deliveryMethodsUpdatePage.getValidFromInput()).to.contain(
      '2001-01-01T02:30',
      'Expected validFrom value to be equals to 2000-12-31'
    );
    expect(await deliveryMethodsUpdatePage.getValidToInput()).to.contain(
      '2001-01-01T02:30',
      'Expected validTo value to be equals to 2000-12-31'
    );
    await deliveryMethodsUpdatePage.save();
    expect(await deliveryMethodsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await deliveryMethodsComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DeliveryMethods', async () => {
    const nbButtonsBeforeDelete = await deliveryMethodsComponentsPage.countDeleteButtons();
    await deliveryMethodsComponentsPage.clickOnLastDeleteButton();

    deliveryMethodsDeleteDialog = new DeliveryMethodsDeleteDialog();
    expect(await deliveryMethodsDeleteDialog.getDialogTitle()).to.eq('epmwebApp.deliveryMethods.delete.question');
    await deliveryMethodsDeleteDialog.clickOnConfirmButton();

    expect(await deliveryMethodsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
