import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { MaterialsDetailComponent } from 'app/entities/materials/materials-detail.component';
import { Materials } from 'app/shared/model/materials.model';

describe('Component Tests', () => {
  describe('Materials Management Detail Component', () => {
    let comp: MaterialsDetailComponent;
    let fixture: ComponentFixture<MaterialsDetailComponent>;
    const route = ({ data: of({ materials: new Materials(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [MaterialsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MaterialsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MaterialsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.materials).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
