import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { WarrantyTypesDetailComponent } from 'app/entities/warranty-types/warranty-types-detail.component';
import { WarrantyTypes } from 'app/shared/model/warranty-types.model';

describe('Component Tests', () => {
  describe('WarrantyTypes Management Detail Component', () => {
    let comp: WarrantyTypesDetailComponent;
    let fixture: ComponentFixture<WarrantyTypesDetailComponent>;
    const route = ({ data: of({ warrantyTypes: new WarrantyTypes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [WarrantyTypesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(WarrantyTypesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WarrantyTypesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.warrantyTypes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
