import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { StockItemTempUpdateComponent } from 'app/entities/stock-item-temp/stock-item-temp-update.component';
import { StockItemTempService } from 'app/entities/stock-item-temp/stock-item-temp.service';
import { StockItemTemp } from 'app/shared/model/stock-item-temp.model';

describe('Component Tests', () => {
  describe('StockItemTemp Management Update Component', () => {
    let comp: StockItemTempUpdateComponent;
    let fixture: ComponentFixture<StockItemTempUpdateComponent>;
    let service: StockItemTempService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [StockItemTempUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StockItemTempUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StockItemTempUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockItemTempService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StockItemTemp(123);
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
        const entity = new StockItemTemp();
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
