import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { ProductTagsComponent } from 'app/entities/product-tags/product-tags.component';
import { ProductTagsService } from 'app/entities/product-tags/product-tags.service';
import { ProductTags } from 'app/shared/model/product-tags.model';

describe('Component Tests', () => {
  describe('ProductTags Management Component', () => {
    let comp: ProductTagsComponent;
    let fixture: ComponentFixture<ProductTagsComponent>;
    let service: ProductTagsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductTagsComponent],
        providers: []
      })
        .overrideTemplate(ProductTagsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductTagsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductTagsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductTags(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productTags[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
