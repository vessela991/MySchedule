import { Component, OnInit } from '@angular/core';
import { Group } from 'src/app/models/group.model';
import { AuthService } from 'src/app/services/auth.service';
import { GroupService } from 'src/app/services/group.service';
import { NavigatorService } from 'src/app/services/navigator.service';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.css']
})
export class GroupsComponent implements OnInit {

  public groups: Array<Group>;

  constructor(private groupService: GroupService, private authService: AuthService, private navigatorService: NavigatorService) {}

  async ngOnInit() {
    this.groups = await this.groupService.getAllGroups();
  }

  isAdminOrManager(group: Group): boolean {
    return this.authService.isAdminOrManager(group.managerId);
  }

  handleGroupSchedule(groupId) {
    this.navigatorService.navigate("/schedules/groups/" + groupId);
  }

  editGroupSchedule(groupId) {
    this.navigatorService.navigate("/groups/" + groupId + '/edit');
  }

  viewGroup(groupId) {
    this.navigatorService.navigate("/groups/" + groupId);
  }
}