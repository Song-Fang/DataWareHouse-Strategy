import {Component, NgZone, OnInit} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {AlertService} from '../Service/alert.service';

@Component({
  selector: 'app-chart3',
  templateUrl: './chart3.component.html',
  styleUrls: ['./chart3.component.css']
})
export class Chart3Component implements OnInit {
  dataSource: any;
  chartObj: any;
  chart: any = 'column2d3';
  dummyDataSource: any;
  loading: boolean;
  message: any
  private tempapi: string;
  private api: string;

  constructor(private http: HttpClient,
              private alertService: AlertService) {
    this.tempapi = sessionStorage.getItem('api');
    if (this.tempapi) {
      this.api = this.tempapi;
    } else {
      this.api = environment.PostgresApi;
    }


    this.dummyDataSource = {
      'chart': {
        'caption': 'Accidents with Different Weather Condition',
        'subCaption': 'All records are since 2016 Feb',
        'xAxisName': 'Weather Condition',
        'yAxisName': 'Numbers',
        'numberSuffix': 'K',
        'theme': 'fusion',
      },
      'data': []
    };

    this.dataSource = this.dummyDataSource;
  }

  ngOnInit() {
    this.loading = true;
    this.http.get(this.api + `/accident/numbersByWeatherCondition`).subscribe(data => {
      this.dummyDataSource.data = data;
      this.dataSource = this.dummyDataSource;
      this.loading = false;
    }, error => {
      this.message = error.error.message == null ? error.error: error.error.message;
      this.alertService.error(this.message);
      this.loading = false;
    });
  }

  initialized($event) {
    this.chartObj = $event.chart; // saving chart instance
  }

  onSelectionChange(chart) {
    this.chart = chart;
    this.chartObj.chartType(chart.substr(0, chart.length - 1)); // Changing chart type using chart instance
  }

}
