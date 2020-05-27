import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EditEnvironmentComponent } from './edit-environment/edit-environment.component';


const routes: Routes = [
  {path: 'edit-environment', component: EditEnvironmentComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
