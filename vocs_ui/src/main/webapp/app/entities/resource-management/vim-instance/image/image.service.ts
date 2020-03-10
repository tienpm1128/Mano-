import { Injectable } from '@angular/core';
import { HttpResponse, HttpClient, HttpHeaders } from '@angular/common/http';
import { IImage } from 'app/shared/model/image.model';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImageService {
  public resourceUrl = SERVER_API_URL + '/vim/v1/images';

  constructor(protected http: HttpClient) {}

  query(vimId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.resourceUrl}/{vimId:${vimId}}`, { observe: 'response' });
  }

  create(image: IImage, filePath: string, vimId: string): Observable<any> {
    const headers: HttpHeaders = new HttpHeaders({
      'file-path': filePath
    });
    const options = { headers };
    return this.http.post<any>(`${this.resourceUrl}/{vimId:${vimId}}`, image, options);
  }

  delete(imageId: string, vimId: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${imageId}/{vimId:${vimId}}`, { observe: 'response' });
  }
}
