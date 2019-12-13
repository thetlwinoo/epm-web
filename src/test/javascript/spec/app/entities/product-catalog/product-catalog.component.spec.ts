import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { ProductCatalogComponent } from 'app/entities/product-catalog/product-catalog.component';
import { ProductCatalogService } from 'app/entities/product-catalog/product-catalog.service';
import { ProductCatalog } from 'app/shared/model/product-catalog.model';

describe('Component Tests', () => {
  describe('ProductCatalog Management Component', () => {
    let comp: ProductCatalogComponent;
    let fixture: ComponentFixture<ProductCatalogComponent>;
    let service: ProductCatalogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductCatalogComponent],
        providers: []
      })
        .overrideTemplate(ProductCatalogComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductCatalogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductCatalogService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductCatalog(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productCatalogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
