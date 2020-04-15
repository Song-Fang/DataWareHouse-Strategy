import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {AlertService} from '../Service/alert.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})


export class HomepageComponent implements OnInit {
  chart: object;
  colorrange: object;
  data: Data[];
  dataSource: any;
  loading: boolean;
  tempapi: string;
  api: string;
  private message: any;

  constructor(private http: HttpClient,
              private alertService: AlertService) {

    this.tempapi = sessionStorage.getItem('api');
    if (this.tempapi) {
      this.api = this.tempapi;
    } else {
      this.api = environment.PostgresApi;
    }

    this.loading = true;

    this.chart = {
      caption: 'States with Accident Numbers',
      subcaption: 'Since the year 2016 Feb',
      numbersuffix: '',
      theme: 'fusion',
      tooltipbgcolor: '#FFFFFF',
      tooltipbordercolor: '#CCCCCC',
      showentitytooltip: '1',
      showentityhovereffect: '1',
      showlabels: '1',
      showtooltip: '1',
      entitytooltext:
      // tslint:disable-next-line:max-line-length
        '<div style=\'font-size:14px; text-align:center; padding: 2px 4px 2px 4px; color:black;\'>$lName</div><div style=\'font-size:12px; color:black;\'>Numbers: <b>$value</b></div>',
      entityfillhovercolor: '#FFF9C4',
      nullentitycolor: '#ECE9D3'
    };

    this.colorrange = {
      minvalue: '40',
      // code: '#EAD95F',
      code: '#FFCCCC',
      gradient: '1',
      color: [
        {
          maxvalue: '1000',
          code: '#FF9999'
        },
        {
          maxvalue: '5000',
          code: '#FF6666'
        }, {
          maxvalue: '10000',
          code: '#FF3333'
        }, {
          maxvalue: '50000',
          code: '#FF9933'
        },
        {
          maxvalue: '100000',
          code: '#FF8000'
        },
        {
          maxvalue: '200000',
          code: '#FF0000'
        },
        {
          maxvalue: '300000',
          code: '#CC6600'
        },
        {
          maxvalue: '400000',
          code: '#CC0000'
        },
        {
          maxvalue: '500000',
          code: '#990000'
        },
        {
          maxvalue: '600000',
          code: '#660000'
        },
        {
          maxvalue: '700000',
          code: '#330000'
        }
      ]
    };

    this.dataSource = {
      chart: this.chart,
      colorrange: this.colorrange,
      data: []
    };
  }


  ngOnInit() {
    this.loading = true;

    this.http.get<Data[]>(this.api + `/accident/numbersByState`).subscribe(data => {
      this.dataSource.data = data;
      this.loading = false;
    }, error => {
      this.message = error.error.message == null ? error.error : error.error.message;
      this.alertService.error(this.message);
      this.loading = false;
    });
  }

}

class Data {
  int: string;
  value: string;
}

class DataSource {
  chart: object;
  colorrange: object;
  data: Data[];
}
