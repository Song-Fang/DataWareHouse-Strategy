import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../Service/authentication.service';
import {Router} from '@angular/router';
import {AlertService} from '../Service/alert.service';
import {AccidentService} from '../Service/accident.service';
import {UserService} from '../Service/user.service';

@Component({
  selector: 'app-user-self-report-page',
  templateUrl: './user-self-report-page.component.html',
  styleUrls: ['./user-self-report-page.component.css']
})
export class UserSelfReportPageComponent implements OnInit {
  reportForm: FormGroup;
  loading: boolean;
  submitted: boolean;
  returnUrl: string;
  message: any;
  map = new Map<string, string>();
  list: string[];
  date: Date;
  dateValue: string;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private alertService: AlertService,
              private authenticationService: AuthenticationService,
              private accidentService: AccidentService,
              private userService: UserService) {
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

  get f() {
    return this.reportForm.controls;

  }

  ngOnInit() {
    this.reportForm = this.formBuilder.group({
      street: ['', Validators.required],
      city: ['', Validators.required],
      state: ['', Validators.required],
      description: [''],
      starttime: ['', Validators.required],
      weathercondition: [''],
      visibility: [''],
      humidity: ['']
    });
  }

  onSubmit() {

    this.submitted = true;
    if (this.reportForm.invalid) {
      return;
    }
    console.log("?????");
      this.accidentService.save(this.userService.currentUserValue.user.username, this.f.street.value, this.f.city.value, this.f.state.value, this.f.description.value,
        this.f.starttime.value, this.f.weathercondition.value, this.f.visibility.value, this.f.humidity.value).subscribe(data => {
        this.alertService.success('Report successfully !');
        window.location.reload();
      }, error => {
        this.alertService.error('something was wrong');
      });
  }
}
