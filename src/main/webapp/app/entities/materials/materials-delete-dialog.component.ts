import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMaterials } from 'app/shared/model/materials.model';
import { MaterialsService } from './materials.service';

@Component({
  templateUrl: './materials-delete-dialog.component.html'
})
export class MaterialsDeleteDialogComponent {
  materials: IMaterials;

  constructor(protected materialsService: MaterialsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.materialsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'materialsListModification',
        content: 'Deleted an materials'
      });
      this.activeModal.dismiss(true);
    });
  }
}
