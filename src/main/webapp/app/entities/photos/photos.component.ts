import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhotos } from 'app/shared/model/photos.model';
import { PhotosService } from './photos.service';
import { PhotosDeleteDialogComponent } from './photos-delete-dialog.component';

@Component({
  selector: 'jhi-photos',
  templateUrl: './photos.component.html'
})
export class PhotosComponent implements OnInit, OnDestroy {
  photos: IPhotos[];
  eventSubscriber: Subscription;

  constructor(
    protected photosService: PhotosService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.photosService.query().subscribe((res: HttpResponse<IPhotos[]>) => {
      this.photos = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPhotos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPhotos) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInPhotos() {
    this.eventSubscriber = this.eventManager.subscribe('photosListModification', () => this.loadAll());
  }

  delete(photos: IPhotos) {
    const modalRef = this.modalService.open(PhotosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.photos = photos;
  }
}
