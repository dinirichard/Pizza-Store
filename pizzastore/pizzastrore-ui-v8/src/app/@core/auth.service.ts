import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import * as decode from 'jwt-decode';
import { JsonPipe } from '@angular/common';

@Injectable({ providedIn: 'root' })
export class AuthService {

  constructor(private http: HttpClient,
    public jwtHelper: JwtHelperService) {
  }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem('access_token');
    return !this.jwtHelper.isTokenExpired(token);
  }

  login(username: string, password: string) {
    return this.http.post<any>('/auth/login', { username, password })
      .pipe(map(result => {
        if (result && result.token) {
          localStorage.setItem('access_token', result.token);
        }
      }));
  }

  register(username: string, password: string) {
    // TODO Make a request to /auth/register endpoint
    return this.http.post<any>('/auth/register', { username, password });
  }

  logout() {
    localStorage.removeItem('access_token');
  }

  isAdmin(): boolean {
    const token = localStorage.getItem('access_token');
    const tokenPayload = this.jwtHelper.decodeToken(token);
    console.log(JSON.stringify(tokenPayload));
    return tokenPayload && tokenPayload.roles.includes('ROLE_ADMIN');
  }
}
