import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpecialDeals } from 'app/shared/model/special-deals.model';

@Component({
  selector: 'jhi-special-deals-detail',
  templateUrl: './special-deals-detail.component.html'
})
export class SpecialDealsDetailComponent implements OnInit {
  specialDeals: ISpecialDeals;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ specialDeals }) => {
      this.specialDeals = specialDeals;
    });
  }

  previousState() {
    window.history.back();
  }
}
