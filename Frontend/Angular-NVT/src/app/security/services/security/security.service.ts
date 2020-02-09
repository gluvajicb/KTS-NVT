import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

const AUTH_API = 'http://localhost:8080/security/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class SecurityService {
  constructor(private http: HttpClient) { }

  login(credentials): Observable<any> {
    return this.http.post(AUTH_API + 'login', {
      username: credentials.username,
      password: credentials.password
    }, httpOptions);
  }

  register(user): Observable<any> {
    return this.http.post(AUTH_API + 'register', {
      username: user.username,
      email: user.email,
      password: user.password,
      passwordConfirmation: user.passwordConfirmation,
      name: user.name,
      surname: user.surname,
      phoneNumber: user.phoneNumber
    }, httpOptions);
  }

  verify(email, token): Observable<any> {
    return this.http.get(AUTH_API + 'verify?email=' + encodeURIComponent(email) + '&token=' + encodeURIComponent(token), httpOptions);
  }
}
