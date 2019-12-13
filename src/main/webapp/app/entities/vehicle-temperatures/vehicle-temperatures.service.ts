import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVehicleTemperatures } from 'app/shared/model/vehicle-temperatures.model';

type EntityResponseType = HttpResponse<IVehicleTemperatures>;
type EntityArrayResponseType = HttpResponse<IVehicleTemperatures[]>;

@Injectable({ providedIn: 'root' })
export class VehicleTemperaturesService {
  public resourceUrl = SERVER_API_URL + 'api/vehicle-temperatures';

  constructor(protected http: HttpClient) {}

  create(vehicleTemperatures: IVehicleTemperatures): Observable<EntityResponseType> {
    return this.http.post<IVehicleTemperatures>(this.resourceUrl, vehicleTemperatures, { observe: 'response' });
  }

  update(vehicleTemperatures: IVehicleTemperatures): Observable<EntityResponseType> {
    return this.http.put<IVehicleTemperatures>(this.resourceUrl, vehicleTemperatures, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVehicleTemperatures>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVehicleTemperatures[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
