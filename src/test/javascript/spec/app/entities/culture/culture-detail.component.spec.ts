import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { CultureDetailComponent } from 'app/entities/culture/culture-detail.component';
import { Culture } from 'app/shared/model/culture.model';

describe('Component Tests', () => {
  describe('Culture Management Detail Component', () => {
    let comp: CultureDetailComponent;
    let fixture: ComponentFixture<CultureDetailComponent>;
    const route = ({ data: of({ culture: new Culture(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CultureDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CultureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CultureDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.culture).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
