import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { DangerousGoodsComponent } from 'app/entities/dangerous-goods/dangerous-goods.component';
import { DangerousGoodsService } from 'app/entities/dangerous-goods/dangerous-goods.service';
import { DangerousGoods } from 'app/shared/model/dangerous-goods.model';

describe('Component Tests', () => {
  describe('DangerousGoods Management Component', () => {
    let comp: DangerousGoodsComponent;
    let fixture: ComponentFixture<DangerousGoodsComponent>;
    let service: DangerousGoodsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [DangerousGoodsComponent],
        providers: []
      })
        .overrideTemplate(DangerousGoodsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DangerousGoodsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DangerousGoodsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DangerousGoods(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dangerousGoods[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
