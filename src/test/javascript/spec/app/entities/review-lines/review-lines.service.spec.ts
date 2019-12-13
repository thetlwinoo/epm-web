import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ReviewLinesService } from 'app/entities/review-lines/review-lines.service';
import { IReviewLines, ReviewLines } from 'app/shared/model/review-lines.model';

describe('Service Tests', () => {
  describe('ReviewLines Service', () => {
    let injector: TestBed;
    let service: ReviewLinesService;
    let httpMock: HttpTestingController;
    let elemDefault: IReviewLines;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ReviewLinesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ReviewLines(0, 0, 'AAAAAAA', 0, 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a ReviewLines', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            lastEditedWhen: currentDate
          },
          returnedFromService
        );
        service
          .create(new ReviewLines(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ReviewLines', () => {
        const returnedFromService = Object.assign(
          {
            productRating: 1,
            productReview: 'BBBBBB',
            sellerRating: 1,
            sellerReview: 'BBBBBB',
            deliveryRating: 1,
            deliveryReview: 'BBBBBB',
            thumbnailUrl: 'BBBBBB',
            lastEditedBy: 'BBBBBB',
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastEditedWhen: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of ReviewLines', () => {
        const returnedFromService = Object.assign(
          {
            productRating: 1,
            productReview: 'BBBBBB',
            sellerRating: 1,
            sellerReview: 'BBBBBB',
            deliveryRating: 1,
            deliveryReview: 'BBBBBB',
            thumbnailUrl: 'BBBBBB',
            lastEditedBy: 'BBBBBB',
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            lastEditedWhen: currentDate
          },
          returnedFromService
        );
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

      it('should delete a ReviewLines', () => {
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
