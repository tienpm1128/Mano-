import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { createRequestOption } from 'app/shared';

@Injectable({
  providedIn: 'root'
})
export class FaultSubscriptionsService {
  public resourceUrl = SERVER_API_URL + '/nsfm/v1/subscriptions';

  constructor(protected http: HttpClient) {}

  query(req?: any): Observable<HttpResponse<any>> {
    const options = createRequestOption(req);
    return this.http.get<any>(this.resourceUrl, { params: options, observe: 'response' });
  }

  create(faultSubscriptions?: any): Observable<HttpResponse<any>> {
    return this.http.post<any>(this.resourceUrl, faultSubscriptions, { observe: 'response' });
  }

  detail(id: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  delete(faultSubscriptionsId: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${faultSubscriptionsId}`, { observe: 'response' });
  }
}
