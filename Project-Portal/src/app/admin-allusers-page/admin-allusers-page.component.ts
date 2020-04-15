import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../Service/authentication.service';
import {UserService} from '../Service/user.service';
import {Router} from '@angular/router';
import {AlertService} from '../Service/alert.service';
import {Users} from '../Models/users';


@Component({
  selector: 'app-admin-allusers-page',
  templateUrl: './admin-allusers-page.component.html',
  styleUrls: ['./admin-allusers-page.component.css']
})
export class AdminAllusersPageComponent implements OnInit {
  users: Users[];
  message: any;
  needUpdate: boolean;


  constructor(private authenticationService: AuthenticationService,
              private userService: UserService,
              private alertService: AlertService,
              private router: Router) {
    if (!this.userService.currentAdminValue) {
      this.router.navigate(['admin/login']);
    }
  }

  ngOnInit() {
    this.getAllUser();
  }

  getAllUser() {
    return this.userService.getAllUser().subscribe(data => {
      this.users = data;
    }, error => {
      this.message = error.error.message == null ? error.error : error.error.message;
      this.alertService.error(this.message);
    });

  }

  UpdateUser(username: string, phonenumber: string, email: string, city: string, state: string) {
    return this.userService.updateUser(username, phonenumber, email, city, state).subscribe(data => {
      this.alertService.success('user has been updated');
      this.needUpdate = false;
    }, error => {
      this.message = error.error.message == null ? error.error : error.error.message;
      this.alertService.error(this.message);
    });

  }

  DeleteUser(username: string) {
    return this.userService.deleteUser(username).subscribe(data => {
      this.alertService.success('user has been deleted');
      window.location.reload();
    }, error => {
      this.message = error.error.message == null ? error.error : error.error.message;
      this.alertService.error(this.message);
    });
  }
}
