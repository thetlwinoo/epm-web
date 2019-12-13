import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { ProductAttributeSetComponent } from 'app/entities/product-attribute-set/product-attribute-set.component';
import { ProductAttributeSetService } from 'app/entities/product-attribute-set/product-attribute-set.service';
import { ProductAttributeSet } from 'app/shared/model/product-attribute-set.model';

describe('Component Tests', () => {
  describe('ProductAttributeSet Management Component', () => {
    let comp: ProductAttributeSetComponent;
    let fixture: ComponentFixture<ProductAttributeSetComponent>;
    let service: ProductAttributeSetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductAttributeSetComponent],
        providers: []
      })
        .overrideTemplate(ProductAttributeSetComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductAttributeSetComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductAttributeSetService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductAttributeSet(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productAttributeSets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
