import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {HttpClientModule} from '@angular/common/http';
import {MatTableModule} from '@angular/material/table';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatPaginatorModule} from '@angular/material';

import {AppComponent} from './app.component';
import {HomepageComponent} from './home-page/homepage.component';
import {UsMapModule} from 'angular-us-map';
import {LoginPageComponent} from './login-page/login-page.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AlertComponent} from './alert/alert.component';
import {RegisterPageComponent} from './register-page/register-page.component';
import {MyaccountPageComponent} from './myaccount-page/myaccount-page.component';
import {ForgetpasswordPageComponent} from './forgetpassword-page/forgetpassword-page.component';
import {FusionChartsModule} from 'angular-fusioncharts';
import {VirtualizationPageComponent} from './virtualization-page/virtualization-page.component';
import {AgmCoreModule} from '@agm/core';
import {DataTablesModule} from 'angular-datatables';
import { MarkerManager } from '@agm/core';
// Import FusionCharts library and chart modules
import * as FusionCharts from 'fusioncharts';
import * as charts from 'fusioncharts/fusioncharts.charts';
import * as FusionTheme from 'fusioncharts/themes/fusioncharts.theme.fusion';
import * as Maps from 'fusioncharts/fusioncharts.maps';
import * as USA from 'fusioncharts/maps/fusioncharts.usa';
import {SearchingPageComponent} from './searching-page/searching-page.component';
import { AdminDashboardPageComponent } from './admin-dashboard-page/admin-dashboard-page.component';
import { AdminLoginComponent } from './admin-login/admin-login.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AdminAllusersPageComponent } from './admin-allusers-page/admin-allusers-page.component';
import { Last100recordsPageComponent } from './admin-last100records-page/last100records-page.component';
import { Chart1Component } from './chart1/chart1.component';
import { Chart2Component } from './chart2/chart2.component';
import { Chart3Component } from './chart3/chart3.component';
import { Chart4Component } from './chart4/chart4.component';
import { UserSelfReportPageComponent } from './user-self-report-page/user-self-report-page.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material';
import { UserReporthistoryPageComponent } from './user-reporthistory-page/user-reporthistory-page.component';


// Pass the fusioncharts library and chart modules
FusionChartsModule.fcRoot(FusionCharts, charts, Maps, FusionTheme, USA);

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    LoginPageComponent,
    AlertComponent,
    RegisterPageComponent,
    MyaccountPageComponent,
    ForgetpasswordPageComponent,
    VirtualizationPageComponent,
    SearchingPageComponent,
    AdminDashboardPageComponent,
    AdminLoginComponent,
    AdminAllusersPageComponent,
    Last100recordsPageComponent,
    Chart1Component,
    Chart2Component,
    Chart3Component,
    Chart4Component,
    UserSelfReportPageComponent,
    UserReporthistoryPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    UsMapModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatTableModule,
    MatInputModule,
    DataTablesModule,
    MatFormFieldModule,
    MatPaginatorModule,
    FusionChartsModule,
    MatNativeDateModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyCunSDzUB8irdZU5KLBLsY7a87Iulf_br8',
      libraries: ['places', 'geometry']
    }),
    BrowserAnimationsModule,
    FormsModule,
    MatDatepickerModule
  ],
  providers: [MarkerManager],
  bootstrap: [AppComponent]
})
export class AppModule {
}

