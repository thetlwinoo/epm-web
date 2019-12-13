import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IWishlists, Wishlists } from 'app/shared/model/wishlists.model';
import { WishlistsService } from './wishlists.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people/people.service';

@Component({
  selector: 'jhi-wishlists-update',
  templateUrl: './wishlists-update.component.html'
})
export class WishlistsUpdateComponent implements OnInit {
  isSaving: boolean;

  wishlistusers: IPeople[];

  editForm = this.fb.group({
    id: [],
    wishlistUserId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected wishlistsService: WishlistsService,
    protected peopleService: PeopleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ wishlists }) => {
      this.updateForm(wishlists);
    });
    this.peopleService.query({ 'wishlistId.specified': 'false' }).subscribe(
      (res: HttpResponse<IPeople[]>) => {
        if (!this.editForm.get('wishlistUserId').value) {
          this.wishlistusers = res.body;
        } else {
          this.peopleService
            .find(this.editForm.get('wishlistUserId').value)
            .subscribe(
              (subRes: HttpResponse<IPeople>) => (this.wishlistusers = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(wishlists: IWishlists) {
    this.editForm.patchValue({
      id: wishlists.id,
      wishlistUserId: wishlists.wishlistUserId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const wishlists = this.createFromForm();
    if (wishlists.id !== undefined) {
      this.subscribeToSaveResponse(this.wishlistsService.update(wishlists));
    } else {
      this.subscribeToSaveResponse(this.wishlistsService.create(wishlists));
    }
  }

  private createFromForm(): IWishlists {
    return {
      ...new Wishlists(),
      id: this.editForm.get(['id']).value,
      wishlistUserId: this.editForm.get(['wishlistUserId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWishlists>>) {
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
