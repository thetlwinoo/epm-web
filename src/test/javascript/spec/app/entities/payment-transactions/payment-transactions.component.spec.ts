import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { PaymentTransactionsComponent } from 'app/entities/payment-transactions/payment-transactions.component';
import { PaymentTransactionsService } from 'app/entities/payment-transactions/payment-transactions.service';
import { PaymentTransactions } from 'app/shared/model/payment-transactions.model';

describe('Component Tests', () => {
  describe('PaymentTransactions Management Component', () => {
    let comp: PaymentTransactionsComponent;
    let fixture: ComponentFixture<PaymentTransactionsComponent>;
    let service: PaymentTransactionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PaymentTransactionsComponent],
        providers: []
      })
        .overrideTemplate(PaymentTransactionsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentTransactionsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentTransactionsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PaymentTransactions(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.paymentTransactions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
