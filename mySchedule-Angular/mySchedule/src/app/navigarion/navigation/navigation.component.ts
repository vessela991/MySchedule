import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {


  constructor(private authService: AuthService, private userService: UserService, private router: Router) {}

  async ngOnInit() {
    if(this.authService.loggedUser === undefined) {

      let userId = this.authService.getLoggedUserId();

      if(userId !== undefined) {
        this.authService.loggedUser = await this.userService.getUserById(userId);
      }
    }
  }

  isLogged(): boolean {
    return this.authService.isLogged();
  }

  logout(): Promise<boolean> {
    this.authService.logout();
    return this.router.navigate([""]);
  }

  getLoggedUserId(): string {
    return this.authService.loggedUser?.id;
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }
}
