import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductOption } from 'app/shared/model/product-option.model';
import { ProductOptionService } from './product-option.service';
import { ProductOptionDeleteDialogComponent } from './product-option-delete-dialog.component';

@Component({
  selector: 'jhi-product-option',
  templateUrl: './product-option.component.html'
})
export class ProductOptionComponent implements OnInit, OnDestroy {
  productOptions: IProductOption[];
  eventSubscriber: Subscription;

  constructor(
    protected productOptionService: ProductOptionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.productOptionService.query().subscribe((res: HttpResponse<IProductOption[]>) => {
      this.productOptions = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProductOptions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductOption) {
    return item.id;
  }

  registerChangeInProductOptions() {
    this.eventSubscriber = this.eventManager.subscribe('productOptionListModification', () => this.loadAll());
  }

  delete(productOption: IProductOption) {
    const modalRef = this.modalService.open(ProductOptionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productOption = productOption;
  }
}
