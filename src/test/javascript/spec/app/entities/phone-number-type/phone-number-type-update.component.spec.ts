import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { PhoneNumberTypeUpdateComponent } from 'app/entities/phone-number-type/phone-number-type-update.component';
import { PhoneNumberTypeService } from 'app/entities/phone-number-type/phone-number-type.service';
import { PhoneNumberType } from 'app/shared/model/phone-number-type.model';

describe('Component Tests', () => {
  describe('PhoneNumberType Management Update Component', () => {
    let comp: PhoneNumberTypeUpdateComponent;
    let fixture: ComponentFixture<PhoneNumberTypeUpdateComponent>;
    let service: PhoneNumberTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PhoneNumberTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PhoneNumberTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhoneNumberTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PhoneNumberTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PhoneNumberType(123);
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
        const entity = new PhoneNumberType();
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
