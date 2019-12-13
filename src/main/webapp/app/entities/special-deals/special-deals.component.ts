import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpecialDeals } from 'app/shared/model/special-deals.model';
import { SpecialDealsService } from './special-deals.service';
import { SpecialDealsDeleteDialogComponent } from './special-deals-delete-dialog.component';

@Component({
  selector: 'jhi-special-deals',
  templateUrl: './special-deals.component.html'
})
export class SpecialDealsComponent implements OnInit, OnDestroy {
  specialDeals: ISpecialDeals[];
  eventSubscriber: Subscription;

  constructor(
    protected specialDealsService: SpecialDealsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.specialDealsService.query().subscribe((res: HttpResponse<ISpecialDeals[]>) => {
      this.specialDeals = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInSpecialDeals();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISpecialDeals) {
    return item.id;
  }

  registerChangeInSpecialDeals() {
    this.eventSubscriber = this.eventManager.subscribe('specialDealsListModification', () => this.loadAll());
  }

  delete(specialDeals: ISpecialDeals) {
    const modalRef = this.modalService.open(SpecialDealsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.specialDeals = specialDeals;
  }
}
