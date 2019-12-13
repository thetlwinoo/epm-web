import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PurchaseOrderLinesService } from 'app/entities/purchase-order-lines/purchase-order-lines.service';
import { IPurchaseOrderLines, PurchaseOrderLines } from 'app/shared/model/purchase-order-lines.model';

describe('Service Tests', () => {
  describe('PurchaseOrderLines Service', () => {
    let injector: TestBed;
    let service: PurchaseOrderLinesService;
    let httpMock: HttpTestingController;
    let elemDefault: IPurchaseOrderLines;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PurchaseOrderLinesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PurchaseOrderLines(0, 0, 'AAAAAAA', 0, 0, currentDate, false, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastReceiptDate: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a PurchaseOrderLines', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastReceiptDate: currentDate.format(DATE_TIME_FORMAT),
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            lastReceiptDate: currentDate,
            lastEditedWhen: currentDate
          },
          returnedFromService
        );
        service
          .create(new PurchaseOrderLines(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PurchaseOrderLines', () => {
        const returnedFromService = Object.assign(
          {
            orderedOuters: 1,
            description: 'BBBBBB',
            receivedOuters: 1,
            expectedUnitPricePerOuter: 1,
            lastReceiptDate: currentDate.format(DATE_TIME_FORMAT),
            isOrderLineFinalized: true,
            lastEditedBy: 'BBBBBB',
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastReceiptDate: currentDate,
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

      it('should return a list of PurchaseOrderLines', () => {
        const returnedFromService = Object.assign(
          {
            orderedOuters: 1,
            description: 'BBBBBB',
            receivedOuters: 1,
            expectedUnitPricePerOuter: 1,
            lastReceiptDate: currentDate.format(DATE_TIME_FORMAT),
            isOrderLineFinalized: true,
            lastEditedBy: 'BBBBBB',
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            lastReceiptDate: currentDate,
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

      it('should delete a PurchaseOrderLines', () => {
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
