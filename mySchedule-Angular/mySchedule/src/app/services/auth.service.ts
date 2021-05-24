import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Login } from '../models/login.model';
import { environment } from '../../environments/environment';
import jwt_decode from 'jwt-decode';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginPath = environment.apiUrl + '/login';
  constructor(private httpClient: HttpClient) { }

  login(data): Observable<Login> {
    return this.httpClient.post<Login>(this.loginPath, data);
  }

  saveToken(token){
    localStorage.setItem('token', token);
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  getToken(){
    return localStorage.getItem('token');
  }

  getDecodedToken(): any {
    return jwt_decode(this.getToken()); 
  }
  
  isLogged(){
    return this.getToken() != null;
  }
}

function JWT(token: any) {
  throw new Error('Function not implemented.');
}
