import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { StateProvincesComponent } from 'app/entities/state-provinces/state-provinces.component';
import { StateProvincesService } from 'app/entities/state-provinces/state-provinces.service';
import { StateProvinces } from 'app/shared/model/state-provinces.model';

describe('Component Tests', () => {
  describe('StateProvinces Management Component', () => {
    let comp: StateProvincesComponent;
    let fixture: ComponentFixture<StateProvincesComponent>;
    let service: StateProvincesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [StateProvincesComponent],
        providers: []
      })
        .overrideTemplate(StateProvincesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StateProvincesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StateProvincesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new StateProvinces(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.stateProvinces[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
