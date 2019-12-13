export interface IWishlistProducts {
  id?: number;
  productName?: string;
  productId?: number;
  wishlistId?: number;
}

export class WishlistProducts implements IWishlistProducts {
  constructor(public id?: number, public productName?: string, public productId?: number, public wishlistId?: number) {}
}
