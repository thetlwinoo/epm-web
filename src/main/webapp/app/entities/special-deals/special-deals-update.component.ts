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
import { ISpecialDeals, SpecialDeals } from 'app/shared/model/special-deals.model';
import { SpecialDealsService } from './special-deals.service';
import { IBuyingGroups } from 'app/shared/model/buying-groups.model';
import { BuyingGroupsService } from 'app/entities/buying-groups/buying-groups.service';
import { ICustomerCategories } from 'app/shared/model/customer-categories.model';
import { CustomerCategoriesService } from 'app/entities/customer-categories/customer-categories.service';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers/customers.service';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category/product-category.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';

@Component({
  selector: 'jhi-special-deals-update',
  templateUrl: './special-deals-update.component.html'
})
export class SpecialDealsUpdateComponent implements OnInit {
  isSaving: boolean;

  buyinggroups: IBuyingGroups[];

  customercategories: ICustomerCategories[];

  customers: ICustomers[];

  productcategories: IProductCategory[];

  stockitems: IStockItems[];

  editForm = this.fb.group({
    id: [],
    dealDescription: [null, [Validators.required]],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    discountAmount: [],
    discountPercentage: [],
    discountCode: [],
    unitPrice: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    buyingGroupId: [],
    customerCategoryId: [],
    customerId: [],
    productCategoryId: [],
    stockItemId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected specialDealsService: SpecialDealsService,
    protected buyingGroupsService: BuyingGroupsService,
    protected customerCategoriesService: CustomerCategoriesService,
    protected customersService: CustomersService,
    protected productCategoryService: ProductCategoryService,
    protected stockItemsService: StockItemsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ specialDeals }) => {
      this.updateForm(specialDeals);
    });
    this.buyingGroupsService
      .query()
      .subscribe(
        (res: HttpResponse<IBuyingGroups[]>) => (this.buyinggroups = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.customerCategoriesService
      .query()
      .subscribe(
        (res: HttpResponse<ICustomerCategories[]>) => (this.customercategories = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.customersService
      .query()
      .subscribe((res: HttpResponse<ICustomers[]>) => (this.customers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.productCategoryService
      .query()
      .subscribe(
        (res: HttpResponse<IProductCategory[]>) => (this.productcategories = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.stockItemsService
      .query()
      .subscribe((res: HttpResponse<IStockItems[]>) => (this.stockitems = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(specialDeals: ISpecialDeals) {
    this.editForm.patchValue({
      id: specialDeals.id,
      dealDescription: specialDeals.dealDescription,
      startDate: specialDeals.startDate != null ? specialDeals.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: specialDeals.endDate != null ? specialDeals.endDate.format(DATE_TIME_FORMAT) : null,
      discountAmount: specialDeals.discountAmount,
      discountPercentage: specialDeals.discountPercentage,
      discountCode: specialDeals.discountCode,
      unitPrice: specialDeals.unitPrice,
      lastEditedBy: specialDeals.lastEditedBy,
      lastEditedWhen: specialDeals.lastEditedWhen != null ? specialDeals.lastEditedWhen.format(DATE_TIME_FORMAT) : null,
      buyingGroupId: specialDeals.buyingGroupId,
      customerCategoryId: specialDeals.customerCategoryId,
      customerId: specialDeals.customerId,
      productCategoryId: specialDeals.productCategoryId,
      stockItemId: specialDeals.stockItemId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const specialDeals = this.createFromForm();
    if (specialDeals.id !== undefined) {
      this.subscribeToSaveResponse(this.specialDealsService.update(specialDeals));
    } else {
      this.subscribeToSaveResponse(this.specialDealsService.create(specialDeals));
    }
  }

  private createFromForm(): ISpecialDeals {
    return {
      ...new SpecialDeals(),
      id: this.editForm.get(['id']).value,
      dealDescription: this.editForm.get(['dealDescription']).value,
      startDate:
        this.editForm.get(['startDate']).value != null ? moment(this.editForm.get(['startDate']).value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate']).value != null ? moment(this.editForm.get(['endDate']).value, DATE_TIME_FORMAT) : undefined,
      discountAmount: this.editForm.get(['discountAmount']).value,
      discountPercentage: this.editForm.get(['discountPercentage']).value,
      discountCode: this.editForm.get(['discountCode']).value,
      unitPrice: this.editForm.get(['unitPrice']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      buyingGroupId: this.editForm.get(['buyingGroupId']).value,
      customerCategoryId: this.editForm.get(['customerCategoryId']).value,
      customerId: this.editForm.get(['customerId']).value,
      productCategoryId: this.editForm.get(['productCategoryId']).value,
      stockItemId: this.editForm.get(['stockItemId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecialDeals>>) {
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

  trackBuyingGroupsById(index: number, item: IBuyingGroups) {
    return item.id;
  }

  trackCustomerCategoriesById(index: number, item: ICustomerCategories) {
    return item.id;
  }

  trackCustomersById(index: number, item: ICustomers) {
    return item.id;
  }

  trackProductCategoryById(index: number, item: IProductCategory) {
    return item.id;
  }

  trackStockItemsById(index: number, item: IStockItems) {
    return item.id;
  }
}
