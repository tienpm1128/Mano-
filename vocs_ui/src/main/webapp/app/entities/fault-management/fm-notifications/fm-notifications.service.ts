import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { createRequestOption } from 'app/shared';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FmNotificationsService {
  public resourceUrl = SERVER_API_URL + '/nsfm/v1/notifications';

  constructor(protected http: HttpClient) {}

  query(req?: any): Observable<HttpResponse<any>> {
    const options = createRequestOption(req);
    return this.http.get<any>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
