import { Product } from '../../@shared/product';
import { Customer } from '../../@shared/customer';

export class OrderSearchResult {
    id: number;
    created: Date;
    customer: Customer;
    pizzas: Pizza[];
    totalPrice: number;
}

export class Pizza {
    base: Product;
    toppings: Product[];
    price: number;
}