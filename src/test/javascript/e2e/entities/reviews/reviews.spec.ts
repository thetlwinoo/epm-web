import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ReviewsComponentsPage, ReviewsDeleteDialog, ReviewsUpdatePage } from './reviews.page-object';

const expect = chai.expect;

describe('Reviews e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let reviewsComponentsPage: ReviewsComponentsPage;
  let reviewsUpdatePage: ReviewsUpdatePage;
  let reviewsDeleteDialog: ReviewsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Reviews', async () => {
    await navBarPage.goToEntity('reviews');
    reviewsComponentsPage = new ReviewsComponentsPage();
    await browser.wait(ec.visibilityOf(reviewsComponentsPage.title), 5000);
    expect(await reviewsComponentsPage.getTitle()).to.eq('epmwebApp.reviews.home.title');
  });

  it('should load create Reviews page', async () => {
    await reviewsComponentsPage.clickOnCreateButton();
    reviewsUpdatePage = new ReviewsUpdatePage();
    expect(await reviewsUpdatePage.getPageTitle()).to.eq('epmwebApp.reviews.home.createOrEditLabel');
    await reviewsUpdatePage.cancel();
  });

  it('should create and save Reviews', async () => {
    const nbButtonsBeforeCreate = await reviewsComponentsPage.countDeleteButtons();

    await reviewsComponentsPage.clickOnCreateButton();
    await promise.all([
      reviewsUpdatePage.setNameInput('name'),
      reviewsUpdatePage.setEmailAddressInput(
        'v)ozg7p(&amp;&amp;H0vXQ!*t}!C|z|buac8eQSQ;&gt;Mo/=S0yY_fLSIn%n![n[dtN2D#v4%[D/&amp;I3~M,#Qkb^%%yNY}O%5@Z&lt;h9;?SZp&gt;WM32RoUJ*w9csW~29?VA85M=!bflHl;&#39;0&#34;,*,{hs8Z3;YMB)&#34;up::U&amp;K,Y-qR$&#34;0.E(rPLh76.#+w%PbXFD&gt;dv{JlLp=&amp;0.GP{nwb/Dlgnhm]Vu#nKuwo0.&amp;VOC(O_m$V.f;.K__o*7&amp;&#34;&gt;FVT0)0Hi{{$~'
      ),
      reviewsUpdatePage.setReviewDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      reviewsUpdatePage.setOverAllSellerRatingInput('5'),
      reviewsUpdatePage.setOverAllSellerReviewInput('overAllSellerReview'),
      reviewsUpdatePage.setOverAllDeliveryRatingInput('5'),
      reviewsUpdatePage.setOverAllDeliveryReviewInput('overAllDeliveryReview'),
      reviewsUpdatePage.setLastEditedByInput('lastEditedBy'),
      reviewsUpdatePage.setLastEditedWhenInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
    ]);
    expect(await reviewsUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await reviewsUpdatePage.getEmailAddressInput()).to.eq(
      'v)ozg7p(&amp;&amp;H0vXQ!*t}!C|z|buac8eQSQ;&gt;Mo/=S0yY_fLSIn%n![n[dtN2D#v4%[D/&amp;I3~M,#Qkb^%%yNY}O%5@Z&lt;h9;?SZp&gt;WM32RoUJ*w9csW~29?VA85M=!bflHl;&#39;0&#34;,*,{hs8Z3;YMB)&#34;up::U&amp;K,Y-qR$&#34;0.E(rPLh76.#+w%PbXFD&gt;dv{JlLp=&amp;0.GP{nwb/Dlgnhm]Vu#nKuwo0.&amp;VOC(O_m$V.f;.K__o*7&amp;&#34;&gt;FVT0)0Hi{{$~',
      'Expected EmailAddress value to be equals to v)ozg7p(&amp;&amp;H0vXQ!*t}!C|z|buac8eQSQ;&gt;Mo/=S0yY_fLSIn%n![n[dtN2D#v4%[D/&amp;I3~M,#Qkb^%%yNY}O%5@Z&lt;h9;?SZp&gt;WM32RoUJ*w9csW~29?VA85M=!bflHl;&#39;0&#34;,*,{hs8Z3;YMB)&#34;up::U&amp;K,Y-qR$&#34;0.E(rPLh76.#+w%PbXFD&gt;dv{JlLp=&amp;0.GP{nwb/Dlgnhm]Vu#nKuwo0.&amp;VOC(O_m$V.f;.K__o*7&amp;&#34;&gt;FVT0)0Hi{{$~'
    );
    expect(await reviewsUpdatePage.getReviewDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected reviewDate value to be equals to 2000-12-31'
    );
    expect(await reviewsUpdatePage.getOverAllSellerRatingInput()).to.eq('5', 'Expected overAllSellerRating value to be equals to 5');
    expect(await reviewsUpdatePage.getOverAllSellerReviewInput()).to.eq(
      'overAllSellerReview',
      'Expected OverAllSellerReview value to be equals to overAllSellerReview'
    );
    expect(await reviewsUpdatePage.getOverAllDeliveryRatingInput()).to.eq('5', 'Expected overAllDeliveryRating value to be equals to 5');
    expect(await reviewsUpdatePage.getOverAllDeliveryReviewInput()).to.eq(
      'overAllDeliveryReview',
      'Expected OverAllDeliveryReview value to be equals to overAllDeliveryReview'
    );
    const selectedReviewAsAnonymous = reviewsUpdatePage.getReviewAsAnonymousInput();
    if (await selectedReviewAsAnonymous.isSelected()) {
      await reviewsUpdatePage.getReviewAsAnonymousInput().click();
      expect(await reviewsUpdatePage.getReviewAsAnonymousInput().isSelected(), 'Expected reviewAsAnonymous not to be selected').to.be.false;
    } else {
      await reviewsUpdatePage.getReviewAsAnonymousInput().click();
      expect(await reviewsUpdatePage.getReviewAsAnonymousInput().isSelected(), 'Expected reviewAsAnonymous to be selected').to.be.true;
    }
    const selectedCompletedReview = reviewsUpdatePage.getCompletedReviewInput();
    if (await selectedCompletedReview.isSelected()) {
      await reviewsUpdatePage.getCompletedReviewInput().click();
      expect(await reviewsUpdatePage.getCompletedReviewInput().isSelected(), 'Expected completedReview not to be selected').to.be.false;
    } else {
      await reviewsUpdatePage.getCompletedReviewInput().click();
      expect(await reviewsUpdatePage.getCompletedReviewInput().isSelected(), 'Expected completedReview to be selected').to.be.true;
    }
    expect(await reviewsUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await reviewsUpdatePage.getLastEditedWhenInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await reviewsUpdatePage.save();
    expect(await reviewsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await reviewsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Reviews', async () => {
    const nbButtonsBeforeDelete = await reviewsComponentsPage.countDeleteButtons();
    await reviewsComponentsPage.clickOnLastDeleteButton();

    reviewsDeleteDialog = new ReviewsDeleteDialog();
    expect(await reviewsDeleteDialog.getDialogTitle()).to.eq('epmwebApp.reviews.delete.question');
    await reviewsDeleteDialog.clickOnConfirmButton();

    expect(await reviewsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
