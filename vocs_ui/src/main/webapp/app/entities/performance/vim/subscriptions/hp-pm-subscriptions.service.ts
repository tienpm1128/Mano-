import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { createRequestOption } from 'app/shared';

@Injectable({
  providedIn: 'root'
})
export class HpPmSubscriptionsService {
  public resourceUrl = SERVER_API_URL + '/hppm/v1/subscriptions';

  constructor(protected http: HttpClient) {}

  query(req?: any): Observable<HttpResponse<any>> {
    const options = createRequestOption(req);
    return this.http.get<any>(this.resourceUrl, { params: options, observe: 'response' });
  }

  detail(id: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  create(data?: any): Observable<HttpResponse<any>> {
    return this.http.post<any>(this.resourceUrl, data, { observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDetailPmJob(id?: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${SERVER_API_URL}/hppm/v1/pm_jobs/hypervisor/${id}`, { observe: 'response' });
  }

  getDetailThreshold(id?: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${SERVER_API_URL}/hppm/v1/thresholds/hypervisor/${id}`, { observe: 'response' });
  }
}
