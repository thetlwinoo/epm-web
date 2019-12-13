import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { PurchaseOrdersService } from './purchase-orders.service';
import { PurchaseOrdersDeleteDialogComponent } from './purchase-orders-delete-dialog.component';

@Component({
  selector: 'jhi-purchase-orders',
  templateUrl: './purchase-orders.component.html'
})
export class PurchaseOrdersComponent implements OnInit, OnDestroy {
  purchaseOrders: IPurchaseOrders[];
  eventSubscriber: Subscription;

  constructor(
    protected purchaseOrdersService: PurchaseOrdersService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.purchaseOrdersService.query().subscribe((res: HttpResponse<IPurchaseOrders[]>) => {
      this.purchaseOrders = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPurchaseOrders();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPurchaseOrders) {
    return item.id;
  }

  registerChangeInPurchaseOrders() {
    this.eventSubscriber = this.eventManager.subscribe('purchaseOrdersListModification', () => this.loadAll());
  }

  delete(purchaseOrders: IPurchaseOrders) {
    const modalRef = this.modalService.open(PurchaseOrdersDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.purchaseOrders = purchaseOrders;
  }
}
