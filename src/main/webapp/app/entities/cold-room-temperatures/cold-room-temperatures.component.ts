import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IColdRoomTemperatures } from 'app/shared/model/cold-room-temperatures.model';
import { ColdRoomTemperaturesService } from './cold-room-temperatures.service';
import { ColdRoomTemperaturesDeleteDialogComponent } from './cold-room-temperatures-delete-dialog.component';

@Component({
  selector: 'jhi-cold-room-temperatures',
  templateUrl: './cold-room-temperatures.component.html'
})
export class ColdRoomTemperaturesComponent implements OnInit, OnDestroy {
  coldRoomTemperatures: IColdRoomTemperatures[];
  eventSubscriber: Subscription;

  constructor(
    protected coldRoomTemperaturesService: ColdRoomTemperaturesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.coldRoomTemperaturesService.query().subscribe((res: HttpResponse<IColdRoomTemperatures[]>) => {
      this.coldRoomTemperatures = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInColdRoomTemperatures();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IColdRoomTemperatures) {
    return item.id;
  }

  registerChangeInColdRoomTemperatures() {
    this.eventSubscriber = this.eventManager.subscribe('coldRoomTemperaturesListModification', () => this.loadAll());
  }

  delete(coldRoomTemperatures: IColdRoomTemperatures) {
    const modalRef = this.modalService.open(ColdRoomTemperaturesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.coldRoomTemperatures = coldRoomTemperatures;
  }
}
