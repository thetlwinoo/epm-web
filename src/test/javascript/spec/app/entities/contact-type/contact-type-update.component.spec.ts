import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ContactTypeUpdateComponent } from 'app/entities/contact-type/contact-type-update.component';
import { ContactTypeService } from 'app/entities/contact-type/contact-type.service';
import { ContactType } from 'app/shared/model/contact-type.model';

describe('Component Tests', () => {
  describe('ContactType Management Update Component', () => {
    let comp: ContactTypeUpdateComponent;
    let fixture: ComponentFixture<ContactTypeUpdateComponent>;
    let service: ContactTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ContactTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContactTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContactType(123);
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
        const entity = new ContactType();
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
