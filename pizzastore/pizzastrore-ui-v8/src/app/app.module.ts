import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { CoreModule } from './@core/core.module';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './material/material.module';

import { DiniOrderComponent } from './pizza-orders/dini-order/dini-order.component';
import { OrderListComponent } from './pizza-orders/order-list/order-list.component';
import { CustomerDetailsComponent } from './pizza-orders/dini-order/customer-details/customer-details.component';
import { PizzaImageComponent } from './pizza-orders/dini-order/pizza-image/pizza-image.component';



@NgModule({
  declarations: [
    AppComponent,
    DiniOrderComponent,
    OrderListComponent,
    CustomerDetailsComponent,
    PizzaImageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    CoreModule,
    ReactiveFormsModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
