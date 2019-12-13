import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from './people.service';

@Component({
  templateUrl: './people-delete-dialog.component.html'
})
export class PeopleDeleteDialogComponent {
  people: IPeople;

  constructor(protected peopleService: PeopleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.peopleService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'peopleListModification',
        content: 'Deleted an people'
      });
      this.activeModal.dismiss(true);
    });
  }
}
