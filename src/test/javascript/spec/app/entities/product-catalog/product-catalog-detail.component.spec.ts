import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductCatalogDetailComponent } from 'app/entities/product-catalog/product-catalog-detail.component';
import { ProductCatalog } from 'app/shared/model/product-catalog.model';

describe('Component Tests', () => {
  describe('ProductCatalog Management Detail Component', () => {
    let comp: ProductCatalogDetailComponent;
    let fixture: ComponentFixture<ProductCatalogDetailComponent>;
    const route = ({ data: of({ productCatalog: new ProductCatalog(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductCatalogDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductCatalogDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductCatalogDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productCatalog).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
