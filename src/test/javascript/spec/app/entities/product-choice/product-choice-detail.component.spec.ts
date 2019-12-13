import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { ProductChoiceDetailComponent } from 'app/entities/product-choice/product-choice-detail.component';
import { ProductChoice } from 'app/shared/model/product-choice.model';

describe('Component Tests', () => {
  describe('ProductChoice Management Detail Component', () => {
    let comp: ProductChoiceDetailComponent;
    let fixture: ComponentFixture<ProductChoiceDetailComponent>;
    const route = ({ data: of({ productChoice: new ProductChoice(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductChoiceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductChoiceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductChoiceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productChoice).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
