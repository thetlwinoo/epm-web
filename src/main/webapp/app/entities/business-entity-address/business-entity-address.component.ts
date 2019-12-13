import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBusinessEntityAddress } from 'app/shared/model/business-entity-address.model';
import { BusinessEntityAddressService } from './business-entity-address.service';
import { BusinessEntityAddressDeleteDialogComponent } from './business-entity-address-delete-dialog.component';

@Component({
  selector: 'jhi-business-entity-address',
  templateUrl: './business-entity-address.component.html'
})
export class BusinessEntityAddressComponent implements OnInit, OnDestroy {
  businessEntityAddresses: IBusinessEntityAddress[];
  eventSubscriber: Subscription;

  constructor(
    protected businessEntityAddressService: BusinessEntityAddressService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.businessEntityAddressService.query().subscribe((res: HttpResponse<IBusinessEntityAddress[]>) => {
      this.businessEntityAddresses = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInBusinessEntityAddresses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBusinessEntityAddress) {
    return item.id;
  }

  registerChangeInBusinessEntityAddresses() {
    this.eventSubscriber = this.eventManager.subscribe('businessEntityAddressListModification', () => this.loadAll());
  }

  delete(businessEntityAddress: IBusinessEntityAddress) {
    const modalRef = this.modalService.open(BusinessEntityAddressDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.businessEntityAddress = businessEntityAddress;
  }
}
