import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ProtectedGuard, PublicGuard} from "ngx-auth";
import {HistoryComponent} from "./protected/history/history.component";


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
    path: 'history',
    canActivate: [ ProtectedGuard ],
    component: HistoryComponent
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
