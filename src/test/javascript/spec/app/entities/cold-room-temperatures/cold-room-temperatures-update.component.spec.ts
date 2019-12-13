import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ColdRoomTemperaturesUpdateComponent } from 'app/entities/cold-room-temperatures/cold-room-temperatures-update.component';
import { ColdRoomTemperaturesService } from 'app/entities/cold-room-temperatures/cold-room-temperatures.service';
import { ColdRoomTemperatures } from 'app/shared/model/cold-room-temperatures.model';

describe('Component Tests', () => {
  describe('ColdRoomTemperatures Management Update Component', () => {
    let comp: ColdRoomTemperaturesUpdateComponent;
    let fixture: ComponentFixture<ColdRoomTemperaturesUpdateComponent>;
    let service: ColdRoomTemperaturesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ColdRoomTemperaturesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ColdRoomTemperaturesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ColdRoomTemperaturesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ColdRoomTemperaturesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ColdRoomTemperatures(123);
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
        const entity = new ColdRoomTemperatures();
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
