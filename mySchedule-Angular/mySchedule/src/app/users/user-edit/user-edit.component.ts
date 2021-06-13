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
  image: File;
  constructor(private toastr: ToastrService,
              private groupService: GroupService,
              private userService: UserService,
              private router: Router,
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
      'picture': [null],
      'userInfo': ['', []]
    })
   }
  
  async ngOnInit() {
    this.groups = await this.groupService.getAllGroups();
    this.route.params.subscribe(async params => {
      this.user = await this.userService.getUserById(params["id"]);
    });

  }

  public onFileChanged(event) {
    this.image = event.target.files[0];
  }

  togglePasswordVisibility(): void {
    this.isPasswordVisible = !this.isPasswordVisible;
  }

  async editUser() {
    try {
      const formData = new FormData();
      if (this.authService.isAdmin()) {
      formData.append('picture', this.image)
      formData.append('username', this.editUserForm.value.username);
      formData.append('email', this.editUserForm.value.email);
      formData.append('gender', this.editUserForm.value.gender);
      formData.append('role', this.editUserForm.value.role);
      formData.append('groupId', this.editUserForm.value.groupId);
      formData.append('userInfo', this.editUserForm.value.userInfo);
      } else {
        if (this.image !== undefined) {
          formData.append('picture', this.image)
        }
        formData.append('username', this.user.username);
        formData.append('email', this.user.email);
        formData.append('gender', this.user.gender);
        formData.append('role', this.user.role);
        formData.append('groupId', this.user.groupId);
        formData.append('userInfo', this.editUserForm.value.userInfo);
      }
  
      await this.userService.editUser(formData, this.user.id);
      this.errorMessage = null;
      this.toastr.success("Successfully updated user.");
      return this.router.navigate(['/users']);
    } catch(error) {
      console.log(error)
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
