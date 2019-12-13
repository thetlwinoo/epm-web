package com.epmweb.server.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.epmweb.server.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.epmweb.server.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.epmweb.server.domain.User.class.getName());
            createCache(cm, com.epmweb.server.domain.Authority.class.getName());
            createCache(cm, com.epmweb.server.domain.User.class.getName() + ".authorities");
            createCache(cm, com.epmweb.server.domain.AddressTypes.class.getName());
            createCache(cm, com.epmweb.server.domain.Culture.class.getName());
            createCache(cm, com.epmweb.server.domain.Addresses.class.getName());
            createCache(cm, com.epmweb.server.domain.BusinessEntityAddress.class.getName());
            createCache(cm, com.epmweb.server.domain.BusinessEntityContact.class.getName());
            createCache(cm, com.epmweb.server.domain.ShipMethod.class.getName());
            createCache(cm, com.epmweb.server.domain.PersonEmailAddress.class.getName());
            createCache(cm, com.epmweb.server.domain.PersonPhone.class.getName());
            createCache(cm, com.epmweb.server.domain.PhoneNumberType.class.getName());
            createCache(cm, com.epmweb.server.domain.ContactType.class.getName());
            createCache(cm, com.epmweb.server.domain.Countries.class.getName());
            createCache(cm, com.epmweb.server.domain.StateProvinces.class.getName());
            createCache(cm, com.epmweb.server.domain.Cities.class.getName());
            createCache(cm, com.epmweb.server.domain.SystemParameters.class.getName());
            createCache(cm, com.epmweb.server.domain.TransactionTypes.class.getName());
            createCache(cm, com.epmweb.server.domain.People.class.getName());
            createCache(cm, com.epmweb.server.domain.DeliveryMethods.class.getName());
            createCache(cm, com.epmweb.server.domain.SupplierCategories.class.getName());
            createCache(cm, com.epmweb.server.domain.Suppliers.class.getName());
            createCache(cm, com.epmweb.server.domain.SupplierTransactions.class.getName());
            createCache(cm, com.epmweb.server.domain.CurrencyRate.class.getName());
            createCache(cm, com.epmweb.server.domain.PurchaseOrders.class.getName());
            createCache(cm, com.epmweb.server.domain.PurchaseOrders.class.getName() + ".purchaseOrderLineLists");
            createCache(cm, com.epmweb.server.domain.PurchaseOrderLines.class.getName());
            createCache(cm, com.epmweb.server.domain.BuyingGroups.class.getName());
            createCache(cm, com.epmweb.server.domain.CustomerCategories.class.getName());
            createCache(cm, com.epmweb.server.domain.Customers.class.getName());
            createCache(cm, com.epmweb.server.domain.CustomerTransactions.class.getName());
            createCache(cm, com.epmweb.server.domain.PaymentTransactions.class.getName());
            createCache(cm, com.epmweb.server.domain.InvoiceLines.class.getName());
            createCache(cm, com.epmweb.server.domain.Invoices.class.getName());
            createCache(cm, com.epmweb.server.domain.Invoices.class.getName() + ".invoiceLineLists");
            createCache(cm, com.epmweb.server.domain.OrderLines.class.getName());
            createCache(cm, com.epmweb.server.domain.Orders.class.getName());
            createCache(cm, com.epmweb.server.domain.Orders.class.getName() + ".orderLineLists");
            createCache(cm, com.epmweb.server.domain.SpecialDeals.class.getName());
            createCache(cm, com.epmweb.server.domain.SpecialDeals.class.getName() + ".cartDiscounts");
            createCache(cm, com.epmweb.server.domain.SpecialDeals.class.getName() + ".orderDiscounts");
            createCache(cm, com.epmweb.server.domain.ColdRoomTemperatures.class.getName());
            createCache(cm, com.epmweb.server.domain.PackageTypes.class.getName());
            createCache(cm, com.epmweb.server.domain.Products.class.getName());
            createCache(cm, com.epmweb.server.domain.Products.class.getName() + ".stockItemLists");
            createCache(cm, com.epmweb.server.domain.BarcodeTypes.class.getName());
            createCache(cm, com.epmweb.server.domain.StockItems.class.getName());
            createCache(cm, com.epmweb.server.domain.StockItems.class.getName() + ".photoLists");
            createCache(cm, com.epmweb.server.domain.StockItems.class.getName() + ".dangerousGoodLists");
            createCache(cm, com.epmweb.server.domain.StockItems.class.getName() + ".specialDiscounts");
            createCache(cm, com.epmweb.server.domain.StockItemTemp.class.getName());
            createCache(cm, com.epmweb.server.domain.UploadTransactions.class.getName());
            createCache(cm, com.epmweb.server.domain.UploadTransactions.class.getName() + ".importDocumentLists");
            createCache(cm, com.epmweb.server.domain.UploadTransactions.class.getName() + ".stockItemTempLists");
            createCache(cm, com.epmweb.server.domain.UploadActionTypes.class.getName());
            createCache(cm, com.epmweb.server.domain.StockItemTransactions.class.getName());
            createCache(cm, com.epmweb.server.domain.StockItemHoldings.class.getName());
            createCache(cm, com.epmweb.server.domain.WarrantyTypes.class.getName());
            createCache(cm, com.epmweb.server.domain.ProductAttribute.class.getName());
            createCache(cm, com.epmweb.server.domain.ProductAttributeSet.class.getName());
            createCache(cm, com.epmweb.server.domain.ProductOption.class.getName());
            createCache(cm, com.epmweb.server.domain.ProductOptionSet.class.getName());
            createCache(cm, com.epmweb.server.domain.ProductChoice.class.getName());
            createCache(cm, com.epmweb.server.domain.ProductSet.class.getName());
            createCache(cm, com.epmweb.server.domain.ProductSetDetails.class.getName());
            createCache(cm, com.epmweb.server.domain.ProductDocument.class.getName());
            createCache(cm, com.epmweb.server.domain.Materials.class.getName());
            createCache(cm, com.epmweb.server.domain.DangerousGoods.class.getName());
            createCache(cm, com.epmweb.server.domain.ProductBrand.class.getName());
            createCache(cm, com.epmweb.server.domain.ProductCategory.class.getName());
            createCache(cm, com.epmweb.server.domain.ProductCategory.class.getName() + ".photoLists");
            createCache(cm, com.epmweb.server.domain.ProductCatalog.class.getName());
            createCache(cm, com.epmweb.server.domain.Currency.class.getName());
            createCache(cm, com.epmweb.server.domain.Photos.class.getName());
            createCache(cm, com.epmweb.server.domain.UnitMeasure.class.getName());
            createCache(cm, com.epmweb.server.domain.VehicleTemperatures.class.getName());
            createCache(cm, com.epmweb.server.domain.ShoppingCarts.class.getName());
            createCache(cm, com.epmweb.server.domain.ShoppingCarts.class.getName() + ".cartItemLists");
            createCache(cm, com.epmweb.server.domain.ShoppingCartItems.class.getName());
            createCache(cm, com.epmweb.server.domain.Wishlists.class.getName());
            createCache(cm, com.epmweb.server.domain.Wishlists.class.getName() + ".wishlistLists");
            createCache(cm, com.epmweb.server.domain.WishlistProducts.class.getName());
            createCache(cm, com.epmweb.server.domain.Compares.class.getName());
            createCache(cm, com.epmweb.server.domain.Compares.class.getName() + ".compareLists");
            createCache(cm, com.epmweb.server.domain.CompareProducts.class.getName());
            createCache(cm, com.epmweb.server.domain.Reviews.class.getName());
            createCache(cm, com.epmweb.server.domain.Reviews.class.getName() + ".reviewLineLists");
            createCache(cm, com.epmweb.server.domain.ReviewLines.class.getName());
            createCache(cm, com.epmweb.server.domain.ProductTags.class.getName());
            createCache(cm, com.epmweb.server.domain.SupplierImportedDocument.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
