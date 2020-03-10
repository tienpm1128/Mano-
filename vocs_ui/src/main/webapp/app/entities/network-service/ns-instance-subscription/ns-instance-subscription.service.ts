import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { createRequestOption } from 'app/shared';

@Injectable({
  providedIn: 'root'
})
export class NsInstanceSubscriptionService {
  public resourceUrl = SERVER_API_URL + '/nslcm/v1/subscriptions';

  constructor(protected http: HttpClient) {}

  query(req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http.get<any>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  create(data: any): Observable<any> {
    return this.http.post<any>(this.resourceUrl, data, { observe: 'response' });
  }
}
