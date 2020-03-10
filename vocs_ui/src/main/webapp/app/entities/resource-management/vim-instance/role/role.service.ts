import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { createRequestOption } from 'app/shared';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  public resourceUrl = SERVER_API_URL + '/api/v1/roles';

  constructor(protected http: HttpClient) {}

  query(vimId: string, req?: any): Observable<HttpResponse<any>> {
    const options = createRequestOption(req);
    return this.http.get<any>(`${this.resourceUrl}/{vimId:${vimId}}`, { params: options, observe: 'response' });
  }

  create(Role: any, vimId: string): Observable<HttpResponse<any>> {
    return this.http.post<any>(`${this.resourceUrl}/{vimId:${vimId}}`, Role, { observe: 'response' });
  }

  update(nameRole: string, RoleID: string, vimId: string): Observable<HttpResponse<any>> {
    const option = {
      name: nameRole
    };
    return this.http.put<any>(`${this.resourceUrl}/${RoleID}/{vimId:${vimId}}`, option, { observe: 'response' });
  }

  delete(RoleID: string, vimId: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${RoleID}/{vimId:${vimId}}`, { observe: 'response' });
  }

  getRole(vimId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.resourceUrl}/{vimId:${vimId}}`, { observe: 'response' });
  }
}
