import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {Users} from '../Models/users';
import {User} from '../Models/users';
import {Router} from '@angular/router';
import {environment} from '../../environments/environment';
import {map} from 'rxjs/operators';
import {Accidents} from '../Models/accidents';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private currentUserNameSubject: BehaviorSubject<string>;
  private currentUserName: Observable<string>;
  private currentUserSubject: BehaviorSubject<Users>;
  private currentUser: Observable<Users>;
  private currentAdminSubject: BehaviorSubject<Users>;
  private currentAdmin: Observable<Users>;
  private api: string;
  private tempapi: string;


  constructor(private http: HttpClient,
              private router: Router) {
    this.currentUserNameSubject = new BehaviorSubject<string>(JSON.parse(sessionStorage.getItem('currentUserName')));
    this.currentUserName = this.currentUserNameSubject.asObservable();
    this.currentUserSubject = new BehaviorSubject<Users>(JSON.parse(sessionStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
    this.currentAdminSubject = new BehaviorSubject<Users>(JSON.parse(sessionStorage.getItem('currentAdmin')));
    this.currentAdmin = this.currentAdminSubject.asObservable();

    this.tempapi = sessionStorage.getItem('api');
    if (this.tempapi) {
      this.api = this.tempapi;
    } else {
      this.api = environment.PostgresApi;
    }
  }

  public get currentUserNameValue(): string {
    return this.currentUserNameSubject.value;
  }

  public get currentUserValue(): Users {
    return this.currentUserSubject.value;
  }

  public get currentAdminValue(): Users {
    return this.currentAdminSubject.value;
  }


  checkUserInfo(username: string, email: string, phonenumber: string, city: string, state: string) {

    return this.http.post<any>(this.api + `/user/infoCheck`, {username, email, phonenumber, city, state})
      .pipe(map(user => {
        sessionStorage.setItem('currentUserName', JSON.stringify(user.username));
        this.currentUserNameSubject.next(user.username);
        return user;
      }));

  }

  getAllUser() {
    return this.http.get<Users[]>(this.api + `/admin/allUsers`, {
      headers: {
        Authorization: 'Bearer ' + this.currentAdminValue.token
      }
    });
  }

  changeRole(username: string) {
    return this.http.put<Users>(this.api + `/admin/changeRole/${username}`, {}, {
      headers: {
        'Access-Control-Allow-Origin': '*',
        Authorization: 'Bearer ' + this.currentAdminValue.token
      }
    });
  }

  updateUser(username: string, phonenumber: string, email: string, city: string, state: string) {
    return this.http.put( this.api + `/admin/updateUser/${username}`, {phonenumber, email, city, state}, {
      headers: {
        'Access-Control-Allow-Origin': '*',
        Authorization: 'Bearer ' + this.currentAdminValue.token
      }
    });
  }

  deleteUser(username: string) {
    return this.http.delete(this.api + `/admin/user/${username}`, {
      headers: {
        'Access-Control-Allow-Origin': '*',
        Authorization: 'Bearer ' + this.currentAdminValue.token
      }
    });
  }

  getRecords() {
    return this.http.get<Accidents[]>(this.api + `/admin/allAccidents`, {
      headers: {
        Authorization: 'Bearer ' + this.currentAdminValue.token
      }
    });
  }

  updateSelf(username: string, phonenumber: string, email: string, city: string, state: string) {
    return this.http.post<User>(this.api + `/user/updateAllInfo/${username}`, {phonenumber, email, city, state}, {
      headers: {
        'Access-Control-Allow-Origin': '*',
        Authorization: 'Bearer ' + this.currentUserValue.token
      }
    });

  }

  getUser() {
    return this.http.get<User>(this.api + `/user/${this.currentUserValue.user.username}`, {
      headers: {
        Authorization: 'Bearer ' + this.currentUserValue.token
      }
    });
  }
}
