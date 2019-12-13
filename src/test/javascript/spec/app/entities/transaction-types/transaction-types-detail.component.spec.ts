import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { TransactionTypesDetailComponent } from 'app/entities/transaction-types/transaction-types-detail.component';
import { TransactionTypes } from 'app/shared/model/transaction-types.model';

describe('Component Tests', () => {
  describe('TransactionTypes Management Detail Component', () => {
    let comp: TransactionTypesDetailComponent;
    let fixture: ComponentFixture<TransactionTypesDetailComponent>;
    const route = ({ data: of({ transactionTypes: new TransactionTypes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [TransactionTypesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransactionTypesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionTypesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionTypes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
