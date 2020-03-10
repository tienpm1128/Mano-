import { Injectable } from '@angular/core';
import { HttpResponse, HttpClient } from '@angular/common/http';
import { AddNewProjects, IProjects } from 'app/shared/model/projects.model';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { IUserProject } from 'app/shared/model/userProject.model';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  public resourceUrl = SERVER_API_URL + '/vim/v1/projects';
  public userProjectUrl = SERVER_API_URL + '/vim/v1/op_users';

  constructor(protected http: HttpClient) {}

  query(vimId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.userProjectUrl}/projects/{vimId:${vimId}}`, { observe: 'response' });
  }

  create(newProject: AddNewProjects, vimId: string): Observable<HttpResponse<any>> {
    return this.http.post<AddNewProjects>(`${this.resourceUrl}/{vimId:${vimId}}`, newProject, { observe: 'response' });
  }

  update(newProject: IProjects, vimId: string): Observable<HttpResponse<any>> {
    return this.http.put<AddNewProjects>(`${this.resourceUrl}/${newProject.projectId}/{vimId:${vimId}}`, newProject, {
      observe: 'response'
    });
  }

  upload(files: any, projectId: string, vimId: string) {
    return this.http.put<any>(`${this.resourceUrl}/${projectId}/{vimId:${vimId}}`, files, { observe: 'response' });
  }

  delete(projectId: string, vimId: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${projectId}/{vimId:${vimId}}`, { observe: 'response' });
  }

  findProjectsByVimUser(openStackUserId: string, vimId: string): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.userProjectUrl}/projects/{openStackUserId:${openStackUserId}}`, { observe: 'response' });
  }

  grantUser(userProject: IUserProject, vimId: string): Observable<HttpResponse<any>> {
    return this.http.post<AddNewProjects>(`${this.resourceUrl}/grant_to_op_users/{vimId:${vimId}}`, userProject, { observe: 'response' });
  }
}
