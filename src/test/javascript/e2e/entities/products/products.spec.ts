import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductsComponentsPage, ProductsDeleteDialog, ProductsUpdatePage } from './products.page-object';

const expect = chai.expect;

describe('Products e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productsComponentsPage: ProductsComponentsPage;
  let productsUpdatePage: ProductsUpdatePage;
  let productsDeleteDialog: ProductsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Products', async () => {
    await navBarPage.goToEntity('products');
    productsComponentsPage = new ProductsComponentsPage();
    await browser.wait(ec.visibilityOf(productsComponentsPage.title), 5000);
    expect(await productsComponentsPage.getTitle()).to.eq('epmwebApp.products.home.title');
  });

  it('should load create Products page', async () => {
    await productsComponentsPage.clickOnCreateButton();
    productsUpdatePage = new ProductsUpdatePage();
    expect(await productsUpdatePage.getPageTitle()).to.eq('epmwebApp.products.home.createOrEditLabel');
    await productsUpdatePage.cancel();
  });

  it('should create and save Products', async () => {
    const nbButtonsBeforeCreate = await productsComponentsPage.countDeleteButtons();

    await productsComponentsPage.clickOnCreateButton();
    await promise.all([
      productsUpdatePage.setNameInput('name'),
      productsUpdatePage.setHandleInput('handle'),
      productsUpdatePage.setProductNumberInput('productNumber'),
      productsUpdatePage.setSearchDetailsInput('searchDetails'),
      productsUpdatePage.setSellCountInput('5'),
      productsUpdatePage.setThumbnailListInput('thumbnailList'),
      productsUpdatePage.setLastEditedByInput('lastEditedBy'),
      productsUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      productsUpdatePage.productDocumentSelectLastOption(),
      productsUpdatePage.supplierSelectLastOption(),
      productsUpdatePage.productCategorySelectLastOption(),
      productsUpdatePage.productBrandSelectLastOption()
    ]);
    expect(await productsUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await productsUpdatePage.getHandleInput()).to.eq('handle', 'Expected Handle value to be equals to handle');
    expect(await productsUpdatePage.getProductNumberInput()).to.eq(
      'productNumber',
      'Expected ProductNumber value to be equals to productNumber'
    );
    expect(await productsUpdatePage.getSearchDetailsInput()).to.eq(
      'searchDetails',
      'Expected SearchDetails value to be equals to searchDetails'
    );
    expect(await productsUpdatePage.getSellCountInput()).to.eq('5', 'Expected sellCount value to be equals to 5');
    expect(await productsUpdatePage.getThumbnailListInput()).to.eq(
      'thumbnailList',
      'Expected ThumbnailList value to be equals to thumbnailList'
    );
    const selectedActiveInd = productsUpdatePage.getActiveIndInput();
    if (await selectedActiveInd.isSelected()) {
      await productsUpdatePage.getActiveIndInput().click();
      expect(await productsUpdatePage.getActiveIndInput().isSelected(), 'Expected activeInd not to be selected').to.be.false;
    } else {
      await productsUpdatePage.getActiveIndInput().click();
      expect(await productsUpdatePage.getActiveIndInput().isSelected(), 'Expected activeInd to be selected').to.be.true;
    }
    expect(await productsUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await productsUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await productsUpdatePage.save();
    expect(await productsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Products', async () => {
    const nbButtonsBeforeDelete = await productsComponentsPage.countDeleteButtons();
    await productsComponentsPage.clickOnLastDeleteButton();

    productsDeleteDialog = new ProductsDeleteDialog();
    expect(await productsDeleteDialog.getDialogTitle()).to.eq('epmwebApp.products.delete.question');
    await productsDeleteDialog.clickOnConfirmButton();

    expect(await productsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
