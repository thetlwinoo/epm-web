import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { CompareProductsComponent } from 'app/entities/compare-products/compare-products.component';
import { CompareProductsService } from 'app/entities/compare-products/compare-products.service';
import { CompareProducts } from 'app/shared/model/compare-products.model';

describe('Component Tests', () => {
  describe('CompareProducts Management Component', () => {
    let comp: CompareProductsComponent;
    let fixture: ComponentFixture<CompareProductsComponent>;
    let service: CompareProductsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [CompareProductsComponent],
        providers: []
      })
        .overrideTemplate(CompareProductsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompareProductsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompareProductsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CompareProducts(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.compareProducts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
