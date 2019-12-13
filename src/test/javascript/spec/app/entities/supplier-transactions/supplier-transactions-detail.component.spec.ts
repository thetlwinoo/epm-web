import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { SupplierTransactionsDetailComponent } from 'app/entities/supplier-transactions/supplier-transactions-detail.component';
import { SupplierTransactions } from 'app/shared/model/supplier-transactions.model';

describe('Component Tests', () => {
  describe('SupplierTransactions Management Detail Component', () => {
    let comp: SupplierTransactionsDetailComponent;
    let fixture: ComponentFixture<SupplierTransactionsDetailComponent>;
    const route = ({ data: of({ supplierTransactions: new SupplierTransactions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [SupplierTransactionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SupplierTransactionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupplierTransactionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.supplierTransactions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
