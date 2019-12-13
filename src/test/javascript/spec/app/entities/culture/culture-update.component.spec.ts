import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { CultureUpdateComponent } from 'app/entities/culture/culture-update.component';
import { CultureService } from 'app/entities/culture/culture.service';
import { Culture } from 'app/shared/model/culture.model';

describe('Component Tests', () => {
  describe('Culture Management Update Component', () => {
    let comp: CultureUpdateComponent;
    let fixture: ComponentFixture<CultureUpdateComponent>;
    let service: CultureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CultureUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CultureUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CultureUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CultureService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Culture(123);
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
        const entity = new Culture();
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
