import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { PaymentTransactionsDetailComponent } from 'app/entities/payment-transactions/payment-transactions-detail.component';
import { PaymentTransactions } from 'app/shared/model/payment-transactions.model';

describe('Component Tests', () => {
  describe('PaymentTransactions Management Detail Component', () => {
    let comp: PaymentTransactionsDetailComponent;
    let fixture: ComponentFixture<PaymentTransactionsDetailComponent>;
    const route = ({ data: of({ paymentTransactions: new PaymentTransactions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PaymentTransactionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PaymentTransactionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentTransactionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paymentTransactions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
