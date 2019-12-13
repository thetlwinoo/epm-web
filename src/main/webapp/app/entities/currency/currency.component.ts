import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from './currency.service';
import { CurrencyDeleteDialogComponent } from './currency-delete-dialog.component';

@Component({
  selector: 'jhi-currency',
  templateUrl: './currency.component.html'
})
export class CurrencyComponent implements OnInit, OnDestroy {
  currencies: ICurrency[];
  eventSubscriber: Subscription;

  constructor(protected currencyService: CurrencyService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.currencyService.query().subscribe((res: HttpResponse<ICurrency[]>) => {
      this.currencies = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCurrencies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICurrency) {
    return item.id;
  }

  registerChangeInCurrencies() {
    this.eventSubscriber = this.eventManager.subscribe('currencyListModification', () => this.loadAll());
  }

  delete(currency: ICurrency) {
    const modalRef = this.modalService.open(CurrencyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.currency = currency;
  }
}
