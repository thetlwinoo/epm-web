import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { PhotosService } from 'app/entities/photos/photos.service';
import { IPhotos, Photos } from 'app/shared/model/photos.model';

describe('Service Tests', () => {
  describe('Photos Service', () => {
    let injector: TestBed;
    let service: PhotosService;
    let httpMock: HttpTestingController;
    let elemDefault: IPhotos;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PhotosService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Photos(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        0,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Photos', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Photos(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Photos', () => {
        const returnedFromService = Object.assign(
          {
            thumbnailPhoto: 'BBBBBB',
            originalPhoto: 'BBBBBB',
            bannerTallPhoto: 'BBBBBB',
            bannerWidePhoto: 'BBBBBB',
            circlePhoto: 'BBBBBB',
            sharpenedPhoto: 'BBBBBB',
            squarePhoto: 'BBBBBB',
            watermarkPhoto: 'BBBBBB',
            thumbnailPhotoBlob: 'BBBBBB',
            originalPhotoBlob: 'BBBBBB',
            bannerTallPhotoBlob: 'BBBBBB',
            bannerWidePhotoBlob: 'BBBBBB',
            circlePhotoBlob: 'BBBBBB',
            sharpenedPhotoBlob: 'BBBBBB',
            squarePhotoBlob: 'BBBBBB',
            watermarkPhotoBlob: 'BBBBBB',
            priority: 1,
            defaultInd: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Photos', () => {
        const returnedFromService = Object.assign(
          {
            thumbnailPhoto: 'BBBBBB',
            originalPhoto: 'BBBBBB',
            bannerTallPhoto: 'BBBBBB',
            bannerWidePhoto: 'BBBBBB',
            circlePhoto: 'BBBBBB',
            sharpenedPhoto: 'BBBBBB',
            squarePhoto: 'BBBBBB',
            watermarkPhoto: 'BBBBBB',
            thumbnailPhotoBlob: 'BBBBBB',
            originalPhotoBlob: 'BBBBBB',
            bannerTallPhotoBlob: 'BBBBBB',
            bannerWidePhotoBlob: 'BBBBBB',
            circlePhotoBlob: 'BBBBBB',
            sharpenedPhotoBlob: 'BBBBBB',
            squarePhotoBlob: 'BBBBBB',
            watermarkPhotoBlob: 'BBBBBB',
            priority: 1,
            defaultInd: true
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Photos', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
