import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ICompares, Compares } from 'app/shared/model/compares.model';
import { ComparesService } from './compares.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people/people.service';

@Component({
  selector: 'jhi-compares-update',
  templateUrl: './compares-update.component.html'
})
export class ComparesUpdateComponent implements OnInit {
  isSaving: boolean;

  compareusers: IPeople[];

  editForm = this.fb.group({
    id: [],
    compareUserId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected comparesService: ComparesService,
    protected peopleService: PeopleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ compares }) => {
      this.updateForm(compares);
    });
    this.peopleService.query({ 'compareId.specified': 'false' }).subscribe(
      (res: HttpResponse<IPeople[]>) => {
        if (!this.editForm.get('compareUserId').value) {
          this.compareusers = res.body;
        } else {
          this.peopleService
            .find(this.editForm.get('compareUserId').value)
            .subscribe(
              (subRes: HttpResponse<IPeople>) => (this.compareusers = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(compares: ICompares) {
    this.editForm.patchValue({
      id: compares.id,
      compareUserId: compares.compareUserId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const compares = this.createFromForm();
    if (compares.id !== undefined) {
      this.subscribeToSaveResponse(this.comparesService.update(compares));
    } else {
      this.subscribeToSaveResponse(this.comparesService.create(compares));
    }
  }

  private createFromForm(): ICompares {
    return {
      ...new Compares(),
      id: this.editForm.get(['id']).value,
      compareUserId: this.editForm.get(['compareUserId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompares>>) {
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
