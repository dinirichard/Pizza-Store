import { NgModule } from '@angular/core';

import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import { AuthGuardService } from './auth-guard.service';
import { LogInComponent } from './log-in/log-in.component';
import { OrderListComponent } from '../pizza-orders/order-list/order-list.component';
import { DiniOrderComponent } from '../pizza-orders/dini-order/dini-order.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LogInComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'orders', component: OrderListComponent, canActivate: [AuthGuardService] },
  { path: 'dini', component: DiniOrderComponent, canActivate: [AuthGuardService] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class CoreRoutingModule { }
