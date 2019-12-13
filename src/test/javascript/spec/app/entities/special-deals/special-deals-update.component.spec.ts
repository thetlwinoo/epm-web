import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { SpecialDealsUpdateComponent } from 'app/entities/special-deals/special-deals-update.component';
import { SpecialDealsService } from 'app/entities/special-deals/special-deals.service';
import { SpecialDeals } from 'app/shared/model/special-deals.model';

describe('Component Tests', () => {
  describe('SpecialDeals Management Update Component', () => {
    let comp: SpecialDealsUpdateComponent;
    let fixture: ComponentFixture<SpecialDealsUpdateComponent>;
    let service: SpecialDealsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [SpecialDealsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SpecialDealsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpecialDealsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpecialDealsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SpecialDeals(123);
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
        const entity = new SpecialDeals();
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
