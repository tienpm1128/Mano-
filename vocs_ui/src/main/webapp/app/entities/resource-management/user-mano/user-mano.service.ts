import { Injectable } from '@angular/core';
import { IUserMano } from 'app/shared/model/userMano.model';
import { HttpResponse, HttpClient, HttpHeaders } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<IUserMano>;

@Injectable({
  providedIn: 'root'
})
export class UserManoService {
  public resourceUrl = SERVER_API_URL + '/user_mgnt/v1/users';

  openStackUserId: string;
  projectId: string;
  httpOptions: any;
  data: any;

  constructor(protected http: HttpClient) {}

  query(req?: any): Observable<HttpResponse<any>> {
    const options = createRequestOption(req);
    return this.http.get<any>(this.resourceUrl, { params: options, observe: 'response' });
  }

  create(userMano: any): Observable<HttpResponse<any>> {
    return this.http.post<any>(this.resourceUrl, userMano, { observe: 'response' });
  }

  update(userMano: any, id: any): Observable<HttpResponse<any>> {
    return this.http.put<any>(`${this.resourceUrl}/${id}`, userMano, { observe: 'response' });
  }

  delete(UserManoId: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${UserManoId}`, { observe: 'response' });
  }
}
