import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { CustomerTransactionsComponent } from 'app/entities/customer-transactions/customer-transactions.component';
import { CustomerTransactionsService } from 'app/entities/customer-transactions/customer-transactions.service';
import { CustomerTransactions } from 'app/shared/model/customer-transactions.model';

describe('Component Tests', () => {
  describe('CustomerTransactions Management Component', () => {
    let comp: CustomerTransactionsComponent;
    let fixture: ComponentFixture<CustomerTransactionsComponent>;
    let service: CustomerTransactionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CustomerTransactionsComponent],
        providers: []
      })
        .overrideTemplate(CustomerTransactionsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerTransactionsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerTransactionsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CustomerTransactions(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.customerTransactions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
