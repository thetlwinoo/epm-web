import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { PersonEmailAddressUpdateComponent } from 'app/entities/person-email-address/person-email-address-update.component';
import { PersonEmailAddressService } from 'app/entities/person-email-address/person-email-address.service';
import { PersonEmailAddress } from 'app/shared/model/person-email-address.model';

describe('Component Tests', () => {
  describe('PersonEmailAddress Management Update Component', () => {
    let comp: PersonEmailAddressUpdateComponent;
    let fixture: ComponentFixture<PersonEmailAddressUpdateComponent>;
    let service: PersonEmailAddressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PersonEmailAddressUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PersonEmailAddressUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonEmailAddressUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonEmailAddressService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PersonEmailAddress(123);
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
        const entity = new PersonEmailAddress();
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
