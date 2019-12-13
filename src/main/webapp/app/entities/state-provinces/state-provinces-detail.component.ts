import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStateProvinces } from 'app/shared/model/state-provinces.model';

@Component({
  selector: 'jhi-state-provinces-detail',
  templateUrl: './state-provinces-detail.component.html'
})
export class StateProvincesDetailComponent implements OnInit {
  stateProvinces: IStateProvinces;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stateProvinces }) => {
      this.stateProvinces = stateProvinces;
    });
  }

  previousState() {
    window.history.back();
  }
}
