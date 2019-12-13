import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductTagsDetailComponent } from 'app/entities/product-tags/product-tags-detail.component';
import { ProductTags } from 'app/shared/model/product-tags.model';

describe('Component Tests', () => {
  describe('ProductTags Management Detail Component', () => {
    let comp: ProductTagsDetailComponent;
    let fixture: ComponentFixture<ProductTagsDetailComponent>;
    const route = ({ data: of({ productTags: new ProductTags(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductTagsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductTagsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductTagsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productTags).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
