import { IWishlistProducts } from 'app/shared/model/wishlist-products.model';

export interface IWishlists {
  id?: number;
  wishlistUserId?: number;
  wishlistLists?: IWishlistProducts[];
}

export class Wishlists implements IWishlists {
  constructor(public id?: number, public wishlistUserId?: number, public wishlistLists?: IWishlistProducts[]) {}
}
