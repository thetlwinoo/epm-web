import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICountries } from 'app/shared/model/countries.model';
import { CountriesService } from './countries.service';

@Component({
  templateUrl: './countries-delete-dialog.component.html'
})
export class CountriesDeleteDialogComponent {
  countries: ICountries;

  constructor(protected countriesService: CountriesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.countriesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'countriesListModification',
        content: 'Deleted an countries'
      });
      this.activeModal.dismiss(true);
    });
  }
}
