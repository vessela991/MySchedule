import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Group } from 'src/app/models/group.model';
import { UserGet } from 'src/app/models/user-get.model';
import { AuthService } from 'src/app/services/auth.service';
import { GroupService } from 'src/app/services/group.service';
import { NavigatorService } from 'src/app/services/navigator.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {

  groups: Array<Group>;
  editUserForm: FormGroup;
  errorMessage: string;
  isPasswordVisible: boolean = false;
  user: UserGet;

  constructor(private toastr: ToastrService,
              private groupService: GroupService,
              private userService: UserService,
              private navigatorService: NavigatorService,
              private formBuilder: FormBuilder,
              private authService: AuthService,
              private route: ActivatedRoute) {
    this.editUserForm = this.formBuilder.group({
      'id': ['', [Validators.required]],
      'username': ['', [Validators.required]],
      'email': ['', [Validators.required]],
      'gender': ['', [Validators.required]],
      'role': ['', [Validators.required]],
      'groupId': ['', [Validators.required]],
      'picture': ['', [Validators.required]],
      'userInfo': ['', []]
    })
   }
  
  async ngOnInit() {
    this.groups = await this.groupService.getAllGroups();
    this.route.params.subscribe(async params => {
      console.log(params);
    this.user = await this.userService.getUserById(params["id"]);
    });

  }

  togglePasswordVisibility(): void {
    this.isPasswordVisible = !this.isPasswordVisible;
  }

  async editUser() {
    try {
      await this.userService.editUser(this.editUserForm.value);
      this.errorMessage = null;
      this.toastr.success("Successfully updated user.");
      return this.navigatorService.navigate('/users');
    } catch(error) {
      this.toastr.error("Error updating user.")
      this.errorMessage = error.error;
    }
  }

  get id() {
    return this.editUserForm.get('id');
  }

  get username() {
    return this.editUserForm.get('username');
  }

  get email() {
    return this.editUserForm.get('email');
  }

  get gender() {
    return this.editUserForm.get('gender');
  }

  get role() {
    return this.editUserForm.get('role');
  }

  get userInfo() {
    return this.editUserForm.get('userInfo');
  }
  
  get picture() {
    return this.editUserForm.get('picture');
  }
  
  isAdmin(): boolean {
    return this.authService.isAdmin();
  }
}
