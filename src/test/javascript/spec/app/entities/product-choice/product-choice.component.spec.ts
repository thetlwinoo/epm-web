import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { ProductChoiceComponent } from 'app/entities/product-choice/product-choice.component';
import { ProductChoiceService } from 'app/entities/product-choice/product-choice.service';
import { ProductChoice } from 'app/shared/model/product-choice.model';

describe('Component Tests', () => {
  describe('ProductChoice Management Component', () => {
    let comp: ProductChoiceComponent;
    let fixture: ComponentFixture<ProductChoiceComponent>;
    let service: ProductChoiceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [ProductChoiceComponent],
        providers: []
      })
        .overrideTemplate(ProductChoiceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductChoiceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductChoiceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductChoice(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productChoices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
