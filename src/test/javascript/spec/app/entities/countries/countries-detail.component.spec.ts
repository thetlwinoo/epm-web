import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { CountriesDetailComponent } from 'app/entities/countries/countries-detail.component';
import { Countries } from 'app/shared/model/countries.model';

describe('Component Tests', () => {
  describe('Countries Management Detail Component', () => {
    let comp: CountriesDetailComponent;
    let fixture: ComponentFixture<CountriesDetailComponent>;
    const route = ({ data: of({ countries: new Countries(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CountriesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CountriesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CountriesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.countries).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
