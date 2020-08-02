import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../authentication/authentication.service";

@Component({
  selector: 'app-root-page',
  templateUrl: './root-page.component.html',
  styleUrls: ['./root-page.component.less']
})
export class RootPageComponent implements OnInit {

  constructor(private auth: AuthenticationService) { }

  ngOnInit(): void {
  }

  logout() {
    this.auth.logout()
  }
}
