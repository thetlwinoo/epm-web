import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { UploadActionTypesUpdateComponent } from 'app/entities/upload-action-types/upload-action-types-update.component';
import { UploadActionTypesService } from 'app/entities/upload-action-types/upload-action-types.service';
import { UploadActionTypes } from 'app/shared/model/upload-action-types.model';

describe('Component Tests', () => {
  describe('UploadActionTypes Management Update Component', () => {
    let comp: UploadActionTypesUpdateComponent;
    let fixture: ComponentFixture<UploadActionTypesUpdateComponent>;
    let service: UploadActionTypesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [UploadActionTypesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UploadActionTypesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UploadActionTypesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UploadActionTypesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UploadActionTypes(123);
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
        const entity = new UploadActionTypes();
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
