import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { ProductDocumentService } from 'app/entities/product-document/product-document.service';
import { IProductDocument, ProductDocument } from 'app/shared/model/product-document.model';

describe('Service Tests', () => {
  describe('ProductDocument Service', () => {
    let injector: TestBed;
    let service: ProductDocumentService;
    let httpMock: HttpTestingController;
    let elemDefault: IProductDocument;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ProductDocumentService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ProductDocument(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
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

      it('should create a ProductDocument', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new ProductDocument(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ProductDocument', () => {
        const returnedFromService = Object.assign(
          {
            videoUrl: 'BBBBBB',
            highlights: 'BBBBBB',
            longDescription: 'BBBBBB',
            shortDescription: 'BBBBBB',
            description: 'BBBBBB',
            careInstructions: 'BBBBBB',
            productType: 'BBBBBB',
            modelName: 'BBBBBB',
            modelNumber: 'BBBBBB',
            fabricType: 'BBBBBB',
            specialFeatures: 'BBBBBB',
            productComplianceCertificate: 'BBBBBB',
            genuineAndLegal: true,
            countryOfOrigin: 'BBBBBB',
            usageAndSideEffects: 'BBBBBB',
            safetyWarnning: 'BBBBBB',
            warrantyPeriod: 'BBBBBB',
            warrantyPolicy: 'BBBBBB'
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

      it('should return a list of ProductDocument', () => {
        const returnedFromService = Object.assign(
          {
            videoUrl: 'BBBBBB',
            highlights: 'BBBBBB',
            longDescription: 'BBBBBB',
            shortDescription: 'BBBBBB',
            description: 'BBBBBB',
            careInstructions: 'BBBBBB',
            productType: 'BBBBBB',
            modelName: 'BBBBBB',
            modelNumber: 'BBBBBB',
            fabricType: 'BBBBBB',
            specialFeatures: 'BBBBBB',
            productComplianceCertificate: 'BBBBBB',
            genuineAndLegal: true,
            countryOfOrigin: 'BBBBBB',
            usageAndSideEffects: 'BBBBBB',
            safetyWarnning: 'BBBBBB',
            warrantyPeriod: 'BBBBBB',
            warrantyPolicy: 'BBBBBB'
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

      it('should delete a ProductDocument', () => {
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
