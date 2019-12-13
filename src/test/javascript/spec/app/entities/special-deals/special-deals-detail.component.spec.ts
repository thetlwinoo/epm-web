import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { SpecialDealsDetailComponent } from 'app/entities/special-deals/special-deals-detail.component';
import { SpecialDeals } from 'app/shared/model/special-deals.model';

describe('Component Tests', () => {
  describe('SpecialDeals Management Detail Component', () => {
    let comp: SpecialDealsDetailComponent;
    let fixture: ComponentFixture<SpecialDealsDetailComponent>;
    const route = ({ data: of({ specialDeals: new SpecialDeals(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [SpecialDealsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SpecialDealsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpecialDealsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.specialDeals).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
