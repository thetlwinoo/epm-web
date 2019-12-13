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
import { IStockItemTemp, StockItemTemp } from 'app/shared/model/stock-item-temp.model';
import { StockItemTempService } from './stock-item-temp.service';
import { IUploadTransactions } from 'app/shared/model/upload-transactions.model';
import { UploadTransactionsService } from 'app/entities/upload-transactions/upload-transactions.service';

@Component({
  selector: 'jhi-stock-item-temp-update',
  templateUrl: './stock-item-temp-update.component.html'
})
export class StockItemTempUpdateComponent implements OnInit {
  isSaving: boolean;

  uploadtransactions: IUploadTransactions[];

  editForm = this.fb.group({
    id: [],
    stockItemName: [null, [Validators.required]],
    vendorCode: [null, [Validators.required]],
    vendorSKU: [null, [Validators.required]],
    barcode: [],
    barcodeTypeId: [],
    barcodeTypeName: [],
    productType: [],
    productCategoryId: [null, [Validators.required]],
    productCategoryName: [],
    productAttributeSetId: [],
    productAttributeId: [],
    productAttributeValue: [],
    productOptionSetId: [],
    productOptionId: [],
    productOptionValue: [],
    modelName: [],
    modelNumber: [],
    materialId: [],
    materialName: [],
    shortDescription: [],
    description: [],
    productBrandId: [],
    productBrandName: [],
    highlights: [],
    searchDetails: [],
    careInstructions: [],
    dangerousGoods: [],
    videoUrl: [],
    unitPrice: [],
    remommendedRetailPrice: [],
    currencyCode: [],
    quantityOnHand: [],
    warrantyPeriod: [],
    warrantyPolicy: [],
    warrantyTypeId: [],
    warrantyTypeName: [],
    whatInTheBox: [],
    itemLength: [],
    itemWidth: [],
    itemHeight: [],
    itemWeight: [],
    itemPackageLength: [],
    itemPackageWidth: [],
    itemPackageHeight: [],
    itemPackageWeight: [],
    itemLengthUnitMeasureId: [],
    itemLengthUnitMeasureCode: [],
    itemWidthUnitMeasureId: [],
    itemWidthUnitMeasureCode: [],
    itemHeightUnitMeasureId: [],
    itemHeightUnitMeasureCode: [],
    itemWeightUnitMeasureId: [],
    itemWeightUnitMeasureCode: [],
    itemPackageLengthUnitMeasureId: [],
    itemPackageLengthUnitMeasureCode: [],
    itemPackageWidthUnitMeasureId: [],
    itemPackageWidthUnitMeasureCode: [],
    itemPackageHeightUnitMeasureId: [],
    itemPackageHeightUnitMeasureCode: [],
    itemPackageWeightUnitMeasureId: [],
    itemPackageWeightUnitMeasureCode: [],
    noOfPieces: [],
    noOfItems: [],
    manufacture: [],
    specialFeactures: [],
    productComplianceCertificate: [],
    genuineAndLegal: [],
    countryOfOrigin: [],
    usageAndSideEffects: [],
    safetyWarnning: [],
    sellStartDate: [],
    sellEndDate: [],
    status: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    uploadTransactionId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected stockItemTempService: StockItemTempService,
    protected uploadTransactionsService: UploadTransactionsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stockItemTemp }) => {
      this.updateForm(stockItemTemp);
    });
    this.uploadTransactionsService
      .query()
      .subscribe(
        (res: HttpResponse<IUploadTransactions[]>) => (this.uploadtransactions = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(stockItemTemp: IStockItemTemp) {
    this.editForm.patchValue({
      id: stockItemTemp.id,
      stockItemName: stockItemTemp.stockItemName,
      vendorCode: stockItemTemp.vendorCode,
      vendorSKU: stockItemTemp.vendorSKU,
      barcode: stockItemTemp.barcode,
      barcodeTypeId: stockItemTemp.barcodeTypeId,
      barcodeTypeName: stockItemTemp.barcodeTypeName,
      productType: stockItemTemp.productType,
      productCategoryId: stockItemTemp.productCategoryId,
      productCategoryName: stockItemTemp.productCategoryName,
      productAttributeSetId: stockItemTemp.productAttributeSetId,
      productAttributeId: stockItemTemp.productAttributeId,
      productAttributeValue: stockItemTemp.productAttributeValue,
      productOptionSetId: stockItemTemp.productOptionSetId,
      productOptionId: stockItemTemp.productOptionId,
      productOptionValue: stockItemTemp.productOptionValue,
      modelName: stockItemTemp.modelName,
      modelNumber: stockItemTemp.modelNumber,
      materialId: stockItemTemp.materialId,
      materialName: stockItemTemp.materialName,
      shortDescription: stockItemTemp.shortDescription,
      description: stockItemTemp.description,
      productBrandId: stockItemTemp.productBrandId,
      productBrandName: stockItemTemp.productBrandName,
      highlights: stockItemTemp.highlights,
      searchDetails: stockItemTemp.searchDetails,
      careInstructions: stockItemTemp.careInstructions,
      dangerousGoods: stockItemTemp.dangerousGoods,
      videoUrl: stockItemTemp.videoUrl,
      unitPrice: stockItemTemp.unitPrice,
      remommendedRetailPrice: stockItemTemp.remommendedRetailPrice,
      currencyCode: stockItemTemp.currencyCode,
      quantityOnHand: stockItemTemp.quantityOnHand,
      warrantyPeriod: stockItemTemp.warrantyPeriod,
      warrantyPolicy: stockItemTemp.warrantyPolicy,
      warrantyTypeId: stockItemTemp.warrantyTypeId,
      warrantyTypeName: stockItemTemp.warrantyTypeName,
      whatInTheBox: stockItemTemp.whatInTheBox,
      itemLength: stockItemTemp.itemLength,
      itemWidth: stockItemTemp.itemWidth,
      itemHeight: stockItemTemp.itemHeight,
      itemWeight: stockItemTemp.itemWeight,
      itemPackageLength: stockItemTemp.itemPackageLength,
      itemPackageWidth: stockItemTemp.itemPackageWidth,
      itemPackageHeight: stockItemTemp.itemPackageHeight,
      itemPackageWeight: stockItemTemp.itemPackageWeight,
      itemLengthUnitMeasureId: stockItemTemp.itemLengthUnitMeasureId,
      itemLengthUnitMeasureCode: stockItemTemp.itemLengthUnitMeasureCode,
      itemWidthUnitMeasureId: stockItemTemp.itemWidthUnitMeasureId,
      itemWidthUnitMeasureCode: stockItemTemp.itemWidthUnitMeasureCode,
      itemHeightUnitMeasureId: stockItemTemp.itemHeightUnitMeasureId,
      itemHeightUnitMeasureCode: stockItemTemp.itemHeightUnitMeasureCode,
      itemWeightUnitMeasureId: stockItemTemp.itemWeightUnitMeasureId,
      itemWeightUnitMeasureCode: stockItemTemp.itemWeightUnitMeasureCode,
      itemPackageLengthUnitMeasureId: stockItemTemp.itemPackageLengthUnitMeasureId,
      itemPackageLengthUnitMeasureCode: stockItemTemp.itemPackageLengthUnitMeasureCode,
      itemPackageWidthUnitMeasureId: stockItemTemp.itemPackageWidthUnitMeasureId,
      itemPackageWidthUnitMeasureCode: stockItemTemp.itemPackageWidthUnitMeasureCode,
      itemPackageHeightUnitMeasureId: stockItemTemp.itemPackageHeightUnitMeasureId,
      itemPackageHeightUnitMeasureCode: stockItemTemp.itemPackageHeightUnitMeasureCode,
      itemPackageWeightUnitMeasureId: stockItemTemp.itemPackageWeightUnitMeasureId,
      itemPackageWeightUnitMeasureCode: stockItemTemp.itemPackageWeightUnitMeasureCode,
      noOfPieces: stockItemTemp.noOfPieces,
      noOfItems: stockItemTemp.noOfItems,
      manufacture: stockItemTemp.manufacture,
      specialFeactures: stockItemTemp.specialFeactures,
      productComplianceCertificate: stockItemTemp.productComplianceCertificate,
      genuineAndLegal: stockItemTemp.genuineAndLegal,
      countryOfOrigin: stockItemTemp.countryOfOrigin,
      usageAndSideEffects: stockItemTemp.usageAndSideEffects,
      safetyWarnning: stockItemTemp.safetyWarnning,
      sellStartDate: stockItemTemp.sellStartDate != null ? stockItemTemp.sellStartDate.format(DATE_TIME_FORMAT) : null,
      sellEndDate: stockItemTemp.sellEndDate != null ? stockItemTemp.sellEndDate.format(DATE_TIME_FORMAT) : null,
      status: stockItemTemp.status,
      lastEditedBy: stockItemTemp.lastEditedBy,
      lastEditedWhen: stockItemTemp.lastEditedWhen != null ? stockItemTemp.lastEditedWhen.format(DATE_TIME_FORMAT) : null,
      uploadTransactionId: stockItemTemp.uploadTransactionId
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
    const stockItemTemp = this.createFromForm();
    if (stockItemTemp.id !== undefined) {
      this.subscribeToSaveResponse(this.stockItemTempService.update(stockItemTemp));
    } else {
      this.subscribeToSaveResponse(this.stockItemTempService.create(stockItemTemp));
    }
  }

  private createFromForm(): IStockItemTemp {
    return {
      ...new StockItemTemp(),
      id: this.editForm.get(['id']).value,
      stockItemName: this.editForm.get(['stockItemName']).value,
      vendorCode: this.editForm.get(['vendorCode']).value,
      vendorSKU: this.editForm.get(['vendorSKU']).value,
      barcode: this.editForm.get(['barcode']).value,
      barcodeTypeId: this.editForm.get(['barcodeTypeId']).value,
      barcodeTypeName: this.editForm.get(['barcodeTypeName']).value,
      productType: this.editForm.get(['productType']).value,
      productCategoryId: this.editForm.get(['productCategoryId']).value,
      productCategoryName: this.editForm.get(['productCategoryName']).value,
      productAttributeSetId: this.editForm.get(['productAttributeSetId']).value,
      productAttributeId: this.editForm.get(['productAttributeId']).value,
      productAttributeValue: this.editForm.get(['productAttributeValue']).value,
      productOptionSetId: this.editForm.get(['productOptionSetId']).value,
      productOptionId: this.editForm.get(['productOptionId']).value,
      productOptionValue: this.editForm.get(['productOptionValue']).value,
      modelName: this.editForm.get(['modelName']).value,
      modelNumber: this.editForm.get(['modelNumber']).value,
      materialId: this.editForm.get(['materialId']).value,
      materialName: this.editForm.get(['materialName']).value,
      shortDescription: this.editForm.get(['shortDescription']).value,
      description: this.editForm.get(['description']).value,
      productBrandId: this.editForm.get(['productBrandId']).value,
      productBrandName: this.editForm.get(['productBrandName']).value,
      highlights: this.editForm.get(['highlights']).value,
      searchDetails: this.editForm.get(['searchDetails']).value,
      careInstructions: this.editForm.get(['careInstructions']).value,
      dangerousGoods: this.editForm.get(['dangerousGoods']).value,
      videoUrl: this.editForm.get(['videoUrl']).value,
      unitPrice: this.editForm.get(['unitPrice']).value,
      remommendedRetailPrice: this.editForm.get(['remommendedRetailPrice']).value,
      currencyCode: this.editForm.get(['currencyCode']).value,
      quantityOnHand: this.editForm.get(['quantityOnHand']).value,
      warrantyPeriod: this.editForm.get(['warrantyPeriod']).value,
      warrantyPolicy: this.editForm.get(['warrantyPolicy']).value,
      warrantyTypeId: this.editForm.get(['warrantyTypeId']).value,
      warrantyTypeName: this.editForm.get(['warrantyTypeName']).value,
      whatInTheBox: this.editForm.get(['whatInTheBox']).value,
      itemLength: this.editForm.get(['itemLength']).value,
      itemWidth: this.editForm.get(['itemWidth']).value,
      itemHeight: this.editForm.get(['itemHeight']).value,
      itemWeight: this.editForm.get(['itemWeight']).value,
      itemPackageLength: this.editForm.get(['itemPackageLength']).value,
      itemPackageWidth: this.editForm.get(['itemPackageWidth']).value,
      itemPackageHeight: this.editForm.get(['itemPackageHeight']).value,
      itemPackageWeight: this.editForm.get(['itemPackageWeight']).value,
      itemLengthUnitMeasureId: this.editForm.get(['itemLengthUnitMeasureId']).value,
      itemLengthUnitMeasureCode: this.editForm.get(['itemLengthUnitMeasureCode']).value,
      itemWidthUnitMeasureId: this.editForm.get(['itemWidthUnitMeasureId']).value,
      itemWidthUnitMeasureCode: this.editForm.get(['itemWidthUnitMeasureCode']).value,
      itemHeightUnitMeasureId: this.editForm.get(['itemHeightUnitMeasureId']).value,
      itemHeightUnitMeasureCode: this.editForm.get(['itemHeightUnitMeasureCode']).value,
      itemWeightUnitMeasureId: this.editForm.get(['itemWeightUnitMeasureId']).value,
      itemWeightUnitMeasureCode: this.editForm.get(['itemWeightUnitMeasureCode']).value,
      itemPackageLengthUnitMeasureId: this.editForm.get(['itemPackageLengthUnitMeasureId']).value,
      itemPackageLengthUnitMeasureCode: this.editForm.get(['itemPackageLengthUnitMeasureCode']).value,
      itemPackageWidthUnitMeasureId: this.editForm.get(['itemPackageWidthUnitMeasureId']).value,
      itemPackageWidthUnitMeasureCode: this.editForm.get(['itemPackageWidthUnitMeasureCode']).value,
      itemPackageHeightUnitMeasureId: this.editForm.get(['itemPackageHeightUnitMeasureId']).value,
      itemPackageHeightUnitMeasureCode: this.editForm.get(['itemPackageHeightUnitMeasureCode']).value,
      itemPackageWeightUnitMeasureId: this.editForm.get(['itemPackageWeightUnitMeasureId']).value,
      itemPackageWeightUnitMeasureCode: this.editForm.get(['itemPackageWeightUnitMeasureCode']).value,
      noOfPieces: this.editForm.get(['noOfPieces']).value,
      noOfItems: this.editForm.get(['noOfItems']).value,
      manufacture: this.editForm.get(['manufacture']).value,
      specialFeactures: this.editForm.get(['specialFeactures']).value,
      productComplianceCertificate: this.editForm.get(['productComplianceCertificate']).value,
      genuineAndLegal: this.editForm.get(['genuineAndLegal']).value,
      countryOfOrigin: this.editForm.get(['countryOfOrigin']).value,
      usageAndSideEffects: this.editForm.get(['usageAndSideEffects']).value,
      safetyWarnning: this.editForm.get(['safetyWarnning']).value,
      sellStartDate:
        this.editForm.get(['sellStartDate']).value != null
          ? moment(this.editForm.get(['sellStartDate']).value, DATE_TIME_FORMAT)
          : undefined,
      sellEndDate:
        this.editForm.get(['sellEndDate']).value != null ? moment(this.editForm.get(['sellEndDate']).value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      uploadTransactionId: this.editForm.get(['uploadTransactionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockItemTemp>>) {
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
