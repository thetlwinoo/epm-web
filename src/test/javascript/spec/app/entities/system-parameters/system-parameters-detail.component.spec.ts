import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { SystemParametersDetailComponent } from 'app/entities/system-parameters/system-parameters-detail.component';
import { SystemParameters } from 'app/shared/model/system-parameters.model';

describe('Component Tests', () => {
  describe('SystemParameters Management Detail Component', () => {
    let comp: SystemParametersDetailComponent;
    let fixture: ComponentFixture<SystemParametersDetailComponent>;
    const route = ({ data: of({ systemParameters: new SystemParameters(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [SystemParametersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SystemParametersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SystemParametersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.systemParameters).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
