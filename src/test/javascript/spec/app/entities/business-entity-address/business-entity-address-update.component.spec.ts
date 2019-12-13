import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { BusinessEntityAddressUpdateComponent } from 'app/entities/business-entity-address/business-entity-address-update.component';
import { BusinessEntityAddressService } from 'app/entities/business-entity-address/business-entity-address.service';
import { BusinessEntityAddress } from 'app/shared/model/business-entity-address.model';

describe('Component Tests', () => {
  describe('BusinessEntityAddress Management Update Component', () => {
    let comp: BusinessEntityAddressUpdateComponent;
    let fixture: ComponentFixture<BusinessEntityAddressUpdateComponent>;
    let service: BusinessEntityAddressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [BusinessEntityAddressUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BusinessEntityAddressUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusinessEntityAddressUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessEntityAddressService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BusinessEntityAddress(123);
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
        const entity = new BusinessEntityAddress();
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
