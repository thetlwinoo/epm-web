import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CurrencyRateService } from 'app/entities/currency-rate/currency-rate.service';
import { ICurrencyRate, CurrencyRate } from 'app/shared/model/currency-rate.model';

describe('Service Tests', () => {
  describe('CurrencyRate Service', () => {
    let injector: TestBed;
    let service: CurrencyRateService;
    let httpMock: HttpTestingController;
    let elemDefault: ICurrencyRate;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CurrencyRateService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CurrencyRate(0, currentDate, 'AAAAAAA', 'AAAAAAA', 0, 0, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            currencyRateDate: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a CurrencyRate', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            currencyRateDate: currentDate.format(DATE_TIME_FORMAT),
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            currencyRateDate: currentDate,
            lastEditedWhen: currentDate
          },
          returnedFromService
        );
        service
          .create(new CurrencyRate(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a CurrencyRate', () => {
        const returnedFromService = Object.assign(
          {
            currencyRateDate: currentDate.format(DATE_TIME_FORMAT),
            fromcode: 'BBBBBB',
            tocode: 'BBBBBB',
            averageRate: 1,
            endOfDayRate: 1,
            lastEditedBy: 'BBBBBB',
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            currencyRateDate: currentDate,
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

      it('should return a list of CurrencyRate', () => {
        const returnedFromService = Object.assign(
          {
            currencyRateDate: currentDate.format(DATE_TIME_FORMAT),
            fromcode: 'BBBBBB',
            tocode: 'BBBBBB',
            averageRate: 1,
            endOfDayRate: 1,
            lastEditedBy: 'BBBBBB',
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            currencyRateDate: currentDate,
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

      it('should delete a CurrencyRate', () => {
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
