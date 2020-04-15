import {Component, NgZone} from '@angular/core';

@Component({
  selector: 'app-virtualization-page\'',
  templateUrl: './virtualization-page.component.html',
  styleUrls: ['./virtualization-page.component.css']
})
export class VirtualizationPageComponent {
  dataSource: any;
  chartObj: any;
  chart: any = 'column2d';

  chartObj2: any;
  // chartObj3: any;
  // chartObj4: any;
  chart2: any = 'column2d';
  // chart2: any = 'column2d';
  // chart3: any = 'column2d';
  // chart4: any = 'column2d';


  constructor(private zone: NgZone) {
    const dataSource = {
      'chart': {
        'caption': 'Countries With Most Oil Reserves [2017-18]',
        'subCaption': 'In MMbbl = One Million barrels',
        'xAxisName': 'Country',
        'yAxisName': 'Reserves (MMbbl)',
        'numberSuffix': 'K',
        'theme': 'fusion',
      },
      'data': [{
        'label': 'Venezuela',
        'value': '290'
      }, {
        'label': 'Saudi',
        'value': '260'
      }, {
        'label': 'Canada',
        'value': '180'
      }, {
        'label': 'Iran',
        'value': '140'
      }, {
        'label': 'Russia',
        'value': '115'
      }, {
        'label': 'UAE',
        'value': '100'
      }, {
        'label': 'US',
        'value': '30'
      }, {
        'label': 'China',
        'value': '30'
      }]
    };

    this.dataSource = dataSource;
    // this.dataSource2 = dataSource;
    // this.dataSource3 = dataSource;
    // this.dataSource4 = dataSource;
  }

  initialized1($event) {
    this.chartObj = $event.chart; // saving chart instance
  }

  initialized2($event) {
    this.chartObj = $event.chart2; // saving chart instance
  }

  //
  // initialized3($event) {
  //   this.chartObj = $event.chart3; // saving chart instance
  // }
  //
  // initialized4($event) {
  //   this.chartObj = $event.chart4; // saving chart instance
  // }


  onSelectionChange1(chart) {
    this.chart = chart;
    this.chartObj.chartType(chart); // Changing chart type using chart instance
  }

  onSelectionChange2(chart) {
    this.chart2 = chart;
    this.chartObj.chartType(this.chart2); // Changing chart type using chart instance
  }

  //
  // onSelectionChange3(chart3) {
  //   this.chart3 = chart3;
  //   this.chartObj.chartType(this.chart3); // Changing chart type using chart instance
  // }
  //
  // onSelectionChange4(chart4) {
  //   this.chart4 = chart4;
  //   this.chartObj.chartType(this.chart4); // Changing chart type using chart instance
  // }
}
