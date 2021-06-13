import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { Group } from '../models/group.model';
import { Event } from '../models/event.model';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private eventPath = environment.apiUrl + '/events';

  constructor(private http:HttpClient, private auth: AuthService) { }

  getById(Id: string): Promise<Event> {
    return this.http.get<Event>(this.eventPath + "/" + Id, this.auth.getAuthorizationOptions()).toPromise();
  }
  
  createEvent(event): Promise<Event> {
    return this.http.post<Event>(this.eventPath, event, this.auth.getAuthorizationOptions()).toPromise();
  }

  editEvent(event, id): Promise<Event> {
    return this.http.put<Event>(this.eventPath + "/" + id, event, this.auth.getAuthorizationOptions()).toPromise();
  }

  deleteEvent(id): Promise<void> {
    return this.http.delete<void>(this.eventPath + "/" + id, this.auth.getAuthorizationOptions()).toPromise();
  }

  
  updateEventStatus(id, status): Promise<Event> {
    let body = `{\"status\": \"${status}\" }`
    return this.http.post<Event>(this.eventPath + "/" + id, body,  {headers: new HttpHeaders({'Authorization': `Bearer ${this.auth.getToken()}`, 'Content-Type': 'application/json'})}).toPromise();
  }
}