import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { createRequestOption } from 'app/shared';
import { SERVER_API_URL } from 'app/app.constants';
import { INsd, INsdInfoModifications, NsdInfoModifications } from 'app/shared/model/nsd.model';
import { Http, ResponseContentType, RequestOptions } from '@angular/http';

@Injectable({
  providedIn: 'root'
})
export class NsdManagementService {
  public resourceUrl = SERVER_API_URL + '/nsd/v1/ns_descriptors';

  constructor(protected http: HttpClient) {}

  create(): Observable<HttpResponse<any>> {
    return this.http.post<{}>(this.resourceUrl, {}, { observe: 'response' });
  }

  find(nsdId: string): Observable<HttpResponse<any>> {
    const options = {
      id: nsdId
    };
    return this.http.get<any>(`${this.resourceUrl}/${nsdId}`, { params: options, observe: 'response' });
  }

  findVnfds(nsdId: string): Observable<HttpResponse<any>> {
    const options = {
      id: nsdId
    };
    return this.http.get<any>(`${SERVER_API_URL}/nsd/v1/vnf_descriptors/nsd/${nsdId}`, { params: options, observe: 'response' });
  }

  findVnfd(vnfdId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${SERVER_API_URL}/nsd/v1/vnf_descriptors/${vnfdId}`, { observe: 'response' });
  }

  query(req?: any): Observable<HttpResponse<any>> {
    const options = createRequestOption(req);
    return this.http.get<any>(this.resourceUrl, { params: options, observe: 'response' });
  }

  update(nsd: INsd): Observable<HttpResponse<any>> {
    const updateNsd: NsdInfoModifications = {
      nsdOperationalState: nsd.nsdOperationalStateType,
      userDefinedData: nsd.userDefinedData,
      timestamp: Date.now()
    };
    return this.http.patch<INsdInfoModifications>(`${this.resourceUrl}/${nsd.id}`, updateNsd, { observe: 'response' });
  }

  upload(files: any, nsdInfoId: string): Observable<HttpResponse<any>> {
    return this.http.put<any>(`${SERVER_API_URL}/nsd/v1/ns_descriptors/${nsdInfoId}/nsd_content`, files, { observe: 'response' });
  }

  download(nsdInfoId: string) {
    return this.http.get(`${SERVER_API_URL}/nsd/v1/ns_descriptors/${nsdInfoId}/nsd_content`, { responseType: 'blob' });
  }

  delete(nsdInfoId: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${SERVER_API_URL}/nsd/v1/ns_descriptors/${nsdInfoId}`, { observe: 'response' });
  }
}
