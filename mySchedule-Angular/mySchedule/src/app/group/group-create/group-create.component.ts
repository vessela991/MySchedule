import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { GroupService } from 'src/app/services/group.service';
import { NavigatorService } from 'src/app/services/navigator.service';

@Component({
  selector: 'app-group-create',
  templateUrl: './group-create.component.html',
  styleUrls: ['./group-create.component.css']
})
export class GroupCreateComponent implements OnInit {

  createGroupForm: FormGroup;
  errorMessage: string;

  constructor(private toastr: ToastrService, private groupService: GroupService, private navigatorService: NavigatorService, private formBuilder: FormBuilder) {
    this.createGroupForm = this.formBuilder.group({
      'name': ['', [Validators.required]],
      'members': ['', []]
    })
   }
  
  async ngOnInit() {
  }

  async createGroup() {
    try {
      console.log(this.createGroupForm.value)

      // await this.groupService.createGroup(this.createGroupForm.value);
      this.errorMessage = null;
      this.toastr.success("Successfully created group.");
      return this.navigatorService.navigate('/groups/create');
    } catch(error) {
      this.toastr.error("Error creating group.")
      this.errorMessage = error.error;
    }
  }

  get name() {
    return this.createGroupForm.get('name');
  }

  get members() {
    return this.createGroupForm.get('members');
  }
}
