import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  SupplierImportedDocumentComponentsPage,
  SupplierImportedDocumentDeleteDialog,
  SupplierImportedDocumentUpdatePage
} from './supplier-imported-document.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('SupplierImportedDocument e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let supplierImportedDocumentComponentsPage: SupplierImportedDocumentComponentsPage;
  let supplierImportedDocumentUpdatePage: SupplierImportedDocumentUpdatePage;
  let supplierImportedDocumentDeleteDialog: SupplierImportedDocumentDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SupplierImportedDocuments', async () => {
    await navBarPage.goToEntity('supplier-imported-document');
    supplierImportedDocumentComponentsPage = new SupplierImportedDocumentComponentsPage();
    await browser.wait(ec.visibilityOf(supplierImportedDocumentComponentsPage.title), 5000);
    expect(await supplierImportedDocumentComponentsPage.getTitle()).to.eq('epmwebApp.supplierImportedDocument.home.title');
  });

  it('should load create SupplierImportedDocument page', async () => {
    await supplierImportedDocumentComponentsPage.clickOnCreateButton();
    supplierImportedDocumentUpdatePage = new SupplierImportedDocumentUpdatePage();
    expect(await supplierImportedDocumentUpdatePage.getPageTitle()).to.eq('epmwebApp.supplierImportedDocument.home.createOrEditLabel');
    await supplierImportedDocumentUpdatePage.cancel();
  });

  it('should create and save SupplierImportedDocuments', async () => {
    const nbButtonsBeforeCreate = await supplierImportedDocumentComponentsPage.countDeleteButtons();

    await supplierImportedDocumentComponentsPage.clickOnCreateButton();
    await promise.all([
      supplierImportedDocumentUpdatePage.setImportedTemplateInput(absolutePath),
      supplierImportedDocumentUpdatePage.setImportedFailedTemplateInput(absolutePath),
      supplierImportedDocumentUpdatePage.setLastEditedByInput('lastEditedBy'),
      supplierImportedDocumentUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      supplierImportedDocumentUpdatePage.uploadTransactionSelectLastOption()
    ]);
    expect(await supplierImportedDocumentUpdatePage.getImportedTemplateInput()).to.endsWith(
      fileNameToUpload,
      'Expected ImportedTemplate value to be end with ' + fileNameToUpload
    );
    expect(await supplierImportedDocumentUpdatePage.getImportedFailedTemplateInput()).to.endsWith(
      fileNameToUpload,
      'Expected ImportedFailedTemplate value to be end with ' + fileNameToUpload
    );
    expect(await supplierImportedDocumentUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await supplierImportedDocumentUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await supplierImportedDocumentUpdatePage.save();
    expect(await supplierImportedDocumentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await supplierImportedDocumentComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last SupplierImportedDocument', async () => {
    const nbButtonsBeforeDelete = await supplierImportedDocumentComponentsPage.countDeleteButtons();
    await supplierImportedDocumentComponentsPage.clickOnLastDeleteButton();

    supplierImportedDocumentDeleteDialog = new SupplierImportedDocumentDeleteDialog();
    expect(await supplierImportedDocumentDeleteDialog.getDialogTitle()).to.eq('epmwebApp.supplierImportedDocument.delete.question');
    await supplierImportedDocumentDeleteDialog.clickOnConfirmButton();

    expect(await supplierImportedDocumentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
