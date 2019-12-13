import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { UnitMeasureDetailComponent } from 'app/entities/unit-measure/unit-measure-detail.component';
import { UnitMeasure } from 'app/shared/model/unit-measure.model';

describe('Component Tests', () => {
  describe('UnitMeasure Management Detail Component', () => {
    let comp: UnitMeasureDetailComponent;
    let fixture: ComponentFixture<UnitMeasureDetailComponent>;
    const route = ({ data: of({ unitMeasure: new UnitMeasure(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [UnitMeasureDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UnitMeasureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UnitMeasureDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.unitMeasure).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
