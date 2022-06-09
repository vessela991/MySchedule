import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Group } from 'src/app/models/group.model';
import { AuthService } from 'src/app/services/auth.service';
import { GroupService } from 'src/app/services/group.service';
import { NavigatorService } from 'src/app/services/navigator.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.css']
})
export class GroupsComponent implements OnInit {

  public groups: Array<Group>;

  constructor(private groupService: GroupService,
              private authService: AuthService,
              private route: Router,
              private toastr: ToastrService) {}

  async ngOnInit() {
    this.groups = await this.groupService.getAllGroups();
  }

  isAdminOrManager(group: Group): boolean {
    return this.authService.isAdminOrManager(group.managerId);
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  handleGroupSchedule(groupId) {
    this.route.navigate(["/schedules/groups/" + groupId]);
  }

  editGroupSchedule(groupId) {
    this.route.navigate(["/groups/" + groupId + '/edit']);
  }

  viewGroup(groupId) {
    this.route.navigate(["/groups/" + groupId]);
  }

  
  async deleteGroup(groupId) {
    await this.groupService.deleteGroup(groupId);
    this.toastr.success("Successfully deleted group.");
    await this.refreshGroups();
  }

  async refreshGroups() {
     let allGroups = await this.groupService.getAllGroups();
     this.groups = allGroups;
  }
}