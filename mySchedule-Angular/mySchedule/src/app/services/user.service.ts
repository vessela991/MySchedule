import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';
import { UserGet } from '../models/user-get.model';
import { AuthService } from '../services/auth.service';



@Injectable({
  providedIn: 'root'
})
export class UserService {

  private options = {
    headers: new HttpHeaders({
      'Authorization': `Bearer ${this.auth.getToken()}`
    })
  };
  private userPath = environment.apiUrl + '/users';
  private userPathById = this.userPath + '/';

  constructor(private http:HttpClient, private auth: AuthService) { }

  getAllUsers(): Observable<Array<UserGet>>{
      return this.http.get<Array<UserGet>>(this.userPath, this.options);
  }

  getUserById(id): Observable<UserGet> {
    return this.http.get<UserGet>(this.userPathById + id, this.options);
  }
}