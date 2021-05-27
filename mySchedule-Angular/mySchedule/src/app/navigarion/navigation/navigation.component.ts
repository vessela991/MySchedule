import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { NavigatorService } from 'src/app/services/navigator.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  isSchedulesClicked = true;
  isGroupsClicked = false;
  isUsersClicked = false;
  isMyProfileClicked = false;
  isCreateUserClicked = false;
  isCreateGroupClicked = false;

  constructor(private authService: AuthService, private userService: UserService, private navigatorService: NavigatorService) {
      this.isSchedulesClicked = localStorage.getItem("isSchedulesClicked") === "true";
      this.isGroupsClicked = localStorage.getItem("isGroupsClicked") === "true";
      this.isUsersClicked = localStorage.getItem("isUsersClicked") === "true";
      this.isMyProfileClicked = localStorage.getItem("isMyProfileClicked") === "true";
      this.isCreateUserClicked = localStorage.getItem("isCreateUserClicked") === "true";
      this.isCreateGroupClicked = localStorage.getItem("isCreateGroupClicked") === "true";
  }

  serializeState(): void {
    localStorage.setItem("isSchedulesClicked", `${this.isSchedulesClicked}`);
    localStorage.setItem("isGroupsClicked", `${this.isGroupsClicked}`);
    localStorage.setItem("isUsersClicked", `${this.isUsersClicked}`);
    localStorage.setItem("isMyProfileClicked", `${this.isMyProfileClicked}`);
    localStorage.setItem("isCreateUserClicked", `${this.isCreateUserClicked}`);
    localStorage.setItem("isCreateGroupClicked", `${this.isCreateGroupClicked}`);
  }

  async ngOnInit() {
    if(this.authService.loggedUser === undefined) {

      let userId = this.authService.getLoggedUserId();

      if(userId !== undefined) {
        this.authService.loggedUser = await this.userService.getUserById(userId);
        console.log(`calling for loggeduser: ${this.authService.loggedUser}`)
      }
    }
  }

  isLogged(): boolean {
    return this.authService.isLogged();
  }

  logout(): Promise<boolean> {
    this.authService.logout();
    return this.navigateTo({route: "", isSchedulesClicked: true});
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  navigateToSchedules(): Promise<boolean> {
    return this.navigateTo({route: "/schedules", isSchedulesClicked: true});
  }

  navigateToGroups(): Promise<boolean> {
    return this.navigateTo({route: "/groups", isGroupsClicked: true});
  }

  navigateToUsers(): Promise<boolean> {
    return this.navigateTo({route: "/users", isUsersClicked: true});
  }

  navigateToMyProfile(): Promise<boolean> {
    return this.navigateTo({route: "/users/" + this.authService.getLoggedUserId(), isMyProfileClicked: true});
  }

  navigateToCreateUser(): Promise<boolean> {
    return this.navigateTo({route: "/users/create", isCreateUserClicked: true});
  }

  navigateToCreateGroup(): Promise<boolean> {
    return this.navigateTo({route: "/groups/create", isCreateGroupClicked: true});
  }

  private navigateTo(
    {route, isSchedulesClicked = false, isGroupsClicked = false, isUsersClicked = false, isMyProfileClicked = false, isCreateUserClicked = false, isCreateGroupClicked = false} 
    : {route: string, isSchedulesClicked?: boolean, isGroupsClicked?: boolean, isUsersClicked?: boolean, isMyProfileClicked?: boolean, isCreateUserClicked?: boolean, isCreateGroupClicked?: boolean}) 
    : Promise<boolean>
    {
      this.isSchedulesClicked = isSchedulesClicked;
      this.isGroupsClicked = isGroupsClicked;
      this.isUsersClicked = isUsersClicked;
      this.isMyProfileClicked = isMyProfileClicked;
      this.isCreateUserClicked = isCreateUserClicked;
      this.isCreateGroupClicked = isCreateGroupClicked;
      this.serializeState();
      return this.navigatorService.navigate(route);
    }
}
