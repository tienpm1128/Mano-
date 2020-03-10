import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { HttpResponse, HttpClient } from '@angular/common/http';
import { createRequestOption } from 'app/shared';

@Injectable({
  providedIn: 'root'
})
export class PmJobsService {
  public resourceUrl = SERVER_API_URL + '/hppm/v1/pm_jobs';

  constructor(protected http: HttpClient) {}

  query(): Observable<HttpResponse<any>> {
    return this.http.get<any>(this.resourceUrl, { observe: 'response' });
  }

  find(id: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  create(data?: any): Observable<HttpResponse<any>> {
    return this.http.post<any>(this.resourceUrl, data, { observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findReportByUrl(url: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(url, { observe: 'response' });
  }

  findReportById(pmJobId: string, reportId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.resourceUrl}/${pmJobId}/reports/0001`, { observe: 'response' });
  }
}
