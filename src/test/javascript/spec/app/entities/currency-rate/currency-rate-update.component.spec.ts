import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { CurrencyRateUpdateComponent } from 'app/entities/currency-rate/currency-rate-update.component';
import { CurrencyRateService } from 'app/entities/currency-rate/currency-rate.service';
import { CurrencyRate } from 'app/shared/model/currency-rate.model';

describe('Component Tests', () => {
  describe('CurrencyRate Management Update Component', () => {
    let comp: CurrencyRateUpdateComponent;
    let fixture: ComponentFixture<CurrencyRateUpdateComponent>;
    let service: CurrencyRateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CurrencyRateUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CurrencyRateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CurrencyRateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CurrencyRateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CurrencyRate(123);
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
        const entity = new CurrencyRate();
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
