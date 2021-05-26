import { trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Group } from 'src/app/models/group.model';
import { UserGet } from 'src/app/models/user-get.model';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';
import { GroupService } from 'src/app/services/group.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.css']
})
export class GroupsComponent implements OnInit {

  constructor(private groupService: GroupService, private auth: AuthService, private userService: UserService, private router: Router) {
  }

  groups: Array<Group>;
  loggedUser: UserGet;

  ngOnInit(): void {
    this.getAllGroups();

    this.userService.getUserById(this.auth.getLoggedUserId()).subscribe(data => {
      this.loggedUser = data;      
    });
}
  getAllGroups() {
    this.groupService.getAllGroups().subscribe(data => {
        this.groups = data;
    })
  }

  isAdminOrManager(group: Group): boolean {
    if (this.loggedUser === undefined) {
      return false;
    }
    
    if (this.loggedUser.role === 'ADMINISTRATOR') {
      return true;
    }

    if (this.loggedUser.role === 'MANAGER' && group.managerId === this.loggedUser.id) {
      return true;
    }
    return false;
  }

  handleGroupSchedule(groupId) {
    this.router.navigate(["/schedules/groups/" + groupId]);
  }
}