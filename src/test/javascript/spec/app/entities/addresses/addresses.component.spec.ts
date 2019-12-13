import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { AddressesComponent } from 'app/entities/addresses/addresses.component';
import { AddressesService } from 'app/entities/addresses/addresses.service';
import { Addresses } from 'app/shared/model/addresses.model';

describe('Component Tests', () => {
  describe('Addresses Management Component', () => {
    let comp: AddressesComponent;
    let fixture: ComponentFixture<AddressesComponent>;
    let service: AddressesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [AddressesComponent],
        providers: []
      })
        .overrideTemplate(AddressesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AddressesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AddressesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Addresses(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.addresses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
