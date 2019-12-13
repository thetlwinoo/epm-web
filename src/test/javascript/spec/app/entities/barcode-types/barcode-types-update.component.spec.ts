import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { BarcodeTypesUpdateComponent } from 'app/entities/barcode-types/barcode-types-update.component';
import { BarcodeTypesService } from 'app/entities/barcode-types/barcode-types.service';
import { BarcodeTypes } from 'app/shared/model/barcode-types.model';

describe('Component Tests', () => {
  describe('BarcodeTypes Management Update Component', () => {
    let comp: BarcodeTypesUpdateComponent;
    let fixture: ComponentFixture<BarcodeTypesUpdateComponent>;
    let service: BarcodeTypesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [BarcodeTypesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BarcodeTypesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BarcodeTypesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BarcodeTypesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BarcodeTypes(123);
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
        const entity = new BarcodeTypes();
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
