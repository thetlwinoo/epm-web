import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPhoneNumberType } from 'app/shared/model/phone-number-type.model';
import { PhoneNumberTypeService } from './phone-number-type.service';

@Component({
  templateUrl: './phone-number-type-delete-dialog.component.html'
})
export class PhoneNumberTypeDeleteDialogComponent {
  phoneNumberType: IPhoneNumberType;

  constructor(
    protected phoneNumberTypeService: PhoneNumberTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.phoneNumberTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'phoneNumberTypeListModification',
        content: 'Deleted an phoneNumberType'
      });
      this.activeModal.dismiss(true);
    });
  }
}
