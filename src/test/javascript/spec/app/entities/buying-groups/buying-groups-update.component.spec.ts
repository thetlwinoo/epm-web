import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { BuyingGroupsUpdateComponent } from 'app/entities/buying-groups/buying-groups-update.component';
import { BuyingGroupsService } from 'app/entities/buying-groups/buying-groups.service';
import { BuyingGroups } from 'app/shared/model/buying-groups.model';

describe('Component Tests', () => {
  describe('BuyingGroups Management Update Component', () => {
    let comp: BuyingGroupsUpdateComponent;
    let fixture: ComponentFixture<BuyingGroupsUpdateComponent>;
    let service: BuyingGroupsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [BuyingGroupsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BuyingGroupsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BuyingGroupsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BuyingGroupsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BuyingGroups(123);
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
        const entity = new BuyingGroups();
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
