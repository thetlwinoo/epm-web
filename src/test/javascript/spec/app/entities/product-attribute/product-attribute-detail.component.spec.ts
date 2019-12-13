import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductAttributeDetailComponent } from 'app/entities/product-attribute/product-attribute-detail.component';
import { ProductAttribute } from 'app/shared/model/product-attribute.model';

describe('Component Tests', () => {
  describe('ProductAttribute Management Detail Component', () => {
    let comp: ProductAttributeDetailComponent;
    let fixture: ComponentFixture<ProductAttributeDetailComponent>;
    const route = ({ data: of({ productAttribute: new ProductAttribute(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductAttributeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductAttributeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductAttributeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productAttribute).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
