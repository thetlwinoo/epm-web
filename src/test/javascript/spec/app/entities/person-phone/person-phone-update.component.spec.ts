import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { PersonPhoneUpdateComponent } from 'app/entities/person-phone/person-phone-update.component';
import { PersonPhoneService } from 'app/entities/person-phone/person-phone.service';
import { PersonPhone } from 'app/shared/model/person-phone.model';

describe('Component Tests', () => {
  describe('PersonPhone Management Update Component', () => {
    let comp: PersonPhoneUpdateComponent;
    let fixture: ComponentFixture<PersonPhoneUpdateComponent>;
    let service: PersonPhoneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PersonPhoneUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PersonPhoneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonPhoneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonPhoneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PersonPhone(123);
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
        const entity = new PersonPhone();
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
