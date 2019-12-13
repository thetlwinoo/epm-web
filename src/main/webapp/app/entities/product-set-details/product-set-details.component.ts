import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductSetDetails } from 'app/shared/model/product-set-details.model';
import { ProductSetDetailsService } from './product-set-details.service';
import { ProductSetDetailsDeleteDialogComponent } from './product-set-details-delete-dialog.component';

@Component({
  selector: 'jhi-product-set-details',
  templateUrl: './product-set-details.component.html'
})
export class ProductSetDetailsComponent implements OnInit, OnDestroy {
  productSetDetails: IProductSetDetails[];
  eventSubscriber: Subscription;

  constructor(
    protected productSetDetailsService: ProductSetDetailsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.productSetDetailsService.query().subscribe((res: HttpResponse<IProductSetDetails[]>) => {
      this.productSetDetails = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProductSetDetails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductSetDetails) {
    return item.id;
  }

  registerChangeInProductSetDetails() {
    this.eventSubscriber = this.eventManager.subscribe('productSetDetailsListModification', () => this.loadAll());
  }

  delete(productSetDetails: IProductSetDetails) {
    const modalRef = this.modalService.open(ProductSetDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productSetDetails = productSetDetails;
  }
}
