import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IDangerousGoods, DangerousGoods } from 'app/shared/model/dangerous-goods.model';
import { DangerousGoodsService } from './dangerous-goods.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';

@Component({
  selector: 'jhi-dangerous-goods-update',
  templateUrl: './dangerous-goods-update.component.html'
})
export class DangerousGoodsUpdateComponent implements OnInit {
  isSaving: boolean;

  stockitems: IStockItems[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    stockItemId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected dangerousGoodsService: DangerousGoodsService,
    protected stockItemsService: StockItemsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dangerousGoods }) => {
      this.updateForm(dangerousGoods);
    });
    this.stockItemsService
      .query()
      .subscribe((res: HttpResponse<IStockItems[]>) => (this.stockitems = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(dangerousGoods: IDangerousGoods) {
    this.editForm.patchValue({
      id: dangerousGoods.id,
      name: dangerousGoods.name,
      stockItemId: dangerousGoods.stockItemId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dangerousGoods = this.createFromForm();
    if (dangerousGoods.id !== undefined) {
      this.subscribeToSaveResponse(this.dangerousGoodsService.update(dangerousGoods));
    } else {
      this.subscribeToSaveResponse(this.dangerousGoodsService.create(dangerousGoods));
    }
  }

  private createFromForm(): IDangerousGoods {
    return {
      ...new DangerousGoods(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      stockItemId: this.editForm.get(['stockItemId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDangerousGoods>>) {
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

  trackStockItemsById(index: number, item: IStockItems) {
    return item.id;
  }
}
