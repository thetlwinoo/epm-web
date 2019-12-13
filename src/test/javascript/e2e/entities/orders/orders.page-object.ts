import { element, by, ElementFinder } from 'protractor';

export class OrdersComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-orders div table .btn-danger'));
  title = element.all(by.css('jhi-orders div h2#page-heading span')).first();

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

export class OrdersUpdatePage {
  pageTitle = element(by.id('jhi-orders-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  orderDateInput = element(by.id('field_orderDate'));
  dueDateInput = element(by.id('field_dueDate'));
  shipDateInput = element(by.id('field_shipDate'));
  paymentStatusInput = element(by.id('field_paymentStatus'));
  orderFlagInput = element(by.id('field_orderFlag'));
  orderNumberInput = element(by.id('field_orderNumber'));
  subTotalInput = element(by.id('field_subTotal'));
  taxAmountInput = element(by.id('field_taxAmount'));
  frieightInput = element(by.id('field_frieight'));
  totalDueInput = element(by.id('field_totalDue'));
  commentsInput = element(by.id('field_comments'));
  deliveryInstructionsInput = element(by.id('field_deliveryInstructions'));
  internalCommentsInput = element(by.id('field_internalComments'));
  pickingCompletedWhenInput = element(by.id('field_pickingCompletedWhen'));
  statusSelect = element(by.id('field_status'));
  lastEditedByInput = element(by.id('field_lastEditedBy'));
  lastEditedWhenInput = element(by.id('field_lastEditedWhen'));
  orderOnReviewSelect = element(by.id('field_orderOnReview'));
  customerSelect = element(by.id('field_customer'));
  shipToAddressSelect = element(by.id('field_shipToAddress'));
  billToAddressSelect = element(by.id('field_billToAddress'));
  shipMethodSelect = element(by.id('field_shipMethod'));
  currencyRateSelect = element(by.id('field_currencyRate'));
  specialDealsSelect = element(by.id('field_specialDeals'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setOrderDateInput(orderDate) {
    await this.orderDateInput.sendKeys(orderDate);
  }

  async getOrderDateInput() {
    return await this.orderDateInput.getAttribute('value');
  }

  async setDueDateInput(dueDate) {
    await this.dueDateInput.sendKeys(dueDate);
  }

  async getDueDateInput() {
    return await this.dueDateInput.getAttribute('value');
  }

  async setShipDateInput(shipDate) {
    await this.shipDateInput.sendKeys(shipDate);
  }

  async getShipDateInput() {
    return await this.shipDateInput.getAttribute('value');
  }

  async setPaymentStatusInput(paymentStatus) {
    await this.paymentStatusInput.sendKeys(paymentStatus);
  }

  async getPaymentStatusInput() {
    return await this.paymentStatusInput.getAttribute('value');
  }

  async setOrderFlagInput(orderFlag) {
    await this.orderFlagInput.sendKeys(orderFlag);
  }

  async getOrderFlagInput() {
    return await this.orderFlagInput.getAttribute('value');
  }

  async setOrderNumberInput(orderNumber) {
    await this.orderNumberInput.sendKeys(orderNumber);
  }

  async getOrderNumberInput() {
    return await this.orderNumberInput.getAttribute('value');
  }

  async setSubTotalInput(subTotal) {
    await this.subTotalInput.sendKeys(subTotal);
  }

  async getSubTotalInput() {
    return await this.subTotalInput.getAttribute('value');
  }

  async setTaxAmountInput(taxAmount) {
    await this.taxAmountInput.sendKeys(taxAmount);
  }

  async getTaxAmountInput() {
    return await this.taxAmountInput.getAttribute('value');
  }

  async setFrieightInput(frieight) {
    await this.frieightInput.sendKeys(frieight);
  }

  async getFrieightInput() {
    return await this.frieightInput.getAttribute('value');
  }

  async setTotalDueInput(totalDue) {
    await this.totalDueInput.sendKeys(totalDue);
  }

  async getTotalDueInput() {
    return await this.totalDueInput.getAttribute('value');
  }

  async setCommentsInput(comments) {
    await this.commentsInput.sendKeys(comments);
  }

  async getCommentsInput() {
    return await this.commentsInput.getAttribute('value');
  }

  async setDeliveryInstructionsInput(deliveryInstructions) {
    await this.deliveryInstructionsInput.sendKeys(deliveryInstructions);
  }

  async getDeliveryInstructionsInput() {
    return await this.deliveryInstructionsInput.getAttribute('value');
  }

  async setInternalCommentsInput(internalComments) {
    await this.internalCommentsInput.sendKeys(internalComments);
  }

  async getInternalCommentsInput() {
    return await this.internalCommentsInput.getAttribute('value');
  }

  async setPickingCompletedWhenInput(pickingCompletedWhen) {
    await this.pickingCompletedWhenInput.sendKeys(pickingCompletedWhen);
  }

  async getPickingCompletedWhenInput() {
    return await this.pickingCompletedWhenInput.getAttribute('value');
  }

  async setStatusSelect(status) {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect() {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption() {
    await this.statusSelect
      .all(by.tagName('option'))
      .last()
      .click();
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

  async orderOnReviewSelectLastOption() {
    await this.orderOnReviewSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async orderOnReviewSelectOption(option) {
    await this.orderOnReviewSelect.sendKeys(option);
  }

  getOrderOnReviewSelect(): ElementFinder {
    return this.orderOnReviewSelect;
  }

  async getOrderOnReviewSelectedOption() {
    return await this.orderOnReviewSelect.element(by.css('option:checked')).getText();
  }

  async customerSelectLastOption() {
    await this.customerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async customerSelectOption(option) {
    await this.customerSelect.sendKeys(option);
  }

  getCustomerSelect(): ElementFinder {
    return this.customerSelect;
  }

  async getCustomerSelectedOption() {
    return await this.customerSelect.element(by.css('option:checked')).getText();
  }

  async shipToAddressSelectLastOption() {
    await this.shipToAddressSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async shipToAddressSelectOption(option) {
    await this.shipToAddressSelect.sendKeys(option);
  }

  getShipToAddressSelect(): ElementFinder {
    return this.shipToAddressSelect;
  }

  async getShipToAddressSelectedOption() {
    return await this.shipToAddressSelect.element(by.css('option:checked')).getText();
  }

  async billToAddressSelectLastOption() {
    await this.billToAddressSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async billToAddressSelectOption(option) {
    await this.billToAddressSelect.sendKeys(option);
  }

  getBillToAddressSelect(): ElementFinder {
    return this.billToAddressSelect;
  }

  async getBillToAddressSelectedOption() {
    return await this.billToAddressSelect.element(by.css('option:checked')).getText();
  }

  async shipMethodSelectLastOption() {
    await this.shipMethodSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async shipMethodSelectOption(option) {
    await this.shipMethodSelect.sendKeys(option);
  }

  getShipMethodSelect(): ElementFinder {
    return this.shipMethodSelect;
  }

  async getShipMethodSelectedOption() {
    return await this.shipMethodSelect.element(by.css('option:checked')).getText();
  }

  async currencyRateSelectLastOption() {
    await this.currencyRateSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async currencyRateSelectOption(option) {
    await this.currencyRateSelect.sendKeys(option);
  }

  getCurrencyRateSelect(): ElementFinder {
    return this.currencyRateSelect;
  }

  async getCurrencyRateSelectedOption() {
    return await this.currencyRateSelect.element(by.css('option:checked')).getText();
  }

  async specialDealsSelectLastOption() {
    await this.specialDealsSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async specialDealsSelectOption(option) {
    await this.specialDealsSelect.sendKeys(option);
  }

  getSpecialDealsSelect(): ElementFinder {
    return this.specialDealsSelect;
  }

  async getSpecialDealsSelectedOption() {
    return await this.specialDealsSelect.element(by.css('option:checked')).getText();
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

export class OrdersDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-orders-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-orders'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
