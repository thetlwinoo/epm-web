import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompares } from 'app/shared/model/compares.model';

@Component({
  selector: 'jhi-compares-detail',
  templateUrl: './compares-detail.component.html'
})
export class ComparesDetailComponent implements OnInit {
  compares: ICompares;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compares }) => {
      this.compares = compares;
    });
  }

  previousState() {
    window.history.back();
  }
}
