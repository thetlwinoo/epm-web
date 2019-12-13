import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICurrencyRate } from 'app/shared/model/currency-rate.model';
import { CurrencyRateService } from './currency-rate.service';
import { CurrencyRateDeleteDialogComponent } from './currency-rate-delete-dialog.component';

@Component({
  selector: 'jhi-currency-rate',
  templateUrl: './currency-rate.component.html'
})
export class CurrencyRateComponent implements OnInit, OnDestroy {
  currencyRates: ICurrencyRate[];
  eventSubscriber: Subscription;

  constructor(
    protected currencyRateService: CurrencyRateService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.currencyRateService.query().subscribe((res: HttpResponse<ICurrencyRate[]>) => {
      this.currencyRates = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCurrencyRates();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICurrencyRate) {
    return item.id;
  }

  registerChangeInCurrencyRates() {
    this.eventSubscriber = this.eventManager.subscribe('currencyRateListModification', () => this.loadAll());
  }

  delete(currencyRate: ICurrencyRate) {
    const modalRef = this.modalService.open(CurrencyRateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.currencyRate = currencyRate;
  }
}
