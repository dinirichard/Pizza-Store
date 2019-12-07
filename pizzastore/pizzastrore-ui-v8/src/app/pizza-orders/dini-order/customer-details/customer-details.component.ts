import { Component, OnInit, Input } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MyErrorStateMatcher } from '../dini-order.component';

@Component({
  selector: 'app-customer-details',
  templateUrl: './customer-details.component.html',
  styleUrls: ['./customer-details.component.scss']
})
export class CustomerDetailsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  @Input() customerControl: FormGroup;
  @Input() matcher: MyErrorStateMatcher;
  @Input() submitted: Boolean;
  // customerControls = this.customerControl.controls;

  // getEmail() {
  //   const temp = <FormGroup>this.customerControl.controls.customer;
  //   // return temp.controls.email;
  //   return temp;
  // }

}
