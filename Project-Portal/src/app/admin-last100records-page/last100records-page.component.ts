import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../Service/authentication.service';
import {UserService} from '../Service/user.service';
import {Router} from '@angular/router';
import {AlertService} from '../Service/alert.service';
import {Accidents} from '../Models/accidents';
import {Subject} from 'rxjs';
import {AccidentService} from '../Service/accident.service';

@Component({
  selector: 'app-last100records-page',
  templateUrl: './last100records-page.component.html',
  styleUrls: ['./last100records-page.component.css']
})
export class Last100recordsPageComponent implements OnInit {
  accidents: Accidents[];
  message: any;
  needUpdate: boolean;
  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject();
  loading: boolean;
  isMongo: boolean;

  constructor(private authenticationService: AuthenticationService,
              private userService: UserService,
              private alertService: AlertService,
              private accidentService: AccidentService,
              private router: Router) {
    if (!this.userService.currentAdminValue) {
      this.router.navigate(['admin/login']);
    }
  }

  ngOnInit() {
    this.dtOptions = {
      pageLength: 20,
      stateSave: true,
      lengthMenu: [[20], [10], [10]],
      processing: true
    };
    this.dtTrigger.next();
    this.loading = true;
    this.getRecords();
  }

  public getRecords() {
    this.userService.getRecords().subscribe(data => {
      this.accidents = data;
      console.log(this.accidents);

      if (sessionStorage.getItem('api') === 'http://localhost:8080/MongoApi') {
        this.isMongo = true;
      } else {
        this.isMongo = false;
      }
      this.loading = false;
    }, error => {
      this.message = error.error.message == null ? error.error : error.error.message;
      this.alertService.error(this.message);
      this.loading = false;
    });
  }

  UpdateAccident(id: number, state: string, city: string, street: string, zipcode: string, latitude: string, longitude: string, visibility: number, humidity: number) {
    this.accidentService.updateAccident(id, state, city, street, zipcode, latitude, longitude, visibility, humidity).subscribe(data => {
        this.alertService.success('Record has been updated');
        window.location.reload();
      },
      error => {
        this.message = error.error.message == null ? error.error : error.error.message;
        this.alertService.error(this.message);
      });
  }

  DeleteUser(id: number) {
    this.accidentService.deleteById(id).subscribe(data => {
      this.alertService.success('Record has been deleted');
      window.location.reload();
    }, error => {
      this.alertService.error('something was wrong');
    });
  }
}
