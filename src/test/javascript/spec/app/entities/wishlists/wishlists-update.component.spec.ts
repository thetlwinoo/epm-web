import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { WishlistsUpdateComponent } from 'app/entities/wishlists/wishlists-update.component';
import { WishlistsService } from 'app/entities/wishlists/wishlists.service';
import { Wishlists } from 'app/shared/model/wishlists.model';

describe('Component Tests', () => {
  describe('Wishlists Management Update Component', () => {
    let comp: WishlistsUpdateComponent;
    let fixture: ComponentFixture<WishlistsUpdateComponent>;
    let service: WishlistsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [WishlistsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(WishlistsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WishlistsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WishlistsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Wishlists(123);
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
        const entity = new Wishlists();
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
