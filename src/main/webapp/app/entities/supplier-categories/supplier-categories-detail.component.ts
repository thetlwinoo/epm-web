import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplierCategories } from 'app/shared/model/supplier-categories.model';

@Component({
  selector: 'jhi-supplier-categories-detail',
  templateUrl: './supplier-categories-detail.component.html'
})
export class SupplierCategoriesDetailComponent implements OnInit {
  supplierCategories: ISupplierCategories;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ supplierCategories }) => {
      this.supplierCategories = supplierCategories;
    });
  }

  previousState() {
    window.history.back();
  }
}
