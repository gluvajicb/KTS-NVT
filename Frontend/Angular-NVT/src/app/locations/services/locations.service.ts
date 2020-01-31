import { Injectable } from '@angular/core';
import { Location } from '../model/location';
import { Sector } from '../model/sector';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SearchLocation } from '../model/search-location';


@Injectable({
  providedIn: 'root'
})
export class LocationsService {

  private baseUrl = 'http://localhost:8080/locations';

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private locationList: Location[] = [];

  constructor(private http: HttpClient) {
/*
    for (let i = 1; i < 20; i++) {
      const loc = new Location();
      loc.id = i;
      loc.address = 'address ' + i;
      loc.title = 'title' + i;
      this.locationList.push(loc);
    }*/
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

   add(newLocation: Location): Observable<any> {

    return this.http.post(this.baseUrl, newLocation, {headers: this.headers, responseType: 'text'});
   }


    delete(id: number): Observable<any> {
     console.log('delete');
     return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
   }

   search(searchData: SearchLocation) {
     // dodati
   }

}
