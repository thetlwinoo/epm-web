import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductOptionSetDetailComponent } from 'app/entities/product-option-set/product-option-set-detail.component';
import { ProductOptionSet } from 'app/shared/model/product-option-set.model';

describe('Component Tests', () => {
  describe('ProductOptionSet Management Detail Component', () => {
    let comp: ProductOptionSetDetailComponent;
    let fixture: ComponentFixture<ProductOptionSetDetailComponent>;
    const route = ({ data: of({ productOptionSet: new ProductOptionSet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductOptionSetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductOptionSetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductOptionSetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productOptionSet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
