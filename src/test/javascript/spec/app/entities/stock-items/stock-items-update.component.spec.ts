import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { StockItemsUpdateComponent } from 'app/entities/stock-items/stock-items-update.component';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';
import { StockItems } from 'app/shared/model/stock-items.model';

describe('Component Tests', () => {
  describe('StockItems Management Update Component', () => {
    let comp: StockItemsUpdateComponent;
    let fixture: ComponentFixture<StockItemsUpdateComponent>;
    let service: StockItemsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [StockItemsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StockItemsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StockItemsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockItemsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StockItems(123);
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
        const entity = new StockItems();
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
