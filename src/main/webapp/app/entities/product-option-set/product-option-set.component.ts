import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductOptionSet } from 'app/shared/model/product-option-set.model';
import { ProductOptionSetService } from './product-option-set.service';
import { ProductOptionSetDeleteDialogComponent } from './product-option-set-delete-dialog.component';

@Component({
  selector: 'jhi-product-option-set',
  templateUrl: './product-option-set.component.html'
})
export class ProductOptionSetComponent implements OnInit, OnDestroy {
  productOptionSets: IProductOptionSet[];
  eventSubscriber: Subscription;

  constructor(
    protected productOptionSetService: ProductOptionSetService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.productOptionSetService.query().subscribe((res: HttpResponse<IProductOptionSet[]>) => {
      this.productOptionSets = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProductOptionSets();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductOptionSet) {
    return item.id;
  }

  registerChangeInProductOptionSets() {
    this.eventSubscriber = this.eventManager.subscribe('productOptionSetListModification', () => this.loadAll());
  }

  delete(productOptionSet: IProductOptionSet) {
    const modalRef = this.modalService.open(ProductOptionSetDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productOptionSet = productOptionSet;
  }
}
