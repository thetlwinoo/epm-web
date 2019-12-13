import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAddressTypes } from 'app/shared/model/address-types.model';
import { AddressTypesService } from './address-types.service';
import { AddressTypesDeleteDialogComponent } from './address-types-delete-dialog.component';

@Component({
  selector: 'jhi-address-types',
  templateUrl: './address-types.component.html'
})
export class AddressTypesComponent implements OnInit, OnDestroy {
  addressTypes: IAddressTypes[];
  eventSubscriber: Subscription;

  constructor(
    protected addressTypesService: AddressTypesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.addressTypesService.query().subscribe((res: HttpResponse<IAddressTypes[]>) => {
      this.addressTypes = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInAddressTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAddressTypes) {
    return item.id;
  }

  registerChangeInAddressTypes() {
    this.eventSubscriber = this.eventManager.subscribe('addressTypesListModification', () => this.loadAll());
  }

  delete(addressTypes: IAddressTypes) {
    const modalRef = this.modalService.open(AddressTypesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.addressTypes = addressTypes;
  }
}
