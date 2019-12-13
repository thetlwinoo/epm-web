import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OrdersService } from 'app/entities/orders/orders.service';
import { IOrders, Orders } from 'app/shared/model/orders.model';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';

describe('Service Tests', () => {
  describe('Orders Service', () => {
    let injector: TestBed;
    let service: OrdersService;
    let httpMock: HttpTestingController;
    let elemDefault: IOrders;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(OrdersService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Orders(
        0,
        currentDate,
        currentDate,
        currentDate,
        0,
        0,
        'AAAAAAA',
        0,
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        OrderStatus.COMPLETED,
        'AAAAAAA',
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            dueDate: currentDate.format(DATE_TIME_FORMAT),
            shipDate: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a Orders', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            dueDate: currentDate.format(DATE_TIME_FORMAT),
            shipDate: currentDate.format(DATE_TIME_FORMAT),
            pickingCompletedWhen: currentDate.format(DATE_TIME_FORMAT),
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            orderDate: currentDate,
            dueDate: currentDate,
            shipDate: currentDate,
            pickingCompletedWhen: currentDate,
            lastEditedWhen: currentDate
          },
          returnedFromService
        );
        service
          .create(new Orders(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Orders', () => {
        const returnedFromService = Object.assign(
          {
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            dueDate: currentDate.format(DATE_TIME_FORMAT),
            shipDate: currentDate.format(DATE_TIME_FORMAT),
            paymentStatus: 1,
            orderFlag: 1,
            orderNumber: 'BBBBBB',
            subTotal: 1,
            taxAmount: 1,
            frieight: 1,
            totalDue: 1,
            comments: 'BBBBBB',
            deliveryInstructions: 'BBBBBB',
            internalComments: 'BBBBBB',
            pickingCompletedWhen: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            lastEditedBy: 'BBBBBB',
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            orderDate: currentDate,
            dueDate: currentDate,
            shipDate: currentDate,
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

      it('should return a list of Orders', () => {
        const returnedFromService = Object.assign(
          {
            orderDate: currentDate.format(DATE_TIME_FORMAT),
            dueDate: currentDate.format(DATE_TIME_FORMAT),
            shipDate: currentDate.format(DATE_TIME_FORMAT),
            paymentStatus: 1,
            orderFlag: 1,
            orderNumber: 'BBBBBB',
            subTotal: 1,
            taxAmount: 1,
            frieight: 1,
            totalDue: 1,
            comments: 'BBBBBB',
            deliveryInstructions: 'BBBBBB',
            internalComments: 'BBBBBB',
            pickingCompletedWhen: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            lastEditedBy: 'BBBBBB',
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            orderDate: currentDate,
            dueDate: currentDate,
            shipDate: currentDate,
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

      it('should delete a Orders', () => {
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
