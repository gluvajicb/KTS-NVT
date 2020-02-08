import { Injectable } from '@angular/core';
import { Sector } from '../model/sector';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SectorService {

  private baseUrl = 'http://localhost:8080/locations';
  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) {

  }

  add(newSector: Sector, locationId: number): Observable<any> {
    console.log(newSector);
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response'
    };
    return this.http.post(`${this.baseUrl}/${locationId}/sectors`, newSector, queryParams);
   }

   delete(id: number): Observable<any> {
    console.log('delete');
    return this.http.delete(`${this.baseUrl}/sector/${id}`, { responseType: 'text' });
  }
}
