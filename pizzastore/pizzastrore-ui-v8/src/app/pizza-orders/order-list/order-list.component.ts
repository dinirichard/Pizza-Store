import { Component, OnInit } from '@angular/core';
import { OrderSearchResult } from './order-search-result';
import { AuthService } from '../../@core/auth.service';
import { Router } from '@angular/router';
import { OrderListService } from './order-list.service';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss']
})
export class OrderListComponent implements OnInit {

  orders: OrderSearchResult[];

  constructor(private orderService: OrderListService,
    private authService: AuthService,
    private router: Router) { }

  ngOnInit() {
    this.orderService.getOrders().subscribe(result => {
      this.orders = result;
    }, error => {
      if (error.status === 403) {
        this.authService.logout();
        this.router.navigate(['/login']);
      }
    });
  }

  cancelOrder(id: number) {
    this.orderService.cancelOrder(id).subscribe(() => {
      const orderIndex = this.orders.findIndex(order => order.id === id);
      this.orders.splice(orderIndex, 1);
    });
  }

  isAdmin() {
    return this.authService.isAdmin();
  }
}
