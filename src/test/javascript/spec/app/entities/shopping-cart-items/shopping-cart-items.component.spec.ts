import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { ShoppingCartItemsComponent } from 'app/entities/shopping-cart-items/shopping-cart-items.component';
import { ShoppingCartItemsService } from 'app/entities/shopping-cart-items/shopping-cart-items.service';
import { ShoppingCartItems } from 'app/shared/model/shopping-cart-items.model';

describe('Component Tests', () => {
  describe('ShoppingCartItems Management Component', () => {
    let comp: ShoppingCartItemsComponent;
    let fixture: ComponentFixture<ShoppingCartItemsComponent>;
    let service: ShoppingCartItemsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ShoppingCartItemsComponent],
        providers: []
      })
        .overrideTemplate(ShoppingCartItemsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShoppingCartItemsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShoppingCartItemsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ShoppingCartItems(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.shoppingCartItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
