import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ReviewsUpdateComponent } from 'app/entities/reviews/reviews-update.component';
import { ReviewsService } from 'app/entities/reviews/reviews.service';
import { Reviews } from 'app/shared/model/reviews.model';

describe('Component Tests', () => {
  describe('Reviews Management Update Component', () => {
    let comp: ReviewsUpdateComponent;
    let fixture: ComponentFixture<ReviewsUpdateComponent>;
    let service: ReviewsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ReviewsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReviewsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReviewsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReviewsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Reviews(123);
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
        const entity = new Reviews();
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
