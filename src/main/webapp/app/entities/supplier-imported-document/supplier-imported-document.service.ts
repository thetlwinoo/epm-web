import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';

type EntityResponseType = HttpResponse<ISupplierImportedDocument>;
type EntityArrayResponseType = HttpResponse<ISupplierImportedDocument[]>;

@Injectable({ providedIn: 'root' })
export class SupplierImportedDocumentService {
  public resourceUrl = SERVER_API_URL + 'api/supplier-imported-documents';

  constructor(protected http: HttpClient) {}

  create(supplierImportedDocument: ISupplierImportedDocument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(supplierImportedDocument);
    return this.http
      .post<ISupplierImportedDocument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(supplierImportedDocument: ISupplierImportedDocument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(supplierImportedDocument);
    return this.http
      .put<ISupplierImportedDocument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISupplierImportedDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISupplierImportedDocument[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(supplierImportedDocument: ISupplierImportedDocument): ISupplierImportedDocument {
    const copy: ISupplierImportedDocument = Object.assign({}, supplierImportedDocument, {
      lastEditedWhen:
        supplierImportedDocument.lastEditedWhen != null && supplierImportedDocument.lastEditedWhen.isValid()
          ? supplierImportedDocument.lastEditedWhen.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastEditedWhen = res.body.lastEditedWhen != null ? moment(res.body.lastEditedWhen) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((supplierImportedDocument: ISupplierImportedDocument) => {
        supplierImportedDocument.lastEditedWhen =
          supplierImportedDocument.lastEditedWhen != null ? moment(supplierImportedDocument.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
