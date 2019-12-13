import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IUploadTransactions, UploadTransactions } from 'app/shared/model/upload-transactions.model';
import { UploadTransactionsService } from './upload-transactions.service';
import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers/suppliers.service';
import { IUploadActionTypes } from 'app/shared/model/upload-action-types.model';
import { UploadActionTypesService } from 'app/entities/upload-action-types/upload-action-types.service';

@Component({
  selector: 'jhi-upload-transactions-update',
  templateUrl: './upload-transactions-update.component.html'
})
export class UploadTransactionsUpdateComponent implements OnInit {
  isSaving: boolean;

  suppliers: ISuppliers[];

  uploadactiontypes: IUploadActionTypes[];

  editForm = this.fb.group({
    id: [],
    fileName: [],
    templateUrl: [],
    status: [],
    generatedCode: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    supplierId: [],
    actionTypeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected uploadTransactionsService: UploadTransactionsService,
    protected suppliersService: SuppliersService,
    protected uploadActionTypesService: UploadActionTypesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ uploadTransactions }) => {
      this.updateForm(uploadTransactions);
    });
    this.suppliersService
      .query()
      .subscribe((res: HttpResponse<ISuppliers[]>) => (this.suppliers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.uploadActionTypesService
      .query()
      .subscribe(
        (res: HttpResponse<IUploadActionTypes[]>) => (this.uploadactiontypes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(uploadTransactions: IUploadTransactions) {
    this.editForm.patchValue({
      id: uploadTransactions.id,
      fileName: uploadTransactions.fileName,
      templateUrl: uploadTransactions.templateUrl,
      status: uploadTransactions.status,
      generatedCode: uploadTransactions.generatedCode,
      lastEditedBy: uploadTransactions.lastEditedBy,
      lastEditedWhen: uploadTransactions.lastEditedWhen != null ? uploadTransactions.lastEditedWhen.format(DATE_TIME_FORMAT) : null,
      supplierId: uploadTransactions.supplierId,
      actionTypeId: uploadTransactions.actionTypeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const uploadTransactions = this.createFromForm();
    if (uploadTransactions.id !== undefined) {
      this.subscribeToSaveResponse(this.uploadTransactionsService.update(uploadTransactions));
    } else {
      this.subscribeToSaveResponse(this.uploadTransactionsService.create(uploadTransactions));
    }
  }

  private createFromForm(): IUploadTransactions {
    return {
      ...new UploadTransactions(),
      id: this.editForm.get(['id']).value,
      fileName: this.editForm.get(['fileName']).value,
      templateUrl: this.editForm.get(['templateUrl']).value,
      status: this.editForm.get(['status']).value,
      generatedCode: this.editForm.get(['generatedCode']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      supplierId: this.editForm.get(['supplierId']).value,
      actionTypeId: this.editForm.get(['actionTypeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUploadTransactions>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackSuppliersById(index: number, item: ISuppliers) {
    return item.id;
  }

  trackUploadActionTypesById(index: number, item: IUploadActionTypes) {
    return item.id;
  }
}
