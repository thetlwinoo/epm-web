import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { PersonPhoneDetailComponent } from 'app/entities/person-phone/person-phone-detail.component';
import { PersonPhone } from 'app/shared/model/person-phone.model';

describe('Component Tests', () => {
  describe('PersonPhone Management Detail Component', () => {
    let comp: PersonPhoneDetailComponent;
    let fixture: ComponentFixture<PersonPhoneDetailComponent>;
    const route = ({ data: of({ personPhone: new PersonPhone(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PersonPhoneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PersonPhoneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonPhoneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.personPhone).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
