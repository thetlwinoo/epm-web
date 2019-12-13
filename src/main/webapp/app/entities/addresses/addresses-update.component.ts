import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IAddresses, Addresses } from 'app/shared/model/addresses.model';
import { AddressesService } from './addresses.service';
import { IStateProvinces } from 'app/shared/model/state-provinces.model';
import { StateProvincesService } from 'app/entities/state-provinces/state-provinces.service';
import { IAddressTypes } from 'app/shared/model/address-types.model';
import { AddressTypesService } from 'app/entities/address-types/address-types.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people/people.service';

@Component({
  selector: 'jhi-addresses-update',
  templateUrl: './addresses-update.component.html'
})
export class AddressesUpdateComponent implements OnInit {
  isSaving: boolean;

  stateprovinces: IStateProvinces[];

  addresstypes: IAddressTypes[];

  people: IPeople[];

  editForm = this.fb.group({
    id: [],
    contactPerson: [null, [Validators.required]],
    contactNumber: [null, [Validators.required]],
    contactEmailAddress: [null, [Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    addressLine1: [null, [Validators.required]],
    addressLine2: [],
    city: [],
    postalCode: [],
    defaultInd: [],
    activeInd: [],
    stateProvinceId: [],
    addressTypeId: [],
    personId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected addressesService: AddressesService,
    protected stateProvincesService: StateProvincesService,
    protected addressTypesService: AddressTypesService,
    protected peopleService: PeopleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ addresses }) => {
      this.updateForm(addresses);
    });
    this.stateProvincesService
      .query()
      .subscribe(
        (res: HttpResponse<IStateProvinces[]>) => (this.stateprovinces = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.addressTypesService
      .query()
      .subscribe(
        (res: HttpResponse<IAddressTypes[]>) => (this.addresstypes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.peopleService
      .query()
      .subscribe((res: HttpResponse<IPeople[]>) => (this.people = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(addresses: IAddresses) {
    this.editForm.patchValue({
      id: addresses.id,
      contactPerson: addresses.contactPerson,
      contactNumber: addresses.contactNumber,
      contactEmailAddress: addresses.contactEmailAddress,
      addressLine1: addresses.addressLine1,
      addressLine2: addresses.addressLine2,
      city: addresses.city,
      postalCode: addresses.postalCode,
      defaultInd: addresses.defaultInd,
      activeInd: addresses.activeInd,
      stateProvinceId: addresses.stateProvinceId,
      addressTypeId: addresses.addressTypeId,
      personId: addresses.personId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const addresses = this.createFromForm();
    if (addresses.id !== undefined) {
      this.subscribeToSaveResponse(this.addressesService.update(addresses));
    } else {
      this.subscribeToSaveResponse(this.addressesService.create(addresses));
    }
  }

  private createFromForm(): IAddresses {
    return {
      ...new Addresses(),
      id: this.editForm.get(['id']).value,
      contactPerson: this.editForm.get(['contactPerson']).value,
      contactNumber: this.editForm.get(['contactNumber']).value,
      contactEmailAddress: this.editForm.get(['contactEmailAddress']).value,
      addressLine1: this.editForm.get(['addressLine1']).value,
      addressLine2: this.editForm.get(['addressLine2']).value,
      city: this.editForm.get(['city']).value,
      postalCode: this.editForm.get(['postalCode']).value,
      defaultInd: this.editForm.get(['defaultInd']).value,
      activeInd: this.editForm.get(['activeInd']).value,
      stateProvinceId: this.editForm.get(['stateProvinceId']).value,
      addressTypeId: this.editForm.get(['addressTypeId']).value,
      personId: this.editForm.get(['personId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddresses>>) {
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

  trackStateProvincesById(index: number, item: IStateProvinces) {
    return item.id;
  }

  trackAddressTypesById(index: number, item: IAddressTypes) {
    return item.id;
  }

  trackPeopleById(index: number, item: IPeople) {
    return item.id;
  }
}
