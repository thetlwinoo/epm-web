import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { CountriesComponent } from 'app/entities/countries/countries.component';
import { CountriesService } from 'app/entities/countries/countries.service';
import { Countries } from 'app/shared/model/countries.model';

describe('Component Tests', () => {
  describe('Countries Management Component', () => {
    let comp: CountriesComponent;
    let fixture: ComponentFixture<CountriesComponent>;
    let service: CountriesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CountriesComponent],
        providers: []
      })
        .overrideTemplate(CountriesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CountriesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CountriesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Countries(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.countries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
