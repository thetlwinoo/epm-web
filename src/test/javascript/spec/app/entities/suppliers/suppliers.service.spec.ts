import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SuppliersService } from 'app/entities/suppliers/suppliers.service';
import { ISuppliers, Suppliers } from 'app/shared/model/suppliers.model';

describe('Service Tests', () => {
  describe('Suppliers Service', () => {
    let injector: TestBed;
    let service: SuppliersService;
    let httpMock: HttpTestingController;
    let elemDefault: ISuppliers;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(SuppliersService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Suppliers(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        false,
        'AAAAAAA',
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            validFrom: currentDate.format(DATE_TIME_FORMAT),
            validTo: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Suppliers', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            validFrom: currentDate.format(DATE_TIME_FORMAT),
            validTo: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            validFrom: currentDate,
            validTo: currentDate
          },
          returnedFromService
        );
        service
          .create(new Suppliers(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Suppliers', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            supplierReference: 'BBBBBB',
            bankAccountName: 'BBBBBB',
            bankAccountBranch: 'BBBBBB',
            bankAccountCode: 'BBBBBB',
            bankAccountNumber: 'BBBBBB',
            bankInternationalCode: 'BBBBBB',
            paymentDays: 1,
            internalComments: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            faxNumber: 'BBBBBB',
            websiteURL: 'BBBBBB',
            webServiceUrl: 'BBBBBB',
            creditRating: 1,
            activeFlag: true,
            thumbnailUrl: 'BBBBBB',
            validFrom: currentDate.format(DATE_TIME_FORMAT),
            validTo: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            validFrom: currentDate,
            validTo: currentDate
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

      it('should return a list of Suppliers', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            supplierReference: 'BBBBBB',
            bankAccountName: 'BBBBBB',
            bankAccountBranch: 'BBBBBB',
            bankAccountCode: 'BBBBBB',
            bankAccountNumber: 'BBBBBB',
            bankInternationalCode: 'BBBBBB',
            paymentDays: 1,
            internalComments: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            faxNumber: 'BBBBBB',
            websiteURL: 'BBBBBB',
            webServiceUrl: 'BBBBBB',
            creditRating: 1,
            activeFlag: true,
            thumbnailUrl: 'BBBBBB',
            validFrom: currentDate.format(DATE_TIME_FORMAT),
            validTo: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            validFrom: currentDate,
            validTo: currentDate
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

      it('should delete a Suppliers', () => {
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
