import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { PhoneNumberTypeDetailComponent } from 'app/entities/phone-number-type/phone-number-type-detail.component';
import { PhoneNumberType } from 'app/shared/model/phone-number-type.model';

describe('Component Tests', () => {
  describe('PhoneNumberType Management Detail Component', () => {
    let comp: PhoneNumberTypeDetailComponent;
    let fixture: ComponentFixture<PhoneNumberTypeDetailComponent>;
    const route = ({ data: of({ phoneNumberType: new PhoneNumberType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PhoneNumberTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PhoneNumberTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PhoneNumberTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.phoneNumberType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
