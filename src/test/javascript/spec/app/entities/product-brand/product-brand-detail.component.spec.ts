import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductBrandDetailComponent } from 'app/entities/product-brand/product-brand-detail.component';
import { ProductBrand } from 'app/shared/model/product-brand.model';

describe('Component Tests', () => {
  describe('ProductBrand Management Detail Component', () => {
    let comp: ProductBrandDetailComponent;
    let fixture: ComponentFixture<ProductBrandDetailComponent>;
    const route = ({ data: of({ productBrand: new ProductBrand(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductBrandDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductBrandDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductBrandDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productBrand).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
