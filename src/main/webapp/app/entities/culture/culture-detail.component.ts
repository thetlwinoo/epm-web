import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICulture } from 'app/shared/model/culture.model';

@Component({
  selector: 'jhi-culture-detail',
  templateUrl: './culture-detail.component.html'
})
export class CultureDetailComponent implements OnInit {
  culture: ICulture;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ culture }) => {
      this.culture = culture;
    });
  }

  previousState() {
    window.history.back();
  }
}
