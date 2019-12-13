import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { DeliveryMethodsDetailComponent } from 'app/entities/delivery-methods/delivery-methods-detail.component';
import { DeliveryMethods } from 'app/shared/model/delivery-methods.model';

describe('Component Tests', () => {
  describe('DeliveryMethods Management Detail Component', () => {
    let comp: DeliveryMethodsDetailComponent;
    let fixture: ComponentFixture<DeliveryMethodsDetailComponent>;
    const route = ({ data: of({ deliveryMethods: new DeliveryMethods(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [DeliveryMethodsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DeliveryMethodsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeliveryMethodsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deliveryMethods).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
