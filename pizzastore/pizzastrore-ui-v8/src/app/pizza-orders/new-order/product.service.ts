import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../../@shared/product';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private readonly url = '/api/products';

  constructor(private readonly http: HttpClient) {
  }

  getProducts(categoryCode: string): Observable<Product[]> {
    const params = new HttpParams().set('categoryCode', categoryCode);
    return this.http.get<Product[]>(this.url, { params }); //Sends the param e.g 'PIZZA_TOPPING' or 'PIZZA_BASE'
  }
}
