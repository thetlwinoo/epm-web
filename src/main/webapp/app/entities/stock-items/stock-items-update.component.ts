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
import { IStockItems, StockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from './stock-items.service';
import { IReviewLines } from 'app/shared/model/review-lines.model';
import { ReviewLinesService } from 'app/entities/review-lines/review-lines.service';
import { IUnitMeasure } from 'app/shared/model/unit-measure.model';
import { UnitMeasureService } from 'app/entities/unit-measure/unit-measure.service';
import { IProductAttribute } from 'app/shared/model/product-attribute.model';
import { ProductAttributeService } from 'app/entities/product-attribute/product-attribute.service';
import { IProductOption } from 'app/shared/model/product-option.model';
import { ProductOptionService } from 'app/entities/product-option/product-option.service';
import { IMaterials } from 'app/shared/model/materials.model';
import { MaterialsService } from 'app/entities/materials/materials.service';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from 'app/entities/currency/currency.service';
import { IBarcodeTypes } from 'app/shared/model/barcode-types.model';
import { BarcodeTypesService } from 'app/entities/barcode-types/barcode-types.service';
import { IStockItemHoldings } from 'app/shared/model/stock-item-holdings.model';
import { StockItemHoldingsService } from 'app/entities/stock-item-holdings/stock-item-holdings.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products/products.service';

@Component({
  selector: 'jhi-stock-items-update',
  templateUrl: './stock-items-update.component.html'
})
export class StockItemsUpdateComponent implements OnInit {
  isSaving: boolean;

  stockitemonreviewlines: IReviewLines[];

  unitmeasures: IUnitMeasure[];

  productattributes: IProductAttribute[];

  productoptions: IProductOption[];

  materials: IMaterials[];

  currencies: ICurrency[];

  barcodetypes: IBarcodeTypes[];

  stockitemholdings: IStockItemHoldings[];

  products: IProducts[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    vendorCode: [],
    vendorSKU: [],
    generatedSKU: [],
    barcode: [],
    unitPrice: [null, [Validators.required]],
    recommendedRetailPrice: [],
    quantityOnHand: [null, [Validators.required]],
    itemLength: [],
    itemWidth: [],
    itemHeight: [],
    itemWeight: [],
    itemPackageLength: [],
    itemPackageWidth: [],
    itemPackageHeight: [],
    itemPackageWeight: [],
    noOfPieces: [],
    noOfItems: [],
    manufacture: [],
    marketingComments: [],
    internalComments: [],
    sellStartDate: [],
    sellEndDate: [],
    sellCount: [],
    customFields: [],
    thumbnailUrl: [],
    activeInd: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    stockItemOnReviewLineId: [],
    itemLengthUnitId: [],
    itemWidthUnitId: [],
    itemHeightUnitId: [],
    packageLengthUnitId: [],
    packageWidthUnitId: [],
    packageHeightUnitId: [],
    itemPackageWeightUnitId: [],
    productAttributeId: [],
    productOptionId: [],
    materialId: [],
    currencyId: [],
    barcodeTypeId: [],
    productId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected stockItemsService: StockItemsService,
    protected reviewLinesService: ReviewLinesService,
    protected unitMeasureService: UnitMeasureService,
    protected productAttributeService: ProductAttributeService,
    protected productOptionService: ProductOptionService,
    protected materialsService: MaterialsService,
    protected currencyService: CurrencyService,
    protected barcodeTypesService: BarcodeTypesService,
    protected stockItemHoldingsService: StockItemHoldingsService,
    protected productsService: ProductsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stockItems }) => {
      this.updateForm(stockItems);
    });
    this.reviewLinesService.query({ 'stockItemId.specified': 'false' }).subscribe(
      (res: HttpResponse<IReviewLines[]>) => {
        if (!this.editForm.get('stockItemOnReviewLineId').value) {
          this.stockitemonreviewlines = res.body;
        } else {
          this.reviewLinesService
            .find(this.editForm.get('stockItemOnReviewLineId').value)
            .subscribe(
              (subRes: HttpResponse<IReviewLines>) => (this.stockitemonreviewlines = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.unitMeasureService
      .query()
      .subscribe(
        (res: HttpResponse<IUnitMeasure[]>) => (this.unitmeasures = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.productAttributeService
      .query()
      .subscribe(
        (res: HttpResponse<IProductAttribute[]>) => (this.productattributes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.productOptionService
      .query()
      .subscribe(
        (res: HttpResponse<IProductOption[]>) => (this.productoptions = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.materialsService
      .query()
      .subscribe((res: HttpResponse<IMaterials[]>) => (this.materials = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.currencyService
      .query()
      .subscribe((res: HttpResponse<ICurrency[]>) => (this.currencies = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.barcodeTypesService
      .query()
      .subscribe(
        (res: HttpResponse<IBarcodeTypes[]>) => (this.barcodetypes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.stockItemHoldingsService
      .query()
      .subscribe(
        (res: HttpResponse<IStockItemHoldings[]>) => (this.stockitemholdings = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.productsService
      .query()
      .subscribe((res: HttpResponse<IProducts[]>) => (this.products = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(stockItems: IStockItems) {
    this.editForm.patchValue({
      id: stockItems.id,
      name: stockItems.name,
      vendorCode: stockItems.vendorCode,
      vendorSKU: stockItems.vendorSKU,
      generatedSKU: stockItems.generatedSKU,
      barcode: stockItems.barcode,
      unitPrice: stockItems.unitPrice,
      recommendedRetailPrice: stockItems.recommendedRetailPrice,
      quantityOnHand: stockItems.quantityOnHand,
      itemLength: stockItems.itemLength,
      itemWidth: stockItems.itemWidth,
      itemHeight: stockItems.itemHeight,
      itemWeight: stockItems.itemWeight,
      itemPackageLength: stockItems.itemPackageLength,
      itemPackageWidth: stockItems.itemPackageWidth,
      itemPackageHeight: stockItems.itemPackageHeight,
      itemPackageWeight: stockItems.itemPackageWeight,
      noOfPieces: stockItems.noOfPieces,
      noOfItems: stockItems.noOfItems,
      manufacture: stockItems.manufacture,
      marketingComments: stockItems.marketingComments,
      internalComments: stockItems.internalComments,
      sellStartDate: stockItems.sellStartDate != null ? stockItems.sellStartDate.format(DATE_TIME_FORMAT) : null,
      sellEndDate: stockItems.sellEndDate != null ? stockItems.sellEndDate.format(DATE_TIME_FORMAT) : null,
      sellCount: stockItems.sellCount,
      customFields: stockItems.customFields,
      thumbnailUrl: stockItems.thumbnailUrl,
      activeInd: stockItems.activeInd,
      lastEditedBy: stockItems.lastEditedBy,
      lastEditedWhen: stockItems.lastEditedWhen != null ? stockItems.lastEditedWhen.format(DATE_TIME_FORMAT) : null,
      stockItemOnReviewLineId: stockItems.stockItemOnReviewLineId,
      itemLengthUnitId: stockItems.itemLengthUnitId,
      itemWidthUnitId: stockItems.itemWidthUnitId,
      itemHeightUnitId: stockItems.itemHeightUnitId,
      packageLengthUnitId: stockItems.packageLengthUnitId,
      packageWidthUnitId: stockItems.packageWidthUnitId,
      packageHeightUnitId: stockItems.packageHeightUnitId,
      itemPackageWeightUnitId: stockItems.itemPackageWeightUnitId,
      productAttributeId: stockItems.productAttributeId,
      productOptionId: stockItems.productOptionId,
      materialId: stockItems.materialId,
      currencyId: stockItems.currencyId,
      barcodeTypeId: stockItems.barcodeTypeId,
      productId: stockItems.productId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const stockItems = this.createFromForm();
    if (stockItems.id !== undefined) {
      this.subscribeToSaveResponse(this.stockItemsService.update(stockItems));
    } else {
      this.subscribeToSaveResponse(this.stockItemsService.create(stockItems));
    }
  }

  private createFromForm(): IStockItems {
    return {
      ...new StockItems(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      vendorCode: this.editForm.get(['vendorCode']).value,
      vendorSKU: this.editForm.get(['vendorSKU']).value,
      generatedSKU: this.editForm.get(['generatedSKU']).value,
      barcode: this.editForm.get(['barcode']).value,
      unitPrice: this.editForm.get(['unitPrice']).value,
      recommendedRetailPrice: this.editForm.get(['recommendedRetailPrice']).value,
      quantityOnHand: this.editForm.get(['quantityOnHand']).value,
      itemLength: this.editForm.get(['itemLength']).value,
      itemWidth: this.editForm.get(['itemWidth']).value,
      itemHeight: this.editForm.get(['itemHeight']).value,
      itemWeight: this.editForm.get(['itemWeight']).value,
      itemPackageLength: this.editForm.get(['itemPackageLength']).value,
      itemPackageWidth: this.editForm.get(['itemPackageWidth']).value,
      itemPackageHeight: this.editForm.get(['itemPackageHeight']).value,
      itemPackageWeight: this.editForm.get(['itemPackageWeight']).value,
      noOfPieces: this.editForm.get(['noOfPieces']).value,
      noOfItems: this.editForm.get(['noOfItems']).value,
      manufacture: this.editForm.get(['manufacture']).value,
      marketingComments: this.editForm.get(['marketingComments']).value,
      internalComments: this.editForm.get(['internalComments']).value,
      sellStartDate:
        this.editForm.get(['sellStartDate']).value != null
          ? moment(this.editForm.get(['sellStartDate']).value, DATE_TIME_FORMAT)
          : undefined,
      sellEndDate:
        this.editForm.get(['sellEndDate']).value != null ? moment(this.editForm.get(['sellEndDate']).value, DATE_TIME_FORMAT) : undefined,
      sellCount: this.editForm.get(['sellCount']).value,
      customFields: this.editForm.get(['customFields']).value,
      thumbnailUrl: this.editForm.get(['thumbnailUrl']).value,
      activeInd: this.editForm.get(['activeInd']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      stockItemOnReviewLineId: this.editForm.get(['stockItemOnReviewLineId']).value,
      itemLengthUnitId: this.editForm.get(['itemLengthUnitId']).value,
      itemWidthUnitId: this.editForm.get(['itemWidthUnitId']).value,
      itemHeightUnitId: this.editForm.get(['itemHeightUnitId']).value,
      packageLengthUnitId: this.editForm.get(['packageLengthUnitId']).value,
      packageWidthUnitId: this.editForm.get(['packageWidthUnitId']).value,
      packageHeightUnitId: this.editForm.get(['packageHeightUnitId']).value,
      itemPackageWeightUnitId: this.editForm.get(['itemPackageWeightUnitId']).value,
      productAttributeId: this.editForm.get(['productAttributeId']).value,
      productOptionId: this.editForm.get(['productOptionId']).value,
      materialId: this.editForm.get(['materialId']).value,
      currencyId: this.editForm.get(['currencyId']).value,
      barcodeTypeId: this.editForm.get(['barcodeTypeId']).value,
      productId: this.editForm.get(['productId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockItems>>) {
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

  trackReviewLinesById(index: number, item: IReviewLines) {
    return item.id;
  }

  trackUnitMeasureById(index: number, item: IUnitMeasure) {
    return item.id;
  }

  trackProductAttributeById(index: number, item: IProductAttribute) {
    return item.id;
  }

  trackProductOptionById(index: number, item: IProductOption) {
    return item.id;
  }

  trackMaterialsById(index: number, item: IMaterials) {
    return item.id;
  }

  trackCurrencyById(index: number, item: ICurrency) {
    return item.id;
  }

  trackBarcodeTypesById(index: number, item: IBarcodeTypes) {
    return item.id;
  }

  trackStockItemHoldingsById(index: number, item: IStockItemHoldings) {
    return item.id;
  }

  trackProductsById(index: number, item: IProducts) {
    return item.id;
  }
}
