import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { UserGet } from '../models/user-get.model';
import { AuthService } from '../services/auth.service';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userPath = environment.apiUrl + '/users';

  constructor(private http:HttpClient, private authService: AuthService) { }

  getAllUsers(): Promise<Array<UserGet>>{
      return this.http.get<Array<UserGet>>(this.userPath, this.authService.getAuthorizationOptions()).toPromise();
  }

  getUserById(id: string): Promise<UserGet> {
    return this.http.get<UserGet>(this.userPath + "/" + id, this.authService.getAuthorizationOptions()).toPromise();
  }

  getLoggedUser(): Promise<UserGet> {
    return this.http.get<UserGet>(this.userPath + "/" + this.authService.getLoggedUserId(), this.authService.getAuthorizationOptions()).toPromise();
  }

  getEventsByUserId(id: string): Promise<Array<Event>> {
    return this.http.get<Array<Event>>(this.userPath + "/" + id + "/events", this.authService.getAuthorizationOptions()).toPromise();
  }

  createUser(user): Promise<UserGet> {
    return this.http.post<UserGet>(this.userPath, user, this.authService.getAuthorizationOptions()).toPromise();
  }
}