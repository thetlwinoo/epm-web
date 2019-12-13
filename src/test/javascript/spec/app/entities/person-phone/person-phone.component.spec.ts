import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { PersonPhoneComponent } from 'app/entities/person-phone/person-phone.component';
import { PersonPhoneService } from 'app/entities/person-phone/person-phone.service';
import { PersonPhone } from 'app/shared/model/person-phone.model';

describe('Component Tests', () => {
  describe('PersonPhone Management Component', () => {
    let comp: PersonPhoneComponent;
    let fixture: ComponentFixture<PersonPhoneComponent>;
    let service: PersonPhoneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PersonPhoneComponent],
        providers: []
      })
        .overrideTemplate(PersonPhoneComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonPhoneComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonPhoneService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PersonPhone(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.personPhones[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
