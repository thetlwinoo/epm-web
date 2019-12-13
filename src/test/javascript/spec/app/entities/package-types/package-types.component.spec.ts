import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { PackageTypesComponent } from 'app/entities/package-types/package-types.component';
import { PackageTypesService } from 'app/entities/package-types/package-types.service';
import { PackageTypes } from 'app/shared/model/package-types.model';

describe('Component Tests', () => {
  describe('PackageTypes Management Component', () => {
    let comp: PackageTypesComponent;
    let fixture: ComponentFixture<PackageTypesComponent>;
    let service: PackageTypesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PackageTypesComponent],
        providers: []
      })
        .overrideTemplate(PackageTypesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PackageTypesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PackageTypesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PackageTypes(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.packageTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
