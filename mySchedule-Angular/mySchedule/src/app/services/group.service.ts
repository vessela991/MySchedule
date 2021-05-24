import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { Group } from '../models/group.model';



@Injectable({
  providedIn: 'root'
})
export class GroupService {

  private options = {
    headers: new HttpHeaders({
      'Authorization': `Bearer ${this.auth.getToken()}`
    })
  };
  private groupPath = environment.apiUrl + 'groups';

  constructor(private http:HttpClient, private auth: AuthService) { }

  getGroupById(id): Observable<Group>{
      return this.http.get<Group>(this.groupPath + "/" + id, this.options);
  }
}