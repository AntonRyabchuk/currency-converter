import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ProtectedGuard, PublicGuard} from "ngx-auth";


const routes: Routes = [
  {
    path: 'welcome',
    canActivate: [ PublicGuard ],
    loadChildren: () => import('./public/public.module').then(m => m.PublicModule)
  },
  {
    path: 'protected',
    canActivate: [ ProtectedGuard ],
    loadChildren: () => import('./protected/protected.module').then(m => m.ProtectedModule)
  },
  {
    path: '',
    redirectTo: 'welcome',
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: 'protected',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
