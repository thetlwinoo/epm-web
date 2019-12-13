import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { SupplierTransactionsUpdateComponent } from 'app/entities/supplier-transactions/supplier-transactions-update.component';
import { SupplierTransactionsService } from 'app/entities/supplier-transactions/supplier-transactions.service';
import { SupplierTransactions } from 'app/shared/model/supplier-transactions.model';

describe('Component Tests', () => {
  describe('SupplierTransactions Management Update Component', () => {
    let comp: SupplierTransactionsUpdateComponent;
    let fixture: ComponentFixture<SupplierTransactionsUpdateComponent>;
    let service: SupplierTransactionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [SupplierTransactionsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SupplierTransactionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupplierTransactionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupplierTransactionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SupplierTransactions(123);
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
        const entity = new SupplierTransactions();
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
