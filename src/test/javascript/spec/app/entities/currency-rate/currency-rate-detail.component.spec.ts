import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { CurrencyRateDetailComponent } from 'app/entities/currency-rate/currency-rate-detail.component';
import { CurrencyRate } from 'app/shared/model/currency-rate.model';

describe('Component Tests', () => {
  describe('CurrencyRate Management Detail Component', () => {
    let comp: CurrencyRateDetailComponent;
    let fixture: ComponentFixture<CurrencyRateDetailComponent>;
    const route = ({ data: of({ currencyRate: new CurrencyRate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CurrencyRateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CurrencyRateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CurrencyRateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.currencyRate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
