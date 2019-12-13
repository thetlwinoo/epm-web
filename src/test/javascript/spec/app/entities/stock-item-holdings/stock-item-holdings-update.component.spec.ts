import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { StockItemHoldingsUpdateComponent } from 'app/entities/stock-item-holdings/stock-item-holdings-update.component';
import { StockItemHoldingsService } from 'app/entities/stock-item-holdings/stock-item-holdings.service';
import { StockItemHoldings } from 'app/shared/model/stock-item-holdings.model';

describe('Component Tests', () => {
  describe('StockItemHoldings Management Update Component', () => {
    let comp: StockItemHoldingsUpdateComponent;
    let fixture: ComponentFixture<StockItemHoldingsUpdateComponent>;
    let service: StockItemHoldingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [StockItemHoldingsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StockItemHoldingsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StockItemHoldingsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockItemHoldingsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StockItemHoldings(123);
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
        const entity = new StockItemHoldings();
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
