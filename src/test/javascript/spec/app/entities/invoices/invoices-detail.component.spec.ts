import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { InvoicesDetailComponent } from 'app/entities/invoices/invoices-detail.component';
import { Invoices } from 'app/shared/model/invoices.model';

describe('Component Tests', () => {
  describe('Invoices Management Detail Component', () => {
    let comp: InvoicesDetailComponent;
    let fixture: ComponentFixture<InvoicesDetailComponent>;
    const route = ({ data: of({ invoices: new Invoices(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [InvoicesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InvoicesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvoicesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.invoices).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
