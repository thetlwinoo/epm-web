import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { UploadTransactionsDetailComponent } from 'app/entities/upload-transactions/upload-transactions-detail.component';
import { UploadTransactions } from 'app/shared/model/upload-transactions.model';

describe('Component Tests', () => {
  describe('UploadTransactions Management Detail Component', () => {
    let comp: UploadTransactionsDetailComponent;
    let fixture: ComponentFixture<UploadTransactionsDetailComponent>;
    const route = ({ data: of({ uploadTransactions: new UploadTransactions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [UploadTransactionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UploadTransactionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UploadTransactionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.uploadTransactions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
