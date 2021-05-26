import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Group } from 'src/app/models/group.model';
import { UserGet } from 'src/app/models/user-get.model';
import { AuthService } from 'src/app/services/auth.service';
import { GroupService } from 'src/app/services/group.service';
import { UserService } from 'src/app/services/user.service';

type NewType = AuthService;

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  constructor(private groupService: GroupService, private auth: AuthService, private userService: UserService, private router: Router) {
  }

  users: Array<UserGet>;
  loggedUser: UserGet;
  group: Group;

  ngOnInit(): void {
    this.getAllUsers();

    this.userService.getUserById(this.auth.getLoggedUserId()).subscribe(data => {
      this.loggedUser = data;  
    });
}
  getAllUsers() {
    this.userService.getAllUsers().subscribe(data => {
        this.users = data;
    })
  }

  isAdmin(): boolean {
    if (this.loggedUser === undefined) {
      return false;
    }
    
    if (this.loggedUser.role === 'ADMINISTRATOR') {
      return true;
    }
  }

  viewUserProfile(userId) {
    this.router.navigate(["/users/" + userId]);
  }

  editUser(user: UserGet) {
    return;
  }

  // getGroupOfCurrentUser(user: UserGet): string {
  //   let groupName: string;
  //   if (user.groupId !== null) {
  //     this.groupService.getGroupById(user.groupId).subscribe(data => {
  //       groupName = data.name;
  //     });
  //     return groupName; 
  //   }
  //   else return;
  // }
}
