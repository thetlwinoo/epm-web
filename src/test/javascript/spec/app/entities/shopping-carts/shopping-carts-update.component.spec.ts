import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ShoppingCartsUpdateComponent } from 'app/entities/shopping-carts/shopping-carts-update.component';
import { ShoppingCartsService } from 'app/entities/shopping-carts/shopping-carts.service';
import { ShoppingCarts } from 'app/shared/model/shopping-carts.model';

describe('Component Tests', () => {
  describe('ShoppingCarts Management Update Component', () => {
    let comp: ShoppingCartsUpdateComponent;
    let fixture: ComponentFixture<ShoppingCartsUpdateComponent>;
    let service: ShoppingCartsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ShoppingCartsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ShoppingCartsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShoppingCartsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShoppingCartsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ShoppingCarts(123);
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
        const entity = new ShoppingCarts();
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
