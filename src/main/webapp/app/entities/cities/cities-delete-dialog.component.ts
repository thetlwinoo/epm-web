import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICities } from 'app/shared/model/cities.model';
import { CitiesService } from './cities.service';

@Component({
  templateUrl: './cities-delete-dialog.component.html'
})
export class CitiesDeleteDialogComponent {
  cities: ICities;

  constructor(protected citiesService: CitiesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.citiesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'citiesListModification',
        content: 'Deleted an cities'
      });
      this.activeModal.dismiss(true);
    });
  }
}
