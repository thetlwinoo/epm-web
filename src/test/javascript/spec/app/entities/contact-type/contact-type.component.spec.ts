import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { ContactTypeComponent } from 'app/entities/contact-type/contact-type.component';
import { ContactTypeService } from 'app/entities/contact-type/contact-type.service';
import { ContactType } from 'app/shared/model/contact-type.model';

describe('Component Tests', () => {
  describe('ContactType Management Component', () => {
    let comp: ContactTypeComponent;
    let fixture: ComponentFixture<ContactTypeComponent>;
    let service: ContactTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ContactTypeComponent],
        providers: []
      })
        .overrideTemplate(ContactTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ContactType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contactTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
