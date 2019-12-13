import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductTags } from 'app/shared/model/product-tags.model';
import { ProductTagsService } from './product-tags.service';
import { ProductTagsDeleteDialogComponent } from './product-tags-delete-dialog.component';

@Component({
  selector: 'jhi-product-tags',
  templateUrl: './product-tags.component.html'
})
export class ProductTagsComponent implements OnInit, OnDestroy {
  productTags: IProductTags[];
  eventSubscriber: Subscription;

  constructor(
    protected productTagsService: ProductTagsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.productTagsService.query().subscribe((res: HttpResponse<IProductTags[]>) => {
      this.productTags = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProductTags();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductTags) {
    return item.id;
  }

  registerChangeInProductTags() {
    this.eventSubscriber = this.eventManager.subscribe('productTagsListModification', () => this.loadAll());
  }

  delete(productTags: IProductTags) {
    const modalRef = this.modalService.open(ProductTagsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productTags = productTags;
  }
}
