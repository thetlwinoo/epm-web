import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWishlistProducts } from 'app/shared/model/wishlist-products.model';
import { WishlistProductsService } from './wishlist-products.service';
import { WishlistProductsDeleteDialogComponent } from './wishlist-products-delete-dialog.component';

@Component({
  selector: 'jhi-wishlist-products',
  templateUrl: './wishlist-products.component.html'
})
export class WishlistProductsComponent implements OnInit, OnDestroy {
  wishlistProducts: IWishlistProducts[];
  eventSubscriber: Subscription;

  constructor(
    protected wishlistProductsService: WishlistProductsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.wishlistProductsService.query().subscribe((res: HttpResponse<IWishlistProducts[]>) => {
      this.wishlistProducts = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInWishlistProducts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IWishlistProducts) {
    return item.id;
  }

  registerChangeInWishlistProducts() {
    this.eventSubscriber = this.eventManager.subscribe('wishlistProductsListModification', () => this.loadAll());
  }

  delete(wishlistProducts: IWishlistProducts) {
    const modalRef = this.modalService.open(WishlistProductsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.wishlistProducts = wishlistProducts;
  }
}
