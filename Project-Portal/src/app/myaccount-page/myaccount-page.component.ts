import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../Service/authentication.service';
import {Router} from '@angular/router';
import {User, Users} from '../Models/users';
import {UserService} from '../Service/user.service';
import {AlertService} from '../Service/alert.service';

@Component({
  selector: 'app-myaccount-page',
  templateUrl: './myaccount-page.component.html',
  styleUrls: ['./myaccount-page.component.css']
})
export class MyaccountPageComponent implements OnInit {
  user: User;
  map = new Map<string, string>();
  list: string[];
  edited: boolean;
  loading: boolean;
  formloading: boolean;
  message: any;

  constructor(private authenticationService: AuthenticationService,
              private router: Router,
              private userService: UserService,
              private alertService: AlertService) {
    if (!this.authenticationService.currentUserValue) {
      this.router.navigate(['user/login']);
    }

    this.map.set('Alabama', 'AL');
    this.map.set('Alaska', 'AK');
    this.map.set('American Samoa', 'AS');
    this.map.set('Arizona', 'AZ');
    this.map.set('Arkansas', 'AR');
    this.map.set('California', 'CA');
    this.map.set('Colorado', 'CO');
    this.map.set('Connecticut', 'CT');
    this.map.set('Delaware', 'DE');
    this.map.set('District Of Columbia', 'DC');
    this.map.set('Federated States Of Micronesia', 'FM');
    this.map.set('Florida', 'FL');
    this.map.set('Georgia', 'GA');
    this.map.set('Guam', 'GU');
    this.map.set('Hawaii', 'HI');
    this.map.set('Idaho', 'ID');
    this.map.set('Illinois', 'IL');
    this.map.set('Indiana', 'IN');
    this.map.set('Iowa', 'IA');
    this.map.set('Kansas', 'KS');
    this.map.set('Kentucky', 'KY');
    this.map.set('Louisiana', 'LA');
    this.map.set('Maine', 'ME');
    this.map.set('Marshall Islands', 'MH');
    this.map.set('Maryland', 'MD');
    this.map.set('Massachusetts', 'MA');
    this.map.set('Michigan', 'MI');
    this.map.set('Minnesota', 'MN');
    this.map.set('Mississippi', 'MS');
    this.map.set('Missouri', 'MO');
    this.map.set('Montana', 'MT');
    this.map.set('Nebraska', 'NE');
    this.map.set('Nevada', 'NV');
    this.map.set('New Hampshire', 'NH');
    this.map.set('New Jersey', 'NJ');
    this.map.set('New Mexico', 'NM');
    this.map.set('New York', 'NY');
    this.map.set('North Carolina', 'NC');
    this.map.set('North Dakota', 'ND');
    this.map.set('Northern Mariana Islands', 'MP');
    this.map.set('Ohio', 'OH');
    this.map.set('Oklahoma', 'OK');
    this.map.set('Oregon', 'OR');
    this.map.set('Palau', 'PW');
    this.map.set('Pennsylvania', 'PA');
    this.map.set('Puerto Rico', 'PR');
    this.map.set('Rhode Island', 'RI');
    this.map.set('South Carolina', 'SC');
    this.map.set('South Dakota', 'SD');
    this.map.set('Tennessee', 'TN');
    this.map.set('Texas', 'TX');
    this.map.set('Utah', 'UT');
    this.map.set('Vermont', 'VT');
    this.map.set('Virgin Islands', 'VI');
    this.map.set('Virginia', 'VA');
    this.map.set('Washington', 'WA');
    this.map.set('West Virginia', 'WV');
    this.map.set('Wisconsin', 'WI');
    this.map.set('Wyoming', 'WY');

    this.list = Array.from(this.map.keys());
  }

  ngOnInit() {
    this.formloading = true;
    this.userService.getUser().subscribe(data => {
      this.user = data;
      this.formloading = false;
    }, error => {
      this.message = error.error.message == null ? error.error : error.error.message;
      this.alertService.error(this.message);
      this.loading = false;
    });
    this.edited = false;
    this.loading = false;
  }

  changeEdited() {
    this.edited = !this.edited;
  }

  updateUser() {
    this.loading = true;
    this.userService.updateSelf(this.user.username, this.user.phonenumber, this.user.email, this.user.city,
      this.user.state).subscribe(data => {
      const users = new Users();
      users.user = data;
      users.token = this.authenticationService.currentUserValue.token;
      console.log(users);
      this.loading = false;
      this.edited = false;
      sessionStorage.setItem('currentUser', JSON.stringify(users));
      this.alertService.success('your profile has been updated');
    }, error => {
      this.alertService.error('something was wrong');
      this.loading = false;
    });
  }
}
