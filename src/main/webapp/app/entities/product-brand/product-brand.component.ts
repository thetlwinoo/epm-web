import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductBrand } from 'app/shared/model/product-brand.model';
import { ProductBrandService } from './product-brand.service';
import { ProductBrandDeleteDialogComponent } from './product-brand-delete-dialog.component';

@Component({
  selector: 'jhi-product-brand',
  templateUrl: './product-brand.component.html'
})
export class ProductBrandComponent implements OnInit, OnDestroy {
  productBrands: IProductBrand[];
  eventSubscriber: Subscription;

  constructor(
    protected productBrandService: ProductBrandService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.productBrandService.query().subscribe((res: HttpResponse<IProductBrand[]>) => {
      this.productBrands = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProductBrands();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductBrand) {
    return item.id;
  }

  registerChangeInProductBrands() {
    this.eventSubscriber = this.eventManager.subscribe('productBrandListModification', () => this.loadAll());
  }

  delete(productBrand: IProductBrand) {
    const modalRef = this.modalService.open(ProductBrandDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productBrand = productBrand;
  }
}
