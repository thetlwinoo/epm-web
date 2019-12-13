import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { UploadTransactionsUpdateComponent } from 'app/entities/upload-transactions/upload-transactions-update.component';
import { UploadTransactionsService } from 'app/entities/upload-transactions/upload-transactions.service';
import { UploadTransactions } from 'app/shared/model/upload-transactions.model';

describe('Component Tests', () => {
  describe('UploadTransactions Management Update Component', () => {
    let comp: UploadTransactionsUpdateComponent;
    let fixture: ComponentFixture<UploadTransactionsUpdateComponent>;
    let service: UploadTransactionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [UploadTransactionsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UploadTransactionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UploadTransactionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UploadTransactionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UploadTransactions(123);
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
        const entity = new UploadTransactions();
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
