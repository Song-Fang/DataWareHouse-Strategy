import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {AlertService} from '../Service/alert.service';

@Component({
  selector: 'app-chart1',
  templateUrl: './chart1.component.html',
  styleUrls: ['./chart1.component.css']
})
export class Chart1Component implements OnInit {
  dataSource: any;
  chartObj: any;
  chart: any = 'column2d1';
  dummyDataSource: any;
  loading: boolean;
  message: any;
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
      chart: {
        caption: 'Accidents with Different Visibility(mi)',
        subCaption: 'All records are since 2016 Feb',
        xAxisName: 'Visibility',
        yAxisName: 'Numbers',
        numberSuffix: '',
        theme: 'fusion',
      },
      data: []
    };
    this.dataSource = this.dummyDataSource;

  }

  ngOnInit() {
    this.loading = true;
    this.http.get(this.api + `/accident/numbersByVisibility`).subscribe(data => {
      this.dummyDataSource.data = data;
      this.dataSource = this.dummyDataSource;
      this.loading = false;
    }, error => {
      this.message = error.error.message == null ? error.error : error.error.message;
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
