import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';

type EntityResponseType = HttpResponse<any>;

@Injectable({
  providedIn: 'root'
})
export class OverviewService {
  public resourceUrl = SERVER_API_URL + '/nsd/v1/total_overview';

  constructor(protected http: HttpClient) {}

  getTotalNsd(): Observable<EntityResponseType> {
    return this.http.get<any>(`${SERVER_API_URL}/nsd/v1/ns_descriptors/total`, { observe: 'response' });
  }

  getTotalVnfd(): Observable<EntityResponseType> {
    return this.http.get<any>(`${SERVER_API_URL}/nsd/v1/vnf_descriptors/total`, { observe: 'response' });
  }

  getTotalNsInstance(): Observable<EntityResponseType> {
    return this.http.get<any>(`${SERVER_API_URL}/nslcm/v1/ns_instances/total`, { observe: 'response' });
  }

  getTotalVnfInstance(): Observable<EntityResponseType> {
    return this.http.get<any>(`${SERVER_API_URL}/nslcm/v1/vnf_instances/total`, { observe: 'response' });
  }

  getTotalVim(): Observable<EntityResponseType> {
    return this.http.get<any>(`${SERVER_API_URL}/vim/v1/vim_instances/total`, { observe: 'response' });
  }

  getTenants(): Observable<EntityResponseType> {
    return this.http.get<any>(`${SERVER_API_URL}/vimpm/v1/threshold-list`, { observe: 'response' });
  }

  getSeverities(timeFilter?: string): Observable<EntityResponseType> {
    const option = {
      time: timeFilter.toString()
    };
    return this.http.get<any>(`${SERVER_API_URL}/nsfm/v1/severities`, { params: option, observe: 'response' });
  }

  getEventTypes(timeFilter?: string): Observable<EntityResponseType> {
    const option = {
      time: timeFilter.toString()
    };
    return this.http.get<any>(`${SERVER_API_URL}/nsfm/v1/event-types`, { params: option, observe: 'response' });
  }

  getFaultTypes(timeFilter?: string): Observable<EntityResponseType> {
    const option = {
      time: timeFilter.toString()
    };
    return this.http.get<any>(`${SERVER_API_URL}/nsfm/v1/fault-types`, { params: option, observe: 'response' });
  }
}
