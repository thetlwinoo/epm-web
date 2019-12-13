import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  UploadTransactionsComponentsPage,
  UploadTransactionsDeleteDialog,
  UploadTransactionsUpdatePage
} from './upload-transactions.page-object';

const expect = chai.expect;

describe('UploadTransactions e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let uploadTransactionsComponentsPage: UploadTransactionsComponentsPage;
  let uploadTransactionsUpdatePage: UploadTransactionsUpdatePage;
  let uploadTransactionsDeleteDialog: UploadTransactionsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UploadTransactions', async () => {
    await navBarPage.goToEntity('upload-transactions');
    uploadTransactionsComponentsPage = new UploadTransactionsComponentsPage();
    await browser.wait(ec.visibilityOf(uploadTransactionsComponentsPage.title), 5000);
    expect(await uploadTransactionsComponentsPage.getTitle()).to.eq('epmwebApp.uploadTransactions.home.title');
  });

  it('should load create UploadTransactions page', async () => {
    await uploadTransactionsComponentsPage.clickOnCreateButton();
    uploadTransactionsUpdatePage = new UploadTransactionsUpdatePage();
    expect(await uploadTransactionsUpdatePage.getPageTitle()).to.eq('epmwebApp.uploadTransactions.home.createOrEditLabel');
    await uploadTransactionsUpdatePage.cancel();
  });

  it('should create and save UploadTransactions', async () => {
    const nbButtonsBeforeCreate = await uploadTransactionsComponentsPage.countDeleteButtons();

    await uploadTransactionsComponentsPage.clickOnCreateButton();
    await promise.all([
      uploadTransactionsUpdatePage.setFileNameInput('fileName'),
      uploadTransactionsUpdatePage.setTemplateUrlInput('templateUrl'),
      uploadTransactionsUpdatePage.setStatusInput('5'),
      uploadTransactionsUpdatePage.setGeneratedCodeInput('generatedCode'),
      uploadTransactionsUpdatePage.setLastEditedByInput('lastEditedBy'),
      uploadTransactionsUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      uploadTransactionsUpdatePage.supplierSelectLastOption(),
      uploadTransactionsUpdatePage.actionTypeSelectLastOption()
    ]);
    expect(await uploadTransactionsUpdatePage.getFileNameInput()).to.eq('fileName', 'Expected FileName value to be equals to fileName');
    expect(await uploadTransactionsUpdatePage.getTemplateUrlInput()).to.eq(
      'templateUrl',
      'Expected TemplateUrl value to be equals to templateUrl'
    );
    expect(await uploadTransactionsUpdatePage.getStatusInput()).to.eq('5', 'Expected status value to be equals to 5');
    expect(await uploadTransactionsUpdatePage.getGeneratedCodeInput()).to.eq(
      'generatedCode',
      'Expected GeneratedCode value to be equals to generatedCode'
    );
    expect(await uploadTransactionsUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await uploadTransactionsUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await uploadTransactionsUpdatePage.save();
    expect(await uploadTransactionsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await uploadTransactionsComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last UploadTransactions', async () => {
    const nbButtonsBeforeDelete = await uploadTransactionsComponentsPage.countDeleteButtons();
    await uploadTransactionsComponentsPage.clickOnLastDeleteButton();

    uploadTransactionsDeleteDialog = new UploadTransactionsDeleteDialog();
    expect(await uploadTransactionsDeleteDialog.getDialogTitle()).to.eq('epmwebApp.uploadTransactions.delete.question');
    await uploadTransactionsDeleteDialog.clickOnConfirmButton();

    expect(await uploadTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
