import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { AddressesService } from 'app/entities/addresses/addresses.service';
import { IAddresses, Addresses } from 'app/shared/model/addresses.model';

describe('Service Tests', () => {
  describe('Addresses Service', () => {
    let injector: TestBed;
    let service: AddressesService;
    let httpMock: HttpTestingController;
    let elemDefault: IAddresses;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AddressesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Addresses(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false, false);
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

      it('should create a Addresses', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Addresses(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Addresses', () => {
        const returnedFromService = Object.assign(
          {
            contactPerson: 'BBBBBB',
            contactNumber: 'BBBBBB',
            contactEmailAddress: 'BBBBBB',
            addressLine1: 'BBBBBB',
            addressLine2: 'BBBBBB',
            city: 'BBBBBB',
            postalCode: 'BBBBBB',
            defaultInd: true,
            activeInd: true
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

      it('should return a list of Addresses', () => {
        const returnedFromService = Object.assign(
          {
            contactPerson: 'BBBBBB',
            contactNumber: 'BBBBBB',
            contactEmailAddress: 'BBBBBB',
            addressLine1: 'BBBBBB',
            addressLine2: 'BBBBBB',
            city: 'BBBBBB',
            postalCode: 'BBBBBB',
            defaultInd: true,
            activeInd: true
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

      it('should delete a Addresses', () => {
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
