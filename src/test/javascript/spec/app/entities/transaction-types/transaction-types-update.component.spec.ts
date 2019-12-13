import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { TransactionTypesUpdateComponent } from 'app/entities/transaction-types/transaction-types-update.component';
import { TransactionTypesService } from 'app/entities/transaction-types/transaction-types.service';
import { TransactionTypes } from 'app/shared/model/transaction-types.model';

describe('Component Tests', () => {
  describe('TransactionTypes Management Update Component', () => {
    let comp: TransactionTypesUpdateComponent;
    let fixture: ComponentFixture<TransactionTypesUpdateComponent>;
    let service: TransactionTypesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [TransactionTypesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransactionTypesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionTypesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionTypesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionTypes(123);
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
        const entity = new TransactionTypes();
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
