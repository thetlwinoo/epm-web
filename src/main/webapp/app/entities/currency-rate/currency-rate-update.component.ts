import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ICurrencyRate, CurrencyRate } from 'app/shared/model/currency-rate.model';
import { CurrencyRateService } from './currency-rate.service';

@Component({
  selector: 'jhi-currency-rate-update',
  templateUrl: './currency-rate-update.component.html'
})
export class CurrencyRateUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    currencyRateDate: [null, [Validators.required]],
    fromcode: [],
    tocode: [],
    averageRate: [],
    endOfDayRate: [],
    lastEditedBy: [],
    lastEditedWhen: []
  });

  constructor(protected currencyRateService: CurrencyRateService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ currencyRate }) => {
      this.updateForm(currencyRate);
    });
  }

  updateForm(currencyRate: ICurrencyRate) {
    this.editForm.patchValue({
      id: currencyRate.id,
      currencyRateDate: currencyRate.currencyRateDate != null ? currencyRate.currencyRateDate.format(DATE_TIME_FORMAT) : null,
      fromcode: currencyRate.fromcode,
      tocode: currencyRate.tocode,
      averageRate: currencyRate.averageRate,
      endOfDayRate: currencyRate.endOfDayRate,
      lastEditedBy: currencyRate.lastEditedBy,
      lastEditedWhen: currencyRate.lastEditedWhen != null ? currencyRate.lastEditedWhen.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const currencyRate = this.createFromForm();
    if (currencyRate.id !== undefined) {
      this.subscribeToSaveResponse(this.currencyRateService.update(currencyRate));
    } else {
      this.subscribeToSaveResponse(this.currencyRateService.create(currencyRate));
    }
  }

  private createFromForm(): ICurrencyRate {
    return {
      ...new CurrencyRate(),
      id: this.editForm.get(['id']).value,
      currencyRateDate:
        this.editForm.get(['currencyRateDate']).value != null
          ? moment(this.editForm.get(['currencyRateDate']).value, DATE_TIME_FORMAT)
          : undefined,
      fromcode: this.editForm.get(['fromcode']).value,
      tocode: this.editForm.get(['tocode']).value,
      averageRate: this.editForm.get(['averageRate']).value,
      endOfDayRate: this.editForm.get(['endOfDayRate']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurrencyRate>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
