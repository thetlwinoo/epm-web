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
import { IPeople, People } from 'app/shared/model/people.model';
import { PeopleService } from './people.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IShoppingCarts } from 'app/shared/model/shopping-carts.model';
import { ShoppingCartsService } from 'app/entities/shopping-carts/shopping-carts.service';
import { IWishlists } from 'app/shared/model/wishlists.model';
import { WishlistsService } from 'app/entities/wishlists/wishlists.service';
import { ICompares } from 'app/shared/model/compares.model';
import { ComparesService } from 'app/entities/compares/compares.service';

@Component({
  selector: 'jhi-people-update',
  templateUrl: './people-update.component.html'
})
export class PeopleUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  shoppingcarts: IShoppingCarts[];

  wishlists: IWishlists[];

  compares: ICompares[];

  editForm = this.fb.group({
    id: [],
    fullName: [null, [Validators.required]],
    preferredName: [null, [Validators.required]],
    searchName: [null, [Validators.required]],
    gender: [null, [Validators.required]],
    isPermittedToLogon: [null, [Validators.required]],
    logonName: [],
    isExternalLogonProvider: [null, [Validators.required]],
    isSystemUser: [null, [Validators.required]],
    isEmployee: [null, [Validators.required]],
    isSalesPerson: [null, [Validators.required]],
    isGuestUser: [null, [Validators.required]],
    emailPromotion: [null, [Validators.required]],
    userPreferences: [],
    phoneNumber: [],
    emailAddress: [null, [Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')]],
    photo: [],
    customFields: [],
    otherLanguages: [],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]],
    userId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected peopleService: PeopleService,
    protected userService: UserService,
    protected shoppingCartsService: ShoppingCartsService,
    protected wishlistsService: WishlistsService,
    protected comparesService: ComparesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ people }) => {
      this.updateForm(people);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.shoppingCartsService
      .query()
      .subscribe(
        (res: HttpResponse<IShoppingCarts[]>) => (this.shoppingcarts = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.wishlistsService
      .query()
      .subscribe((res: HttpResponse<IWishlists[]>) => (this.wishlists = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.comparesService
      .query()
      .subscribe((res: HttpResponse<ICompares[]>) => (this.compares = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(people: IPeople) {
    this.editForm.patchValue({
      id: people.id,
      fullName: people.fullName,
      preferredName: people.preferredName,
      searchName: people.searchName,
      gender: people.gender,
      isPermittedToLogon: people.isPermittedToLogon,
      logonName: people.logonName,
      isExternalLogonProvider: people.isExternalLogonProvider,
      isSystemUser: people.isSystemUser,
      isEmployee: people.isEmployee,
      isSalesPerson: people.isSalesPerson,
      isGuestUser: people.isGuestUser,
      emailPromotion: people.emailPromotion,
      userPreferences: people.userPreferences,
      phoneNumber: people.phoneNumber,
      emailAddress: people.emailAddress,
      photo: people.photo,
      customFields: people.customFields,
      otherLanguages: people.otherLanguages,
      validFrom: people.validFrom != null ? people.validFrom.format(DATE_TIME_FORMAT) : null,
      validTo: people.validTo != null ? people.validTo.format(DATE_TIME_FORMAT) : null,
      userId: people.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const people = this.createFromForm();
    if (people.id !== undefined) {
      this.subscribeToSaveResponse(this.peopleService.update(people));
    } else {
      this.subscribeToSaveResponse(this.peopleService.create(people));
    }
  }

  private createFromForm(): IPeople {
    return {
      ...new People(),
      id: this.editForm.get(['id']).value,
      fullName: this.editForm.get(['fullName']).value,
      preferredName: this.editForm.get(['preferredName']).value,
      searchName: this.editForm.get(['searchName']).value,
      gender: this.editForm.get(['gender']).value,
      isPermittedToLogon: this.editForm.get(['isPermittedToLogon']).value,
      logonName: this.editForm.get(['logonName']).value,
      isExternalLogonProvider: this.editForm.get(['isExternalLogonProvider']).value,
      isSystemUser: this.editForm.get(['isSystemUser']).value,
      isEmployee: this.editForm.get(['isEmployee']).value,
      isSalesPerson: this.editForm.get(['isSalesPerson']).value,
      isGuestUser: this.editForm.get(['isGuestUser']).value,
      emailPromotion: this.editForm.get(['emailPromotion']).value,
      userPreferences: this.editForm.get(['userPreferences']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      emailAddress: this.editForm.get(['emailAddress']).value,
      photo: this.editForm.get(['photo']).value,
      customFields: this.editForm.get(['customFields']).value,
      otherLanguages: this.editForm.get(['otherLanguages']).value,
      validFrom:
        this.editForm.get(['validFrom']).value != null ? moment(this.editForm.get(['validFrom']).value, DATE_TIME_FORMAT) : undefined,
      validTo: this.editForm.get(['validTo']).value != null ? moment(this.editForm.get(['validTo']).value, DATE_TIME_FORMAT) : undefined,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeople>>) {
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

  trackShoppingCartsById(index: number, item: IShoppingCarts) {
    return item.id;
  }

  trackWishlistsById(index: number, item: IWishlists) {
    return item.id;
  }

  trackComparesById(index: number, item: ICompares) {
    return item.id;
  }
}
