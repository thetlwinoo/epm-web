import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBusinessEntityContact } from 'app/shared/model/business-entity-contact.model';
import { BusinessEntityContactService } from './business-entity-contact.service';

@Component({
  templateUrl: './business-entity-contact-delete-dialog.component.html'
})
export class BusinessEntityContactDeleteDialogComponent {
  businessEntityContact: IBusinessEntityContact;

  constructor(
    protected businessEntityContactService: BusinessEntityContactService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.businessEntityContactService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'businessEntityContactListModification',
        content: 'Deleted an businessEntityContact'
      });
      this.activeModal.dismiss(true);
    });
  }
}
