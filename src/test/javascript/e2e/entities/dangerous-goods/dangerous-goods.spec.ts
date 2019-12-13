import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DangerousGoodsComponentsPage, DangerousGoodsDeleteDialog, DangerousGoodsUpdatePage } from './dangerous-goods.page-object';

const expect = chai.expect;

describe('DangerousGoods e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let dangerousGoodsComponentsPage: DangerousGoodsComponentsPage;
  let dangerousGoodsUpdatePage: DangerousGoodsUpdatePage;
  let dangerousGoodsDeleteDialog: DangerousGoodsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DangerousGoods', async () => {
    await navBarPage.goToEntity('dangerous-goods');
    dangerousGoodsComponentsPage = new DangerousGoodsComponentsPage();
    await browser.wait(ec.visibilityOf(dangerousGoodsComponentsPage.title), 5000);
    expect(await dangerousGoodsComponentsPage.getTitle()).to.eq('epmwebApp.dangerousGoods.home.title');
  });

  it('should load create DangerousGoods page', async () => {
    await dangerousGoodsComponentsPage.clickOnCreateButton();
    dangerousGoodsUpdatePage = new DangerousGoodsUpdatePage();
    expect(await dangerousGoodsUpdatePage.getPageTitle()).to.eq('epmwebApp.dangerousGoods.home.createOrEditLabel');
    await dangerousGoodsUpdatePage.cancel();
  });

  it('should create and save DangerousGoods', async () => {
    const nbButtonsBeforeCreate = await dangerousGoodsComponentsPage.countDeleteButtons();

    await dangerousGoodsComponentsPage.clickOnCreateButton();
    await promise.all([dangerousGoodsUpdatePage.setNameInput('name'), dangerousGoodsUpdatePage.stockItemSelectLastOption()]);
    expect(await dangerousGoodsUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    await dangerousGoodsUpdatePage.save();
    expect(await dangerousGoodsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await dangerousGoodsComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DangerousGoods', async () => {
    const nbButtonsBeforeDelete = await dangerousGoodsComponentsPage.countDeleteButtons();
    await dangerousGoodsComponentsPage.clickOnLastDeleteButton();

    dangerousGoodsDeleteDialog = new DangerousGoodsDeleteDialog();
    expect(await dangerousGoodsDeleteDialog.getDialogTitle()).to.eq('epmwebApp.dangerousGoods.delete.question');
    await dangerousGoodsDeleteDialog.clickOnConfirmButton();

    expect(await dangerousGoodsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
