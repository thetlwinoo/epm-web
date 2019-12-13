import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProductDocument, ProductDocument } from 'app/shared/model/product-document.model';
import { ProductDocumentService } from './product-document.service';
import { IWarrantyTypes } from 'app/shared/model/warranty-types.model';
import { WarrantyTypesService } from 'app/entities/warranty-types/warranty-types.service';
import { ICulture } from 'app/shared/model/culture.model';
import { CultureService } from 'app/entities/culture/culture.service';

@Component({
  selector: 'jhi-product-document-update',
  templateUrl: './product-document-update.component.html'
})
export class ProductDocumentUpdateComponent implements OnInit {
  isSaving: boolean;

  warrantytypes: IWarrantyTypes[];

  cultures: ICulture[];

  editForm = this.fb.group({
    id: [],
    videoUrl: [],
    highlights: [],
    longDescription: [],
    shortDescription: [],
    description: [],
    careInstructions: [],
    productType: [],
    modelName: [],
    modelNumber: [],
    fabricType: [],
    specialFeatures: [],
    productComplianceCertificate: [],
    genuineAndLegal: [],
    countryOfOrigin: [],
    usageAndSideEffects: [],
    safetyWarnning: [],
    warrantyPeriod: [],
    warrantyPolicy: [],
    warrantyTypeId: [],
    cultureId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected productDocumentService: ProductDocumentService,
    protected warrantyTypesService: WarrantyTypesService,
    protected cultureService: CultureService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productDocument }) => {
      this.updateForm(productDocument);
    });
    this.warrantyTypesService
      .query()
      .subscribe(
        (res: HttpResponse<IWarrantyTypes[]>) => (this.warrantytypes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.cultureService
      .query()
      .subscribe((res: HttpResponse<ICulture[]>) => (this.cultures = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productDocument: IProductDocument) {
    this.editForm.patchValue({
      id: productDocument.id,
      videoUrl: productDocument.videoUrl,
      highlights: productDocument.highlights,
      longDescription: productDocument.longDescription,
      shortDescription: productDocument.shortDescription,
      description: productDocument.description,
      careInstructions: productDocument.careInstructions,
      productType: productDocument.productType,
      modelName: productDocument.modelName,
      modelNumber: productDocument.modelNumber,
      fabricType: productDocument.fabricType,
      specialFeatures: productDocument.specialFeatures,
      productComplianceCertificate: productDocument.productComplianceCertificate,
      genuineAndLegal: productDocument.genuineAndLegal,
      countryOfOrigin: productDocument.countryOfOrigin,
      usageAndSideEffects: productDocument.usageAndSideEffects,
      safetyWarnning: productDocument.safetyWarnning,
      warrantyPeriod: productDocument.warrantyPeriod,
      warrantyPolicy: productDocument.warrantyPolicy,
      warrantyTypeId: productDocument.warrantyTypeId,
      cultureId: productDocument.cultureId
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
    const productDocument = this.createFromForm();
    if (productDocument.id !== undefined) {
      this.subscribeToSaveResponse(this.productDocumentService.update(productDocument));
    } else {
      this.subscribeToSaveResponse(this.productDocumentService.create(productDocument));
    }
  }

  private createFromForm(): IProductDocument {
    return {
      ...new ProductDocument(),
      id: this.editForm.get(['id']).value,
      videoUrl: this.editForm.get(['videoUrl']).value,
      highlights: this.editForm.get(['highlights']).value,
      longDescription: this.editForm.get(['longDescription']).value,
      shortDescription: this.editForm.get(['shortDescription']).value,
      description: this.editForm.get(['description']).value,
      careInstructions: this.editForm.get(['careInstructions']).value,
      productType: this.editForm.get(['productType']).value,
      modelName: this.editForm.get(['modelName']).value,
      modelNumber: this.editForm.get(['modelNumber']).value,
      fabricType: this.editForm.get(['fabricType']).value,
      specialFeatures: this.editForm.get(['specialFeatures']).value,
      productComplianceCertificate: this.editForm.get(['productComplianceCertificate']).value,
      genuineAndLegal: this.editForm.get(['genuineAndLegal']).value,
      countryOfOrigin: this.editForm.get(['countryOfOrigin']).value,
      usageAndSideEffects: this.editForm.get(['usageAndSideEffects']).value,
      safetyWarnning: this.editForm.get(['safetyWarnning']).value,
      warrantyPeriod: this.editForm.get(['warrantyPeriod']).value,
      warrantyPolicy: this.editForm.get(['warrantyPolicy']).value,
      warrantyTypeId: this.editForm.get(['warrantyTypeId']).value,
      cultureId: this.editForm.get(['cultureId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductDocument>>) {
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

  trackWarrantyTypesById(index: number, item: IWarrantyTypes) {
    return item.id;
  }

  trackCultureById(index: number, item: ICulture) {
    return item.id;
  }
}
