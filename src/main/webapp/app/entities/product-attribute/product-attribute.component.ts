import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductAttribute } from 'app/shared/model/product-attribute.model';
import { ProductAttributeService } from './product-attribute.service';
import { ProductAttributeDeleteDialogComponent } from './product-attribute-delete-dialog.component';

@Component({
  selector: 'jhi-product-attribute',
  templateUrl: './product-attribute.component.html'
})
export class ProductAttributeComponent implements OnInit, OnDestroy {
  productAttributes: IProductAttribute[];
  eventSubscriber: Subscription;

  constructor(
    protected productAttributeService: ProductAttributeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.productAttributeService.query().subscribe((res: HttpResponse<IProductAttribute[]>) => {
      this.productAttributes = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProductAttributes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductAttribute) {
    return item.id;
  }

  registerChangeInProductAttributes() {
    this.eventSubscriber = this.eventManager.subscribe('productAttributeListModification', () => this.loadAll());
  }

  delete(productAttribute: IProductAttribute) {
    const modalRef = this.modalService.open(ProductAttributeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productAttribute = productAttribute;
  }
}
