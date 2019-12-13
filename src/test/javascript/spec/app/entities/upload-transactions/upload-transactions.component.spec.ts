import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { UploadTransactionsComponent } from 'app/entities/upload-transactions/upload-transactions.component';
import { UploadTransactionsService } from 'app/entities/upload-transactions/upload-transactions.service';
import { UploadTransactions } from 'app/shared/model/upload-transactions.model';

describe('Component Tests', () => {
  describe('UploadTransactions Management Component', () => {
    let comp: UploadTransactionsComponent;
    let fixture: ComponentFixture<UploadTransactionsComponent>;
    let service: UploadTransactionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [UploadTransactionsComponent],
        providers: []
      })
        .overrideTemplate(UploadTransactionsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UploadTransactionsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UploadTransactionsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UploadTransactions(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.uploadTransactions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
