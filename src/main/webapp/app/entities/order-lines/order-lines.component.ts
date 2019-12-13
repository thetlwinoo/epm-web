import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrderLines } from 'app/shared/model/order-lines.model';
import { OrderLinesService } from './order-lines.service';
import { OrderLinesDeleteDialogComponent } from './order-lines-delete-dialog.component';

@Component({
  selector: 'jhi-order-lines',
  templateUrl: './order-lines.component.html'
})
export class OrderLinesComponent implements OnInit, OnDestroy {
  orderLines: IOrderLines[];
  eventSubscriber: Subscription;

  constructor(protected orderLinesService: OrderLinesService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.orderLinesService.query().subscribe((res: HttpResponse<IOrderLines[]>) => {
      this.orderLines = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInOrderLines();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOrderLines) {
    return item.id;
  }

  registerChangeInOrderLines() {
    this.eventSubscriber = this.eventManager.subscribe('orderLinesListModification', () => this.loadAll());
  }

  delete(orderLines: IOrderLines) {
    const modalRef = this.modalService.open(OrderLinesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.orderLines = orderLines;
  }
}
