import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ReviewLinesUpdateComponent } from 'app/entities/review-lines/review-lines-update.component';
import { ReviewLinesService } from 'app/entities/review-lines/review-lines.service';
import { ReviewLines } from 'app/shared/model/review-lines.model';

describe('Component Tests', () => {
  describe('ReviewLines Management Update Component', () => {
    let comp: ReviewLinesUpdateComponent;
    let fixture: ComponentFixture<ReviewLinesUpdateComponent>;
    let service: ReviewLinesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ReviewLinesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReviewLinesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReviewLinesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReviewLinesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReviewLines(123);
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
        const entity = new ReviewLines();
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
