import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { CustomerTransactionsDetailComponent } from 'app/entities/customer-transactions/customer-transactions-detail.component';
import { CustomerTransactions } from 'app/shared/model/customer-transactions.model';

describe('Component Tests', () => {
  describe('CustomerTransactions Management Detail Component', () => {
    let comp: CustomerTransactionsDetailComponent;
    let fixture: ComponentFixture<CustomerTransactionsDetailComponent>;
    const route = ({ data: of({ customerTransactions: new CustomerTransactions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CustomerTransactionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CustomerTransactionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerTransactionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customerTransactions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
