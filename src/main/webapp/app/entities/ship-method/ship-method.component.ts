import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IShipMethod } from 'app/shared/model/ship-method.model';
import { ShipMethodService } from './ship-method.service';
import { ShipMethodDeleteDialogComponent } from './ship-method-delete-dialog.component';

@Component({
  selector: 'jhi-ship-method',
  templateUrl: './ship-method.component.html'
})
export class ShipMethodComponent implements OnInit, OnDestroy {
  shipMethods: IShipMethod[];
  eventSubscriber: Subscription;

  constructor(protected shipMethodService: ShipMethodService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.shipMethodService.query().subscribe((res: HttpResponse<IShipMethod[]>) => {
      this.shipMethods = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInShipMethods();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IShipMethod) {
    return item.id;
  }

  registerChangeInShipMethods() {
    this.eventSubscriber = this.eventManager.subscribe('shipMethodListModification', () => this.loadAll());
  }

  delete(shipMethod: IShipMethod) {
    const modalRef = this.modalService.open(ShipMethodDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.shipMethod = shipMethod;
  }
}
