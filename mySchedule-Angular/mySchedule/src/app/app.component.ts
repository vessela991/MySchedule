import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserGet } from './models/user-get.model';
import { AuthService } from './services/auth.service';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'mySchedule';
}
