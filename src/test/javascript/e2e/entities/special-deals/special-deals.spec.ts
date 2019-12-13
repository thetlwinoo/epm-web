import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SpecialDealsComponentsPage, SpecialDealsDeleteDialog, SpecialDealsUpdatePage } from './special-deals.page-object';

const expect = chai.expect;

describe('SpecialDeals e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let specialDealsComponentsPage: SpecialDealsComponentsPage;
  let specialDealsUpdatePage: SpecialDealsUpdatePage;
  let specialDealsDeleteDialog: SpecialDealsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SpecialDeals', async () => {
    await navBarPage.goToEntity('special-deals');
    specialDealsComponentsPage = new SpecialDealsComponentsPage();
    await browser.wait(ec.visibilityOf(specialDealsComponentsPage.title), 5000);
    expect(await specialDealsComponentsPage.getTitle()).to.eq('epmwebApp.specialDeals.home.title');
  });

  it('should load create SpecialDeals page', async () => {
    await specialDealsComponentsPage.clickOnCreateButton();
    specialDealsUpdatePage = new SpecialDealsUpdatePage();
    expect(await specialDealsUpdatePage.getPageTitle()).to.eq('epmwebApp.specialDeals.home.createOrEditLabel');
    await specialDealsUpdatePage.cancel();
  });

  it('should create and save SpecialDeals', async () => {
    const nbButtonsBeforeCreate = await specialDealsComponentsPage.countDeleteButtons();

    await specialDealsComponentsPage.clickOnCreateButton();
    await promise.all([
      specialDealsUpdatePage.setDealDescriptionInput('dealDescription'),
      specialDealsUpdatePage.setStartDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      specialDealsUpdatePage.setEndDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      specialDealsUpdatePage.setDiscountAmountInput('5'),
      specialDealsUpdatePage.setDiscountPercentageInput('5'),
      specialDealsUpdatePage.setDiscountCodeInput('discountCode'),
      specialDealsUpdatePage.setUnitPriceInput('5'),
      specialDealsUpdatePage.setLastEditedByInput('lastEditedBy'),
      specialDealsUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      specialDealsUpdatePage.buyingGroupSelectLastOption(),
      specialDealsUpdatePage.customerCategorySelectLastOption(),
      specialDealsUpdatePage.customerSelectLastOption(),
      specialDealsUpdatePage.productCategorySelectLastOption(),
      specialDealsUpdatePage.stockItemSelectLastOption()
    ]);
    expect(await specialDealsUpdatePage.getDealDescriptionInput()).to.eq(
      'dealDescription',
      'Expected DealDescription value to be equals to dealDescription'
    );
    expect(await specialDealsUpdatePage.getStartDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected startDate value to be equals to 2000-12-31'
    );
    expect(await specialDealsUpdatePage.getEndDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected endDate value to be equals to 2000-12-31'
    );
    expect(await specialDealsUpdatePage.getDiscountAmountInput()).to.eq('5', 'Expected discountAmount value to be equals to 5');
    expect(await specialDealsUpdatePage.getDiscountPercentageInput()).to.eq('5', 'Expected discountPercentage value to be equals to 5');
    expect(await specialDealsUpdatePage.getDiscountCodeInput()).to.eq(
      'discountCode',
      'Expected DiscountCode value to be equals to discountCode'
    );
    expect(await specialDealsUpdatePage.getUnitPriceInput()).to.eq('5', 'Expected unitPrice value to be equals to 5');
    expect(await specialDealsUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await specialDealsUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await specialDealsUpdatePage.save();
    expect(await specialDealsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await specialDealsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last SpecialDeals', async () => {
    const nbButtonsBeforeDelete = await specialDealsComponentsPage.countDeleteButtons();
    await specialDealsComponentsPage.clickOnLastDeleteButton();

    specialDealsDeleteDialog = new SpecialDealsDeleteDialog();
    expect(await specialDealsDeleteDialog.getDialogTitle()).to.eq('epmwebApp.specialDeals.delete.question');
    await specialDealsDeleteDialog.clickOnConfirmButton();

    expect(await specialDealsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
