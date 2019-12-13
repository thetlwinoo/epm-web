import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { PhotosComponent } from 'app/entities/photos/photos.component';
import { PhotosService } from 'app/entities/photos/photos.service';
import { Photos } from 'app/shared/model/photos.model';

describe('Component Tests', () => {
  describe('Photos Management Component', () => {
    let comp: PhotosComponent;
    let fixture: ComponentFixture<PhotosComponent>;
    let service: PhotosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PhotosComponent],
        providers: []
      })
        .overrideTemplate(PhotosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhotosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PhotosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Photos(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.photos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
