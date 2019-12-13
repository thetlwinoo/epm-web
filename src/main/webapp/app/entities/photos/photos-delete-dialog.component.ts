import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPhotos } from 'app/shared/model/photos.model';
import { PhotosService } from './photos.service';

@Component({
  templateUrl: './photos-delete-dialog.component.html'
})
export class PhotosDeleteDialogComponent {
  photos: IPhotos;

  constructor(protected photosService: PhotosService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.photosService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'photosListModification',
        content: 'Deleted an photos'
      });
      this.activeModal.dismiss(true);
    });
  }
}
