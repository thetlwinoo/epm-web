import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISystemParameters } from 'app/shared/model/system-parameters.model';

@Component({
  selector: 'jhi-system-parameters-detail',
  templateUrl: './system-parameters-detail.component.html'
})
export class SystemParametersDetailComponent implements OnInit {
  systemParameters: ISystemParameters;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ systemParameters }) => {
      this.systemParameters = systemParameters;
    });
  }

  previousState() {
    window.history.back();
  }
}
