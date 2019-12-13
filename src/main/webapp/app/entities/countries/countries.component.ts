import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICountries } from 'app/shared/model/countries.model';
import { CountriesService } from './countries.service';
import { CountriesDeleteDialogComponent } from './countries-delete-dialog.component';

@Component({
  selector: 'jhi-countries',
  templateUrl: './countries.component.html'
})
export class CountriesComponent implements OnInit, OnDestroy {
  countries: ICountries[];
  eventSubscriber: Subscription;

  constructor(protected countriesService: CountriesService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.countriesService.query().subscribe((res: HttpResponse<ICountries[]>) => {
      this.countries = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCountries();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICountries) {
    return item.id;
  }

  registerChangeInCountries() {
    this.eventSubscriber = this.eventManager.subscribe('countriesListModification', () => this.loadAll());
  }

  delete(countries: ICountries) {
    const modalRef = this.modalService.open(CountriesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.countries = countries;
  }
}
