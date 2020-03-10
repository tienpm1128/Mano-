import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ISoftWare } from 'app/shared/model/software.model';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { createRequestOption } from 'app/shared';

@Injectable({
  providedIn: 'root'
})
export class SoftwareService {
  public resourceUrl = SERVER_API_URL + '/sw/v1/external/software';

  constructor(protected http: HttpClient) {}

  query(vimId: string, req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http.get<any>(`${this.resourceUrl}/{vimId:${vimId}}`, { params: options, observe: 'response' });
  }

  create(software: ISoftWare, vimId: string): Observable<any> {
    return this.http.post<ISoftWare>(`${this.resourceUrl}/{vimId:${vimId}}`, software, { observe: 'response' });
  }

  find(id: string): Observable<any> {
    return this.http.get<ISoftWare>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  upload(formData: FormData, openStackUserId: string, projectId: string, vimId: string): Observable<any> {
    const headers: HttpHeaders = new HttpHeaders({
      openStackUserId,
      projectId
    });
    const options = { headers };

    return this.http.post<any>(`${this.resourceUrl}/upload/{vimId:${vimId}}`, formData, options);
  }

  update(software: ISoftWare, vimId: string): Observable<any> {
    return this.http.put<any>(`${this.resourceUrl}/${software.id}/update/{vimId:${vimId}}`, software, { observe: 'response' });
  }
}
