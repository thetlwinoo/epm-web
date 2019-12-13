import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { DangerousGoodsDeleteDialogComponent } from 'app/entities/dangerous-goods/dangerous-goods-delete-dialog.component';
import { DangerousGoodsService } from 'app/entities/dangerous-goods/dangerous-goods.service';

describe('Component Tests', () => {
  describe('DangerousGoods Management Delete Component', () => {
    let comp: DangerousGoodsDeleteDialogComponent;
    let fixture: ComponentFixture<DangerousGoodsDeleteDialogComponent>;
    let service: DangerousGoodsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [DangerousGoodsDeleteDialogComponent]
      })
        .overrideTemplate(DangerousGoodsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DangerousGoodsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DangerousGoodsService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
