import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Group } from 'src/app/models/group.model';
import { GroupService } from 'src/app/services/group.service';
import { NavigatorService } from 'src/app/services/navigator.service';

@Component({
  selector: 'app-group-edit',
  templateUrl: './group-edit.component.html',
  styleUrls: ['./group-edit.component.css']
})
export class GroupEditComponent implements OnInit {

  editGroupForm: FormGroup;
  errorMessage: string;
  groupId: string;
  group: Group;

  constructor(private toastr: ToastrService,
              private groupService: GroupService,
              private router: Router,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute) {
    this.editGroupForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      members: [[], []],
      managerId: ['', []]
    })
   }
  
  async ngOnInit() {
    this.route.params.subscribe(async params => {
      this.groupId = params.id;

      this.group = await this.groupService.getGroupById(this.groupId);
      this.setAllValues(this.group.name, this.group.members, this.group.managerId);
    });
  }

  async editGroup() {
    try {
      await this.groupService.editGroup(this.editGroupForm.value, this.groupId);
      this.errorMessage = null;
      this.toastr.success("Successfully updated group.");
      return this.router.navigate(['/groups/']);
    } catch(error) {
      this.toastr.error("Error updated group.")
      this.errorMessage = error.error;
    }
  }

  get name() {
    return this.editGroupForm.get('name');
  }

  get members() {
    return this.editGroupForm.get('members');
  }

  get managerId() {
    return this.editGroupForm.get('managerId');
  }

  setAllValues(newName: string, currentMembers:Array<String>, currentManagerId: string) {
    this.editGroupForm.setValue({name: newName, members: currentMembers, managerId: currentManagerId});
  }
  
}
