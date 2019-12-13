import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICulture } from 'app/shared/model/culture.model';
import { CultureService } from './culture.service';

@Component({
  templateUrl: './culture-delete-dialog.component.html'
})
export class CultureDeleteDialogComponent {
  culture: ICulture;

  constructor(protected cultureService: CultureService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cultureService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'cultureListModification',
        content: 'Deleted an culture'
      });
      this.activeModal.dismiss(true);
    });
  }
}
