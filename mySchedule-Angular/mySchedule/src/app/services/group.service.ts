import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { Group } from '../models/group.model';

@Injectable({
  providedIn: 'root'
})
export class GroupService {
  private groupPath = environment.apiUrl + '/groups';

  constructor(private http:HttpClient, private auth: AuthService) { }

  getGroupById(id): Promise<Group>{
    return this.http.get<Group>(this.groupPath + "/" + id, this.auth.getAuthorizationOptions()).toPromise();
  }

  getAllGroups(): Promise<Array<Group>>{
    return this.http.get<Array<Group>>(this.groupPath, this.auth.getAuthorizationOptions()).toPromise();
  } 

  getLoggedUserGroup(): Promise<Group>{
    return this.http.get<Group>(this.groupPath + "/" + this.auth.loggedUser.groupId, this.auth.getAuthorizationOptions()).toPromise();
  }
}