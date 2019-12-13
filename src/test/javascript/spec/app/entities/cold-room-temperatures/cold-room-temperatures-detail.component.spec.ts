import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ColdRoomTemperaturesDetailComponent } from 'app/entities/cold-room-temperatures/cold-room-temperatures-detail.component';
import { ColdRoomTemperatures } from 'app/shared/model/cold-room-temperatures.model';

describe('Component Tests', () => {
  describe('ColdRoomTemperatures Management Detail Component', () => {
    let comp: ColdRoomTemperaturesDetailComponent;
    let fixture: ComponentFixture<ColdRoomTemperaturesDetailComponent>;
    const route = ({ data: of({ coldRoomTemperatures: new ColdRoomTemperatures(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ColdRoomTemperaturesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ColdRoomTemperaturesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ColdRoomTemperaturesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.coldRoomTemperatures).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
