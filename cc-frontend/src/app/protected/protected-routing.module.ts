import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {RootPageComponent} from "./root-page/root-page.component";

const routes: Routes = [
    {
        path: '',
        component: RootPageComponent,
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ProtectedRoutingModule {

}
