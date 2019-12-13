import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BuyingGroupsComponentsPage, BuyingGroupsDeleteDialog, BuyingGroupsUpdatePage } from './buying-groups.page-object';

const expect = chai.expect;

describe('BuyingGroups e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let buyingGroupsComponentsPage: BuyingGroupsComponentsPage;
  let buyingGroupsUpdatePage: BuyingGroupsUpdatePage;
  let buyingGroupsDeleteDialog: BuyingGroupsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BuyingGroups', async () => {
    await navBarPage.goToEntity('buying-groups');
    buyingGroupsComponentsPage = new BuyingGroupsComponentsPage();
    await browser.wait(ec.visibilityOf(buyingGroupsComponentsPage.title), 5000);
    expect(await buyingGroupsComponentsPage.getTitle()).to.eq('epmwebApp.buyingGroups.home.title');
  });

  it('should load create BuyingGroups page', async () => {
    await buyingGroupsComponentsPage.clickOnCreateButton();
    buyingGroupsUpdatePage = new BuyingGroupsUpdatePage();
    expect(await buyingGroupsUpdatePage.getPageTitle()).to.eq('epmwebApp.buyingGroups.home.createOrEditLabel');
    await buyingGroupsUpdatePage.cancel();
  });

  it('should create and save BuyingGroups', async () => {
    const nbButtonsBeforeCreate = await buyingGroupsComponentsPage.countDeleteButtons();

    await buyingGroupsComponentsPage.clickOnCreateButton();
    await promise.all([
      buyingGroupsUpdatePage.setNameInput('name'),
      buyingGroupsUpdatePage.setValidFromInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      buyingGroupsUpdatePage.setValidToInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
    ]);
    expect(await buyingGroupsUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await buyingGroupsUpdatePage.getValidFromInput()).to.contain(
      '2001-01-01T02:30',
      'Expected validFrom value to be equals to 2000-12-31'
    );
    expect(await buyingGroupsUpdatePage.getValidToInput()).to.contain(
      '2001-01-01T02:30',
      'Expected validTo value to be equals to 2000-12-31'
    );
    await buyingGroupsUpdatePage.save();
    expect(await buyingGroupsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await buyingGroupsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last BuyingGroups', async () => {
    const nbButtonsBeforeDelete = await buyingGroupsComponentsPage.countDeleteButtons();
    await buyingGroupsComponentsPage.clickOnLastDeleteButton();

    buyingGroupsDeleteDialog = new BuyingGroupsDeleteDialog();
    expect(await buyingGroupsDeleteDialog.getDialogTitle()).to.eq('epmwebApp.buyingGroups.delete.question');
    await buyingGroupsDeleteDialog.clickOnConfirmButton();

    expect(await buyingGroupsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
