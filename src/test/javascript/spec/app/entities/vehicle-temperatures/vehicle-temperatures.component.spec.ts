import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { VehicleTemperaturesComponent } from 'app/entities/vehicle-temperatures/vehicle-temperatures.component';
import { VehicleTemperaturesService } from 'app/entities/vehicle-temperatures/vehicle-temperatures.service';
import { VehicleTemperatures } from 'app/shared/model/vehicle-temperatures.model';

describe('Component Tests', () => {
  describe('VehicleTemperatures Management Component', () => {
    let comp: VehicleTemperaturesComponent;
    let fixture: ComponentFixture<VehicleTemperaturesComponent>;
    let service: VehicleTemperaturesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [VehicleTemperaturesComponent],
        providers: []
      })
        .overrideTemplate(VehicleTemperaturesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleTemperaturesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehicleTemperaturesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VehicleTemperatures(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vehicleTemperatures[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
