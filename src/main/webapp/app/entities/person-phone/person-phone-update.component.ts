import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IPersonPhone, PersonPhone } from 'app/shared/model/person-phone.model';
import { PersonPhoneService } from './person-phone.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people/people.service';
import { IPhoneNumberType } from 'app/shared/model/phone-number-type.model';
import { PhoneNumberTypeService } from 'app/entities/phone-number-type/phone-number-type.service';

@Component({
  selector: 'jhi-person-phone-update',
  templateUrl: './person-phone-update.component.html'
})
export class PersonPhoneUpdateComponent implements OnInit {
  isSaving: boolean;

  people: IPeople[];

  phonenumbertypes: IPhoneNumberType[];

  editForm = this.fb.group({
    id: [],
    phoneNumber: [null, [Validators.required]],
    defaultInd: [],
    activeInd: [],
    personId: [],
    phoneNumberTypeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected personPhoneService: PersonPhoneService,
    protected peopleService: PeopleService,
    protected phoneNumberTypeService: PhoneNumberTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ personPhone }) => {
      this.updateForm(personPhone);
    });
    this.peopleService
      .query()
      .subscribe((res: HttpResponse<IPeople[]>) => (this.people = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.phoneNumberTypeService
      .query()
      .subscribe(
        (res: HttpResponse<IPhoneNumberType[]>) => (this.phonenumbertypes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(personPhone: IPersonPhone) {
    this.editForm.patchValue({
      id: personPhone.id,
      phoneNumber: personPhone.phoneNumber,
      defaultInd: personPhone.defaultInd,
      activeInd: personPhone.activeInd,
      personId: personPhone.personId,
      phoneNumberTypeId: personPhone.phoneNumberTypeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const personPhone = this.createFromForm();
    if (personPhone.id !== undefined) {
      this.subscribeToSaveResponse(this.personPhoneService.update(personPhone));
    } else {
      this.subscribeToSaveResponse(this.personPhoneService.create(personPhone));
    }
  }

  private createFromForm(): IPersonPhone {
    return {
      ...new PersonPhone(),
      id: this.editForm.get(['id']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      defaultInd: this.editForm.get(['defaultInd']).value,
      activeInd: this.editForm.get(['activeInd']).value,
      personId: this.editForm.get(['personId']).value,
      phoneNumberTypeId: this.editForm.get(['phoneNumberTypeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonPhone>>) {
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

  trackPhoneNumberTypeById(index: number, item: IPhoneNumberType) {
    return item.id;
  }
}
