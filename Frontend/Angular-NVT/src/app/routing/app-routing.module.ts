import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LocationContainerComponent } from '../locations/location-container/location-container.component';
import { LocationDetailsComponent } from '../locations/location-details/location-details.component';
import { AddLocationFormComponent } from '../locations/add-location-form/add-location-form.component';


const routes: Routes = [
  {path: 'locations', component: LocationContainerComponent},
  {path: 'locations/details/:id', component: LocationDetailsComponent},
  {path: 'locations/add', component: AddLocationFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
