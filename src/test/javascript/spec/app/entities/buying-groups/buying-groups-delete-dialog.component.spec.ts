import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EpmwebTestModule } from '../../../test.module';
import { BuyingGroupsDeleteDialogComponent } from 'app/entities/buying-groups/buying-groups-delete-dialog.component';
import { BuyingGroupsService } from 'app/entities/buying-groups/buying-groups.service';

describe('Component Tests', () => {
  describe('BuyingGroups Management Delete Component', () => {
    let comp: BuyingGroupsDeleteDialogComponent;
    let fixture: ComponentFixture<BuyingGroupsDeleteDialogComponent>;
    let service: BuyingGroupsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [BuyingGroupsDeleteDialogComponent]
      })
        .overrideTemplate(BuyingGroupsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BuyingGroupsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BuyingGroupsService);
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
