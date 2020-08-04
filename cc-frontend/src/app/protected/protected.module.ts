import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RootPageComponent } from './root-page/root-page.component';
import {ProtectedRoutingModule} from "./protected-routing.module";
import { HistoryComponent } from './history/history.component';
import {FormsModule} from "@angular/forms";



@NgModule({
  declarations: [RootPageComponent, HistoryComponent],
  imports: [
    CommonModule,
    ProtectedRoutingModule,
    FormsModule
  ]
})
export class ProtectedModule { }
