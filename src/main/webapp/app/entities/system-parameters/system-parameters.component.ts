import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISystemParameters } from 'app/shared/model/system-parameters.model';
import { SystemParametersService } from './system-parameters.service';
import { SystemParametersDeleteDialogComponent } from './system-parameters-delete-dialog.component';

@Component({
  selector: 'jhi-system-parameters',
  templateUrl: './system-parameters.component.html'
})
export class SystemParametersComponent implements OnInit, OnDestroy {
  systemParameters: ISystemParameters[];
  eventSubscriber: Subscription;

  constructor(
    protected systemParametersService: SystemParametersService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.systemParametersService.query().subscribe((res: HttpResponse<ISystemParameters[]>) => {
      this.systemParameters = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInSystemParameters();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISystemParameters) {
    return item.id;
  }

  registerChangeInSystemParameters() {
    this.eventSubscriber = this.eventManager.subscribe('systemParametersListModification', () => this.loadAll());
  }

  delete(systemParameters: ISystemParameters) {
    const modalRef = this.modalService.open(SystemParametersDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.systemParameters = systemParameters;
  }
}
