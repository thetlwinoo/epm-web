import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUploadActionTypes } from 'app/shared/model/upload-action-types.model';

type EntityResponseType = HttpResponse<IUploadActionTypes>;
type EntityArrayResponseType = HttpResponse<IUploadActionTypes[]>;

@Injectable({ providedIn: 'root' })
export class UploadActionTypesService {
  public resourceUrl = SERVER_API_URL + 'api/upload-action-types';

  constructor(protected http: HttpClient) {}

  create(uploadActionTypes: IUploadActionTypes): Observable<EntityResponseType> {
    return this.http.post<IUploadActionTypes>(this.resourceUrl, uploadActionTypes, { observe: 'response' });
  }

  update(uploadActionTypes: IUploadActionTypes): Observable<EntityResponseType> {
    return this.http.put<IUploadActionTypes>(this.resourceUrl, uploadActionTypes, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUploadActionTypes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUploadActionTypes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
