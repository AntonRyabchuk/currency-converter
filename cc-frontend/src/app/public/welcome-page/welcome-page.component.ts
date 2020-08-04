import {Component} from '@angular/core';
import {AuthenticationService} from "../../authentication/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.css']
})
export class WelcomePageComponent {

  password: string;
  username: string;

  constructor(private auth: AuthenticationService,
              private router: Router
              ) {  }

  onClick() {
    console.log(this.username);
    console.log(this.password);
    this.auth.login({username: this.username, password: this.password})
      .subscribe(value => this.router.navigateByUrl("/protected"), error => alert("error"))
  }
}
