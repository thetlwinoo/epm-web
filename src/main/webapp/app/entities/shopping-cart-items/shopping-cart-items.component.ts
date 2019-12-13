import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IShoppingCartItems } from 'app/shared/model/shopping-cart-items.model';
import { ShoppingCartItemsService } from './shopping-cart-items.service';
import { ShoppingCartItemsDeleteDialogComponent } from './shopping-cart-items-delete-dialog.component';

@Component({
  selector: 'jhi-shopping-cart-items',
  templateUrl: './shopping-cart-items.component.html'
})
export class ShoppingCartItemsComponent implements OnInit, OnDestroy {
  shoppingCartItems: IShoppingCartItems[];
  eventSubscriber: Subscription;

  constructor(
    protected shoppingCartItemsService: ShoppingCartItemsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.shoppingCartItemsService.query().subscribe((res: HttpResponse<IShoppingCartItems[]>) => {
      this.shoppingCartItems = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInShoppingCartItems();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IShoppingCartItems) {
    return item.id;
  }

  registerChangeInShoppingCartItems() {
    this.eventSubscriber = this.eventManager.subscribe('shoppingCartItemsListModification', () => this.loadAll());
  }

  delete(shoppingCartItems: IShoppingCartItems) {
    const modalRef = this.modalService.open(ShoppingCartItemsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.shoppingCartItems = shoppingCartItems;
  }
}
