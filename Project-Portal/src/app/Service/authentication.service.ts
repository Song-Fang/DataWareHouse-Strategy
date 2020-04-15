import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {Users} from '../Models/users';
import {environment} from '../../environments/environment';
import {Router} from '@angular/router';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private currentUserSubject: BehaviorSubject<Users>;
  private currentUser: Observable<Users>;
  private tempapi: string;
  private api: string;

  constructor(private http: HttpClient,
              private router: Router) {
    this.tempapi = sessionStorage.getItem('api');
    if (this.tempapi) {
      this.api = this.tempapi;
    } else {
      this.api = environment.PostgresApi;
    }
    this.currentUserSubject = new BehaviorSubject<Users>(JSON.parse(sessionStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): Users {
    return this.currentUserSubject.value;
  }

  login(username: string, password: string, isAdminLogin: boolean) {
    if (!isAdminLogin) {
      return this.http.post<any>(this.api + `/user/login`, {username, password})
        .pipe(map(user => {
          sessionStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
          return user;
        }));
    } else {
      return this.http.post<any>(this.api + `/admin/login`, {username, password})
        .pipe(map(admin => {
          sessionStorage.setItem('currentAdmin', JSON.stringify(admin));
          this.currentUserSubject.next(admin);
          return admin;
        }));
    }

  }

  register(username: string, password: string, email: string, phonenumber: string, city: string, state: string) {
    return this.http.post<any>(this.api + `/user/signup`, {username, password, email, phonenumber, city, state})
      .pipe(map(user => {
        sessionStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserSubject.next(user);
        return user;
      }));
  }

  restPassword(username: string, password: string) {
    return this.http.put<any>( this.api + `/user/updatePassword/${username}`, {password})
      .pipe(map(user => {
        sessionStorage.removeItem('currentUserName');
        sessionStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserSubject.next(user);
        return user;
      }));
  }


  Userlogout() {
    sessionStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.router.navigate(['user/login']);
  }

  Adminlogout() {
    sessionStorage.removeItem('currentAdmin');
    this.currentUserSubject.next(null);
    this.router.navigate(['admin/login']);
  }
}
