import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Accidents} from '../Models/accidents';
import {UserService} from './user.service';


@Injectable({
  providedIn: 'root'
})
export class AccidentService {
  private tempapi: string;
  private api: string;

  constructor(private http: HttpClient,
              private userService: UserService) {
    this.tempapi = sessionStorage.getItem('api');
    if (this.tempapi) {
      this.api = this.tempapi;
    } else {
      this.api = environment.PostgresApi;
    }
  }

  getRoadInfo(state: string, city: string, road: string) {
    return this.http.get<Marker[]>(this.api + `/accident/accidentsByRoad/${state}/${city.trim()}/${road}`);
  }

  updateAccident(id: number, state: string, city: string, street: string, zipcode: string, latitude: string, longitude: string, visibility: number, humidity: number) {
    return this.http.put<Accidents>(this.api + `/admin/${id}`, {
      state,
      city,
      street,
      zipcode,
      latitude,
      longitude,
      visibility,
      humidity
    }, {
      headers: {
        'Access-Control-Allow-Origin': '*',
        Authorization: 'Bearer ' + this.userService.currentAdminValue.token
      }
    });
  }

  deleteById(id: number) {
    return this.http.delete(this.api + `/admin/report/${id}`, {
      headers: {
        Authorization: 'Bearer ' + this.userService.currentAdminValue.token
      }
    });
  }

  save(username: string, street: string, city: string, state: string, description: string, startTime: string, weathercondition: string, visibility: number, humidity: number) {
    return this.http.post(this.api + `/user/self-report/${username}`, {
      street,
      city,
      state,
      description,
      startTime,
      weathercondition,
      visibility,
      humidity
    }, {
      headers: {
        Authorization: 'Bearer ' + this.userService.currentUserValue.token
      }
    });
  }

  getUserReport(username: string) {
    return this.http.get<Accidents[]>(this.api + `/user/reports/${username}`, {
      headers: {
        Authorization: 'Bearer ' + this.userService.currentUserValue.token
      }
    });
  }

  UserdeleteById(id: number) {
    return this.http.delete(this.api + `/user/report/${id}`, {
      headers: {
        Authorization: 'Bearer ' + this.userService.currentUserValue.token
      }
    });
  }
}

interface Marker {
  latitude: number;
  longitude: number;
}
