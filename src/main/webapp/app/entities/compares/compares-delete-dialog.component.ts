import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompares } from 'app/shared/model/compares.model';
import { ComparesService } from './compares.service';

@Component({
  templateUrl: './compares-delete-dialog.component.html'
})
export class ComparesDeleteDialogComponent {
  compares: ICompares;

  constructor(protected comparesService: ComparesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.comparesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'comparesListModification',
        content: 'Deleted an compares'
      });
      this.activeModal.dismiss(true);
    });
  }
}
