import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { StockItemTransactionsUpdateComponent } from 'app/entities/stock-item-transactions/stock-item-transactions-update.component';
import { StockItemTransactionsService } from 'app/entities/stock-item-transactions/stock-item-transactions.service';
import { StockItemTransactions } from 'app/shared/model/stock-item-transactions.model';

describe('Component Tests', () => {
  describe('StockItemTransactions Management Update Component', () => {
    let comp: StockItemTransactionsUpdateComponent;
    let fixture: ComponentFixture<StockItemTransactionsUpdateComponent>;
    let service: StockItemTransactionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [StockItemTransactionsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StockItemTransactionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StockItemTransactionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockItemTransactionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StockItemTransactions(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new StockItemTransactions();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
