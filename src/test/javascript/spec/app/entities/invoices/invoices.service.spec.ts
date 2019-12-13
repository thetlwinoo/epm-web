import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { InvoicesService } from 'app/entities/invoices/invoices.service';
import { IInvoices, Invoices } from 'app/shared/model/invoices.model';
import { PaymentMethod } from 'app/shared/model/enumerations/payment-method.model';
import { InvoiceStatus } from 'app/shared/model/enumerations/invoice-status.model';

describe('Service Tests', () => {
  describe('Invoices Service', () => {
    let injector: TestBed;
    let service: InvoicesService;
    let httpMock: HttpTestingController;
    let elemDefault: IInvoices;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(InvoicesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Invoices(
        0,
        currentDate,
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        PaymentMethod.CREDIT_CARD,
        InvoiceStatus.PAID,
        'AAAAAAA',
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            invoiceDate: currentDate.format(DATE_TIME_FORMAT),
            confirmedDeliveryTime: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a Invoices', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            invoiceDate: currentDate.format(DATE_TIME_FORMAT),
            confirmedDeliveryTime: currentDate.format(DATE_TIME_FORMAT),
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            invoiceDate: currentDate,
            confirmedDeliveryTime: currentDate,
            lastEditedWhen: currentDate
          },
          returnedFromService
        );
        service
          .create(new Invoices(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Invoices', () => {
        const returnedFromService = Object.assign(
          {
            invoiceDate: currentDate.format(DATE_TIME_FORMAT),
            customerPurchaseOrderNumber: 'BBBBBB',
            isCreditNote: true,
            creditNoteReason: 'BBBBBB',
            comments: 'BBBBBB',
            deliveryInstructions: 'BBBBBB',
            internalComments: 'BBBBBB',
            totalDryItems: 1,
            totalChillerItems: 1,
            deliveryRun: 'BBBBBB',
            runPosition: 'BBBBBB',
            returnedDeliveryData: 'BBBBBB',
            confirmedDeliveryTime: currentDate.format(DATE_TIME_FORMAT),
            confirmedReceivedBy: 'BBBBBB',
            paymentMethod: 'BBBBBB',
            status: 'BBBBBB',
            lastEditedBy: 'BBBBBB',
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            invoiceDate: currentDate,
            confirmedDeliveryTime: currentDate,
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

      it('should return a list of Invoices', () => {
        const returnedFromService = Object.assign(
          {
            invoiceDate: currentDate.format(DATE_TIME_FORMAT),
            customerPurchaseOrderNumber: 'BBBBBB',
            isCreditNote: true,
            creditNoteReason: 'BBBBBB',
            comments: 'BBBBBB',
            deliveryInstructions: 'BBBBBB',
            internalComments: 'BBBBBB',
            totalDryItems: 1,
            totalChillerItems: 1,
            deliveryRun: 'BBBBBB',
            runPosition: 'BBBBBB',
            returnedDeliveryData: 'BBBBBB',
            confirmedDeliveryTime: currentDate.format(DATE_TIME_FORMAT),
            confirmedReceivedBy: 'BBBBBB',
            paymentMethod: 'BBBBBB',
            status: 'BBBBBB',
            lastEditedBy: 'BBBBBB',
            lastEditedWhen: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            invoiceDate: currentDate,
            confirmedDeliveryTime: currentDate,
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

      it('should delete a Invoices', () => {
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
