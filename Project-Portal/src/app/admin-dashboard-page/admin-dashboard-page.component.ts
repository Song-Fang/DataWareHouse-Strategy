import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../Service/authentication.service';
import {UserService} from '../Service/user.service';
import {Users} from '../Models/users';
import {AlertService} from '../Service/alert.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-admin-dashboard-page',
  templateUrl: './admin-dashboard-page.component.html',
  styleUrls: ['./admin-dashboard-page.component.css']
})
export class AdminDashboardPageComponent implements OnInit {

  dataarray: Users[];
  message: any;
  user: Users;
  isMongo: boolean;

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
    if (sessionStorage.getItem('api') === 'http://localhost:8080/MongoApi') {
      this.isMongo = true;
    } else {
      this.isMongo = false;
    }
  }

  getAllUser() {
    return this.userService.getAllUser().subscribe(data => {
      this.dataarray = data;
      console.log(this.dataarray);
    }, error => {
      this.message = error.error.message == null ? error.error : error.error.message;
      this.alertService.error(this.message);
    });
  }


  ChangeRole(username: string) {
    return this.userService.changeRole(username).subscribe(data => {
      this.user = data;
      if (this.user) {
        this.alertService.success('Role has been changed');
        window.location.reload();
      } else {
        this.alertService.error('something was wrong');
      }
    });
  }

  DeleteUser(username: string) {
    return this.userService.deleteUser(username).subscribe(data => {
      this.alertService.success('Role has been deleted');
      window.location.reload();
    }, error => {
      this.alertService.error('something was wrong');
    });
  }
}
