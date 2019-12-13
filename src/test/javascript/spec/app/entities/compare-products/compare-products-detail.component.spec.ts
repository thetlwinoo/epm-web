import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { CompareProductsDetailComponent } from 'app/entities/compare-products/compare-products-detail.component';
import { CompareProducts } from 'app/shared/model/compare-products.model';

describe('Component Tests', () => {
  describe('CompareProducts Management Detail Component', () => {
    let comp: CompareProductsDetailComponent;
    let fixture: ComponentFixture<CompareProductsDetailComponent>;
    const route = ({ data: of({ compareProducts: new CompareProducts(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CompareProductsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompareProductsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompareProductsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.compareProducts).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
