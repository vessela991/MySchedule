import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { NavigatorService } from 'src/app/services/navigator.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginForm: FormGroup;
  errorMessage: string;
  isPasswordVisible: boolean = false;
  
  constructor(private router: Router, private formBuilder: FormBuilder, private authService: AuthService) {
    this.loginForm = this.formBuilder.group({
      'username': ['', [Validators.required]],
      'password': ['', [Validators.required]]
    })
  }

  async login() {
    try {
      let loginResult = await this.authService.login(this.loginForm.value);
      this.errorMessage = null;
      this.authService.saveToken(loginResult.jwt);
      return this.router.navigate(['/schedules']);
    }
    catch (error) {
      this.errorMessage = error.error;
    }
  }

  togglePasswordVisibility() {
    this.isPasswordVisible = !this.isPasswordVisible;
  }

  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }
}
