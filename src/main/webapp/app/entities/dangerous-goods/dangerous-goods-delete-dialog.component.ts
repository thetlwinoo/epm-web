import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDangerousGoods } from 'app/shared/model/dangerous-goods.model';
import { DangerousGoodsService } from './dangerous-goods.service';

@Component({
  templateUrl: './dangerous-goods-delete-dialog.component.html'
})
export class DangerousGoodsDeleteDialogComponent {
  dangerousGoods: IDangerousGoods;

  constructor(
    protected dangerousGoodsService: DangerousGoodsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dangerousGoodsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'dangerousGoodsListModification',
        content: 'Deleted an dangerousGoods'
      });
      this.activeModal.dismiss(true);
    });
  }
}
