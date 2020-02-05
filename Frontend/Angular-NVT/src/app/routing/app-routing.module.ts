import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LocationContainerComponent } from '../locations/location-container/location-container.component';
import { LocationDetailsComponent } from '../locations/location-details/location-details.component';
import { AddLocationFormComponent } from '../locations/add-location-form/add-location-form.component';
import { LocationUpdateComponent } from '../locations/location-update/location-update.component';

import { EventContainerComponent } from '../events/event-container/event-container.component';
import { AddEventFormComponent } from '../events/add-event-form/add-event-form.component';
import { EventDetailsComponent } from '../events/event-details/event-details.component';
import { EventReportContainerComponent } from '../reports/event-report-container/event-report-container.component';
import { UserEventDetailsComponent } from '../events/user-event-details/user-event-details.component';
import { UserEventContainerComponent } from '../events/user-event-container/user-event-container.component';
import {LoginComponent} from '../security/login/login.component';
import {RegisterComponent} from '../security/register/register.component';

const routes: Routes = [
  {path: 'locations', component: LocationContainerComponent},
  {path: 'locations/details/:id', component: LocationDetailsComponent},
  {path: 'locations/add', component: AddLocationFormComponent },
  {path: 'locations/update/:id', component: LocationUpdateComponent},
  {path: 'events', component: EventContainerComponent},
  {path: 'events/add', component: AddEventFormComponent},
  {path: 'events/details/:id', component: EventDetailsComponent},
  {path: 'reports', component: EventReportContainerComponent},
  {path: 'user/events/details/:id', component: UserEventDetailsComponent},
  {path: 'user/events', component: UserEventContainerComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
