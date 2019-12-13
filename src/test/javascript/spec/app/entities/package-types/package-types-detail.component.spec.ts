import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { PackageTypesDetailComponent } from 'app/entities/package-types/package-types-detail.component';
import { PackageTypes } from 'app/shared/model/package-types.model';

describe('Component Tests', () => {
  describe('PackageTypes Management Detail Component', () => {
    let comp: PackageTypesDetailComponent;
    let fixture: ComponentFixture<PackageTypesDetailComponent>;
    const route = ({ data: of({ packageTypes: new PackageTypes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PackageTypesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PackageTypesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PackageTypesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.packageTypes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
