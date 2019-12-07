import { Component, OnInit, Input } from '@angular/core';
import { FormArray } from '@angular/forms';

@Component({
  selector: 'app-pizza-image',
  templateUrl: './pizza-image.component.html',
  styleUrls: ['./pizza-image.component.scss']
})
export class PizzaImageComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  @Input() pizzas: FormArray;
  @Input() toppingNameFn: (id) => string;
  @Input() selectedPizza: number;

}
