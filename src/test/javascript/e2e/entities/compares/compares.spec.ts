import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ComparesComponentsPage, ComparesDeleteDialog, ComparesUpdatePage } from './compares.page-object';

const expect = chai.expect;

describe('Compares e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let comparesComponentsPage: ComparesComponentsPage;
  let comparesUpdatePage: ComparesUpdatePage;
  let comparesDeleteDialog: ComparesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Compares', async () => {
    await navBarPage.goToEntity('compares');
    comparesComponentsPage = new ComparesComponentsPage();
    await browser.wait(ec.visibilityOf(comparesComponentsPage.title), 5000);
    expect(await comparesComponentsPage.getTitle()).to.eq('epmwebApp.compares.home.title');
  });

  it('should load create Compares page', async () => {
    await comparesComponentsPage.clickOnCreateButton();
    comparesUpdatePage = new ComparesUpdatePage();
    expect(await comparesUpdatePage.getPageTitle()).to.eq('epmwebApp.compares.home.createOrEditLabel');
    await comparesUpdatePage.cancel();
  });

  it('should create and save Compares', async () => {
    const nbButtonsBeforeCreate = await comparesComponentsPage.countDeleteButtons();

    await comparesComponentsPage.clickOnCreateButton();
    await promise.all([comparesUpdatePage.compareUserSelectLastOption()]);
    await comparesUpdatePage.save();
    expect(await comparesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await comparesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Compares', async () => {
    const nbButtonsBeforeDelete = await comparesComponentsPage.countDeleteButtons();
    await comparesComponentsPage.clickOnLastDeleteButton();

    comparesDeleteDialog = new ComparesDeleteDialog();
    expect(await comparesDeleteDialog.getDialogTitle()).to.eq('epmwebApp.compares.delete.question');
    await comparesDeleteDialog.clickOnConfirmButton();

    expect(await comparesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
