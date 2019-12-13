import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from './customers.service';

@Component({
  templateUrl: './customers-delete-dialog.component.html'
})
export class CustomersDeleteDialogComponent {
  customers: ICustomers;

  constructor(protected customersService: CustomersService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.customersService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'customersListModification',
        content: 'Deleted an customers'
      });
      this.activeModal.dismiss(true);
    });
  }
}
