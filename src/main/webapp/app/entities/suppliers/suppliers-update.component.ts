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
import { ISuppliers, Suppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from './suppliers.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ISupplierCategories } from 'app/shared/model/supplier-categories.model';
import { SupplierCategoriesService } from 'app/entities/supplier-categories/supplier-categories.service';
import { IDeliveryMethods } from 'app/shared/model/delivery-methods.model';
import { DeliveryMethodsService } from 'app/entities/delivery-methods/delivery-methods.service';
import { ICities } from 'app/shared/model/cities.model';
import { CitiesService } from 'app/entities/cities/cities.service';

@Component({
  selector: 'jhi-suppliers-update',
  templateUrl: './suppliers-update.component.html'
})
export class SuppliersUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  suppliercategories: ISupplierCategories[];

  deliverymethods: IDeliveryMethods[];

  cities: ICities[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    supplierReference: [],
    bankAccountName: [],
    bankAccountBranch: [],
    bankAccountCode: [],
    bankAccountNumber: [],
    bankInternationalCode: [],
    paymentDays: [null, [Validators.required]],
    internalComments: [],
    phoneNumber: [null, [Validators.required]],
    faxNumber: [],
    websiteURL: [],
    webServiceUrl: [],
    creditRating: [],
    activeFlag: [],
    thumbnailUrl: [],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]],
    userId: [],
    supplierCategoryId: [],
    deliveryMethodId: [],
    deliveryCityId: [],
    postalCityId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected suppliersService: SuppliersService,
    protected userService: UserService,
    protected supplierCategoriesService: SupplierCategoriesService,
    protected deliveryMethodsService: DeliveryMethodsService,
    protected citiesService: CitiesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ suppliers }) => {
      this.updateForm(suppliers);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.supplierCategoriesService
      .query()
      .subscribe(
        (res: HttpResponse<ISupplierCategories[]>) => (this.suppliercategories = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.deliveryMethodsService
      .query()
      .subscribe(
        (res: HttpResponse<IDeliveryMethods[]>) => (this.deliverymethods = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.citiesService
      .query()
      .subscribe((res: HttpResponse<ICities[]>) => (this.cities = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(suppliers: ISuppliers) {
    this.editForm.patchValue({
      id: suppliers.id,
      name: suppliers.name,
      supplierReference: suppliers.supplierReference,
      bankAccountName: suppliers.bankAccountName,
      bankAccountBranch: suppliers.bankAccountBranch,
      bankAccountCode: suppliers.bankAccountCode,
      bankAccountNumber: suppliers.bankAccountNumber,
      bankInternationalCode: suppliers.bankInternationalCode,
      paymentDays: suppliers.paymentDays,
      internalComments: suppliers.internalComments,
      phoneNumber: suppliers.phoneNumber,
      faxNumber: suppliers.faxNumber,
      websiteURL: suppliers.websiteURL,
      webServiceUrl: suppliers.webServiceUrl,
      creditRating: suppliers.creditRating,
      activeFlag: suppliers.activeFlag,
      thumbnailUrl: suppliers.thumbnailUrl,
      validFrom: suppliers.validFrom != null ? suppliers.validFrom.format(DATE_TIME_FORMAT) : null,
      validTo: suppliers.validTo != null ? suppliers.validTo.format(DATE_TIME_FORMAT) : null,
      userId: suppliers.userId,
      supplierCategoryId: suppliers.supplierCategoryId,
      deliveryMethodId: suppliers.deliveryMethodId,
      deliveryCityId: suppliers.deliveryCityId,
      postalCityId: suppliers.postalCityId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const suppliers = this.createFromForm();
    if (suppliers.id !== undefined) {
      this.subscribeToSaveResponse(this.suppliersService.update(suppliers));
    } else {
      this.subscribeToSaveResponse(this.suppliersService.create(suppliers));
    }
  }

  private createFromForm(): ISuppliers {
    return {
      ...new Suppliers(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      supplierReference: this.editForm.get(['supplierReference']).value,
      bankAccountName: this.editForm.get(['bankAccountName']).value,
      bankAccountBranch: this.editForm.get(['bankAccountBranch']).value,
      bankAccountCode: this.editForm.get(['bankAccountCode']).value,
      bankAccountNumber: this.editForm.get(['bankAccountNumber']).value,
      bankInternationalCode: this.editForm.get(['bankInternationalCode']).value,
      paymentDays: this.editForm.get(['paymentDays']).value,
      internalComments: this.editForm.get(['internalComments']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      faxNumber: this.editForm.get(['faxNumber']).value,
      websiteURL: this.editForm.get(['websiteURL']).value,
      webServiceUrl: this.editForm.get(['webServiceUrl']).value,
      creditRating: this.editForm.get(['creditRating']).value,
      activeFlag: this.editForm.get(['activeFlag']).value,
      thumbnailUrl: this.editForm.get(['thumbnailUrl']).value,
      validFrom:
        this.editForm.get(['validFrom']).value != null ? moment(this.editForm.get(['validFrom']).value, DATE_TIME_FORMAT) : undefined,
      validTo: this.editForm.get(['validTo']).value != null ? moment(this.editForm.get(['validTo']).value, DATE_TIME_FORMAT) : undefined,
      userId: this.editForm.get(['userId']).value,
      supplierCategoryId: this.editForm.get(['supplierCategoryId']).value,
      deliveryMethodId: this.editForm.get(['deliveryMethodId']).value,
      deliveryCityId: this.editForm.get(['deliveryCityId']).value,
      postalCityId: this.editForm.get(['postalCityId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISuppliers>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackSupplierCategoriesById(index: number, item: ISupplierCategories) {
    return item.id;
  }

  trackDeliveryMethodsById(index: number, item: IDeliveryMethods) {
    return item.id;
  }

  trackCitiesById(index: number, item: ICities) {
    return item.id;
  }
}
