import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductSetDetailComponent } from 'app/entities/product-set/product-set-detail.component';
import { ProductSet } from 'app/shared/model/product-set.model';

describe('Component Tests', () => {
  describe('ProductSet Management Detail Component', () => {
    let comp: ProductSetDetailComponent;
    let fixture: ComponentFixture<ProductSetDetailComponent>;
    const route = ({ data: of({ productSet: new ProductSet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductSetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductSetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductSetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productSet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
