import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUnitMeasure } from 'app/shared/model/unit-measure.model';
import { UnitMeasureService } from './unit-measure.service';

@Component({
  templateUrl: './unit-measure-delete-dialog.component.html'
})
export class UnitMeasureDeleteDialogComponent {
  unitMeasure: IUnitMeasure;

  constructor(
    protected unitMeasureService: UnitMeasureService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.unitMeasureService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'unitMeasureListModification',
        content: 'Deleted an unitMeasure'
      });
      this.activeModal.dismiss(true);
    });
  }
}
