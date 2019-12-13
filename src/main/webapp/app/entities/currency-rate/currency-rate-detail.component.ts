import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICurrencyRate } from 'app/shared/model/currency-rate.model';

@Component({
  selector: 'jhi-currency-rate-detail',
  templateUrl: './currency-rate-detail.component.html'
})
export class CurrencyRateDetailComponent implements OnInit {
  currencyRate: ICurrencyRate;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ currencyRate }) => {
      this.currencyRate = currencyRate;
    });
  }

  previousState() {
    window.history.back();
  }
}
