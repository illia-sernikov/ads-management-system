import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../../../service';

@Component({
  selector: 'ams-login-form',
  templateUrl: 'login-form.component.html',
  styleUrls: ['login-form.component.css']
})
export class LoginFormComponent {

  constructor(private authService: AuthService) {
  }

  onSignIn(signinForm: NgForm): void {
    let authRequest = signinForm.value;
    this.authService.signIn(authRequest);
  }

}
