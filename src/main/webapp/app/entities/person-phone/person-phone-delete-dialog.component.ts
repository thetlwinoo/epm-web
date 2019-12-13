import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonPhone } from 'app/shared/model/person-phone.model';
import { PersonPhoneService } from './person-phone.service';

@Component({
  templateUrl: './person-phone-delete-dialog.component.html'
})
export class PersonPhoneDeleteDialogComponent {
  personPhone: IPersonPhone;

  constructor(
    protected personPhoneService: PersonPhoneService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.personPhoneService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'personPhoneListModification',
        content: 'Deleted an personPhone'
      });
      this.activeModal.dismiss(true);
    });
  }
}
