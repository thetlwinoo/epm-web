import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductChoice } from 'app/shared/model/product-choice.model';
import { ProductChoiceService } from './product-choice.service';
import { ProductChoiceDeleteDialogComponent } from './product-choice-delete-dialog.component';

@Component({
  selector: 'jhi-product-choice',
  templateUrl: './product-choice.component.html'
})
export class ProductChoiceComponent implements OnInit, OnDestroy {
  productChoices: IProductChoice[];
  eventSubscriber: Subscription;

  constructor(
    protected productChoiceService: ProductChoiceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.productChoiceService.query().subscribe((res: HttpResponse<IProductChoice[]>) => {
      this.productChoices = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProductChoices();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductChoice) {
    return item.id;
  }

  registerChangeInProductChoices() {
    this.eventSubscriber = this.eventManager.subscribe('productChoiceListModification', () => this.loadAll());
  }

  delete(productChoice: IProductChoice) {
    const modalRef = this.modalService.open(ProductChoiceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productChoice = productChoice;
  }
}
