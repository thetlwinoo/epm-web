import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { DangerousGoodsUpdateComponent } from 'app/entities/dangerous-goods/dangerous-goods-update.component';
import { DangerousGoodsService } from 'app/entities/dangerous-goods/dangerous-goods.service';
import { DangerousGoods } from 'app/shared/model/dangerous-goods.model';

describe('Component Tests', () => {
  describe('DangerousGoods Management Update Component', () => {
    let comp: DangerousGoodsUpdateComponent;
    let fixture: ComponentFixture<DangerousGoodsUpdateComponent>;
    let service: DangerousGoodsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [DangerousGoodsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DangerousGoodsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DangerousGoodsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DangerousGoodsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DangerousGoods(123);
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
        const entity = new DangerousGoods();
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
