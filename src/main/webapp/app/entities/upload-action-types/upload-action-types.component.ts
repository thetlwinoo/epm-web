import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUploadActionTypes } from 'app/shared/model/upload-action-types.model';
import { UploadActionTypesService } from './upload-action-types.service';
import { UploadActionTypesDeleteDialogComponent } from './upload-action-types-delete-dialog.component';

@Component({
  selector: 'jhi-upload-action-types',
  templateUrl: './upload-action-types.component.html'
})
export class UploadActionTypesComponent implements OnInit, OnDestroy {
  uploadActionTypes: IUploadActionTypes[];
  eventSubscriber: Subscription;

  constructor(
    protected uploadActionTypesService: UploadActionTypesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.uploadActionTypesService.query().subscribe((res: HttpResponse<IUploadActionTypes[]>) => {
      this.uploadActionTypes = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInUploadActionTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUploadActionTypes) {
    return item.id;
  }

  registerChangeInUploadActionTypes() {
    this.eventSubscriber = this.eventManager.subscribe('uploadActionTypesListModification', () => this.loadAll());
  }

  delete(uploadActionTypes: IUploadActionTypes) {
    const modalRef = this.modalService.open(UploadActionTypesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.uploadActionTypes = uploadActionTypes;
  }
}
