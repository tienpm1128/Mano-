import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class NsInstanceNotificationService {
  public resourceUrl = SERVER_API_URL + '/nslcm/v1/notifications';

  constructor(protected http: HttpClient) {}

  query(): Observable<any> {
    return this.http.get<any>(this.resourceUrl, { observe: 'response' });
  }
}
