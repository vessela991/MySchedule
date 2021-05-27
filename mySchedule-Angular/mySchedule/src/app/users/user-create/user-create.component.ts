import {  FormBuilder, FormControl, ReactiveFormsModule, Validators} from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Group } from 'src/app/models/group.model';
import { GroupService } from 'src/app/services/group.service';
import { UserService } from 'src/app/services/user.service';
import { NavigatorService } from 'src/app/services/navigator.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.css']
})
export class UserCreateComponent implements OnInit {
  groups: Array<Group>;
  createUserForm: FormGroup;
  errorMessage: string;
  isPasswordVisible: boolean = false;

  constructor(private toastr: ToastrService, private groupService: GroupService, private userService: UserService, private navigatorService: NavigatorService, private formBuilder: FormBuilder) {
    this.createUserForm = this.formBuilder.group({
      'username': ['', [Validators.required]],
      'password': ['', [Validators.required]],
      'email': ['', [Validators.required]],
      'gender': ['', [Validators.required]],
      'role': ['', [Validators.required]],
      'groupId': ['', [Validators.required]],
      'userInfo': ['', []]
    })
   }
  
  async ngOnInit() {
    this.groups = await this.groupService.getAllGroups();
  }

  togglePasswordVisibility(): void {
    this.isPasswordVisible = !this.isPasswordVisible;
  }

  async createUser() {
    try {
      await this.userService.createUser(this.createUserForm.value);
      this.errorMessage = null;
      this.toastr.success("Successfully created user.");
      return this.navigatorService.navigate('/users/create');
    } catch(error) {
      this.toastr.error("Error creating user.")
      this.errorMessage = error.error;
    }
  }

  get username() {
    return this.createUserForm.get('username');
  }

  get password() {
    return this.createUserForm.get('password');
  }

  get email() {
    return this.createUserForm.get('email');
  }

  get gender() {
    return this.createUserForm.get('gender');
  }

  get role() {
    return this.createUserForm.get('role');
  }

  get userInfo() {
    return this.createUserForm.get('userInfo');
  }
}
