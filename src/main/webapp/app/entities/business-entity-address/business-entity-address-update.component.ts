import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IBusinessEntityAddress, BusinessEntityAddress } from 'app/shared/model/business-entity-address.model';
import { BusinessEntityAddressService } from './business-entity-address.service';
import { IAddresses } from 'app/shared/model/addresses.model';
import { AddressesService } from 'app/entities/addresses/addresses.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people/people.service';
import { IAddressTypes } from 'app/shared/model/address-types.model';
import { AddressTypesService } from 'app/entities/address-types/address-types.service';

@Component({
  selector: 'jhi-business-entity-address-update',
  templateUrl: './business-entity-address-update.component.html'
})
export class BusinessEntityAddressUpdateComponent implements OnInit {
  isSaving: boolean;

  addresses: IAddresses[];

  people: IPeople[];

  addresstypes: IAddressTypes[];

  editForm = this.fb.group({
    id: [],
    addressId: [],
    personId: [],
    addressTypeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected businessEntityAddressService: BusinessEntityAddressService,
    protected addressesService: AddressesService,
    protected peopleService: PeopleService,
    protected addressTypesService: AddressTypesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ businessEntityAddress }) => {
      this.updateForm(businessEntityAddress);
    });
    this.addressesService
      .query()
      .subscribe((res: HttpResponse<IAddresses[]>) => (this.addresses = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.peopleService
      .query()
      .subscribe((res: HttpResponse<IPeople[]>) => (this.people = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.addressTypesService
      .query()
      .subscribe(
        (res: HttpResponse<IAddressTypes[]>) => (this.addresstypes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(businessEntityAddress: IBusinessEntityAddress) {
    this.editForm.patchValue({
      id: businessEntityAddress.id,
      addressId: businessEntityAddress.addressId,
      personId: businessEntityAddress.personId,
      addressTypeId: businessEntityAddress.addressTypeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const businessEntityAddress = this.createFromForm();
    if (businessEntityAddress.id !== undefined) {
      this.subscribeToSaveResponse(this.businessEntityAddressService.update(businessEntityAddress));
    } else {
      this.subscribeToSaveResponse(this.businessEntityAddressService.create(businessEntityAddress));
    }
  }

  private createFromForm(): IBusinessEntityAddress {
    return {
      ...new BusinessEntityAddress(),
      id: this.editForm.get(['id']).value,
      addressId: this.editForm.get(['addressId']).value,
      personId: this.editForm.get(['personId']).value,
      addressTypeId: this.editForm.get(['addressTypeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBusinessEntityAddress>>) {
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

  trackAddressesById(index: number, item: IAddresses) {
    return item.id;
  }

  trackPeopleById(index: number, item: IPeople) {
    return item.id;
  }

  trackAddressTypesById(index: number, item: IAddressTypes) {
    return item.id;
  }
}
