import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RootPageComponent } from './root-page/root-page.component';
import {ProtectedRoutingModule} from "./protected-routing.module";



@NgModule({
  declarations: [RootPageComponent],
  imports: [
    CommonModule,
    ProtectedRoutingModule
  ]
})
export class ProtectedModule { }
