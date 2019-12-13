import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { CustomerTransactionsUpdateComponent } from 'app/entities/customer-transactions/customer-transactions-update.component';
import { CustomerTransactionsService } from 'app/entities/customer-transactions/customer-transactions.service';
import { CustomerTransactions } from 'app/shared/model/customer-transactions.model';

describe('Component Tests', () => {
  describe('CustomerTransactions Management Update Component', () => {
    let comp: CustomerTransactionsUpdateComponent;
    let fixture: ComponentFixture<CustomerTransactionsUpdateComponent>;
    let service: CustomerTransactionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CustomerTransactionsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CustomerTransactionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerTransactionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerTransactionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerTransactions(123);
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
        const entity = new CustomerTransactions();
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
