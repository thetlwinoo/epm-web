import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAddresses } from 'app/shared/model/addresses.model';
import { AddressesService } from './addresses.service';
import { AddressesDeleteDialogComponent } from './addresses-delete-dialog.component';

@Component({
  selector: 'jhi-addresses',
  templateUrl: './addresses.component.html'
})
export class AddressesComponent implements OnInit, OnDestroy {
  addresses: IAddresses[];
  eventSubscriber: Subscription;

  constructor(protected addressesService: AddressesService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.addressesService.query().subscribe((res: HttpResponse<IAddresses[]>) => {
      this.addresses = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInAddresses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAddresses) {
    return item.id;
  }

  registerChangeInAddresses() {
    this.eventSubscriber = this.eventManager.subscribe('addressesListModification', () => this.loadAll());
  }

  delete(addresses: IAddresses) {
    const modalRef = this.modalService.open(AddressesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.addresses = addresses;
  }
}
