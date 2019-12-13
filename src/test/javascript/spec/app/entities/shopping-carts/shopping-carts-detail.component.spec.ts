import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ShoppingCartsDetailComponent } from 'app/entities/shopping-carts/shopping-carts-detail.component';
import { ShoppingCarts } from 'app/shared/model/shopping-carts.model';

describe('Component Tests', () => {
  describe('ShoppingCarts Management Detail Component', () => {
    let comp: ShoppingCartsDetailComponent;
    let fixture: ComponentFixture<ShoppingCartsDetailComponent>;
    const route = ({ data: of({ shoppingCarts: new ShoppingCarts(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ShoppingCartsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ShoppingCartsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShoppingCartsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.shoppingCarts).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
