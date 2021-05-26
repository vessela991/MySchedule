import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserGet } from 'src/app/models/user-get.model';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  constructor(private auth: AuthService, private router: Router, private userService: UserService) {}
  isSchedulesClicked: boolean = true;
  isGroupsClicked: boolean = false;
  isUsersClicked: boolean = false;
  isMyProfileClicked: boolean = false;
  isCreateUserClicked: boolean = false;
  loggedUser: UserGet;

  ngOnInit(): void {
    this.userService.getUserById(this.auth.getLoggedUserId()).subscribe(data => {
      this.loggedUser = data;  
    });
  }

  isLogged() {
    return this.auth.isLogged();
  }

  logout() {
    this.auth.logout();
    return this.router.navigate(["/"]);
  }

  isAdmin(): boolean {
    if (this.loggedUser === undefined) {
      return false;
    }
    
    if (this.loggedUser.role === 'ADMINISTRATOR') {
      return true;
    }
  }

  resetAllActiveButtons() {
    // let schedules = document.getElementById("navSchedules");
    // let groups = document.getElementById("navGroups");
    // let users = document.getElementById("navUsers");
    // let myProfile = document.getElementById("navMyProfile");
    // // let createUser = document.getElementById("navCreateUser");

    // if (schedules.classList.contains("active")) {
    //   schedules.classList.remove("active");
    // }
    // if (groups.classList.contains("active")) {
    //   groups.classList.remove("active");
    // }
    // if (users.classList.contains("active")) {
    //   users.classList.remove("active");
    // }
    // if (myProfile.classList.contains("active")) {
    //   myProfile.classList.remove("active");
    // }
    // if (createUser.classList.contains("active")) {
    //   createUser.classList.remove("active");
    // }
  }

  navigateToSchedules() {
    // this.resetAllActiveButtons();
    // let schedules = document.getElementById("navSchedules");
    // schedules.classList.add("active");
    this.isSchedulesClicked = true;
    this.isGroupsClicked = false;
    this.isUsersClicked = false;
    this.isMyProfileClicked = false;
    this.isCreateUserClicked = false;

    return this.router.navigate(["/schedules"]);
  }

  navigateToGroups() {
    // this.resetAllActiveButtons();
    // let groups = document.getElementById("navGroups");
    // groups.classList.add("active");
    this.isSchedulesClicked = false;
    this.isGroupsClicked = true;
    this.isUsersClicked = false;
    this.isMyProfileClicked = false;
    this.isCreateUserClicked = false;

    return this.router.navigate(["/groups"]);
  }

  navigateToUsers() {
    // this.resetAllActiveButtons();
    // let users = document.getElementById("navUsers");
    // users.classList.add("active");
    this.isSchedulesClicked = false;
    this.isGroupsClicked = false;
    this.isUsersClicked = true;
    this.isMyProfileClicked = false;
    this.isCreateUserClicked = false;

    return this.router.navigate(["/users"]);
  }

  navigateToMyProfile() {
    // this.resetAllActiveButtons();
    // let myProfile = document.getElementById("navMyProfile");
    // myProfile.classList.add("active");
    this.isSchedulesClicked = false;
    this.isGroupsClicked = false;
    this.isUsersClicked = false;
    this.isMyProfileClicked = true;
    this.isCreateUserClicked = false;

    return this.router.navigate(["/users/" + this.auth.getLoggedUserId()]);
  }

  navigateToCreateUser() {
    // this.resetAllActiveButtons();
    // let createUser = document.getElementById("navMyProfile");
    // createUser.classList.add("active");
    this.isSchedulesClicked = false;
    this.isGroupsClicked = false;
    this.isUsersClicked = false;
    this.isMyProfileClicked = false;
    this.isCreateUserClicked = true;

    return this.router.navigate(["/users/create"]);
  }
}
