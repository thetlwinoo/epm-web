import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStateProvinces } from 'app/shared/model/state-provinces.model';
import { StateProvincesService } from './state-provinces.service';
import { StateProvincesDeleteDialogComponent } from './state-provinces-delete-dialog.component';

@Component({
  selector: 'jhi-state-provinces',
  templateUrl: './state-provinces.component.html'
})
export class StateProvincesComponent implements OnInit, OnDestroy {
  stateProvinces: IStateProvinces[];
  eventSubscriber: Subscription;

  constructor(
    protected stateProvincesService: StateProvincesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.stateProvincesService.query().subscribe((res: HttpResponse<IStateProvinces[]>) => {
      this.stateProvinces = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInStateProvinces();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStateProvinces) {
    return item.id;
  }

  registerChangeInStateProvinces() {
    this.eventSubscriber = this.eventManager.subscribe('stateProvincesListModification', () => this.loadAll());
  }

  delete(stateProvinces: IStateProvinces) {
    const modalRef = this.modalService.open(StateProvincesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.stateProvinces = stateProvinces;
  }
}
