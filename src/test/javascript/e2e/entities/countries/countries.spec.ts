import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CountriesComponentsPage, CountriesDeleteDialog, CountriesUpdatePage } from './countries.page-object';

const expect = chai.expect;

describe('Countries e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let countriesComponentsPage: CountriesComponentsPage;
  let countriesUpdatePage: CountriesUpdatePage;
  let countriesDeleteDialog: CountriesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Countries', async () => {
    await navBarPage.goToEntity('countries');
    countriesComponentsPage = new CountriesComponentsPage();
    await browser.wait(ec.visibilityOf(countriesComponentsPage.title), 5000);
    expect(await countriesComponentsPage.getTitle()).to.eq('epmwebApp.countries.home.title');
  });

  it('should load create Countries page', async () => {
    await countriesComponentsPage.clickOnCreateButton();
    countriesUpdatePage = new CountriesUpdatePage();
    expect(await countriesUpdatePage.getPageTitle()).to.eq('epmwebApp.countries.home.createOrEditLabel');
    await countriesUpdatePage.cancel();
  });

  it('should create and save Countries', async () => {
    const nbButtonsBeforeCreate = await countriesComponentsPage.countDeleteButtons();

    await countriesComponentsPage.clickOnCreateButton();
    await promise.all([
      countriesUpdatePage.setNameInput('name'),
      countriesUpdatePage.setFormalNameInput('formalName'),
      countriesUpdatePage.setIsoAplha3CodeInput('isoAplha3Code'),
      countriesUpdatePage.setIsoNumericCodeInput('5'),
      countriesUpdatePage.setCountryTypeInput('countryType'),
      countriesUpdatePage.setLatestRecordedPopulationInput('5'),
      countriesUpdatePage.setContinentInput('continent'),
      countriesUpdatePage.setRegionInput('region'),
      countriesUpdatePage.setSubregionInput('subregion'),
      countriesUpdatePage.setBorderInput('border'),
      countriesUpdatePage.setValidFromInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      countriesUpdatePage.setValidToInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
    ]);
    expect(await countriesUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await countriesUpdatePage.getFormalNameInput()).to.eq('formalName', 'Expected FormalName value to be equals to formalName');
    expect(await countriesUpdatePage.getIsoAplha3CodeInput()).to.eq(
      'isoAplha3Code',
      'Expected IsoAplha3Code value to be equals to isoAplha3Code'
    );
    expect(await countriesUpdatePage.getIsoNumericCodeInput()).to.eq('5', 'Expected isoNumericCode value to be equals to 5');
    expect(await countriesUpdatePage.getCountryTypeInput()).to.eq('countryType', 'Expected CountryType value to be equals to countryType');
    expect(await countriesUpdatePage.getLatestRecordedPopulationInput()).to.eq(
      '5',
      'Expected latestRecordedPopulation value to be equals to 5'
    );
    expect(await countriesUpdatePage.getContinentInput()).to.eq('continent', 'Expected Continent value to be equals to continent');
    expect(await countriesUpdatePage.getRegionInput()).to.eq('region', 'Expected Region value to be equals to region');
    expect(await countriesUpdatePage.getSubregionInput()).to.eq('subregion', 'Expected Subregion value to be equals to subregion');
    expect(await countriesUpdatePage.getBorderInput()).to.eq('border', 'Expected Border value to be equals to border');
    expect(await countriesUpdatePage.getValidFromInput()).to.contain(
      '2001-01-01T02:30',
      'Expected validFrom value to be equals to 2000-12-31'
    );
    expect(await countriesUpdatePage.getValidToInput()).to.contain('2001-01-01T02:30', 'Expected validTo value to be equals to 2000-12-31');
    await countriesUpdatePage.save();
    expect(await countriesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await countriesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Countries', async () => {
    const nbButtonsBeforeDelete = await countriesComponentsPage.countDeleteButtons();
    await countriesComponentsPage.clickOnLastDeleteButton();

    countriesDeleteDialog = new CountriesDeleteDialog();
    expect(await countriesDeleteDialog.getDialogTitle()).to.eq('epmwebApp.countries.delete.question');
    await countriesDeleteDialog.clickOnConfirmButton();

    expect(await countriesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
