import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ShoppingCartItemsUpdateComponent } from 'app/entities/shopping-cart-items/shopping-cart-items-update.component';
import { ShoppingCartItemsService } from 'app/entities/shopping-cart-items/shopping-cart-items.service';
import { ShoppingCartItems } from 'app/shared/model/shopping-cart-items.model';

describe('Component Tests', () => {
  describe('ShoppingCartItems Management Update Component', () => {
    let comp: ShoppingCartItemsUpdateComponent;
    let fixture: ComponentFixture<ShoppingCartItemsUpdateComponent>;
    let service: ShoppingCartItemsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ShoppingCartItemsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ShoppingCartItemsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShoppingCartItemsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShoppingCartItemsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ShoppingCartItems(123);
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
        const entity = new ShoppingCartItems();
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
