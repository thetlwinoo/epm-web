export interface IStockItemHoldings {
  id?: number;
  quantityOnHand?: number;
  binLocation?: string;
  lastStocktakeQuantity?: number;
  lastCostPrice?: number;
  reorderLevel?: number;
  targerStockLevel?: number;
  stockItemHoldingOnStockItemId?: number;
}

export class StockItemHoldings implements IStockItemHoldings {
  constructor(
    public id?: number,
    public quantityOnHand?: number,
    public binLocation?: string,
    public lastStocktakeQuantity?: number,
    public lastCostPrice?: number,
    public reorderLevel?: number,
    public targerStockLevel?: number,
    public stockItemHoldingOnStockItemId?: number
  ) {}
}
