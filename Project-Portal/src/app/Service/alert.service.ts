import {Injectable} from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {Observable, Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  private subject = new Subject<any>();
  private changed = false;

  constructor(private router: Router) {
    router.events.subscribe(events => {
      if (events instanceof NavigationStart) {
        if (this.changed) {
          this.changed = false;
        } else {
          this.subject.next;
        }
      }
    });

  }

  success(message: string, changed = false) {
    this.changed = changed;
    this.subject.next({type: 'success', text: message});
  }

  error(message: string, changed = false) {
    console.log(message);
    this.changed = changed;
    this.subject.next({type: 'error', text: message});
  }

  getMessage(): Observable<any> {
    return this.subject.asObservable();
  }
}
