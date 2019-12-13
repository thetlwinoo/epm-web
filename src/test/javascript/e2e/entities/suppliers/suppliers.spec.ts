import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SuppliersComponentsPage, SuppliersDeleteDialog, SuppliersUpdatePage } from './suppliers.page-object';

const expect = chai.expect;

describe('Suppliers e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let suppliersComponentsPage: SuppliersComponentsPage;
  let suppliersUpdatePage: SuppliersUpdatePage;
  let suppliersDeleteDialog: SuppliersDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Suppliers', async () => {
    await navBarPage.goToEntity('suppliers');
    suppliersComponentsPage = new SuppliersComponentsPage();
    await browser.wait(ec.visibilityOf(suppliersComponentsPage.title), 5000);
    expect(await suppliersComponentsPage.getTitle()).to.eq('epmwebApp.suppliers.home.title');
  });

  it('should load create Suppliers page', async () => {
    await suppliersComponentsPage.clickOnCreateButton();
    suppliersUpdatePage = new SuppliersUpdatePage();
    expect(await suppliersUpdatePage.getPageTitle()).to.eq('epmwebApp.suppliers.home.createOrEditLabel');
    await suppliersUpdatePage.cancel();
  });

  it('should create and save Suppliers', async () => {
    const nbButtonsBeforeCreate = await suppliersComponentsPage.countDeleteButtons();

    await suppliersComponentsPage.clickOnCreateButton();
    await promise.all([
      suppliersUpdatePage.setNameInput('name'),
      suppliersUpdatePage.setSupplierReferenceInput('supplierReference'),
      suppliersUpdatePage.setBankAccountNameInput('bankAccountName'),
      suppliersUpdatePage.setBankAccountBranchInput('bankAccountBranch'),
      suppliersUpdatePage.setBankAccountCodeInput('bankAccountCode'),
      suppliersUpdatePage.setBankAccountNumberInput('bankAccountNumber'),
      suppliersUpdatePage.setBankInternationalCodeInput('bankInternationalCode'),
      suppliersUpdatePage.setPaymentDaysInput('5'),
      suppliersUpdatePage.setInternalCommentsInput('internalComments'),
      suppliersUpdatePage.setPhoneNumberInput('phoneNumber'),
      suppliersUpdatePage.setFaxNumberInput('faxNumber'),
      suppliersUpdatePage.setWebsiteURLInput('websiteURL'),
      suppliersUpdatePage.setWebServiceUrlInput('webServiceUrl'),
      suppliersUpdatePage.setCreditRatingInput('5'),
      suppliersUpdatePage.setThumbnailUrlInput('thumbnailUrl'),
      suppliersUpdatePage.setValidFromInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      suppliersUpdatePage.setValidToInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      suppliersUpdatePage.userSelectLastOption(),
      suppliersUpdatePage.supplierCategorySelectLastOption(),
      suppliersUpdatePage.deliveryMethodSelectLastOption(),
      suppliersUpdatePage.deliveryCitySelectLastOption(),
      suppliersUpdatePage.postalCitySelectLastOption()
    ]);
    expect(await suppliersUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await suppliersUpdatePage.getSupplierReferenceInput()).to.eq(
      'supplierReference',
      'Expected SupplierReference value to be equals to supplierReference'
    );
    expect(await suppliersUpdatePage.getBankAccountNameInput()).to.eq(
      'bankAccountName',
      'Expected BankAccountName value to be equals to bankAccountName'
    );
    expect(await suppliersUpdatePage.getBankAccountBranchInput()).to.eq(
      'bankAccountBranch',
      'Expected BankAccountBranch value to be equals to bankAccountBranch'
    );
    expect(await suppliersUpdatePage.getBankAccountCodeInput()).to.eq(
      'bankAccountCode',
      'Expected BankAccountCode value to be equals to bankAccountCode'
    );
    expect(await suppliersUpdatePage.getBankAccountNumberInput()).to.eq(
      'bankAccountNumber',
      'Expected BankAccountNumber value to be equals to bankAccountNumber'
    );
    expect(await suppliersUpdatePage.getBankInternationalCodeInput()).to.eq(
      'bankInternationalCode',
      'Expected BankInternationalCode value to be equals to bankInternationalCode'
    );
    expect(await suppliersUpdatePage.getPaymentDaysInput()).to.eq('5', 'Expected paymentDays value to be equals to 5');
    expect(await suppliersUpdatePage.getInternalCommentsInput()).to.eq(
      'internalComments',
      'Expected InternalComments value to be equals to internalComments'
    );
    expect(await suppliersUpdatePage.getPhoneNumberInput()).to.eq('phoneNumber', 'Expected PhoneNumber value to be equals to phoneNumber');
    expect(await suppliersUpdatePage.getFaxNumberInput()).to.eq('faxNumber', 'Expected FaxNumber value to be equals to faxNumber');
    expect(await suppliersUpdatePage.getWebsiteURLInput()).to.eq('websiteURL', 'Expected WebsiteURL value to be equals to websiteURL');
    expect(await suppliersUpdatePage.getWebServiceUrlInput()).to.eq(
      'webServiceUrl',
      'Expected WebServiceUrl value to be equals to webServiceUrl'
    );
    expect(await suppliersUpdatePage.getCreditRatingInput()).to.eq('5', 'Expected creditRating value to be equals to 5');
    const selectedActiveFlag = suppliersUpdatePage.getActiveFlagInput();
    if (await selectedActiveFlag.isSelected()) {
      await suppliersUpdatePage.getActiveFlagInput().click();
      expect(await suppliersUpdatePage.getActiveFlagInput().isSelected(), 'Expected activeFlag not to be selected').to.be.false;
    } else {
      await suppliersUpdatePage.getActiveFlagInput().click();
      expect(await suppliersUpdatePage.getActiveFlagInput().isSelected(), 'Expected activeFlag to be selected').to.be.true;
    }
    expect(await suppliersUpdatePage.getThumbnailUrlInput()).to.eq(
      'thumbnailUrl',
      'Expected ThumbnailUrl value to be equals to thumbnailUrl'
    );
    expect(await suppliersUpdatePage.getValidFromInput()).to.contain(
      '2001-01-01T02:30',
      'Expected validFrom value to be equals to 2000-12-31'
    );
    expect(await suppliersUpdatePage.getValidToInput()).to.contain('2001-01-01T02:30', 'Expected validTo value to be equals to 2000-12-31');
    await suppliersUpdatePage.save();
    expect(await suppliersUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await suppliersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Suppliers', async () => {
    const nbButtonsBeforeDelete = await suppliersComponentsPage.countDeleteButtons();
    await suppliersComponentsPage.clickOnLastDeleteButton();

    suppliersDeleteDialog = new SuppliersDeleteDialog();
    expect(await suppliersDeleteDialog.getDialogTitle()).to.eq('epmwebApp.suppliers.delete.question');
    await suppliersDeleteDialog.clickOnConfirmButton();

    expect(await suppliersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
