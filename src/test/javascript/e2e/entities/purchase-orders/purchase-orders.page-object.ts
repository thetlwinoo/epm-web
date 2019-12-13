import { element, by, ElementFinder } from 'protractor';

export class PurchaseOrdersComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-purchase-orders div table .btn-danger'));
  title = element.all(by.css('jhi-purchase-orders div h2#page-heading span')).first();

  async clickOnCreateButton() {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton() {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class PurchaseOrdersUpdatePage {
  pageTitle = element(by.id('jhi-purchase-orders-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  orderDateInput = element(by.id('field_orderDate'));
  expectedDeliveryDateInput = element(by.id('field_expectedDeliveryDate'));
  supplierReferenceInput = element(by.id('field_supplierReference'));
  isOrderFinalizedInput = element(by.id('field_isOrderFinalized'));
  commentsInput = element(by.id('field_comments'));
  internalCommentsInput = element(by.id('field_internalComments'));
  lastEditedByInput = element(by.id('field_lastEditedBy'));
  lastEditedWhenInput = element(by.id('field_lastEditedWhen'));
  contactPersonSelect = element(by.id('field_contactPerson'));
  supplierSelect = element(by.id('field_supplier'));
  deliveryMethodSelect = element(by.id('field_deliveryMethod'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setOrderDateInput(orderDate) {
    await this.orderDateInput.sendKeys(orderDate);
  }

  async getOrderDateInput() {
    return await this.orderDateInput.getAttribute('value');
  }

  async setExpectedDeliveryDateInput(expectedDeliveryDate) {
    await this.expectedDeliveryDateInput.sendKeys(expectedDeliveryDate);
  }

  async getExpectedDeliveryDateInput() {
    return await this.expectedDeliveryDateInput.getAttribute('value');
  }

  async setSupplierReferenceInput(supplierReference) {
    await this.supplierReferenceInput.sendKeys(supplierReference);
  }

  async getSupplierReferenceInput() {
    return await this.supplierReferenceInput.getAttribute('value');
  }

  async setIsOrderFinalizedInput(isOrderFinalized) {
    await this.isOrderFinalizedInput.sendKeys(isOrderFinalized);
  }

  async getIsOrderFinalizedInput() {
    return await this.isOrderFinalizedInput.getAttribute('value');
  }

  async setCommentsInput(comments) {
    await this.commentsInput.sendKeys(comments);
  }

  async getCommentsInput() {
    return await this.commentsInput.getAttribute('value');
  }

  async setInternalCommentsInput(internalComments) {
    await this.internalCommentsInput.sendKeys(internalComments);
  }

  async getInternalCommentsInput() {
    return await this.internalCommentsInput.getAttribute('value');
  }

  async setLastEditedByInput(lastEditedBy) {
    await this.lastEditedByInput.sendKeys(lastEditedBy);
  }

  async getLastEditedByInput() {
    return await this.lastEditedByInput.getAttribute('value');
  }

  async setLastEditedWhenInput(lastEditedWhen) {
    await this.lastEditedWhenInput.sendKeys(lastEditedWhen);
  }

  async getLastEditedWhenInput() {
    return await this.lastEditedWhenInput.getAttribute('value');
  }

  async contactPersonSelectLastOption() {
    await this.contactPersonSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async contactPersonSelectOption(option) {
    await this.contactPersonSelect.sendKeys(option);
  }

  getContactPersonSelect(): ElementFinder {
    return this.contactPersonSelect;
  }

  async getContactPersonSelectedOption() {
    return await this.contactPersonSelect.element(by.css('option:checked')).getText();
  }

  async supplierSelectLastOption() {
    await this.supplierSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async supplierSelectOption(option) {
    await this.supplierSelect.sendKeys(option);
  }

  getSupplierSelect(): ElementFinder {
    return this.supplierSelect;
  }

  async getSupplierSelectedOption() {
    return await this.supplierSelect.element(by.css('option:checked')).getText();
  }

  async deliveryMethodSelectLastOption() {
    await this.deliveryMethodSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async deliveryMethodSelectOption(option) {
    await this.deliveryMethodSelect.sendKeys(option);
  }

  getDeliveryMethodSelect(): ElementFinder {
    return this.deliveryMethodSelect;
  }

  async getDeliveryMethodSelectedOption() {
    return await this.deliveryMethodSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class PurchaseOrdersDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-purchaseOrders-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-purchaseOrders'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
