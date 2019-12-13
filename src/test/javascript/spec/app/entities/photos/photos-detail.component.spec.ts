import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { PhotosDetailComponent } from 'app/entities/photos/photos-detail.component';
import { Photos } from 'app/shared/model/photos.model';

describe('Component Tests', () => {
  describe('Photos Management Detail Component', () => {
    let comp: PhotosDetailComponent;
    let fixture: ComponentFixture<PhotosDetailComponent>;
    const route = ({ data: of({ photos: new Photos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [PhotosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PhotosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PhotosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.photos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
