import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IBusinessEntityContact, BusinessEntityContact } from 'app/shared/model/business-entity-contact.model';
import { BusinessEntityContactService } from './business-entity-contact.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people/people.service';
import { IContactType } from 'app/shared/model/contact-type.model';
import { ContactTypeService } from 'app/entities/contact-type/contact-type.service';

@Component({
  selector: 'jhi-business-entity-contact-update',
  templateUrl: './business-entity-contact-update.component.html'
})
export class BusinessEntityContactUpdateComponent implements OnInit {
  isSaving: boolean;

  people: IPeople[];

  contacttypes: IContactType[];

  editForm = this.fb.group({
    id: [],
    personId: [],
    contactTypeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected businessEntityContactService: BusinessEntityContactService,
    protected peopleService: PeopleService,
    protected contactTypeService: ContactTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ businessEntityContact }) => {
      this.updateForm(businessEntityContact);
    });
    this.peopleService
      .query()
      .subscribe((res: HttpResponse<IPeople[]>) => (this.people = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.contactTypeService
      .query()
      .subscribe(
        (res: HttpResponse<IContactType[]>) => (this.contacttypes = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(businessEntityContact: IBusinessEntityContact) {
    this.editForm.patchValue({
      id: businessEntityContact.id,
      personId: businessEntityContact.personId,
      contactTypeId: businessEntityContact.contactTypeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const businessEntityContact = this.createFromForm();
    if (businessEntityContact.id !== undefined) {
      this.subscribeToSaveResponse(this.businessEntityContactService.update(businessEntityContact));
    } else {
      this.subscribeToSaveResponse(this.businessEntityContactService.create(businessEntityContact));
    }
  }

  private createFromForm(): IBusinessEntityContact {
    return {
      ...new BusinessEntityContact(),
      id: this.editForm.get(['id']).value,
      personId: this.editForm.get(['personId']).value,
      contactTypeId: this.editForm.get(['contactTypeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBusinessEntityContact>>) {
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

  trackContactTypeById(index: number, item: IContactType) {
    return item.id;
  }
}
