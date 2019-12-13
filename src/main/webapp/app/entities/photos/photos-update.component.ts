import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPhotos, Photos } from 'app/shared/model/photos.model';
import { PhotosService } from './photos.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category/product-category.service';

@Component({
  selector: 'jhi-photos-update',
  templateUrl: './photos-update.component.html'
})
export class PhotosUpdateComponent implements OnInit {
  isSaving: boolean;

  stockitems: IStockItems[];

  productcategories: IProductCategory[];

  editForm = this.fb.group({
    id: [],
    thumbnailPhoto: [],
    originalPhoto: [],
    bannerTallPhoto: [],
    bannerWidePhoto: [],
    circlePhoto: [],
    sharpenedPhoto: [],
    squarePhoto: [],
    watermarkPhoto: [],
    thumbnailPhotoBlob: [],
    thumbnailPhotoBlobContentType: [],
    originalPhotoBlob: [],
    originalPhotoBlobContentType: [],
    bannerTallPhotoBlob: [],
    bannerTallPhotoBlobContentType: [],
    bannerWidePhotoBlob: [],
    bannerWidePhotoBlobContentType: [],
    circlePhotoBlob: [],
    circlePhotoBlobContentType: [],
    sharpenedPhotoBlob: [],
    sharpenedPhotoBlobContentType: [],
    squarePhotoBlob: [],
    squarePhotoBlobContentType: [],
    watermarkPhotoBlob: [],
    watermarkPhotoBlobContentType: [],
    priority: [],
    defaultInd: [],
    stockItemId: [],
    productCategoryId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected photosService: PhotosService,
    protected stockItemsService: StockItemsService,
    protected productCategoryService: ProductCategoryService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ photos }) => {
      this.updateForm(photos);
    });
    this.stockItemsService
      .query()
      .subscribe((res: HttpResponse<IStockItems[]>) => (this.stockitems = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.productCategoryService
      .query()
      .subscribe(
        (res: HttpResponse<IProductCategory[]>) => (this.productcategories = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(photos: IPhotos) {
    this.editForm.patchValue({
      id: photos.id,
      thumbnailPhoto: photos.thumbnailPhoto,
      originalPhoto: photos.originalPhoto,
      bannerTallPhoto: photos.bannerTallPhoto,
      bannerWidePhoto: photos.bannerWidePhoto,
      circlePhoto: photos.circlePhoto,
      sharpenedPhoto: photos.sharpenedPhoto,
      squarePhoto: photos.squarePhoto,
      watermarkPhoto: photos.watermarkPhoto,
      thumbnailPhotoBlob: photos.thumbnailPhotoBlob,
      thumbnailPhotoBlobContentType: photos.thumbnailPhotoBlobContentType,
      originalPhotoBlob: photos.originalPhotoBlob,
      originalPhotoBlobContentType: photos.originalPhotoBlobContentType,
      bannerTallPhotoBlob: photos.bannerTallPhotoBlob,
      bannerTallPhotoBlobContentType: photos.bannerTallPhotoBlobContentType,
      bannerWidePhotoBlob: photos.bannerWidePhotoBlob,
      bannerWidePhotoBlobContentType: photos.bannerWidePhotoBlobContentType,
      circlePhotoBlob: photos.circlePhotoBlob,
      circlePhotoBlobContentType: photos.circlePhotoBlobContentType,
      sharpenedPhotoBlob: photos.sharpenedPhotoBlob,
      sharpenedPhotoBlobContentType: photos.sharpenedPhotoBlobContentType,
      squarePhotoBlob: photos.squarePhotoBlob,
      squarePhotoBlobContentType: photos.squarePhotoBlobContentType,
      watermarkPhotoBlob: photos.watermarkPhotoBlob,
      watermarkPhotoBlobContentType: photos.watermarkPhotoBlobContentType,
      priority: photos.priority,
      defaultInd: photos.defaultInd,
      stockItemId: photos.stockItemId,
      productCategoryId: photos.productCategoryId
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

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const photos = this.createFromForm();
    if (photos.id !== undefined) {
      this.subscribeToSaveResponse(this.photosService.update(photos));
    } else {
      this.subscribeToSaveResponse(this.photosService.create(photos));
    }
  }

  private createFromForm(): IPhotos {
    return {
      ...new Photos(),
      id: this.editForm.get(['id']).value,
      thumbnailPhoto: this.editForm.get(['thumbnailPhoto']).value,
      originalPhoto: this.editForm.get(['originalPhoto']).value,
      bannerTallPhoto: this.editForm.get(['bannerTallPhoto']).value,
      bannerWidePhoto: this.editForm.get(['bannerWidePhoto']).value,
      circlePhoto: this.editForm.get(['circlePhoto']).value,
      sharpenedPhoto: this.editForm.get(['sharpenedPhoto']).value,
      squarePhoto: this.editForm.get(['squarePhoto']).value,
      watermarkPhoto: this.editForm.get(['watermarkPhoto']).value,
      thumbnailPhotoBlobContentType: this.editForm.get(['thumbnailPhotoBlobContentType']).value,
      thumbnailPhotoBlob: this.editForm.get(['thumbnailPhotoBlob']).value,
      originalPhotoBlobContentType: this.editForm.get(['originalPhotoBlobContentType']).value,
      originalPhotoBlob: this.editForm.get(['originalPhotoBlob']).value,
      bannerTallPhotoBlobContentType: this.editForm.get(['bannerTallPhotoBlobContentType']).value,
      bannerTallPhotoBlob: this.editForm.get(['bannerTallPhotoBlob']).value,
      bannerWidePhotoBlobContentType: this.editForm.get(['bannerWidePhotoBlobContentType']).value,
      bannerWidePhotoBlob: this.editForm.get(['bannerWidePhotoBlob']).value,
      circlePhotoBlobContentType: this.editForm.get(['circlePhotoBlobContentType']).value,
      circlePhotoBlob: this.editForm.get(['circlePhotoBlob']).value,
      sharpenedPhotoBlobContentType: this.editForm.get(['sharpenedPhotoBlobContentType']).value,
      sharpenedPhotoBlob: this.editForm.get(['sharpenedPhotoBlob']).value,
      squarePhotoBlobContentType: this.editForm.get(['squarePhotoBlobContentType']).value,
      squarePhotoBlob: this.editForm.get(['squarePhotoBlob']).value,
      watermarkPhotoBlobContentType: this.editForm.get(['watermarkPhotoBlobContentType']).value,
      watermarkPhotoBlob: this.editForm.get(['watermarkPhotoBlob']).value,
      priority: this.editForm.get(['priority']).value,
      defaultInd: this.editForm.get(['defaultInd']).value,
      stockItemId: this.editForm.get(['stockItemId']).value,
      productCategoryId: this.editForm.get(['productCategoryId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhotos>>) {
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

  trackStockItemsById(index: number, item: IStockItems) {
    return item.id;
  }

  trackProductCategoryById(index: number, item: IProductCategory) {
    return item.id;
  }
}
