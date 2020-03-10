import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { createRequestOption } from 'app/shared';

@Injectable({
  providedIn: 'root'
})
export class TenantService {
  public resourceUrl = SERVER_API_URL + '/user_mgnt/v1/tenants';
  public resourceUrlUserMano = SERVER_API_URL + '/user_mgnt/v1/users';

  openStackUserId: string;
  projectId: string;
  httpOptions: any;
  data: any;

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

  delete(id: any): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  createUserMano(data: any): Observable<HttpResponse<any>> {
    return this.http.post<any>(this.resourceUrlUserMano, data, { observe: 'response' });
  }

  updateUserMano(data: any, id: any): Observable<HttpResponse<any>> {
    return this.http.put<any>(`${this.resourceUrlUserMano}/${id}`, data, { observe: 'response' });
  }

  deleteUserMano(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrlUserMano}/${id}`, { observe: 'response' });
  }
}
