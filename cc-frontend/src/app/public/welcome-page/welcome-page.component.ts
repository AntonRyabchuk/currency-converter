import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../authentication/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.less']
})
export class WelcomePageComponent implements OnInit {

  constructor(private auth: AuthenticationService,
              private router: Router) { }

  ngOnInit(): void {
  }

    onClick() {
      this.auth.login({username: 'root', password: '123'})
          .subscribe(value => this.router.navigateByUrl("/protected"), error => alert("error"))
    }
}
