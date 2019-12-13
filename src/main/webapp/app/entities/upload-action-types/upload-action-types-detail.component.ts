import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUploadActionTypes } from 'app/shared/model/upload-action-types.model';

@Component({
  selector: 'jhi-upload-action-types-detail',
  templateUrl: './upload-action-types-detail.component.html'
})
export class UploadActionTypesDetailComponent implements OnInit {
  uploadActionTypes: IUploadActionTypes;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ uploadActionTypes }) => {
      this.uploadActionTypes = uploadActionTypes;
    });
  }

  previousState() {
    window.history.back();
  }
}
