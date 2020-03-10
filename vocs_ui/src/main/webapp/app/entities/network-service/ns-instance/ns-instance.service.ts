import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { createRequestOption } from 'app/shared';
import { INsInstance } from 'app/shared/model/nsInstance.model';

@Injectable({
  providedIn: 'root'
})
export class NsInstanceService {
  public resourceUrl = SERVER_API_URL + '/nslcm/v1/ns_instances';
  public vnfUrl = SERVER_API_URL + '/vnflcm/v1/vnf_instances';

  constructor(protected http: HttpClient) {}

  create(nsInstance: INsInstance): Observable<any> {
    return this.http.post<INsInstance>(this.resourceUrl, nsInstance, { observe: 'response' });
  }

  find(nsInstanceId: string): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/${nsInstanceId}`, { observe: 'response' });
  }

  query(req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http.get<any>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(nsInstanceId: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${nsInstanceId}`, { observe: 'response' });
  }

  deleteVnfInstance(vnfInstanceId: string) {
    return this.http.delete<any>(`${this.vnfUrl}/${vnfInstanceId}`, { observe: 'response' });
  }

  instantiateVnfInstance(vnfInstanceId, instantiateVnfRequest: any): Observable<any> {
    return this.http.post<any>(`${this.vnfUrl}/${vnfInstanceId}/instantiate`, instantiateVnfRequest, { observe: 'response' });
  }

  scale(vnfInstanceId, scaleVnfRequest: any): Observable<any> {
    return this.http.post<any>(`${this.vnfUrl}/${vnfInstanceId}/scale`, scaleVnfRequest, { observe: 'response' });
  }

  terminate(vnfInstanceId, terminateVnfRequest: any): Observable<any> {
    return this.http.post<any>(`${this.vnfUrl}/${vnfInstanceId}/terminate`, terminateVnfRequest, { observe: 'response' });
  }
}
