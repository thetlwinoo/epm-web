import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ISupplierImportedDocument, SupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';
import { SupplierImportedDocumentService } from './supplier-imported-document.service';
import { IUploadTransactions } from 'app/shared/model/upload-transactions.model';
import { UploadTransactionsService } from 'app/entities/upload-transactions/upload-transactions.service';

@Component({
  selector: 'jhi-supplier-imported-document-update',
  templateUrl: './supplier-imported-document-update.component.html'
})
export class SupplierImportedDocumentUpdateComponent implements OnInit {
  isSaving: boolean;

  uploadtransactions: IUploadTransactions[];

  editForm = this.fb.group({
    id: [],
    importedTemplate: [],
    importedTemplateContentType: [],
    importedFailedTemplate: [],
    importedFailedTemplateContentType: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    uploadTransactionId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected supplierImportedDocumentService: SupplierImportedDocumentService,
    protected uploadTransactionsService: UploadTransactionsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ supplierImportedDocument }) => {
      this.updateForm(supplierImportedDocument);
    });
    this.uploadTransactionsService
      .query()
      .subscribe(
        (res: HttpResponse<IUploadTransactions[]>) => (this.uploadtransactions = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(supplierImportedDocument: ISupplierImportedDocument) {
    this.editForm.patchValue({
      id: supplierImportedDocument.id,
      importedTemplate: supplierImportedDocument.importedTemplate,
      importedTemplateContentType: supplierImportedDocument.importedTemplateContentType,
      importedFailedTemplate: supplierImportedDocument.importedFailedTemplate,
      importedFailedTemplateContentType: supplierImportedDocument.importedFailedTemplateContentType,
      lastEditedBy: supplierImportedDocument.lastEditedBy,
      lastEditedWhen:
        supplierImportedDocument.lastEditedWhen != null ? supplierImportedDocument.lastEditedWhen.format(DATE_TIME_FORMAT) : null,
      uploadTransactionId: supplierImportedDocument.uploadTransactionId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const supplierImportedDocument = this.createFromForm();
    if (supplierImportedDocument.id !== undefined) {
      this.subscribeToSaveResponse(this.supplierImportedDocumentService.update(supplierImportedDocument));
    } else {
      this.subscribeToSaveResponse(this.supplierImportedDocumentService.create(supplierImportedDocument));
    }
  }

  private createFromForm(): ISupplierImportedDocument {
    return {
      ...new SupplierImportedDocument(),
      id: this.editForm.get(['id']).value,
      importedTemplateContentType: this.editForm.get(['importedTemplateContentType']).value,
      importedTemplate: this.editForm.get(['importedTemplate']).value,
      importedFailedTemplateContentType: this.editForm.get(['importedFailedTemplateContentType']).value,
      importedFailedTemplate: this.editForm.get(['importedFailedTemplate']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      uploadTransactionId: this.editForm.get(['uploadTransactionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplierImportedDocument>>) {
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

  trackUploadTransactionsById(index: number, item: IUploadTransactions) {
    return item.id;
  }
}
