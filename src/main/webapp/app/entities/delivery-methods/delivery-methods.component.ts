import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeliveryMethods } from 'app/shared/model/delivery-methods.model';
import { DeliveryMethodsService } from './delivery-methods.service';
import { DeliveryMethodsDeleteDialogComponent } from './delivery-methods-delete-dialog.component';

@Component({
  selector: 'jhi-delivery-methods',
  templateUrl: './delivery-methods.component.html'
})
export class DeliveryMethodsComponent implements OnInit, OnDestroy {
  deliveryMethods: IDeliveryMethods[];
  eventSubscriber: Subscription;

  constructor(
    protected deliveryMethodsService: DeliveryMethodsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.deliveryMethodsService.query().subscribe((res: HttpResponse<IDeliveryMethods[]>) => {
      this.deliveryMethods = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInDeliveryMethods();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDeliveryMethods) {
    return item.id;
  }

  registerChangeInDeliveryMethods() {
    this.eventSubscriber = this.eventManager.subscribe('deliveryMethodsListModification', () => this.loadAll());
  }

  delete(deliveryMethods: IDeliveryMethods) {
    const modalRef = this.modalService.open(DeliveryMethodsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deliveryMethods = deliveryMethods;
  }
}
