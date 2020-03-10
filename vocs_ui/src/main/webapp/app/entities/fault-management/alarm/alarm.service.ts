import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { createRequestOption } from 'app/shared';

@Injectable({
  providedIn: 'root'
})
export class AlarmService {
  public resourceUrl = SERVER_API_URL + '/nsfm/v1/alarms';

  constructor(protected http: HttpClient) {}

  query(req?: any): Observable<HttpResponse<any>> {
    const options = createRequestOption(req);
    return this.http.get<any>(SERVER_API_URL + '/fm/extended/v1/extended-alarms', { params: options, observe: 'response' });
  }

  detail(id: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  update(alarmsID: any, alarms: any): Observable<HttpResponse<any>> {
    return this.http.patch<any>(`${this.resourceUrl}/${alarmsID}`, alarms, { observe: 'response' });
  }
}
