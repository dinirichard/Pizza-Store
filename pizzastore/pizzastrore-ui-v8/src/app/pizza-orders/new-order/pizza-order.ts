import { Customer } from '../../@shared/customer';

export class PizzaOrder {
    customer: Customer;
    pizzas: Pizza[];
}

export class Pizza {
    base: number;
    toppings: number[];
}
