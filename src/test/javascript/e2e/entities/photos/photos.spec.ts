import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PhotosComponentsPage, PhotosDeleteDialog, PhotosUpdatePage } from './photos.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Photos e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let photosComponentsPage: PhotosComponentsPage;
  let photosUpdatePage: PhotosUpdatePage;
  let photosDeleteDialog: PhotosDeleteDialog;
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

  it('should load Photos', async () => {
    await navBarPage.goToEntity('photos');
    photosComponentsPage = new PhotosComponentsPage();
    await browser.wait(ec.visibilityOf(photosComponentsPage.title), 5000);
    expect(await photosComponentsPage.getTitle()).to.eq('epmwebApp.photos.home.title');
  });

  it('should load create Photos page', async () => {
    await photosComponentsPage.clickOnCreateButton();
    photosUpdatePage = new PhotosUpdatePage();
    expect(await photosUpdatePage.getPageTitle()).to.eq('epmwebApp.photos.home.createOrEditLabel');
    await photosUpdatePage.cancel();
  });

  it('should create and save Photos', async () => {
    const nbButtonsBeforeCreate = await photosComponentsPage.countDeleteButtons();

    await photosComponentsPage.clickOnCreateButton();
    await promise.all([
      photosUpdatePage.setThumbnailPhotoInput('thumbnailPhoto'),
      photosUpdatePage.setOriginalPhotoInput('originalPhoto'),
      photosUpdatePage.setBannerTallPhotoInput('bannerTallPhoto'),
      photosUpdatePage.setBannerWidePhotoInput('bannerWidePhoto'),
      photosUpdatePage.setCirclePhotoInput('circlePhoto'),
      photosUpdatePage.setSharpenedPhotoInput('sharpenedPhoto'),
      photosUpdatePage.setSquarePhotoInput('squarePhoto'),
      photosUpdatePage.setWatermarkPhotoInput('watermarkPhoto'),
      photosUpdatePage.setThumbnailPhotoBlobInput(absolutePath),
      photosUpdatePage.setOriginalPhotoBlobInput(absolutePath),
      photosUpdatePage.setBannerTallPhotoBlobInput(absolutePath),
      photosUpdatePage.setBannerWidePhotoBlobInput(absolutePath),
      photosUpdatePage.setCirclePhotoBlobInput(absolutePath),
      photosUpdatePage.setSharpenedPhotoBlobInput(absolutePath),
      photosUpdatePage.setSquarePhotoBlobInput(absolutePath),
      photosUpdatePage.setWatermarkPhotoBlobInput(absolutePath),
      photosUpdatePage.setPriorityInput('5'),
      photosUpdatePage.stockItemSelectLastOption(),
      photosUpdatePage.productCategorySelectLastOption()
    ]);
    expect(await photosUpdatePage.getThumbnailPhotoInput()).to.eq(
      'thumbnailPhoto',
      'Expected ThumbnailPhoto value to be equals to thumbnailPhoto'
    );
    expect(await photosUpdatePage.getOriginalPhotoInput()).to.eq(
      'originalPhoto',
      'Expected OriginalPhoto value to be equals to originalPhoto'
    );
    expect(await photosUpdatePage.getBannerTallPhotoInput()).to.eq(
      'bannerTallPhoto',
      'Expected BannerTallPhoto value to be equals to bannerTallPhoto'
    );
    expect(await photosUpdatePage.getBannerWidePhotoInput()).to.eq(
      'bannerWidePhoto',
      'Expected BannerWidePhoto value to be equals to bannerWidePhoto'
    );
    expect(await photosUpdatePage.getCirclePhotoInput()).to.eq('circlePhoto', 'Expected CirclePhoto value to be equals to circlePhoto');
    expect(await photosUpdatePage.getSharpenedPhotoInput()).to.eq(
      'sharpenedPhoto',
      'Expected SharpenedPhoto value to be equals to sharpenedPhoto'
    );
    expect(await photosUpdatePage.getSquarePhotoInput()).to.eq('squarePhoto', 'Expected SquarePhoto value to be equals to squarePhoto');
    expect(await photosUpdatePage.getWatermarkPhotoInput()).to.eq(
      'watermarkPhoto',
      'Expected WatermarkPhoto value to be equals to watermarkPhoto'
    );
    expect(await photosUpdatePage.getThumbnailPhotoBlobInput()).to.endsWith(
      fileNameToUpload,
      'Expected ThumbnailPhotoBlob value to be end with ' + fileNameToUpload
    );
    expect(await photosUpdatePage.getOriginalPhotoBlobInput()).to.endsWith(
      fileNameToUpload,
      'Expected OriginalPhotoBlob value to be end with ' + fileNameToUpload
    );
    expect(await photosUpdatePage.getBannerTallPhotoBlobInput()).to.endsWith(
      fileNameToUpload,
      'Expected BannerTallPhotoBlob value to be end with ' + fileNameToUpload
    );
    expect(await photosUpdatePage.getBannerWidePhotoBlobInput()).to.endsWith(
      fileNameToUpload,
      'Expected BannerWidePhotoBlob value to be end with ' + fileNameToUpload
    );
    expect(await photosUpdatePage.getCirclePhotoBlobInput()).to.endsWith(
      fileNameToUpload,
      'Expected CirclePhotoBlob value to be end with ' + fileNameToUpload
    );
    expect(await photosUpdatePage.getSharpenedPhotoBlobInput()).to.endsWith(
      fileNameToUpload,
      'Expected SharpenedPhotoBlob value to be end with ' + fileNameToUpload
    );
    expect(await photosUpdatePage.getSquarePhotoBlobInput()).to.endsWith(
      fileNameToUpload,
      'Expected SquarePhotoBlob value to be end with ' + fileNameToUpload
    );
    expect(await photosUpdatePage.getWatermarkPhotoBlobInput()).to.endsWith(
      fileNameToUpload,
      'Expected WatermarkPhotoBlob value to be end with ' + fileNameToUpload
    );
    expect(await photosUpdatePage.getPriorityInput()).to.eq('5', 'Expected priority value to be equals to 5');
    const selectedDefaultInd = photosUpdatePage.getDefaultIndInput();
    if (await selectedDefaultInd.isSelected()) {
      await photosUpdatePage.getDefaultIndInput().click();
      expect(await photosUpdatePage.getDefaultIndInput().isSelected(), 'Expected defaultInd not to be selected').to.be.false;
    } else {
      await photosUpdatePage.getDefaultIndInput().click();
      expect(await photosUpdatePage.getDefaultIndInput().isSelected(), 'Expected defaultInd to be selected').to.be.true;
    }
    await photosUpdatePage.save();
    expect(await photosUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await photosComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Photos', async () => {
    const nbButtonsBeforeDelete = await photosComponentsPage.countDeleteButtons();
    await photosComponentsPage.clickOnLastDeleteButton();

    photosDeleteDialog = new PhotosDeleteDialog();
    expect(await photosDeleteDialog.getDialogTitle()).to.eq('epmwebApp.photos.delete.question');
    await photosDeleteDialog.clickOnConfirmButton();

    expect(await photosComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
