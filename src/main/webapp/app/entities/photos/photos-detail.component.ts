import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPhotos } from 'app/shared/model/photos.model';

@Component({
  selector: 'jhi-photos-detail',
  templateUrl: './photos-detail.component.html'
})
export class PhotosDetailComponent implements OnInit {
  photos: IPhotos;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ photos }) => {
      this.photos = photos;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
