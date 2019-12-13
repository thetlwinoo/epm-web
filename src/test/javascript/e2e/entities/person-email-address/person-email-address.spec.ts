import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PersonEmailAddressComponentsPage,
  PersonEmailAddressDeleteDialog,
  PersonEmailAddressUpdatePage
} from './person-email-address.page-object';

const expect = chai.expect;

describe('PersonEmailAddress e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let personEmailAddressComponentsPage: PersonEmailAddressComponentsPage;
  let personEmailAddressUpdatePage: PersonEmailAddressUpdatePage;
  let personEmailAddressDeleteDialog: PersonEmailAddressDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PersonEmailAddresses', async () => {
    await navBarPage.goToEntity('person-email-address');
    personEmailAddressComponentsPage = new PersonEmailAddressComponentsPage();
    await browser.wait(ec.visibilityOf(personEmailAddressComponentsPage.title), 5000);
    expect(await personEmailAddressComponentsPage.getTitle()).to.eq('epmwebApp.personEmailAddress.home.title');
  });

  it('should load create PersonEmailAddress page', async () => {
    await personEmailAddressComponentsPage.clickOnCreateButton();
    personEmailAddressUpdatePage = new PersonEmailAddressUpdatePage();
    expect(await personEmailAddressUpdatePage.getPageTitle()).to.eq('epmwebApp.personEmailAddress.home.createOrEditLabel');
    await personEmailAddressUpdatePage.cancel();
  });

  it('should create and save PersonEmailAddresses', async () => {
    const nbButtonsBeforeCreate = await personEmailAddressComponentsPage.countDeleteButtons();

    await personEmailAddressComponentsPage.clickOnCreateButton();
    await promise.all([
      personEmailAddressUpdatePage.setEmailAddressInput(
        '8&lt;^fjVdE@s+&lt;d`gzyQ~s-EV[l_#sKD&#34;jm`Uir1GIIFqnd#&amp;^Lawe5h;b+M$fpZP*P9/!N:Xn8sf#kfj#Qt|tY[5f&gt;{p;k-3dE8Amk{.&#39;x&amp;9?mK.T`%w#*CV5&gt;^HF|&#39;}bJ#unns8&amp;&lt;1$W7f~!&amp;:GVi7iYnoX7XN?bA2&#34;,X]Zt,pKpyhKYBrRW'
      ),
      personEmailAddressUpdatePage.personSelectLastOption()
    ]);
    expect(await personEmailAddressUpdatePage.getEmailAddressInput()).to.eq(
      '8&lt;^fjVdE@s+&lt;d`gzyQ~s-EV[l_#sKD&#34;jm`Uir1GIIFqnd#&amp;^Lawe5h;b+M$fpZP*P9/!N:Xn8sf#kfj#Qt|tY[5f&gt;{p;k-3dE8Amk{.&#39;x&amp;9?mK.T`%w#*CV5&gt;^HF|&#39;}bJ#unns8&amp;&lt;1$W7f~!&amp;:GVi7iYnoX7XN?bA2&#34;,X]Zt,pKpyhKYBrRW',
      'Expected EmailAddress value to be equals to 8&lt;^fjVdE@s+&lt;d`gzyQ~s-EV[l_#sKD&#34;jm`Uir1GIIFqnd#&amp;^Lawe5h;b+M$fpZP*P9/!N:Xn8sf#kfj#Qt|tY[5f&gt;{p;k-3dE8Amk{.&#39;x&amp;9?mK.T`%w#*CV5&gt;^HF|&#39;}bJ#unns8&amp;&lt;1$W7f~!&amp;:GVi7iYnoX7XN?bA2&#34;,X]Zt,pKpyhKYBrRW'
    );
    const selectedDefaultInd = personEmailAddressUpdatePage.getDefaultIndInput();
    if (await selectedDefaultInd.isSelected()) {
      await personEmailAddressUpdatePage.getDefaultIndInput().click();
      expect(await personEmailAddressUpdatePage.getDefaultIndInput().isSelected(), 'Expected defaultInd not to be selected').to.be.false;
    } else {
      await personEmailAddressUpdatePage.getDefaultIndInput().click();
      expect(await personEmailAddressUpdatePage.getDefaultIndInput().isSelected(), 'Expected defaultInd to be selected').to.be.true;
    }
    const selectedActiveInd = personEmailAddressUpdatePage.getActiveIndInput();
    if (await selectedActiveInd.isSelected()) {
      await personEmailAddressUpdatePage.getActiveIndInput().click();
      expect(await personEmailAddressUpdatePage.getActiveIndInput().isSelected(), 'Expected activeInd not to be selected').to.be.false;
    } else {
      await personEmailAddressUpdatePage.getActiveIndInput().click();
      expect(await personEmailAddressUpdatePage.getActiveIndInput().isSelected(), 'Expected activeInd to be selected').to.be.true;
    }
    await personEmailAddressUpdatePage.save();
    expect(await personEmailAddressUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await personEmailAddressComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PersonEmailAddress', async () => {
    const nbButtonsBeforeDelete = await personEmailAddressComponentsPage.countDeleteButtons();
    await personEmailAddressComponentsPage.clickOnLastDeleteButton();

    personEmailAddressDeleteDialog = new PersonEmailAddressDeleteDialog();
    expect(await personEmailAddressDeleteDialog.getDialogTitle()).to.eq('epmwebApp.personEmailAddress.delete.question');
    await personEmailAddressDeleteDialog.clickOnConfirmButton();

    expect(await personEmailAddressComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
