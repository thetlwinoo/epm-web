import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PeopleComponentsPage,
  /* PeopleDeleteDialog,
   */ PeopleUpdatePage
} from './people.page-object';

const expect = chai.expect;

describe('People e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let peopleComponentsPage: PeopleComponentsPage;
  let peopleUpdatePage: PeopleUpdatePage;
  /* let peopleDeleteDialog: PeopleDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load People', async () => {
    await navBarPage.goToEntity('people');
    peopleComponentsPage = new PeopleComponentsPage();
    await browser.wait(ec.visibilityOf(peopleComponentsPage.title), 5000);
    expect(await peopleComponentsPage.getTitle()).to.eq('epmwebApp.people.home.title');
  });

  it('should load create People page', async () => {
    await peopleComponentsPage.clickOnCreateButton();
    peopleUpdatePage = new PeopleUpdatePage();
    expect(await peopleUpdatePage.getPageTitle()).to.eq('epmwebApp.people.home.createOrEditLabel');
    await peopleUpdatePage.cancel();
  });

  /*  it('should create and save People', async () => {
        const nbButtonsBeforeCreate = await peopleComponentsPage.countDeleteButtons();

        await peopleComponentsPage.clickOnCreateButton();
        await promise.all([
            peopleUpdatePage.setFullNameInput('fullName'),
            peopleUpdatePage.setPreferredNameInput('preferredName'),
            peopleUpdatePage.setSearchNameInput('searchName'),
            peopleUpdatePage.genderSelectLastOption(),
            peopleUpdatePage.setLogonNameInput('logonName'),
            peopleUpdatePage.setEmailPromotionInput('5'),
            peopleUpdatePage.setUserPreferencesInput('userPreferences'),
            peopleUpdatePage.setPhoneNumberInput('phoneNumber'),
            peopleUpdatePage.setEmailAddressInput('&gt;9W#WPG?C/du5UZB;^:FEel++%)VmVi[&amp;gSA5J?yFf:GnC(7F%.~&gt;l2c$KZeBD`nL}ab48$}ia2![lT@I0b1&#39;LJinr6HNQc&#39;%&#34;+F&gt;uH+W&lt;Y;&#39;QmLwEl;7W{MO=:)T2L;pMvdDVz~U6!oCb|Tqt&#39;E}K(OOVuX?9LQRrlkMkm*KN?4L~NQ`W.GnY'),
            peopleUpdatePage.setPhotoInput('photo'),
            peopleUpdatePage.setCustomFieldsInput('customFields'),
            peopleUpdatePage.setOtherLanguagesInput('otherLanguages'),
            peopleUpdatePage.setValidFromInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            peopleUpdatePage.setValidToInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            peopleUpdatePage.userSelectLastOption(),
        ]);
        expect(await peopleUpdatePage.getFullNameInput()).to.eq('fullName', 'Expected FullName value to be equals to fullName');
        expect(await peopleUpdatePage.getPreferredNameInput()).to.eq('preferredName', 'Expected PreferredName value to be equals to preferredName');
        expect(await peopleUpdatePage.getSearchNameInput()).to.eq('searchName', 'Expected SearchName value to be equals to searchName');
        const selectedIsPermittedToLogon = peopleUpdatePage.getIsPermittedToLogonInput();
        if (await selectedIsPermittedToLogon.isSelected()) {
            await peopleUpdatePage.getIsPermittedToLogonInput().click();
            expect(await peopleUpdatePage.getIsPermittedToLogonInput().isSelected(), 'Expected isPermittedToLogon not to be selected').to.be.false;
        } else {
            await peopleUpdatePage.getIsPermittedToLogonInput().click();
            expect(await peopleUpdatePage.getIsPermittedToLogonInput().isSelected(), 'Expected isPermittedToLogon to be selected').to.be.true;
        }
        expect(await peopleUpdatePage.getLogonNameInput()).to.eq('logonName', 'Expected LogonName value to be equals to logonName');
        const selectedIsExternalLogonProvider = peopleUpdatePage.getIsExternalLogonProviderInput();
        if (await selectedIsExternalLogonProvider.isSelected()) {
            await peopleUpdatePage.getIsExternalLogonProviderInput().click();
            expect(await peopleUpdatePage.getIsExternalLogonProviderInput().isSelected(), 'Expected isExternalLogonProvider not to be selected').to.be.false;
        } else {
            await peopleUpdatePage.getIsExternalLogonProviderInput().click();
            expect(await peopleUpdatePage.getIsExternalLogonProviderInput().isSelected(), 'Expected isExternalLogonProvider to be selected').to.be.true;
        }
        const selectedIsSystemUser = peopleUpdatePage.getIsSystemUserInput();
        if (await selectedIsSystemUser.isSelected()) {
            await peopleUpdatePage.getIsSystemUserInput().click();
            expect(await peopleUpdatePage.getIsSystemUserInput().isSelected(), 'Expected isSystemUser not to be selected').to.be.false;
        } else {
            await peopleUpdatePage.getIsSystemUserInput().click();
            expect(await peopleUpdatePage.getIsSystemUserInput().isSelected(), 'Expected isSystemUser to be selected').to.be.true;
        }
        const selectedIsEmployee = peopleUpdatePage.getIsEmployeeInput();
        if (await selectedIsEmployee.isSelected()) {
            await peopleUpdatePage.getIsEmployeeInput().click();
            expect(await peopleUpdatePage.getIsEmployeeInput().isSelected(), 'Expected isEmployee not to be selected').to.be.false;
        } else {
            await peopleUpdatePage.getIsEmployeeInput().click();
            expect(await peopleUpdatePage.getIsEmployeeInput().isSelected(), 'Expected isEmployee to be selected').to.be.true;
        }
        const selectedIsSalesPerson = peopleUpdatePage.getIsSalesPersonInput();
        if (await selectedIsSalesPerson.isSelected()) {
            await peopleUpdatePage.getIsSalesPersonInput().click();
            expect(await peopleUpdatePage.getIsSalesPersonInput().isSelected(), 'Expected isSalesPerson not to be selected').to.be.false;
        } else {
            await peopleUpdatePage.getIsSalesPersonInput().click();
            expect(await peopleUpdatePage.getIsSalesPersonInput().isSelected(), 'Expected isSalesPerson to be selected').to.be.true;
        }
        const selectedIsGuestUser = peopleUpdatePage.getIsGuestUserInput();
        if (await selectedIsGuestUser.isSelected()) {
            await peopleUpdatePage.getIsGuestUserInput().click();
            expect(await peopleUpdatePage.getIsGuestUserInput().isSelected(), 'Expected isGuestUser not to be selected').to.be.false;
        } else {
            await peopleUpdatePage.getIsGuestUserInput().click();
            expect(await peopleUpdatePage.getIsGuestUserInput().isSelected(), 'Expected isGuestUser to be selected').to.be.true;
        }
        expect(await peopleUpdatePage.getEmailPromotionInput()).to.eq('5', 'Expected emailPromotion value to be equals to 5');
        expect(await peopleUpdatePage.getUserPreferencesInput()).to.eq('userPreferences', 'Expected UserPreferences value to be equals to userPreferences');
        expect(await peopleUpdatePage.getPhoneNumberInput()).to.eq('phoneNumber', 'Expected PhoneNumber value to be equals to phoneNumber');
        expect(await peopleUpdatePage.getEmailAddressInput()).to.eq('&gt;9W#WPG?C/du5UZB;^:FEel++%)VmVi[&amp;gSA5J?yFf:GnC(7F%.~&gt;l2c$KZeBD`nL}ab48$}ia2![lT@I0b1&#39;LJinr6HNQc&#39;%&#34;+F&gt;uH+W&lt;Y;&#39;QmLwEl;7W{MO=:)T2L;pMvdDVz~U6!oCb|Tqt&#39;E}K(OOVuX?9LQRrlkMkm*KN?4L~NQ`W.GnY', 'Expected EmailAddress value to be equals to &gt;9W#WPG?C/du5UZB;^:FEel++%)VmVi[&amp;gSA5J?yFf:GnC(7F%.~&gt;l2c$KZeBD`nL}ab48$}ia2![lT@I0b1&#39;LJinr6HNQc&#39;%&#34;+F&gt;uH+W&lt;Y;&#39;QmLwEl;7W{MO=:)T2L;pMvdDVz~U6!oCb|Tqt&#39;E}K(OOVuX?9LQRrlkMkm*KN?4L~NQ`W.GnY');
        expect(await peopleUpdatePage.getPhotoInput()).to.eq('photo', 'Expected Photo value to be equals to photo');
        expect(await peopleUpdatePage.getCustomFieldsInput()).to.eq('customFields', 'Expected CustomFields value to be equals to customFields');
        expect(await peopleUpdatePage.getOtherLanguagesInput()).to.eq('otherLanguages', 'Expected OtherLanguages value to be equals to otherLanguages');
        expect(await peopleUpdatePage.getValidFromInput()).to.contain('2001-01-01T02:30', 'Expected validFrom value to be equals to 2000-12-31');
        expect(await peopleUpdatePage.getValidToInput()).to.contain('2001-01-01T02:30', 'Expected validTo value to be equals to 2000-12-31');
        await peopleUpdatePage.save();
        expect(await peopleUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await peopleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last People', async () => {
        const nbButtonsBeforeDelete = await peopleComponentsPage.countDeleteButtons();
        await peopleComponentsPage.clickOnLastDeleteButton();

        peopleDeleteDialog = new PeopleDeleteDialog();
        expect(await peopleDeleteDialog.getDialogTitle())
            .to.eq('epmwebApp.people.delete.question');
        await peopleDeleteDialog.clickOnConfirmButton();

        expect(await peopleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
