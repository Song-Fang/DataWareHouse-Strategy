import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../Service/authentication.service';
import {Accidents} from '../Models/accidents';
import {AccidentService} from '../Service/accident.service';
import {UserService} from '../Service/user.service';
import {AlertService} from '../Service/alert.service';

@Component({
  selector: 'app-user-reporthistory-page',
  templateUrl: './user-reporthistory-page.component.html',
  styleUrls: ['./user-reporthistory-page.component.css']
})
export class UserReporthistoryPageComponent implements OnInit {
  accidents: Accidents[];
  loading: boolean;
  isMongo: boolean;

  constructor(private authenticationService: AuthenticationService,
              private accidentService: AccidentService,
              private userService: UserService,
              private alertService: AlertService) { }

  ngOnInit() {
    if (sessionStorage.getItem('api') === 'http://localhost:8080/MongoApi') {
      this.isMongo = true;
    } else {
      this.isMongo = false;
    }
    this.loading = true;
    this.accidentService.getUserReport(this.userService.currentUserValue.user.username).subscribe(data => {
      this.accidents = data;
      this.loading = false;
      console.log(this.accidents);
    });
  }

  deleteReport(id: number) {
    this.accidentService.UserdeleteById(id).subscribe(data => {
      this.alertService.success('Record has been deleted');
      window.location.reload();
    }, error => {
      this.alertService.error('something was wrong');
    });
  }
}
