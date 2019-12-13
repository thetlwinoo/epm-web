import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomerCategories } from 'app/shared/model/customer-categories.model';
import { CustomerCategoriesService } from './customer-categories.service';
import { CustomerCategoriesDeleteDialogComponent } from './customer-categories-delete-dialog.component';

@Component({
  selector: 'jhi-customer-categories',
  templateUrl: './customer-categories.component.html'
})
export class CustomerCategoriesComponent implements OnInit, OnDestroy {
  customerCategories: ICustomerCategories[];
  eventSubscriber: Subscription;

  constructor(
    protected customerCategoriesService: CustomerCategoriesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.customerCategoriesService.query().subscribe((res: HttpResponse<ICustomerCategories[]>) => {
      this.customerCategories = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCustomerCategories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICustomerCategories) {
    return item.id;
  }

  registerChangeInCustomerCategories() {
    this.eventSubscriber = this.eventManager.subscribe('customerCategoriesListModification', () => this.loadAll());
  }

  delete(customerCategories: ICustomerCategories) {
    const modalRef = this.modalService.open(CustomerCategoriesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.customerCategories = customerCategories;
  }
}
