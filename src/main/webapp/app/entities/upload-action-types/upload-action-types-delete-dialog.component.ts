import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUploadActionTypes } from 'app/shared/model/upload-action-types.model';
import { UploadActionTypesService } from './upload-action-types.service';

@Component({
  templateUrl: './upload-action-types-delete-dialog.component.html'
})
export class UploadActionTypesDeleteDialogComponent {
  uploadActionTypes: IUploadActionTypes;

  constructor(
    protected uploadActionTypesService: UploadActionTypesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.uploadActionTypesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'uploadActionTypesListModification',
        content: 'Deleted an uploadActionTypes'
      });
      this.activeModal.dismiss(true);
    });
  }
}
