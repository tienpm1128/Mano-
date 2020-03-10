import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient } from '@angular/common/http';
import { IGrantVimRoleRequest } from 'app/shared/model/grantVimRoleRequest.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VimInstaceDetailService {
  public resourceUrl = SERVER_API_URL + '/vim/v1/vim_instances';

  constructor(private http: HttpClient) {}

  grantVimRole(grantVimRoleRequest: IGrantVimRoleRequest, vimId: any): Observable<any> {
    if (!vimId || !grantVimRoleRequest) {
      return;
    }
    return this.http.post(`${this.resourceUrl}/${vimId}/grant_to_mano_user/{vimId:${vimId}}`, grantVimRoleRequest, { observe: 'response' });
  }
}
