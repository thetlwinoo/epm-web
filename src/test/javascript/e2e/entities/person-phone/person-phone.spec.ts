import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PersonPhoneComponentsPage, PersonPhoneDeleteDialog, PersonPhoneUpdatePage } from './person-phone.page-object';

const expect = chai.expect;

describe('PersonPhone e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let personPhoneComponentsPage: PersonPhoneComponentsPage;
  let personPhoneUpdatePage: PersonPhoneUpdatePage;
  let personPhoneDeleteDialog: PersonPhoneDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PersonPhones', async () => {
    await navBarPage.goToEntity('person-phone');
    personPhoneComponentsPage = new PersonPhoneComponentsPage();
    await browser.wait(ec.visibilityOf(personPhoneComponentsPage.title), 5000);
    expect(await personPhoneComponentsPage.getTitle()).to.eq('epmwebApp.personPhone.home.title');
  });

  it('should load create PersonPhone page', async () => {
    await personPhoneComponentsPage.clickOnCreateButton();
    personPhoneUpdatePage = new PersonPhoneUpdatePage();
    expect(await personPhoneUpdatePage.getPageTitle()).to.eq('epmwebApp.personPhone.home.createOrEditLabel');
    await personPhoneUpdatePage.cancel();
  });

  it('should create and save PersonPhones', async () => {
    const nbButtonsBeforeCreate = await personPhoneComponentsPage.countDeleteButtons();

    await personPhoneComponentsPage.clickOnCreateButton();
    await promise.all([
      personPhoneUpdatePage.setPhoneNumberInput('phoneNumber'),
      personPhoneUpdatePage.personSelectLastOption(),
      personPhoneUpdatePage.phoneNumberTypeSelectLastOption()
    ]);
    expect(await personPhoneUpdatePage.getPhoneNumberInput()).to.eq(
      'phoneNumber',
      'Expected PhoneNumber value to be equals to phoneNumber'
    );
    const selectedDefaultInd = personPhoneUpdatePage.getDefaultIndInput();
    if (await selectedDefaultInd.isSelected()) {
      await personPhoneUpdatePage.getDefaultIndInput().click();
      expect(await personPhoneUpdatePage.getDefaultIndInput().isSelected(), 'Expected defaultInd not to be selected').to.be.false;
    } else {
      await personPhoneUpdatePage.getDefaultIndInput().click();
      expect(await personPhoneUpdatePage.getDefaultIndInput().isSelected(), 'Expected defaultInd to be selected').to.be.true;
    }
    const selectedActiveInd = personPhoneUpdatePage.getActiveIndInput();
    if (await selectedActiveInd.isSelected()) {
      await personPhoneUpdatePage.getActiveIndInput().click();
      expect(await personPhoneUpdatePage.getActiveIndInput().isSelected(), 'Expected activeInd not to be selected').to.be.false;
    } else {
      await personPhoneUpdatePage.getActiveIndInput().click();
      expect(await personPhoneUpdatePage.getActiveIndInput().isSelected(), 'Expected activeInd to be selected').to.be.true;
    }
    await personPhoneUpdatePage.save();
    expect(await personPhoneUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await personPhoneComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PersonPhone', async () => {
    const nbButtonsBeforeDelete = await personPhoneComponentsPage.countDeleteButtons();
    await personPhoneComponentsPage.clickOnLastDeleteButton();

    personPhoneDeleteDialog = new PersonPhoneDeleteDialog();
    expect(await personPhoneDeleteDialog.getDialogTitle()).to.eq('epmwebApp.personPhone.delete.question');
    await personPhoneDeleteDialog.clickOnConfirmButton();

    expect(await personPhoneComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
