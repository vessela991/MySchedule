import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Login } from '../models/login.model';
import { environment } from '../../environments/environment';
import { UserGet } from '../models/user-get.model';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginPath: string = environment.apiUrl + '/login';
  private userPath = environment.apiUrl + '/users';
  private tokenName: string = "token";
  private _loggedUser: UserGet;

  constructor(private httpClient: HttpClient) { }

  getAuthorizationOptions() {
    return {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${this.getToken()}`
      })
    };
  }

  login(data: any): Promise<Login> {
    return this.httpClient.post<Login>(this.loginPath, data).toPromise();
  }

  saveToken(token: string): void {
    localStorage.setItem(this.tokenName, token);
  }

  logout(): void {
    localStorage.removeItem(this.tokenName);
  }

  getToken(): string {
    return localStorage.getItem(this.tokenName);
  }

  getLoggedUserId(): string {
    if(this.isLogged) {
      let payload = JSON.parse(atob(this.getToken().split('.')[1]));
      return payload.userId;
    }
    return undefined;
  }
  
  isLogged(): boolean {
    return this.getToken() != null;
  }

  isAdmin(): boolean {
    return this.loggedUser !== undefined && this.loggedUser.role === 'ADMINISTRATOR';
  }

  isAdminOrOwner(userId: string): boolean {
    return this.isAdmin() || this.loggedUser.id === userId
  }

  isAdminOrManager(groupManagerId: string): boolean {
    return this.isAdmin() || (this.loggedUser.role === 'MANAGER' && groupManagerId === this.loggedUser.id);
  }

  get loggedUser(): UserGet {
    return this._loggedUser;
  }

  set loggedUser(value: UserGet) {
    this._loggedUser = value;
  }
}
