import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IShoppingCarts } from 'app/shared/model/shopping-carts.model';
import { ShoppingCartsService } from './shopping-carts.service';
import { ShoppingCartsDeleteDialogComponent } from './shopping-carts-delete-dialog.component';

@Component({
  selector: 'jhi-shopping-carts',
  templateUrl: './shopping-carts.component.html'
})
export class ShoppingCartsComponent implements OnInit, OnDestroy {
  shoppingCarts: IShoppingCarts[];
  eventSubscriber: Subscription;

  constructor(
    protected shoppingCartsService: ShoppingCartsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.shoppingCartsService.query().subscribe((res: HttpResponse<IShoppingCarts[]>) => {
      this.shoppingCarts = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInShoppingCarts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IShoppingCarts) {
    return item.id;
  }

  registerChangeInShoppingCarts() {
    this.eventSubscriber = this.eventManager.subscribe('shoppingCartsListModification', () => this.loadAll());
  }

  delete(shoppingCarts: IShoppingCarts) {
    const modalRef = this.modalService.open(ShoppingCartsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.shoppingCarts = shoppingCarts;
  }
}
