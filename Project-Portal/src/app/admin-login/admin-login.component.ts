import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';
import {AuthenticationService} from '../Service/authentication.service';
import {AlertService} from '../Service/alert.service';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent implements OnInit {
  loginForm: FormGroup;
  loading: boolean;
  submitted: boolean;
  returnUrl: string;
  message: any;

  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private alertService: AlertService,
              private authentication: AuthenticationService) {
    if (sessionStorage.getItem('currentAdmin')) {
      this.router.navigate(['/admin/dashboard']);
    }
  }

  get f() {
    return this.loginForm.controls;
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.authentication.login(this.f.username.value, this.f.password.value, true).pipe(first()).subscribe(
      data => {
        this.router.navigate(['admin/dashboard']);
      },
      error => {
        this.message = error.error.message == null ? error.error: error.error.message;
        this.alertService.error(this.message);
        this.loading = false;
      });
  }

}

