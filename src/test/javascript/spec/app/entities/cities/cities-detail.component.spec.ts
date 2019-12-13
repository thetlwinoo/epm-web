import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { CitiesDetailComponent } from 'app/entities/cities/cities-detail.component';
import { Cities } from 'app/shared/model/cities.model';

describe('Component Tests', () => {
  describe('Cities Management Detail Component', () => {
    let comp: CitiesDetailComponent;
    let fixture: ComponentFixture<CitiesDetailComponent>;
    const route = ({ data: of({ cities: new Cities(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CitiesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CitiesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CitiesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cities).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
