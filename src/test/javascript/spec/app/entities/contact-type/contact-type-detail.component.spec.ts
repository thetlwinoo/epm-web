import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ContactTypeDetailComponent } from 'app/entities/contact-type/contact-type-detail.component';
import { ContactType } from 'app/shared/model/contact-type.model';

describe('Component Tests', () => {
  describe('ContactType Management Detail Component', () => {
    let comp: ContactTypeDetailComponent;
    let fixture: ComponentFixture<ContactTypeDetailComponent>;
    const route = ({ data: of({ contactType: new ContactType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ContactTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContactTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContactTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contactType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
