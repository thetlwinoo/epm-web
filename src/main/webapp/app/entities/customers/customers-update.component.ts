import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ICustomers, Customers } from 'app/shared/model/customers.model';
import { CustomersService } from './customers.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-customers-update',
  templateUrl: './customers-update.component.html'
})
export class CustomersUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    accountNumber: [null, [Validators.required]],
    userId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected customersService: CustomersService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customers }) => {
      this.updateForm(customers);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(customers: ICustomers) {
    this.editForm.patchValue({
      id: customers.id,
      accountNumber: customers.accountNumber,
      userId: customers.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const customers = this.createFromForm();
    if (customers.id !== undefined) {
      this.subscribeToSaveResponse(this.customersService.update(customers));
    } else {
      this.subscribeToSaveResponse(this.customersService.create(customers));
    }
  }

  private createFromForm(): ICustomers {
    return {
      ...new Customers(),
      id: this.editForm.get(['id']).value,
      accountNumber: this.editForm.get(['accountNumber']).value,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomers>>) {
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
}
