import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, Validators, FormGroup, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { Router } from '@angular/router';
import { v4 as uuid } from 'uuid';

import { ProductService } from '../new-order/product.service';
import { AuthService } from '../../@core/auth.service';
import { OrderListService } from '../order-list/order-list.service';

import { Product } from '../../@shared/product';
import { Pizza, PizzaOrder } from '../new-order/pizza-order';
import { Customer } from '../../@shared/customer';
import { ErrorStateMatcher } from '@angular/material/core';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}


@Component({
  selector: 'app-dini-order',
  templateUrl: './dini-order.component.html',
  styleUrls: ['./dini-order.component.scss']
})
export class DiniOrderComponent implements OnInit {


  total: number;
  selectedPizza = 0;
  pizzaCreating = true;
  pizzaBases: Product[];
  toppings: Product[];
  progPizza: Pizza[];
  submitted = false;


  Pizzas: Pizza[];

  pizzaOrderForm = this.fb.group({
    pizzas: this.fb.array([
      // this.createPizzasFormGroup()
      this.fb.group({
        base: [],
        toppings: [[]]
      })

    ]),
    customer: this.fb.group({  // TODO Add validations for customer details
      name: ['', Validators.required],
      email: ['', [Validators.email, Validators.required]],
      phone: ['', Validators.required],
      address: ['', Validators.min(4)]
    })

  });
  matcher = new MyErrorStateMatcher();

  step: number = 0;
  setStep(index: number) {
    this.step = index;
  }
  checkStep(index: number): boolean {
    if (this.step === index) {
      return true;
    } else {
      return false;
    }
  }


  constructor(private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private productService: ProductService,
    private orderService: OrderListService) { }

  ngOnInit(): void {
    const bases$ = this.productService.getProducts('PIZZA_BASE');
    const toppings$ = this.productService.getProducts('PIZZA_TOPPING');

    forkJoin(bases$, toppings$).subscribe(([bases, toppings]) => {
      this.pizzaBases = bases;
      this.toppings = toppings;
      console.log(this.pizzaBases);
      console.log(toppings);
      // this.calculateTotal(this.getPizzasFormArray().value);
      // this.getPizzasFormArray().valueChanges.subscribe(value => this.calculateTotal(value));
    }, error => {
      if (error.status === 403) {
        this.authService.logout();
        this.router.navigate(['/login']);
      }
    });
  }



  createPizzasFormGroup() {
    return this.fb.group({
      base: [],
      toppings: [[]]
    });
  }

  addPizzaControl() {
    const controls = this.getPizzasFormArray();
    controls.push(this.createPizzasFormGroup());
  }

  getPizzasFormArray(): FormArray {
    const pizzasKey = 'pizzas';
    return this.pizzaOrderForm.get(pizzasKey) as FormArray;
  }

  getPizzasControls() {
    const pizzas = this.getPizzasFormArray();
    return pizzas.controls;
  }
  pizzasControls = this.getPizzasControls();

  getCustomerFormGroup() {
    const customerKey = 'customer';
    return this.pizzaOrderForm.get(customerKey) as FormGroup;
  }
  customerGroup = this.getCustomerFormGroup();

  getCustomerControls() {
    const customer = this.getCustomerFormGroup();
    this.customerGroup.controls;
    return customer.controls;
  }
  get customerControl() {
    return this.pizzaOrderForm.get('customer');
  }
  customerControls = this.getCustomerControls();

  addPizza() {
    this.addPizzaControl();
    this.selectedPizza = this.pizzasControls.length - 1;
    this.setStep(this.pizzasControls.length - 1);
    // this.selectPizza(this.selectedPizza);
  }


  private createPizzas(): Pizza[] {
    const pizzas = this.getPizzasFormArray().value;

    const orderPizzas = [];
    for (const p of pizzas) {
      const pizza = new Pizza();
      pizza.base = p.base;
      pizza.toppings = p.toppings;
      orderPizzas.push(pizza);
    }
    return orderPizzas;
  }

  pizzaCreatingTest() {
    this.pizzaCreating = false;
    this.progPizza = this.createPizzas();
    this.calculateTotal(this.progPizza);
  }

  private createCustomer(): Customer {
    const customerControl = this.pizzaOrderForm.get('customer');

    const customer = new Customer();
    customer.name = customerControl.get('name').value;
    customer.email = customerControl.get('email').value;
    customer.phone = customerControl.get('phone').value;
    customer.address = customerControl.get('address').value;

    return customer;
  }

  submitOrder() {
    this.submitted = true;
    if (this.pizzaOrderForm.invalid) {
      return;
    }
    const order = new PizzaOrder();
    order.customer = this.createCustomer();
    order.pizzas = this.createPizzas();
    console.log(this.createPizzas());
    this.orderService.placeOrder(order).subscribe(() => {
      this.router.navigate(['/orders']);
    });
  }

  ////////////////////////////////////////////////////////////////

  getBaseLabel = id => {
    const base = this.pizzaBases.find(b => b.id === id);
    return base ? base.name : '';
  };

  getToppingLabel = id => {
    const topping = this.toppings.find(t => t.id === id);
    return topping ? topping.name : '';
  };

  getBasePrice = id => {
    const base = this.pizzaBases.find(b => b.id === id);
    return base ? base.price : null;
  };

  getToppingPrice = id => {
    const topping = this.toppings.find(t => t.id === id);
    return topping ? topping.price : null;
  };

  private calculateTotal(pizzas) {
    let price: number = 0;
    for (const pizza of pizzas) {
      price = price + this.getBasePrice(pizza.base);
      for (const topping of pizza.toppings) {
        price = price + this.getToppingPrice(topping);
      }
    }
    this.total = price;
  }



}
