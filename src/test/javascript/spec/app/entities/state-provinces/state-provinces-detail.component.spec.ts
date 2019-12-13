import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { StateProvincesDetailComponent } from 'app/entities/state-provinces/state-provinces-detail.component';
import { StateProvinces } from 'app/shared/model/state-provinces.model';

describe('Component Tests', () => {
  describe('StateProvinces Management Detail Component', () => {
    let comp: StateProvincesDetailComponent;
    let fixture: ComponentFixture<StateProvincesDetailComponent>;
    const route = ({ data: of({ stateProvinces: new StateProvinces(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [StateProvincesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StateProvincesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StateProvincesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stateProvinces).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
