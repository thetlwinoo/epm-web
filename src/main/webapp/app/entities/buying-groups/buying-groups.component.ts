import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBuyingGroups } from 'app/shared/model/buying-groups.model';
import { BuyingGroupsService } from './buying-groups.service';
import { BuyingGroupsDeleteDialogComponent } from './buying-groups-delete-dialog.component';

@Component({
  selector: 'jhi-buying-groups',
  templateUrl: './buying-groups.component.html'
})
export class BuyingGroupsComponent implements OnInit, OnDestroy {
  buyingGroups: IBuyingGroups[];
  eventSubscriber: Subscription;

  constructor(
    protected buyingGroupsService: BuyingGroupsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.buyingGroupsService.query().subscribe((res: HttpResponse<IBuyingGroups[]>) => {
      this.buyingGroups = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInBuyingGroups();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBuyingGroups) {
    return item.id;
  }

  registerChangeInBuyingGroups() {
    this.eventSubscriber = this.eventManager.subscribe('buyingGroupsListModification', () => this.loadAll());
  }

  delete(buyingGroups: IBuyingGroups) {
    const modalRef = this.modalService.open(BuyingGroupsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.buyingGroups = buyingGroups;
  }
}
