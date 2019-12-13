import { element, by, ElementFinder } from 'protractor';

export class StockItemHoldingsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-stock-item-holdings div table .btn-danger'));
  title = element.all(by.css('jhi-stock-item-holdings div h2#page-heading span')).first();

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

export class StockItemHoldingsUpdatePage {
  pageTitle = element(by.id('jhi-stock-item-holdings-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  quantityOnHandInput = element(by.id('field_quantityOnHand'));
  binLocationInput = element(by.id('field_binLocation'));
  lastStocktakeQuantityInput = element(by.id('field_lastStocktakeQuantity'));
  lastCostPriceInput = element(by.id('field_lastCostPrice'));
  reorderLevelInput = element(by.id('field_reorderLevel'));
  targerStockLevelInput = element(by.id('field_targerStockLevel'));
  stockItemHoldingOnStockItemSelect = element(by.id('field_stockItemHoldingOnStockItem'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setQuantityOnHandInput(quantityOnHand) {
    await this.quantityOnHandInput.sendKeys(quantityOnHand);
  }

  async getQuantityOnHandInput() {
    return await this.quantityOnHandInput.getAttribute('value');
  }

  async setBinLocationInput(binLocation) {
    await this.binLocationInput.sendKeys(binLocation);
  }

  async getBinLocationInput() {
    return await this.binLocationInput.getAttribute('value');
  }

  async setLastStocktakeQuantityInput(lastStocktakeQuantity) {
    await this.lastStocktakeQuantityInput.sendKeys(lastStocktakeQuantity);
  }

  async getLastStocktakeQuantityInput() {
    return await this.lastStocktakeQuantityInput.getAttribute('value');
  }

  async setLastCostPriceInput(lastCostPrice) {
    await this.lastCostPriceInput.sendKeys(lastCostPrice);
  }

  async getLastCostPriceInput() {
    return await this.lastCostPriceInput.getAttribute('value');
  }

  async setReorderLevelInput(reorderLevel) {
    await this.reorderLevelInput.sendKeys(reorderLevel);
  }

  async getReorderLevelInput() {
    return await this.reorderLevelInput.getAttribute('value');
  }

  async setTargerStockLevelInput(targerStockLevel) {
    await this.targerStockLevelInput.sendKeys(targerStockLevel);
  }

  async getTargerStockLevelInput() {
    return await this.targerStockLevelInput.getAttribute('value');
  }

  async stockItemHoldingOnStockItemSelectLastOption() {
    await this.stockItemHoldingOnStockItemSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async stockItemHoldingOnStockItemSelectOption(option) {
    await this.stockItemHoldingOnStockItemSelect.sendKeys(option);
  }

  getStockItemHoldingOnStockItemSelect(): ElementFinder {
    return this.stockItemHoldingOnStockItemSelect;
  }

  async getStockItemHoldingOnStockItemSelectedOption() {
    return await this.stockItemHoldingOnStockItemSelect.element(by.css('option:checked')).getText();
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

export class StockItemHoldingsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-stockItemHoldings-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-stockItemHoldings'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
