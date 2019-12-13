import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { DangerousGoodsDetailComponent } from 'app/entities/dangerous-goods/dangerous-goods-detail.component';
import { DangerousGoods } from 'app/shared/model/dangerous-goods.model';

describe('Component Tests', () => {
  describe('DangerousGoods Management Detail Component', () => {
    let comp: DangerousGoodsDetailComponent;
    let fixture: ComponentFixture<DangerousGoodsDetailComponent>;
    const route = ({ data: of({ dangerousGoods: new DangerousGoods(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [DangerousGoodsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DangerousGoodsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DangerousGoodsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dangerousGoods).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
