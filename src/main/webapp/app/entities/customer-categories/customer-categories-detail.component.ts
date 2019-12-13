import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomerCategories } from 'app/shared/model/customer-categories.model';

@Component({
  selector: 'jhi-customer-categories-detail',
  templateUrl: './customer-categories-detail.component.html'
})
export class CustomerCategoriesDetailComponent implements OnInit {
  customerCategories: ICustomerCategories;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ customerCategories }) => {
      this.customerCategories = customerCategories;
    });
  }

  previousState() {
    window.history.back();
  }
}
