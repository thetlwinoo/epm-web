import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { ProductSetDetailsComponent } from 'app/entities/product-set-details/product-set-details.component';
import { ProductSetDetailsService } from 'app/entities/product-set-details/product-set-details.service';
import { ProductSetDetails } from 'app/shared/model/product-set-details.model';

describe('Component Tests', () => {
  describe('ProductSetDetails Management Component', () => {
    let comp: ProductSetDetailsComponent;
    let fixture: ComponentFixture<ProductSetDetailsComponent>;
    let service: ProductSetDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductSetDetailsComponent],
        providers: []
      })
        .overrideTemplate(ProductSetDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductSetDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductSetDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductSetDetails(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productSetDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
