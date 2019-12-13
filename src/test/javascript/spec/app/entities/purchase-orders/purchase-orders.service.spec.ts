import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PurchaseOrdersService } from 'app/entities/purchase-orders/purchase-orders.service';
import { IPurchaseOrders, PurchaseOrders } from 'app/shared/model/purchase-orders.model';

describe('Service Tests', () => {
  describe('PurchaseOrders Service', () => {
    let injector: TestBed;
    let service: PurchaseOrdersService;
    let httpMock: HttpTestingController;
    let elemDefault: IPurchaseOrders;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PurchaseOrdersService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PurchaseOrders(0, currentDate, currentDate, 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            expectedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a PurchaseOrders', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            expectedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            orderDate: currentDate,
            expectedDeliveryDate: currentDate,
            lastEditedWhen: currentDate
          },
          returnedFromService
        );
        service
          .create(new PurchaseOrders(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PurchaseOrders', () => {
        const returnedFromService = Object.assign(
          {
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            expectedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
            supplierReference: 'BBBBBB',
            isOrderFinalized: 1,
            comments: 'BBBBBB',
            internalComments: 'BBBBBB',
            lastEditedBy: 'BBBBBB',
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            orderDate: currentDate,
            expectedDeliveryDate: currentDate,
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

      it('should return a list of PurchaseOrders', () => {
        const returnedFromService = Object.assign(
          {
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            expectedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
            supplierReference: 'BBBBBB',
            isOrderFinalized: 1,
            comments: 'BBBBBB',
            internalComments: 'BBBBBB',
            lastEditedBy: 'BBBBBB',
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            orderDate: currentDate,
            expectedDeliveryDate: currentDate,
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

      it('should delete a PurchaseOrders', () => {
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
