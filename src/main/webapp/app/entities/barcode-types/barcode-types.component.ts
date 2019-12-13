import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBarcodeTypes } from 'app/shared/model/barcode-types.model';
import { BarcodeTypesService } from './barcode-types.service';
import { BarcodeTypesDeleteDialogComponent } from './barcode-types-delete-dialog.component';

@Component({
  selector: 'jhi-barcode-types',
  templateUrl: './barcode-types.component.html'
})
export class BarcodeTypesComponent implements OnInit, OnDestroy {
  barcodeTypes: IBarcodeTypes[];
  eventSubscriber: Subscription;

  constructor(
    protected barcodeTypesService: BarcodeTypesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.barcodeTypesService.query().subscribe((res: HttpResponse<IBarcodeTypes[]>) => {
      this.barcodeTypes = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInBarcodeTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBarcodeTypes) {
    return item.id;
  }

  registerChangeInBarcodeTypes() {
    this.eventSubscriber = this.eventManager.subscribe('barcodeTypesListModification', () => this.loadAll());
  }

  delete(barcodeTypes: IBarcodeTypes) {
    const modalRef = this.modalService.open(BarcodeTypesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.barcodeTypes = barcodeTypes;
  }
}
