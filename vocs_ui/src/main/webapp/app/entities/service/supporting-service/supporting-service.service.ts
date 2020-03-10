import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { createRequestOption } from 'app/shared';

@Injectable({
  providedIn: 'root'
})
export class SupportingServiceService {
  public resourceUrl = SERVER_API_URL + '/api/v1/object/supporting';

  constructor(protected http: HttpClient) {}

  query(req?: any): Observable<HttpResponse<any>> {
    const options = createRequestOption(req);
    return this.http.get<any>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
