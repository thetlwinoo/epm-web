import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BarcodeTypesComponentsPage, BarcodeTypesDeleteDialog, BarcodeTypesUpdatePage } from './barcode-types.page-object';

const expect = chai.expect;

describe('BarcodeTypes e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let barcodeTypesComponentsPage: BarcodeTypesComponentsPage;
  let barcodeTypesUpdatePage: BarcodeTypesUpdatePage;
  let barcodeTypesDeleteDialog: BarcodeTypesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BarcodeTypes', async () => {
    await navBarPage.goToEntity('barcode-types');
    barcodeTypesComponentsPage = new BarcodeTypesComponentsPage();
    await browser.wait(ec.visibilityOf(barcodeTypesComponentsPage.title), 5000);
    expect(await barcodeTypesComponentsPage.getTitle()).to.eq('epmwebApp.barcodeTypes.home.title');
  });

  it('should load create BarcodeTypes page', async () => {
    await barcodeTypesComponentsPage.clickOnCreateButton();
    barcodeTypesUpdatePage = new BarcodeTypesUpdatePage();
    expect(await barcodeTypesUpdatePage.getPageTitle()).to.eq('epmwebApp.barcodeTypes.home.createOrEditLabel');
    await barcodeTypesUpdatePage.cancel();
  });

  it('should create and save BarcodeTypes', async () => {
    const nbButtonsBeforeCreate = await barcodeTypesComponentsPage.countDeleteButtons();

    await barcodeTypesComponentsPage.clickOnCreateButton();
    await promise.all([barcodeTypesUpdatePage.setNameInput('name')]);
    expect(await barcodeTypesUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    await barcodeTypesUpdatePage.save();
    expect(await barcodeTypesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await barcodeTypesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last BarcodeTypes', async () => {
    const nbButtonsBeforeDelete = await barcodeTypesComponentsPage.countDeleteButtons();
    await barcodeTypesComponentsPage.clickOnLastDeleteButton();

    barcodeTypesDeleteDialog = new BarcodeTypesDeleteDialog();
    expect(await barcodeTypesDeleteDialog.getDialogTitle()).to.eq('epmwebApp.barcodeTypes.delete.question');
    await barcodeTypesDeleteDialog.clickOnConfirmButton();

    expect(await barcodeTypesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
