import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMaterials } from 'app/shared/model/materials.model';

type EntityResponseType = HttpResponse<IMaterials>;
type EntityArrayResponseType = HttpResponse<IMaterials[]>;

@Injectable({ providedIn: 'root' })
export class MaterialsService {
  public resourceUrl = SERVER_API_URL + 'api/materials';

  constructor(protected http: HttpClient) {}

  create(materials: IMaterials): Observable<EntityResponseType> {
    return this.http.post<IMaterials>(this.resourceUrl, materials, { observe: 'response' });
  }

  update(materials: IMaterials): Observable<EntityResponseType> {
    return this.http.put<IMaterials>(this.resourceUrl, materials, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMaterials>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMaterials[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
