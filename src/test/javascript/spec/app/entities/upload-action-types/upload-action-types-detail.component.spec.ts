import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { UploadActionTypesDetailComponent } from 'app/entities/upload-action-types/upload-action-types-detail.component';
import { UploadActionTypes } from 'app/shared/model/upload-action-types.model';

describe('Component Tests', () => {
  describe('UploadActionTypes Management Detail Component', () => {
    let comp: UploadActionTypesDetailComponent;
    let fixture: ComponentFixture<UploadActionTypesDetailComponent>;
    const route = ({ data: of({ uploadActionTypes: new UploadActionTypes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [UploadActionTypesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UploadActionTypesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UploadActionTypesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.uploadActionTypes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
