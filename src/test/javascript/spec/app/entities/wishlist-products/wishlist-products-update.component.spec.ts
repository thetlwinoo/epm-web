import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { WishlistProductsUpdateComponent } from 'app/entities/wishlist-products/wishlist-products-update.component';
import { WishlistProductsService } from 'app/entities/wishlist-products/wishlist-products.service';
import { WishlistProducts } from 'app/shared/model/wishlist-products.model';

describe('Component Tests', () => {
  describe('WishlistProducts Management Update Component', () => {
    let comp: WishlistProductsUpdateComponent;
    let fixture: ComponentFixture<WishlistProductsUpdateComponent>;
    let service: WishlistProductsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [WishlistProductsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(WishlistProductsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WishlistProductsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WishlistProductsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WishlistProducts(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new WishlistProducts();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
