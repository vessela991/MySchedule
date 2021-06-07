import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Group } from 'src/app/models/group.model';
import { UserGet } from 'src/app/models/user-get.model';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';
import { GroupService } from 'src/app/services/group.service';
import { NavigatorService } from 'src/app/services/navigator.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-group-view',
  templateUrl: './group-view.component.html',
  styleUrls: ['./group-view.component.css']
})
export class GroupViewComponent implements OnInit {

  public group: Group;
  public members: Array<UserGet> = [];
  public manager: UserGet;

  constructor(private groupService: GroupService, private userService: UserService, private authService: AuthService, private router: Router, private route: ActivatedRoute) {}

  async ngOnInit() {
    this.route.params.subscribe(async params => {
      let groupId = params["id"];
      this.group = await this.groupService.getGroupById(groupId);
      if (this.group.managerId === undefined) {
        return;
      }
      this.manager = await this.userService.getUserById(this.group.managerId);

     
      for (let member of this.group.members) {
        var user = await this.userService.getUserById(member);
        this.members.push(user);
      }

    });
  }

  isAdminOrManager(group: Group): boolean {
    return this.authService.isAdminOrManager(group.managerId);
  }

  viewGroupSchedule(groupId) {
    this.router.navigate(["/schedules/groups/" + groupId]);
  }

  editGroupSchedule(groupId) {
    this.router.navigate(["/groups/" + groupId + '/edit']);
  }
}