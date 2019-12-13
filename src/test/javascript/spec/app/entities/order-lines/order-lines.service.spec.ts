import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OrderLinesService } from 'app/entities/order-lines/order-lines.service';
import { IOrderLines, OrderLines } from 'app/shared/model/order-lines.model';
import { OrderLineStatus } from 'app/shared/model/enumerations/order-line-status.model';

describe('Service Tests', () => {
  describe('OrderLines Service', () => {
    let injector: TestBed;
    let service: OrderLinesService;
    let httpMock: HttpTestingController;
    let elemDefault: IOrderLines;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(OrderLinesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new OrderLines(0, 'AAAAAAA', 0, 0, 0, 0, 0, 0, currentDate, OrderLineStatus.AVAILABLE, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            pickingCompletedWhen: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a OrderLines', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            pickingCompletedWhen: currentDate.format(DATE_TIME_FORMAT),
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            pickingCompletedWhen: currentDate,
            lastEditedWhen: currentDate
          },
          returnedFromService
        );
        service
          .create(new OrderLines(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a OrderLines', () => {
        const returnedFromService = Object.assign(
          {
            carrierTrackingNumber: 'BBBBBB',
            quantity: 1,
            unitPrice: 1,
            unitPriceDiscount: 1,
            lineTotal: 1,
            taxRate: 1,
            pickedQuantity: 1,
            pickingCompletedWhen: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            lastEditedBy: 'BBBBBB',
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            pickingCompletedWhen: currentDate,
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

      it('should return a list of OrderLines', () => {
        const returnedFromService = Object.assign(
          {
            carrierTrackingNumber: 'BBBBBB',
            quantity: 1,
            unitPrice: 1,
            unitPriceDiscount: 1,
            lineTotal: 1,
            taxRate: 1,
            pickedQuantity: 1,
            pickingCompletedWhen: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            lastEditedBy: 'BBBBBB',
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            pickingCompletedWhen: currentDate,
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

      it('should delete a OrderLines', () => {
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
