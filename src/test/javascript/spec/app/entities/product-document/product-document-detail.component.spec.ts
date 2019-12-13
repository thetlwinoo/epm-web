import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductDocumentDetailComponent } from 'app/entities/product-document/product-document-detail.component';
import { ProductDocument } from 'app/shared/model/product-document.model';

describe('Component Tests', () => {
  describe('ProductDocument Management Detail Component', () => {
    let comp: ProductDocumentDetailComponent;
    let fixture: ComponentFixture<ProductDocumentDetailComponent>;
    const route = ({ data: of({ productDocument: new ProductDocument(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductDocumentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductDocumentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductDocumentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productDocument).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
