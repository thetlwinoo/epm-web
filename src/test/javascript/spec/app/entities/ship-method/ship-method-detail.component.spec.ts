import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ShipMethodDetailComponent } from 'app/entities/ship-method/ship-method-detail.component';
import { ShipMethod } from 'app/shared/model/ship-method.model';

describe('Component Tests', () => {
  describe('ShipMethod Management Detail Component', () => {
    let comp: ShipMethodDetailComponent;
    let fixture: ComponentFixture<ShipMethodDetailComponent>;
    const route = ({ data: of({ shipMethod: new ShipMethod(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ShipMethodDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ShipMethodDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShipMethodDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.shipMethod).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
