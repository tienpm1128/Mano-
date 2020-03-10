import { Injectable } from '@angular/core';
import { HttpResponse, HttpClient } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServerGroupService {
  public resourceUrl = SERVER_API_URL + '/vim/v1/server_groups';

  constructor(protected http: HttpClient) {}

  getAll(vimId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.resourceUrl}/{vimId:${vimId}}`, { observe: 'response' });
  }

  create(serverGroup: any, vimId: string): Observable<HttpResponse<any>> {
    return this.http.post<any>(`${this.resourceUrl}/{vimId:${vimId}}`, serverGroup, { observe: 'response' });
  }

  delete(serverGroupId: string, vimId: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${serverGroupId}/{vimId:${vimId}}`, { observe: 'response' });
  }
}
