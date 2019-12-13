import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IBuyingGroups, BuyingGroups } from 'app/shared/model/buying-groups.model';
import { BuyingGroupsService } from './buying-groups.service';

@Component({
  selector: 'jhi-buying-groups-update',
  templateUrl: './buying-groups-update.component.html'
})
export class BuyingGroupsUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]]
  });

  constructor(protected buyingGroupsService: BuyingGroupsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ buyingGroups }) => {
      this.updateForm(buyingGroups);
    });
  }

  updateForm(buyingGroups: IBuyingGroups) {
    this.editForm.patchValue({
      id: buyingGroups.id,
      name: buyingGroups.name,
      validFrom: buyingGroups.validFrom != null ? buyingGroups.validFrom.format(DATE_TIME_FORMAT) : null,
      validTo: buyingGroups.validTo != null ? buyingGroups.validTo.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const buyingGroups = this.createFromForm();
    if (buyingGroups.id !== undefined) {
      this.subscribeToSaveResponse(this.buyingGroupsService.update(buyingGroups));
    } else {
      this.subscribeToSaveResponse(this.buyingGroupsService.create(buyingGroups));
    }
  }

  private createFromForm(): IBuyingGroups {
    return {
      ...new BuyingGroups(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      validFrom:
        this.editForm.get(['validFrom']).value != null ? moment(this.editForm.get(['validFrom']).value, DATE_TIME_FORMAT) : undefined,
      validTo: this.editForm.get(['validTo']).value != null ? moment(this.editForm.get(['validTo']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBuyingGroups>>) {
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
