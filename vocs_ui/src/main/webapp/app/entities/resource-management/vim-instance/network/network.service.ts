import { Injectable } from '@angular/core';
import { INetWork } from 'app/shared/model/network.model';
import { HttpResponse, HttpClient } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<INetWork>;

@Injectable({
  providedIn: 'root'
})
export class NetworkService {
  public resourceUrl = SERVER_API_URL + '/vim/v1/networks';

  constructor(protected http: HttpClient) {}

  query(vimId: string, req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http.get<any>(`${this.resourceUrl}/{vimId:${vimId}}`, { params: options, observe: 'response' });
  }
}
