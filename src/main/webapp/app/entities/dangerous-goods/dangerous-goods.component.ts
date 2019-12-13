import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDangerousGoods } from 'app/shared/model/dangerous-goods.model';
import { DangerousGoodsService } from './dangerous-goods.service';
import { DangerousGoodsDeleteDialogComponent } from './dangerous-goods-delete-dialog.component';

@Component({
  selector: 'jhi-dangerous-goods',
  templateUrl: './dangerous-goods.component.html'
})
export class DangerousGoodsComponent implements OnInit, OnDestroy {
  dangerousGoods: IDangerousGoods[];
  eventSubscriber: Subscription;

  constructor(
    protected dangerousGoodsService: DangerousGoodsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.dangerousGoodsService.query().subscribe((res: HttpResponse<IDangerousGoods[]>) => {
      this.dangerousGoods = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInDangerousGoods();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDangerousGoods) {
    return item.id;
  }

  registerChangeInDangerousGoods() {
    this.eventSubscriber = this.eventManager.subscribe('dangerousGoodsListModification', () => this.loadAll());
  }

  delete(dangerousGoods: IDangerousGoods) {
    const modalRef = this.modalService.open(DangerousGoodsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dangerousGoods = dangerousGoods;
  }
}
