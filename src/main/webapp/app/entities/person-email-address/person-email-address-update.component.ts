import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IPersonEmailAddress, PersonEmailAddress } from 'app/shared/model/person-email-address.model';
import { PersonEmailAddressService } from './person-email-address.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people/people.service';

@Component({
  selector: 'jhi-person-email-address-update',
  templateUrl: './person-email-address-update.component.html'
})
export class PersonEmailAddressUpdateComponent implements OnInit {
  isSaving: boolean;

  people: IPeople[];

  editForm = this.fb.group({
    id: [],
    emailAddress: [null, [Validators.required, Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    defaultInd: [],
    activeInd: [],
    personId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected personEmailAddressService: PersonEmailAddressService,
    protected peopleService: PeopleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ personEmailAddress }) => {
      this.updateForm(personEmailAddress);
    });
    this.peopleService
      .query()
      .subscribe((res: HttpResponse<IPeople[]>) => (this.people = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(personEmailAddress: IPersonEmailAddress) {
    this.editForm.patchValue({
      id: personEmailAddress.id,
      emailAddress: personEmailAddress.emailAddress,
      defaultInd: personEmailAddress.defaultInd,
      activeInd: personEmailAddress.activeInd,
      personId: personEmailAddress.personId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const personEmailAddress = this.createFromForm();
    if (personEmailAddress.id !== undefined) {
      this.subscribeToSaveResponse(this.personEmailAddressService.update(personEmailAddress));
    } else {
      this.subscribeToSaveResponse(this.personEmailAddressService.create(personEmailAddress));
    }
  }

  private createFromForm(): IPersonEmailAddress {
    return {
      ...new PersonEmailAddress(),
      id: this.editForm.get(['id']).value,
      emailAddress: this.editForm.get(['emailAddress']).value,
      defaultInd: this.editForm.get(['defaultInd']).value,
      activeInd: this.editForm.get(['activeInd']).value,
      personId: this.editForm.get(['personId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonEmailAddress>>) {
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
}
