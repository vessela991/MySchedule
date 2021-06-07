import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserGet } from 'src/app/models/user-get.model';
import { AuthService } from 'src/app/services/auth.service';
import { NavigatorService } from 'src/app/services/navigator.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  user: UserGet;
  constructor(private activatedRoute: ActivatedRoute,
              private authService: AuthService,
              private userService: UserService,
              private router: Router) {}

  async ngOnInit() {
    this.activatedRoute.params.subscribe(async params => {
      this.user = await this.userService.getUserById(params["id"]);
    });
  }

  isAdminOrOwner(userId: string): boolean {
    return this.authService.isAdminOrOwner(userId);
  }

  editProfile(userId) {
    return this.router.navigate(["/users/" + userId + '/edit'])
  }
//admin group is null by defualt, need to handle it
  viewGroupSchedule(groupId: string) {
    return this.router.navigate(["/schedules/groups/" + groupId])
  }
  
  viewPersonalSchedule(userId: string) {
    return this.router.navigate(["/schedules/users/" + userId])
  }
  
}
