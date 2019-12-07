import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { PizzaOrder } from '../new-order/pizza-order';
import { OrderSearchResult } from './order-search-result';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class OrderListService {

  private readonly url = '/api/orders';

  constructor(private readonly http: HttpClient, public jwtHelper: JwtHelperService) {
  }

  placeOrder(order: PizzaOrder) {
    const token = localStorage.getItem('access_token');
    const tokenPayload = this.jwtHelper.decodeToken(token);
    let headers: HttpHeaders = new HttpHeaders();
    headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
    headers.append('Authorization', 'Bearer ${token}');
    // headers.append('Authorization', tokenPayload);
    return this.http.post(this.url, order);
  }

  getOrders(): Observable<OrderSearchResult[]> {
    return this.http.get<OrderSearchResult[]>(this.url);
  }

  cancelOrder(id: number) {
    return this.http.delete(this.url + '/' + id);
  }
}
