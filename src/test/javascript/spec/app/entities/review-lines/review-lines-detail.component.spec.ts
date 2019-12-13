import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ReviewLinesDetailComponent } from 'app/entities/review-lines/review-lines-detail.component';
import { ReviewLines } from 'app/shared/model/review-lines.model';

describe('Component Tests', () => {
  describe('ReviewLines Management Detail Component', () => {
    let comp: ReviewLinesDetailComponent;
    let fixture: ComponentFixture<ReviewLinesDetailComponent>;
    const route = ({ data: of({ reviewLines: new ReviewLines(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ReviewLinesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReviewLinesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReviewLinesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reviewLines).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
