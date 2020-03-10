import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { createRequestOption } from 'app/shared';
import { IVimInstance, IVimSubscriptionRequest } from 'app/shared/model/vimInstance.model';

@Injectable({
  providedIn: 'root'
})
export class VimInstanceService {
  public resourceUrl = SERVER_API_URL + '/vim/v1/vim_instances';

  constructor(protected http: HttpClient) {}

  create(vimInstance: IVimInstance): Observable<any> {
    return this.http.post<IVimInstance>(this.resourceUrl, vimInstance, { observe: 'response' });
  }

  find(vimInstanceId: string): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/${vimInstanceId}`, { observe: 'response' });
  }

  query(req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http.get<any>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(vimInstanceId: string) {
    return this.http.delete<any>(`${this.resourceUrl}/${vimInstanceId}`, { observe: 'response' });
  }

  validateVimUser(vimInstanceId: string, user: string, pass: string): Observable<HttpResponse<any>> {
    const credentials = {
      username: user,
      password: pass
    };
    return this.http.post<any>(`${this.resourceUrl}/${vimInstanceId}/verify_vim`, credentials, { observe: 'response' });
  }

  subscribeVim(vimSubscriptionRequest: IVimSubscriptionRequest): Observable<HttpResponse<any>> {
    return this.http.post<any>(`${this.resourceUrl}/subscribe`, vimSubscriptionRequest, { observe: 'response' });
  }

  getCompute(vimId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${SERVER_API_URL}/vim/v1/quotas/compute/{vimId:${vimId}}`, { observe: 'response' });
  }

  getComputeLeft(vimId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${SERVER_API_URL}/vim/v1/quotas/compute-left/{vimId:${vimId}}`, { observe: 'response' });
  }

  getNetworkTotal(vimId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${SERVER_API_URL}/vim/v1/networks/total/{vimId:${vimId}}`, { observe: 'response' });
  }

  getNetwork(vimId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${SERVER_API_URL}/vim/v1/quotas/network/{vimId:${vimId}}`, { observe: 'response' });
  }

  getNetworkLeft(vimId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${SERVER_API_URL}/vim/v1/quotas/network-left/{vimId:${vimId}}`, { observe: 'response' });
  }

  getStorage(vimId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${SERVER_API_URL}/vim/v1/quotas/storage/{vimId:${vimId}}`, { observe: 'response' });
  }

  getStorageLeft(vimId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${SERVER_API_URL}/vim/v1/quotas/storage-left/{vimId:${vimId}}`, { observe: 'response' });
  }

  getResource(vimId: string, id: string): Observable<any> {
    const headers: HttpHeaders = new HttpHeaders({
      projectId: id
    });
    const options = { headers };
    return this.http.get<any>(`${SERVER_API_URL}/vim/v1/quotas/resource/{vimId:${vimId}}`, options);
  }

  getResourceQuota(vimId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${SERVER_API_URL}/user_mgnt/v1/resources/vims/${vimId}`, { observe: 'response' });
  }

  getThreshold(vimId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${SERVER_API_URL}/vimpm/v1/vimthresholds/${vimId}`, { observe: 'response' });
  }
}
