import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { BusinessEntityContactDetailComponent } from 'app/entities/business-entity-contact/business-entity-contact-detail.component';
import { BusinessEntityContact } from 'app/shared/model/business-entity-contact.model';

describe('Component Tests', () => {
  describe('BusinessEntityContact Management Detail Component', () => {
    let comp: BusinessEntityContactDetailComponent;
    let fixture: ComponentFixture<BusinessEntityContactDetailComponent>;
    const route = ({ data: of({ businessEntityContact: new BusinessEntityContact(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [BusinessEntityContactDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BusinessEntityContactDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BusinessEntityContactDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.businessEntityContact).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
