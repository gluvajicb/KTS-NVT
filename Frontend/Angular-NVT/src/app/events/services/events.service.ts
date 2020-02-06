import { Injectable } from '@angular/core';
import { Event } from '../model/event';
import { EventDay } from '../model/event-day';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class EventsService {

  private baseUrl = 'http://localhost:8080/events';

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private locationList: Location[] = [];

  constructor(private http: HttpClient) {
  }

   getAll(page: number, size: number): Observable<any> {

    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response',
      params: new HttpParams()
    };

    return this.http.get(this.baseUrl, queryParams);
   }

  getUpcoming(): Observable<any> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.get(`${this.baseUrl}/upcoming`, queryParams);
  }

   getTotalSize(): number {
     return this.locationList.length;
   }

   getOne(id: number): Observable<any> {

    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response'
    };

    return this.http.get(`${this.baseUrl}/${id}`, queryParams);
   }

   add(newEvent: Event): Observable<any> {

    return this.http.post(this.baseUrl, newEvent, {headers: this.headers, responseType: 'text'});
   }


    deactivate(id: number): Observable<any> {
     console.log('delete');
     return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
   }

}
