import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Group } from '../models/group.model';
import { UserGet } from '../models/user-get.model';
import { AuthService } from '../services/auth.service';
import { GroupService } from '../services/group.service';
import { NavigatorService } from '../services/navigator.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-schedules',
  templateUrl: './schedules.component.html',
  styleUrls: ['./schedules.component.css']
})
export class SchedulesComponent implements OnInit {
  public user: UserGet;
  public group: Group;

  constructor(private userService: UserService, 
    private groupService: GroupService, 
    private authService: AuthService, 
    private router: Router) {}
  
  async ngOnInit() {
    this.user = await this.userService.getLoggedUser();
    this.authService.loggedUser = this.user;
    this.group = await this.groupService.getLoggedUserGroup();
    
  }

  handlePersonalSchedule() {
    this.router.navigate(["/schedules/users/" + this.user.id]);
  }

  handleGroupSchedule() {
    this.router.navigate(["/schedules/groups/" + this.group.id]);
  }
}
