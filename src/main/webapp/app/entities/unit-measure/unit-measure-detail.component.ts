import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUnitMeasure } from 'app/shared/model/unit-measure.model';

@Component({
  selector: 'jhi-unit-measure-detail',
  templateUrl: './unit-measure-detail.component.html'
})
export class UnitMeasureDetailComponent implements OnInit {
  unitMeasure: IUnitMeasure;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ unitMeasure }) => {
      this.unitMeasure = unitMeasure;
    });
  }

  previousState() {
    window.history.back();
  }
}
