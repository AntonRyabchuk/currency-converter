import { NgModule } from '@angular/core';
import {AUTH_SERVICE, AuthModule, PROTECTED_FALLBACK_PAGE_URI, PUBLIC_FALLBACK_PAGE_URI} from 'ngx-auth';
import {AuthenticationService} from './authentication.service';

export function factory(authenticationService: AuthenticationService) {
  return authenticationService;
}

@NgModule({
  declarations: [

  ],
  imports: [
    AuthModule
  ],
  exports: [

  ],
  providers: [
    {provide: PROTECTED_FALLBACK_PAGE_URI, useValue: '/protected'},
    {provide: PUBLIC_FALLBACK_PAGE_URI, useValue: '/'},
    {
      provide: AUTH_SERVICE,
      deps: [AuthenticationService],
      useFactory: factory
    }
  ]
})
export class AuthenticationModule { }
