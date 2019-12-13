import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'address-types',
        loadChildren: () => import('./address-types/address-types.module').then(m => m.EpmwebAddressTypesModule)
      },
      {
        path: 'culture',
        loadChildren: () => import('./culture/culture.module').then(m => m.EpmwebCultureModule)
      },
      {
        path: 'addresses',
        loadChildren: () => import('./addresses/addresses.module').then(m => m.EpmwebAddressesModule)
      },
      {
        path: 'business-entity-address',
        loadChildren: () =>
          import('./business-entity-address/business-entity-address.module').then(m => m.EpmwebBusinessEntityAddressModule)
      },
      {
        path: 'business-entity-contact',
        loadChildren: () =>
          import('./business-entity-contact/business-entity-contact.module').then(m => m.EpmwebBusinessEntityContactModule)
      },
      {
        path: 'ship-method',
        loadChildren: () => import('./ship-method/ship-method.module').then(m => m.EpmwebShipMethodModule)
      },
      {
        path: 'person-email-address',
        loadChildren: () => import('./person-email-address/person-email-address.module').then(m => m.EpmwebPersonEmailAddressModule)
      },
      {
        path: 'person-phone',
        loadChildren: () => import('./person-phone/person-phone.module').then(m => m.EpmwebPersonPhoneModule)
      },
      {
        path: 'phone-number-type',
        loadChildren: () => import('./phone-number-type/phone-number-type.module').then(m => m.EpmwebPhoneNumberTypeModule)
      },
      {
        path: 'contact-type',
        loadChildren: () => import('./contact-type/contact-type.module').then(m => m.EpmwebContactTypeModule)
      },
      {
        path: 'countries',
        loadChildren: () => import('./countries/countries.module').then(m => m.EpmwebCountriesModule)
      },
      {
        path: 'state-provinces',
        loadChildren: () => import('./state-provinces/state-provinces.module').then(m => m.EpmwebStateProvincesModule)
      },
      {
        path: 'cities',
        loadChildren: () => import('./cities/cities.module').then(m => m.EpmwebCitiesModule)
      },
      {
        path: 'system-parameters',
        loadChildren: () => import('./system-parameters/system-parameters.module').then(m => m.EpmwebSystemParametersModule)
      },
      {
        path: 'transaction-types',
        loadChildren: () => import('./transaction-types/transaction-types.module').then(m => m.EpmwebTransactionTypesModule)
      },
      {
        path: 'people',
        loadChildren: () => import('./people/people.module').then(m => m.EpmwebPeopleModule)
      },
      {
        path: 'delivery-methods',
        loadChildren: () => import('./delivery-methods/delivery-methods.module').then(m => m.EpmwebDeliveryMethodsModule)
      },
      {
        path: 'supplier-categories',
        loadChildren: () => import('./supplier-categories/supplier-categories.module').then(m => m.EpmwebSupplierCategoriesModule)
      },
      {
        path: 'suppliers',
        loadChildren: () => import('./suppliers/suppliers.module').then(m => m.EpmwebSuppliersModule)
      },
      {
        path: 'supplier-transactions',
        loadChildren: () => import('./supplier-transactions/supplier-transactions.module').then(m => m.EpmwebSupplierTransactionsModule)
      },
      {
        path: 'currency-rate',
        loadChildren: () => import('./currency-rate/currency-rate.module').then(m => m.EpmwebCurrencyRateModule)
      },
      {
        path: 'purchase-orders',
        loadChildren: () => import('./purchase-orders/purchase-orders.module').then(m => m.EpmwebPurchaseOrdersModule)
      },
      {
        path: 'purchase-order-lines',
        loadChildren: () => import('./purchase-order-lines/purchase-order-lines.module').then(m => m.EpmwebPurchaseOrderLinesModule)
      },
      {
        path: 'buying-groups',
        loadChildren: () => import('./buying-groups/buying-groups.module').then(m => m.EpmwebBuyingGroupsModule)
      },
      {
        path: 'customer-categories',
        loadChildren: () => import('./customer-categories/customer-categories.module').then(m => m.EpmwebCustomerCategoriesModule)
      },
      {
        path: 'customers',
        loadChildren: () => import('./customers/customers.module').then(m => m.EpmwebCustomersModule)
      },
      {
        path: 'customer-transactions',
        loadChildren: () => import('./customer-transactions/customer-transactions.module').then(m => m.EpmwebCustomerTransactionsModule)
      },
      {
        path: 'payment-transactions',
        loadChildren: () => import('./payment-transactions/payment-transactions.module').then(m => m.EpmwebPaymentTransactionsModule)
      },
      {
        path: 'invoice-lines',
        loadChildren: () => import('./invoice-lines/invoice-lines.module').then(m => m.EpmwebInvoiceLinesModule)
      },
      {
        path: 'invoices',
        loadChildren: () => import('./invoices/invoices.module').then(m => m.EpmwebInvoicesModule)
      },
      {
        path: 'order-lines',
        loadChildren: () => import('./order-lines/order-lines.module').then(m => m.EpmwebOrderLinesModule)
      },
      {
        path: 'orders',
        loadChildren: () => import('./orders/orders.module').then(m => m.EpmwebOrdersModule)
      },
      {
        path: 'special-deals',
        loadChildren: () => import('./special-deals/special-deals.module').then(m => m.EpmwebSpecialDealsModule)
      },
      {
        path: 'cold-room-temperatures',
        loadChildren: () => import('./cold-room-temperatures/cold-room-temperatures.module').then(m => m.EpmwebColdRoomTemperaturesModule)
      },
      {
        path: 'package-types',
        loadChildren: () => import('./package-types/package-types.module').then(m => m.EpmwebPackageTypesModule)
      },
      {
        path: 'products',
        loadChildren: () => import('./products/products.module').then(m => m.EpmwebProductsModule)
      },
      {
        path: 'barcode-types',
        loadChildren: () => import('./barcode-types/barcode-types.module').then(m => m.EpmwebBarcodeTypesModule)
      },
      {
        path: 'stock-items',
        loadChildren: () => import('./stock-items/stock-items.module').then(m => m.EpmwebStockItemsModule)
      },
      {
        path: 'stock-item-temp',
        loadChildren: () => import('./stock-item-temp/stock-item-temp.module').then(m => m.EpmwebStockItemTempModule)
      },
      {
        path: 'upload-transactions',
        loadChildren: () => import('./upload-transactions/upload-transactions.module').then(m => m.EpmwebUploadTransactionsModule)
      },
      {
        path: 'upload-action-types',
        loadChildren: () => import('./upload-action-types/upload-action-types.module').then(m => m.EpmwebUploadActionTypesModule)
      },
      {
        path: 'stock-item-transactions',
        loadChildren: () =>
          import('./stock-item-transactions/stock-item-transactions.module').then(m => m.EpmwebStockItemTransactionsModule)
      },
      {
        path: 'stock-item-holdings',
        loadChildren: () => import('./stock-item-holdings/stock-item-holdings.module').then(m => m.EpmwebStockItemHoldingsModule)
      },
      {
        path: 'warranty-types',
        loadChildren: () => import('./warranty-types/warranty-types.module').then(m => m.EpmwebWarrantyTypesModule)
      },
      {
        path: 'product-attribute',
        loadChildren: () => import('./product-attribute/product-attribute.module').then(m => m.EpmwebProductAttributeModule)
      },
      {
        path: 'product-attribute-set',
        loadChildren: () => import('./product-attribute-set/product-attribute-set.module').then(m => m.EpmwebProductAttributeSetModule)
      },
      {
        path: 'product-option',
        loadChildren: () => import('./product-option/product-option.module').then(m => m.EpmwebProductOptionModule)
      },
      {
        path: 'product-option-set',
        loadChildren: () => import('./product-option-set/product-option-set.module').then(m => m.EpmwebProductOptionSetModule)
      },
      {
        path: 'product-choice',
        loadChildren: () => import('./product-choice/product-choice.module').then(m => m.EpmwebProductChoiceModule)
      },
      {
        path: 'product-set',
        loadChildren: () => import('./product-set/product-set.module').then(m => m.EpmwebProductSetModule)
      },
      {
        path: 'product-set-details',
        loadChildren: () => import('./product-set-details/product-set-details.module').then(m => m.EpmwebProductSetDetailsModule)
      },
      {
        path: 'product-document',
        loadChildren: () => import('./product-document/product-document.module').then(m => m.EpmwebProductDocumentModule)
      },
      {
        path: 'materials',
        loadChildren: () => import('./materials/materials.module').then(m => m.EpmwebMaterialsModule)
      },
      {
        path: 'dangerous-goods',
        loadChildren: () => import('./dangerous-goods/dangerous-goods.module').then(m => m.EpmwebDangerousGoodsModule)
      },
      {
        path: 'product-brand',
        loadChildren: () => import('./product-brand/product-brand.module').then(m => m.EpmwebProductBrandModule)
      },
      {
        path: 'product-category',
        loadChildren: () => import('./product-category/product-category.module').then(m => m.EpmwebProductCategoryModule)
      },
      {
        path: 'product-catalog',
        loadChildren: () => import('./product-catalog/product-catalog.module').then(m => m.EpmwebProductCatalogModule)
      },
      {
        path: 'currency',
        loadChildren: () => import('./currency/currency.module').then(m => m.EpmwebCurrencyModule)
      },
      {
        path: 'photos',
        loadChildren: () => import('./photos/photos.module').then(m => m.EpmwebPhotosModule)
      },
      {
        path: 'unit-measure',
        loadChildren: () => import('./unit-measure/unit-measure.module').then(m => m.EpmwebUnitMeasureModule)
      },
      {
        path: 'vehicle-temperatures',
        loadChildren: () => import('./vehicle-temperatures/vehicle-temperatures.module').then(m => m.EpmwebVehicleTemperaturesModule)
      },
      {
        path: 'shopping-carts',
        loadChildren: () => import('./shopping-carts/shopping-carts.module').then(m => m.EpmwebShoppingCartsModule)
      },
      {
        path: 'shopping-cart-items',
        loadChildren: () => import('./shopping-cart-items/shopping-cart-items.module').then(m => m.EpmwebShoppingCartItemsModule)
      },
      {
        path: 'wishlists',
        loadChildren: () => import('./wishlists/wishlists.module').then(m => m.EpmwebWishlistsModule)
      },
      {
        path: 'wishlist-products',
        loadChildren: () => import('./wishlist-products/wishlist-products.module').then(m => m.EpmwebWishlistProductsModule)
      },
      {
        path: 'compares',
        loadChildren: () => import('./compares/compares.module').then(m => m.EpmwebComparesModule)
      },
      {
        path: 'compare-products',
        loadChildren: () => import('./compare-products/compare-products.module').then(m => m.EpmwebCompareProductsModule)
      },
      {
        path: 'reviews',
        loadChildren: () => import('./reviews/reviews.module').then(m => m.EpmwebReviewsModule)
      },
      {
        path: 'review-lines',
        loadChildren: () => import('./review-lines/review-lines.module').then(m => m.EpmwebReviewLinesModule)
      },
      {
        path: 'product-tags',
        loadChildren: () => import('./product-tags/product-tags.module').then(m => m.EpmwebProductTagsModule)
      },
      {
        path: 'supplier-imported-document',
        loadChildren: () =>
          import('./supplier-imported-document/supplier-imported-document.module').then(m => m.EpmwebSupplierImportedDocumentModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class EpmwebEntityModule {}
