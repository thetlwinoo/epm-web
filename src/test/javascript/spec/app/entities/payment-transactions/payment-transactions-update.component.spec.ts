import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { PaymentTransactionsUpdateComponent } from 'app/entities/payment-transactions/payment-transactions-update.component';
import { PaymentTransactionsService } from 'app/entities/payment-transactions/payment-transactions.service';
import { PaymentTransactions } from 'app/shared/model/payment-transactions.model';

describe('Component Tests', () => {
  describe('PaymentTransactions Management Update Component', () => {
    let comp: PaymentTransactionsUpdateComponent;
    let fixture: ComponentFixture<PaymentTransactionsUpdateComponent>;
    let service: PaymentTransactionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PaymentTransactionsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PaymentTransactionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentTransactionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentTransactionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentTransactions(123);
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
        const entity = new PaymentTransactions();
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
