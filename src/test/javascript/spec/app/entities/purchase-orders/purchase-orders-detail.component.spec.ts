import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { PurchaseOrdersDetailComponent } from 'app/entities/purchase-orders/purchase-orders-detail.component';
import { PurchaseOrders } from 'app/shared/model/purchase-orders.model';

describe('Component Tests', () => {
  describe('PurchaseOrders Management Detail Component', () => {
    let comp: PurchaseOrdersDetailComponent;
    let fixture: ComponentFixture<PurchaseOrdersDetailComponent>;
    const route = ({ data: of({ purchaseOrders: new PurchaseOrders(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PurchaseOrdersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PurchaseOrdersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PurchaseOrdersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.purchaseOrders).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
