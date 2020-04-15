import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from './Service/authentication.service';
import {environment} from '../environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'project-portal';
  isMongo: boolean;
  constructor(public router: Router,
              private authenticationService: AuthenticationService) {
    this.isMongo = sessionStorage.getItem('api') === 'http://localhost:8080/MongoApi';
  }


  isLoginPage() {
    return this.router.url === '/user/login' || this.router.url === '/admin/dashboard' || this.router.url === '/admin/login'
      || this.router.url === '/admin/allusers' || this.router.url === '/admin/last100records' || this.router.url === '/user/myaccount' ||
      this.router.url === '/user/selfReport' || this.router.url === '/user/reporthistory';
  }

  changePostgreApi() {
    sessionStorage.removeItem('currentUser');
    sessionStorage.removeItem('currentAdmin');
    sessionStorage.removeItem('currentUserName');
    sessionStorage.setItem('api', 'http://localhost:8080/PostgresApi');
    // environment.PostgresApi = 'http://localhost:8080/PostgresApi';
  }

  changeMongoDBApi() {
    sessionStorage.removeItem('currentUser');
    sessionStorage.removeItem('currentAdmin');
    sessionStorage.removeItem('currentUserName');
    sessionStorage.setItem('api', 'http://localhost:8080/MongoApi');
    // environment.PostgresApi = 'http://localhost:8080/PostgresApi';
  }


  changeNeo4jApi() {
    sessionStorage.removeItem('currentUser');
    sessionStorage.removeItem('currentAdmin');
    sessionStorage.removeItem('currentUserName');
    sessionStorage.setItem('api', 'http://localhost:8080/Neo4jApi');
    // environment.PostgresApi = 'http://localhost:8080/Neo4jApi';
  }
}
