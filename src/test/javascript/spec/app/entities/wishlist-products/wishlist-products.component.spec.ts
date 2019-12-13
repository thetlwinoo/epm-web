import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { WishlistProductsComponent } from 'app/entities/wishlist-products/wishlist-products.component';
import { WishlistProductsService } from 'app/entities/wishlist-products/wishlist-products.service';
import { WishlistProducts } from 'app/shared/model/wishlist-products.model';

describe('Component Tests', () => {
  describe('WishlistProducts Management Component', () => {
    let comp: WishlistProductsComponent;
    let fixture: ComponentFixture<WishlistProductsComponent>;
    let service: WishlistProductsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [WishlistProductsComponent],
        providers: []
      })
        .overrideTemplate(WishlistProductsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WishlistProductsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WishlistProductsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WishlistProducts(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.wishlistProducts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
