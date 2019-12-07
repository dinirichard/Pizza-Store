import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { AlertService } from '../../@shared/alert/alert.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  returnUrl: string;
  submitted = false;
  errorMessage = "";
  errorCheck = false;

  constructor(private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private alertService: AlertService) { }

  get f() { return this.registerForm.controls; }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.returnUrl = this.route.snapshot.queryParams.returnUrl;
  }

  onSubmit() {
    // TODO Register user ...DONE
    this.submitted = true;
    if (this.registerForm.invalid) {
      return;
    }

    this.authService.register(this.f.username.value, this.f.password.value)
      .subscribe(
        response => {
          this.authService.login(
            this.f.username.value,
            this.f.password.value)
            .pipe(first())
            .subscribe(
              () => {
                const returnUrl = this.returnUrl ? this.returnUrl : this.authService.isAdmin() ? '/orders' : '/new-order';
                this.router.navigate([returnUrl]);
                this.alertService.success("Thanks for joining us " + this.f.username.value);
              },
              error => {

                this.alertService.error(error.error);

              });


          console.log(response);
          console.log(response.status);
        },
        error => {

          this.alertService.error(error.error);
          this.errorCheck = true;
        }
      )



  }

}

