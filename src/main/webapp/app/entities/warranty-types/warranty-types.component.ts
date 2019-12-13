import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWarrantyTypes } from 'app/shared/model/warranty-types.model';
import { WarrantyTypesService } from './warranty-types.service';
import { WarrantyTypesDeleteDialogComponent } from './warranty-types-delete-dialog.component';

@Component({
  selector: 'jhi-warranty-types',
  templateUrl: './warranty-types.component.html'
})
export class WarrantyTypesComponent implements OnInit, OnDestroy {
  warrantyTypes: IWarrantyTypes[];
  eventSubscriber: Subscription;

  constructor(
    protected warrantyTypesService: WarrantyTypesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.warrantyTypesService.query().subscribe((res: HttpResponse<IWarrantyTypes[]>) => {
      this.warrantyTypes = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInWarrantyTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IWarrantyTypes) {
    return item.id;
  }

  registerChangeInWarrantyTypes() {
    this.eventSubscriber = this.eventManager.subscribe('warrantyTypesListModification', () => this.loadAll());
  }

  delete(warrantyTypes: IWarrantyTypes) {
    const modalRef = this.modalService.open(WarrantyTypesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.warrantyTypes = warrantyTypes;
  }
}
