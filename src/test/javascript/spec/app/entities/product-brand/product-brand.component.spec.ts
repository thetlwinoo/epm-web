import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { ProductBrandComponent } from 'app/entities/product-brand/product-brand.component';
import { ProductBrandService } from 'app/entities/product-brand/product-brand.service';
import { ProductBrand } from 'app/shared/model/product-brand.model';

describe('Component Tests', () => {
  describe('ProductBrand Management Component', () => {
    let comp: ProductBrandComponent;
    let fixture: ComponentFixture<ProductBrandComponent>;
    let service: ProductBrandService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductBrandComponent],
        providers: []
      })
        .overrideTemplate(ProductBrandComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductBrandComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductBrandService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductBrand(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productBrands[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
