import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class NsdSubscriptionsService {
  public resourceUrl = SERVER_API_URL + '/nsd/v1/subscriptions';

  constructor(protected http: HttpClient) {}

  query(): Observable<any> {
    return this.http.get<any>(this.resourceUrl, { observe: 'response' });
  }

  delete(subscriptionId: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${subscriptionId}`, { observe: 'response' });
  }
}
