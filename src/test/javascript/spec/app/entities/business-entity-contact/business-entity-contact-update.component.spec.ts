import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { BusinessEntityContactUpdateComponent } from 'app/entities/business-entity-contact/business-entity-contact-update.component';
import { BusinessEntityContactService } from 'app/entities/business-entity-contact/business-entity-contact.service';
import { BusinessEntityContact } from 'app/shared/model/business-entity-contact.model';

describe('Component Tests', () => {
  describe('BusinessEntityContact Management Update Component', () => {
    let comp: BusinessEntityContactUpdateComponent;
    let fixture: ComponentFixture<BusinessEntityContactUpdateComponent>;
    let service: BusinessEntityContactService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [BusinessEntityContactUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BusinessEntityContactUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusinessEntityContactUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessEntityContactService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BusinessEntityContact(123);
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
        const entity = new BusinessEntityContact();
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
