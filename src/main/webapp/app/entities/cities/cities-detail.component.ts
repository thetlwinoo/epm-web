import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICities } from 'app/shared/model/cities.model';

@Component({
  selector: 'jhi-cities-detail',
  templateUrl: './cities-detail.component.html'
})
export class CitiesDetailComponent implements OnInit {
  cities: ICities;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cities }) => {
      this.cities = cities;
    });
  }

  previousState() {
    window.history.back();
  }
}
