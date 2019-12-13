import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { PeopleDetailComponent } from 'app/entities/people/people-detail.component';
import { People } from 'app/shared/model/people.model';

describe('Component Tests', () => {
  describe('People Management Detail Component', () => {
    let comp: PeopleDetailComponent;
    let fixture: ComponentFixture<PeopleDetailComponent>;
    const route = ({ data: of({ people: new People(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PeopleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PeopleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PeopleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.people).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
