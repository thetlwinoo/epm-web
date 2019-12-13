import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { SupplierCategoriesDetailComponent } from 'app/entities/supplier-categories/supplier-categories-detail.component';
import { SupplierCategories } from 'app/shared/model/supplier-categories.model';

describe('Component Tests', () => {
  describe('SupplierCategories Management Detail Component', () => {
    let comp: SupplierCategoriesDetailComponent;
    let fixture: ComponentFixture<SupplierCategoriesDetailComponent>;
    const route = ({ data: of({ supplierCategories: new SupplierCategories(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [SupplierCategoriesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SupplierCategoriesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupplierCategoriesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.supplierCategories).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
