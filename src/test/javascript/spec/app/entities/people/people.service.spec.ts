import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PeopleService } from 'app/entities/people/people.service';
import { IPeople, People } from 'app/shared/model/people.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

describe('Service Tests', () => {
  describe('People Service', () => {
    let injector: TestBed;
    let service: PeopleService;
    let httpMock: HttpTestingController;
    let elemDefault: IPeople;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PeopleService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new People(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        Gender.MALE,
        false,
        'AAAAAAA',
        false,
        false,
        false,
        false,
        false,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
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

      it('should create a People', () => {
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
          .create(new People(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a People', () => {
        const returnedFromService = Object.assign(
          {
            fullName: 'BBBBBB',
            preferredName: 'BBBBBB',
            searchName: 'BBBBBB',
            gender: 'BBBBBB',
            isPermittedToLogon: true,
            logonName: 'BBBBBB',
            isExternalLogonProvider: true,
            isSystemUser: true,
            isEmployee: true,
            isSalesPerson: true,
            isGuestUser: true,
            emailPromotion: 1,
            userPreferences: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            emailAddress: 'BBBBBB',
            photo: 'BBBBBB',
            customFields: 'BBBBBB',
            otherLanguages: 'BBBBBB',
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

      it('should return a list of People', () => {
        const returnedFromService = Object.assign(
          {
            fullName: 'BBBBBB',
            preferredName: 'BBBBBB',
            searchName: 'BBBBBB',
            gender: 'BBBBBB',
            isPermittedToLogon: true,
            logonName: 'BBBBBB',
            isExternalLogonProvider: true,
            isSystemUser: true,
            isEmployee: true,
            isSalesPerson: true,
            isGuestUser: true,
            emailPromotion: 1,
            userPreferences: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            emailAddress: 'BBBBBB',
            photo: 'BBBBBB',
            customFields: 'BBBBBB',
            otherLanguages: 'BBBBBB',
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

      it('should delete a People', () => {
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
