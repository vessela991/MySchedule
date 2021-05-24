import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  errorMessage: string;

  constructor(private router: Router, private formBuilder: FormBuilder, private authService: AuthService) {
    this.loginForm = this.formBuilder.group({
      'username': ['', [Validators.required]],
      'password': ['', [Validators.required]]
    })
  }

  ngOnInit(): void {
  }

  navigate() {
    return this.router.navigate(['/schedules']);
  }

  login() {
    this.authService.login(this.loginForm.value).subscribe(
      data => {
        this.errorMessage = null;
        localStorage.setItem('token', data.jwt);
        return this.navigate();
      }, error => {
        this.errorMessage = error.error;
      });
  }

  get username() {
    return this.loginForm.get('username');
  }


  get password() {
    return this.loginForm.get('password');
  }
}
