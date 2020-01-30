import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LocationContainerComponent } from '../locations/location-container/location-container.component';
import { LocationDetailsComponent } from '../locations/location-details/location-details.component';
import { AddLocationFormComponent } from '../locations/add-location-form/add-location-form.component';

import { EventContainerComponent } from '../events/event-container/event-container.component';
import { AddEventFormComponent } from '../events/add-event-form/add-event-form.component';
import { EventDetailsComponent } from '../events/event-details/event-details.component';
import { EventReportContainerComponent } from '../reports/event-report-container/event-report-container.component';


const routes: Routes = [
  {path: 'locations', component: LocationContainerComponent},
  {path: 'locations/details/:id', component: LocationDetailsComponent},
  {path: 'locations/add', component: AddLocationFormComponent },
  {path: 'events', component: EventContainerComponent},
  {path: 'events/add', component: AddEventFormComponent},
  {path: 'events/details/:id', component: EventDetailsComponent},
  {path: 'reports', component: EventReportContainerComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
