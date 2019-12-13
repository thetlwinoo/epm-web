import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CitiesComponentsPage, CitiesDeleteDialog, CitiesUpdatePage } from './cities.page-object';

const expect = chai.expect;

describe('Cities e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let citiesComponentsPage: CitiesComponentsPage;
  let citiesUpdatePage: CitiesUpdatePage;
  let citiesDeleteDialog: CitiesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Cities', async () => {
    await navBarPage.goToEntity('cities');
    citiesComponentsPage = new CitiesComponentsPage();
    await browser.wait(ec.visibilityOf(citiesComponentsPage.title), 5000);
    expect(await citiesComponentsPage.getTitle()).to.eq('epmwebApp.cities.home.title');
  });

  it('should load create Cities page', async () => {
    await citiesComponentsPage.clickOnCreateButton();
    citiesUpdatePage = new CitiesUpdatePage();
    expect(await citiesUpdatePage.getPageTitle()).to.eq('epmwebApp.cities.home.createOrEditLabel');
    await citiesUpdatePage.cancel();
  });

  it('should create and save Cities', async () => {
    const nbButtonsBeforeCreate = await citiesComponentsPage.countDeleteButtons();

    await citiesComponentsPage.clickOnCreateButton();
    await promise.all([
      citiesUpdatePage.setNameInput('name'),
      citiesUpdatePage.setLocationInput('location'),
      citiesUpdatePage.setLatestRecordedPopulationInput('5'),
      citiesUpdatePage.setValidFromInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      citiesUpdatePage.setValidToInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      citiesUpdatePage.stateProvinceSelectLastOption()
    ]);
    expect(await citiesUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await citiesUpdatePage.getLocationInput()).to.eq('location', 'Expected Location value to be equals to location');
    expect(await citiesUpdatePage.getLatestRecordedPopulationInput()).to.eq(
      '5',
      'Expected latestRecordedPopulation value to be equals to 5'
    );
    expect(await citiesUpdatePage.getValidFromInput()).to.contain(
      '2001-01-01T02:30',
      'Expected validFrom value to be equals to 2000-12-31'
    );
    expect(await citiesUpdatePage.getValidToInput()).to.contain('2001-01-01T02:30', 'Expected validTo value to be equals to 2000-12-31');
    await citiesUpdatePage.save();
    expect(await citiesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await citiesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Cities', async () => {
    const nbButtonsBeforeDelete = await citiesComponentsPage.countDeleteButtons();
    await citiesComponentsPage.clickOnLastDeleteButton();

    citiesDeleteDialog = new CitiesDeleteDialog();
    expect(await citiesDeleteDialog.getDialogTitle()).to.eq('epmwebApp.cities.delete.question');
    await citiesDeleteDialog.clickOnConfirmButton();

    expect(await citiesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
