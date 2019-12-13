import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { UploadActionTypesComponent } from 'app/entities/upload-action-types/upload-action-types.component';
import { UploadActionTypesService } from 'app/entities/upload-action-types/upload-action-types.service';
import { UploadActionTypes } from 'app/shared/model/upload-action-types.model';

describe('Component Tests', () => {
  describe('UploadActionTypes Management Component', () => {
    let comp: UploadActionTypesComponent;
    let fixture: ComponentFixture<UploadActionTypesComponent>;
    let service: UploadActionTypesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [UploadActionTypesComponent],
        providers: []
      })
        .overrideTemplate(UploadActionTypesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UploadActionTypesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UploadActionTypesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UploadActionTypes(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.uploadActionTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
