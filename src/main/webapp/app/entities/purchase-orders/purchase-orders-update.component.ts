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
import { IPurchaseOrders, PurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { PurchaseOrdersService } from './purchase-orders.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people/people.service';
import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers/suppliers.service';
import { IDeliveryMethods } from 'app/shared/model/delivery-methods.model';
import { DeliveryMethodsService } from 'app/entities/delivery-methods/delivery-methods.service';

@Component({
  selector: 'jhi-purchase-orders-update',
  templateUrl: './purchase-orders-update.component.html'
})
export class PurchaseOrdersUpdateComponent implements OnInit {
  isSaving: boolean;

  people: IPeople[];

  suppliers: ISuppliers[];

  deliverymethods: IDeliveryMethods[];

  editForm = this.fb.group({
    id: [],
    orderDate: [null, [Validators.required]],
    expectedDeliveryDate: [],
    supplierReference: [],
    isOrderFinalized: [null, [Validators.required]],
    comments: [],
    internalComments: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    contactPersonId: [],
    supplierId: [],
    deliveryMethodId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected purchaseOrdersService: PurchaseOrdersService,
    protected peopleService: PeopleService,
    protected suppliersService: SuppliersService,
    protected deliveryMethodsService: DeliveryMethodsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ purchaseOrders }) => {
      this.updateForm(purchaseOrders);
    });
    this.peopleService
      .query()
      .subscribe((res: HttpResponse<IPeople[]>) => (this.people = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.suppliersService
      .query()
      .subscribe((res: HttpResponse<ISuppliers[]>) => (this.suppliers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.deliveryMethodsService
      .query()
      .subscribe(
        (res: HttpResponse<IDeliveryMethods[]>) => (this.deliverymethods = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(purchaseOrders: IPurchaseOrders) {
    this.editForm.patchValue({
      id: purchaseOrders.id,
      orderDate: purchaseOrders.orderDate != null ? purchaseOrders.orderDate.format(DATE_TIME_FORMAT) : null,
      expectedDeliveryDate:
        purchaseOrders.expectedDeliveryDate != null ? purchaseOrders.expectedDeliveryDate.format(DATE_TIME_FORMAT) : null,
      supplierReference: purchaseOrders.supplierReference,
      isOrderFinalized: purchaseOrders.isOrderFinalized,
      comments: purchaseOrders.comments,
      internalComments: purchaseOrders.internalComments,
      lastEditedBy: purchaseOrders.lastEditedBy,
      lastEditedWhen: purchaseOrders.lastEditedWhen != null ? purchaseOrders.lastEditedWhen.format(DATE_TIME_FORMAT) : null,
      contactPersonId: purchaseOrders.contactPersonId,
      supplierId: purchaseOrders.supplierId,
      deliveryMethodId: purchaseOrders.deliveryMethodId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const purchaseOrders = this.createFromForm();
    if (purchaseOrders.id !== undefined) {
      this.subscribeToSaveResponse(this.purchaseOrdersService.update(purchaseOrders));
    } else {
      this.subscribeToSaveResponse(this.purchaseOrdersService.create(purchaseOrders));
    }
  }

  private createFromForm(): IPurchaseOrders {
    return {
      ...new PurchaseOrders(),
      id: this.editForm.get(['id']).value,
      orderDate:
        this.editForm.get(['orderDate']).value != null ? moment(this.editForm.get(['orderDate']).value, DATE_TIME_FORMAT) : undefined,
      expectedDeliveryDate:
        this.editForm.get(['expectedDeliveryDate']).value != null
          ? moment(this.editForm.get(['expectedDeliveryDate']).value, DATE_TIME_FORMAT)
          : undefined,
      supplierReference: this.editForm.get(['supplierReference']).value,
      isOrderFinalized: this.editForm.get(['isOrderFinalized']).value,
      comments: this.editForm.get(['comments']).value,
      internalComments: this.editForm.get(['internalComments']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen:
        this.editForm.get(['lastEditedWhen']).value != null
          ? moment(this.editForm.get(['lastEditedWhen']).value, DATE_TIME_FORMAT)
          : undefined,
      contactPersonId: this.editForm.get(['contactPersonId']).value,
      supplierId: this.editForm.get(['supplierId']).value,
      deliveryMethodId: this.editForm.get(['deliveryMethodId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseOrders>>) {
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

  trackSuppliersById(index: number, item: ISuppliers) {
    return item.id;
  }

  trackDeliveryMethodsById(index: number, item: IDeliveryMethods) {
    return item.id;
  }
}
