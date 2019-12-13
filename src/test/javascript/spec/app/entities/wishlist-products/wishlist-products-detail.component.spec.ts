import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { WishlistProductsDetailComponent } from 'app/entities/wishlist-products/wishlist-products-detail.component';
import { WishlistProducts } from 'app/shared/model/wishlist-products.model';

describe('Component Tests', () => {
  describe('WishlistProducts Management Detail Component', () => {
    let comp: WishlistProductsDetailComponent;
    let fixture: ComponentFixture<WishlistProductsDetailComponent>;
    const route = ({ data: of({ wishlistProducts: new WishlistProducts(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [WishlistProductsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(WishlistProductsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WishlistProductsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.wishlistProducts).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
