import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../Service/authentication.service';
import {AlertService} from '../Service/alert.service';


@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit {
  registerForm: FormGroup;
  loading: boolean;
  submitted: boolean;
  returnUrl: string;
  message: any;
  map = new Map<string, string> ();
  list: string[];

  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private alertService: AlertService,
              private authentication: AuthenticationService) {
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
    return this.registerForm.controls;
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      reenter: ['', Validators.required],
      email: ['', [Validators.email, Validators.required]],
      phonenumber: ['', [Validators.required, Validators.pattern('^(\\(\\d{3}\\)|^\\d{3}[.-]?)?\\d{3}[.-]?\\d{4}$')]],
      city: ['', Validators.required],
      state: ['', Validators.required]


    });
  }

  onSubmit() {
    this.submitted = true;

    if (this.registerForm.invalid) {
      return;
    }

    this.loading = true;
    this.authentication.register(this.f.username.value, this.f.password.value,
      this.f.email.value, this.f.phonenumber.value, this.f.city.value, this.f.state.value)
      .subscribe(data => {
        this.router.navigate(['/']);
      }, error => {
        this.message = error.error.message == null ? error.error : error.error.message;
        this.alertService.error(this.message);
        this.loading = false;
      });

  }
}
