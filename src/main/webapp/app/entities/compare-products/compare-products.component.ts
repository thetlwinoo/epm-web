import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompareProducts } from 'app/shared/model/compare-products.model';
import { CompareProductsService } from './compare-products.service';
import { CompareProductsDeleteDialogComponent } from './compare-products-delete-dialog.component';

@Component({
  selector: 'jhi-compare-products',
  templateUrl: './compare-products.component.html'
})
export class CompareProductsComponent implements OnInit, OnDestroy {
  compareProducts: ICompareProducts[];
  eventSubscriber: Subscription;

  constructor(
    protected compareProductsService: CompareProductsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.compareProductsService.query().subscribe((res: HttpResponse<ICompareProducts[]>) => {
      this.compareProducts = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCompareProducts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICompareProducts) {
    return item.id;
  }

  registerChangeInCompareProducts() {
    this.eventSubscriber = this.eventManager.subscribe('compareProductsListModification', () => this.loadAll());
  }

  delete(compareProducts: ICompareProducts) {
    const modalRef = this.modalService.open(CompareProductsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.compareProducts = compareProducts;
  }
}
