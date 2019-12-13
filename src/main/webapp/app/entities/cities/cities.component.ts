import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICities } from 'app/shared/model/cities.model';
import { CitiesService } from './cities.service';
import { CitiesDeleteDialogComponent } from './cities-delete-dialog.component';

@Component({
  selector: 'jhi-cities',
  templateUrl: './cities.component.html'
})
export class CitiesComponent implements OnInit, OnDestroy {
  cities: ICities[];
  eventSubscriber: Subscription;

  constructor(protected citiesService: CitiesService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.citiesService.query().subscribe((res: HttpResponse<ICities[]>) => {
      this.cities = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICities) {
    return item.id;
  }

  registerChangeInCities() {
    this.eventSubscriber = this.eventManager.subscribe('citiesListModification', () => this.loadAll());
  }

  delete(cities: ICities) {
    const modalRef = this.modalService.open(CitiesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cities = cities;
  }
}
