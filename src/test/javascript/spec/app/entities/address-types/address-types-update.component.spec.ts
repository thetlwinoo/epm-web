import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { AddressTypesUpdateComponent } from 'app/entities/address-types/address-types-update.component';
import { AddressTypesService } from 'app/entities/address-types/address-types.service';
import { AddressTypes } from 'app/shared/model/address-types.model';

describe('Component Tests', () => {
  describe('AddressTypes Management Update Component', () => {
    let comp: AddressTypesUpdateComponent;
    let fixture: ComponentFixture<AddressTypesUpdateComponent>;
    let service: AddressTypesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [AddressTypesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AddressTypesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AddressTypesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AddressTypesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AddressTypes(123);
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
        const entity = new AddressTypes();
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
