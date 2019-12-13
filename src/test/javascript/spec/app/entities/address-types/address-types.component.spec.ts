import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { AddressTypesComponent } from 'app/entities/address-types/address-types.component';
import { AddressTypesService } from 'app/entities/address-types/address-types.service';
import { AddressTypes } from 'app/shared/model/address-types.model';

describe('Component Tests', () => {
  describe('AddressTypes Management Component', () => {
    let comp: AddressTypesComponent;
    let fixture: ComponentFixture<AddressTypesComponent>;
    let service: AddressTypesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [AddressTypesComponent],
        providers: []
      })
        .overrideTemplate(AddressTypesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AddressTypesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AddressTypesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AddressTypes(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.addressTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
