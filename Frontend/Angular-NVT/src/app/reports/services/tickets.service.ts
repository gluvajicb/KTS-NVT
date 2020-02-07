import { Injectable } from '@angular/core';
import { Ticket } from '../model/ticket';
import { HttpClientModule, HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Event} from '../../events/model/event';
import {SeatsTicketDTO} from '../model/seats-ticket';
import {StandTicketDTO} from '../model/stand-ticker';


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

  getUsersTickets(): Observable<any> {
    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response',
      params: new HttpParams()
    };

    return this.http.get(`${this.baseUrl}/user`, queryParams);
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

  getTakenSeatsForEvent(id: number): Observable<any> {

    let queryParams = {};

    queryParams = {
      headers: this.headers,
      observe: 'response'
    };

    return this.http.get(`${this.baseUrl}/takenSeatsAllDays/${id}`, queryParams);
  }

  addSeatsTicket(ticket: SeatsTicketDTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/add-seats-ticket`, ticket, {headers: this.headers, responseType: 'text'});
  }

  addStandTicket(ticket: StandTicketDTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/add-stand-ticket`, ticket, {headers: this.headers, responseType: 'text'});
  }
}
