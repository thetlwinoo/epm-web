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
import { IShoppingCarts, ShoppingCarts } from 'app/shared/model/shopping-carts.model';
import { ShoppingCartsService } from './shopping-carts.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people/people.service';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers/customers.service';
import { ISpecialDeals } from 'app/shared/model/special-deals.model';
import { SpecialDealsService } from 'app/entities/special-deals/special-deals.service';

@Component({
  selector: 'jhi-shopping-carts-update',
  templateUrl: './shopping-carts-update.component.html'
})
export class ShoppingCartsUpdateComponent implements OnInit {
  isSaving: boolean;

  cartusers: IPeople[];

  customers: ICustomers[];

  specialdeals: ISpecialDeals[];

  editForm = this.fb.group({
    id: [],
    totalPrice: [],
    totalCargoPrice: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    cartUserId: [],
    customerId: [],
    specialDealsId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected shoppingCartsService: ShoppingCartsService,
    protected peopleService: PeopleService,
    protected customersService: CustomersService,
    protected specialDealsService: SpecialDealsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ shoppingCarts }) => {
      this.updateForm(shoppingCarts);
    });
    this.peopleService.query({ 'cartId.specified': 'false' }).subscribe(
      (res: HttpResponse<IPeople[]>) => {
        if (!this.editForm.get('cartUserId').value) {
          this.cartusers = res.body;
        } else {
          this.peopleService
            .find(this.editForm.get('cartUserId').value)
            .subscribe(
              (subRes: HttpResponse<IPeople>) => (this.cartusers = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.customersService
      .query()
      .subscribe((res: HttpResponse<ICustomers[]>) => (this.customers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.specialDealsService
      .query()
      .subscribe(
        (res: HttpResponse<ISpecialDeals[]>) => (this.specialdeals = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(shoppingCarts: IShoppingCarts) {
    this.editForm.patchValue({
      id: shoppingCarts.id,
      totalPrice: shoppingCarts.totalPrice,
      totalCargoPrice: shoppingCarts.totalCargoPrice,
      lastEditedBy: shoppingCarts.lastEditedBy,
      lastEditedWhen: shoppingCarts.lastEditedWhen != null ? shoppingCarts.lastEditedWhen.format(DATE_TIME_FORMAT) : null,
      cartUserId: shoppingCarts.cartUserId,
      customerId: shoppingCarts.customerId,
      specialDealsId: shoppingCarts.specialDealsId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const shoppingCarts = this.createFromForm();
    if (shoppingCarts.id !== undefined) {
      this.subscribeToSaveResponse(this.shoppingCartsService.update(shoppingCarts));
    } else {
      this.subscribeToSaveResponse(this.shoppingCartsService.create(shoppingCarts));
    }
  }

  private createFromForm(): IShoppingCarts {
    return {
      ...new ShoppingCarts(),
      id: this.editForm.get(['id']).value,
      totalPrice: this.editForm.get(['totalPrice']).value,
      totalCargoPrice: this.editForm.get(['totalCargoPrice']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      cartUserId: this.editForm.get(['cartUserId']).value,
      customerId: this.editForm.get(['customerId']).value,
      specialDealsId: this.editForm.get(['specialDealsId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShoppingCarts>>) {
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

  trackPeopleById(index: number, item: IPeople) {
    return item.id;
  }

  trackCustomersById(index: number, item: ICustomers) {
    return item.id;
  }

  trackSpecialDealsById(index: number, item: ISpecialDeals) {
    return item.id;
  }
}
