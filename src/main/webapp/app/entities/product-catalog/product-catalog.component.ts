import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductCatalog } from 'app/shared/model/product-catalog.model';
import { ProductCatalogService } from './product-catalog.service';
import { ProductCatalogDeleteDialogComponent } from './product-catalog-delete-dialog.component';

@Component({
  selector: 'jhi-product-catalog',
  templateUrl: './product-catalog.component.html'
})
export class ProductCatalogComponent implements OnInit, OnDestroy {
  productCatalogs: IProductCatalog[];
  eventSubscriber: Subscription;

  constructor(
    protected productCatalogService: ProductCatalogService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.productCatalogService.query().subscribe((res: HttpResponse<IProductCatalog[]>) => {
      this.productCatalogs = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProductCatalogs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductCatalog) {
    return item.id;
  }

  registerChangeInProductCatalogs() {
    this.eventSubscriber = this.eventManager.subscribe('productCatalogListModification', () => this.loadAll());
  }

  delete(productCatalog: IProductCatalog) {
    const modalRef = this.modalService.open(ProductCatalogDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productCatalog = productCatalog;
  }
}
