import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductSet } from 'app/shared/model/product-set.model';
import { ProductSetService } from './product-set.service';
import { ProductSetDeleteDialogComponent } from './product-set-delete-dialog.component';

@Component({
  selector: 'jhi-product-set',
  templateUrl: './product-set.component.html'
})
export class ProductSetComponent implements OnInit, OnDestroy {
  productSets: IProductSet[];
  eventSubscriber: Subscription;

  constructor(protected productSetService: ProductSetService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.productSetService.query().subscribe((res: HttpResponse<IProductSet[]>) => {
      this.productSets = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProductSets();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductSet) {
    return item.id;
  }

  registerChangeInProductSets() {
    this.eventSubscriber = this.eventManager.subscribe('productSetListModification', () => this.loadAll());
  }

  delete(productSet: IProductSet) {
    const modalRef = this.modalService.open(ProductSetDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productSet = productSet;
  }
}
