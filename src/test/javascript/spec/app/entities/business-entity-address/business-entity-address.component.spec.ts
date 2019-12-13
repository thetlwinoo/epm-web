import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { BusinessEntityAddressComponent } from 'app/entities/business-entity-address/business-entity-address.component';
import { BusinessEntityAddressService } from 'app/entities/business-entity-address/business-entity-address.service';
import { BusinessEntityAddress } from 'app/shared/model/business-entity-address.model';

describe('Component Tests', () => {
  describe('BusinessEntityAddress Management Component', () => {
    let comp: BusinessEntityAddressComponent;
    let fixture: ComponentFixture<BusinessEntityAddressComponent>;
    let service: BusinessEntityAddressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [BusinessEntityAddressComponent],
        providers: []
      })
        .overrideTemplate(BusinessEntityAddressComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusinessEntityAddressComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessEntityAddressService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BusinessEntityAddress(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.businessEntityAddresses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
