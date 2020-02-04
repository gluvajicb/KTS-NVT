import { Injectable } from '@angular/core';
import { Ticket } from '../model/ticket';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class TicketsService {

  private baseUrl = 'http://localhost:8080/tickets';

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private ticketList: Ticket[] = [];

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

   getTicketsForEvent(id: number): Observable<any> {

    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response'
    };

    return this.http.get(`${this.baseUrl}/${id}`, queryParams);
   }

  getTakenSeatsForSector(id: number): Observable<any> {

    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response'
    };

    return this.http.get(`${this.baseUrl}/takenSeats/${id}`, queryParams);
  }
}
