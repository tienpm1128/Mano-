import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserVimService {
  public resourceUrl = SERVER_API_URL + '/vim/v1/op_users';

  constructor(protected http: HttpClient) {}

  get(vimId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.resourceUrl}/{vimId:${vimId}}`, { observe: 'response' });
  }

  create(userVim: any, vimId: string): Observable<HttpResponse<any>> {
    return this.http.post<any>(`${this.resourceUrl}/{vimId:${vimId}}`, userVim, { observe: 'response' });
  }

  update(userVim: any, vimId: string): Observable<HttpResponse<any>> {
    return this.http.put<any>(`${this.resourceUrl}/${userVim.id}/{vimId:${vimId}}`, userVim, { observe: 'response' });
  }

  delete(UserVimId: string, vimId: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${UserVimId}/{vimId:${vimId}}`, { observe: 'response' });
  }
}
