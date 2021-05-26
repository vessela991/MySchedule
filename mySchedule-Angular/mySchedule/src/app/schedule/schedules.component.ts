import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Group } from '../models/group.model';
import { Schedule } from '../models/schedule.model';
import { UserGet } from '../models/user-get.model';
import { AuthService } from '../services/auth.service';
import { GroupService } from '../services/group.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-schedules',
  templateUrl: './schedules.component.html',
  styleUrls: ['./schedules.component.css']
})
export class SchedulesComponent implements OnInit {

  // schedule: Schedule = new Schedule();
  user: UserGet;
  group: Group;

  constructor(private auth: AuthService, private groupService: GroupService, private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.constructSchedule();
  }

  handlePersonalSchedule() {
    this.router.navigate(["/schedules/users/" + this.user.id]);
  }

  handleGroupSchedule() {
    this.router.navigate(["/schedules/groups/" + this.group.id]);
  }

  getGroupById(id) {
    return this.groupService.getGroupById(id);
  }

  constructSchedule() {
    this.userService.getUserById(this.auth.getLoggedUserId()).subscribe(data => {
      this.user = data;

      if (this.user.groupId === null) {
        return;
      }

      this.groupService.getGroupById(this.user.groupId).subscribe(data => {
        this.group = data;
        // this.schedule.group = this.group;
      });
    });   
  }

  isLogged(){
    return this.auth.isLogged();
  }
}
