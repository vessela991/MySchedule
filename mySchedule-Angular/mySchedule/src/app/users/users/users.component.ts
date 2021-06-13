import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserGet } from 'src/app/models/user-get.model';
import { AuthService } from 'src/app/services/auth.service';
import { NavigatorService } from 'src/app/services/navigator.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  users: Array<UserGet> = undefined;

  constructor(private authService: AuthService, private userService: UserService, private router: Router) {}

  async ngOnInit() {
    this.users = await this.userService.getAllUsers();
  }

  isAdminOrUser(userId: string): boolean {
    return this.authService.isAdmin() || this.authService.getLoggedUserId() === userId;
  }

  viewUserProfile(userId: string) {
    return this.router.navigate(["/users/" + userId]);
  }

  editUser(userId: string) {
    return this.router.navigate(["/users/" + userId + '/edit']);
  }
}
