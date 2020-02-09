import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {LocationContainerComponent} from '../locations/location-container/location-container.component';
import {LocationDetailsComponent} from '../locations/location-details/location-details.component';
import {AddLocationFormComponent} from '../locations/add-location-form/add-location-form.component';
import {LocationUpdateComponent} from '../locations/location-update/location-update.component';

import {EventContainerComponent} from '../events/event-container/event-container.component';
import {AddEventFormComponent} from '../events/add-event-form/add-event-form.component';
import {EventDetailsComponent} from '../events/event-details/event-details.component';
import {EventReportContainerComponent} from '../reports/event-report-container/event-report-container.component';
import {UserEventDetailsComponent} from '../events/user-event-details/user-event-details.component';
import {UserEventContainerComponent} from '../events/user-event-container/user-event-container.component';
import {LoginComponent} from '../security/login/login.component';
import {RegisterComponent} from '../security/register/register.component';
import {HomeComponent} from '../core/home/home.component';
import {ProfileComponent} from '../security/profile/profile.component';
import {AuthGuard} from '../helpers/AuthGuard';
import {UserRole} from '../security/models/UserRole';
import {UserTicketsContainerComponent} from '../reports/user-tickets-container/user-tickets-container.component';
import {PaypalPaymentComponent} from '../payment/paypal-payment/paypal-payment.component';
import {VerificationComponent} from '../security/verification/verification.component';

const routes: Routes = [
  {path: 'locations', component: LocationContainerComponent, canActivate: [AuthGuard]},
  {path: 'locations/details/:id', component: LocationDetailsComponent, canActivate: [AuthGuard]},
  {path: 'locations/add', component: AddLocationFormComponent, canActivate: [AuthGuard], data: {roles: [UserRole.Admin]}},
  {path: 'locations/update/:id', component: LocationUpdateComponent, canActivate: [AuthGuard], data: {roles: [UserRole.Admin]}},
  {path: 'events', component: EventContainerComponent, canActivate: [AuthGuard], data: {roles: [UserRole.Admin]}},
  {path: 'events/add', component: AddEventFormComponent, canActivate: [AuthGuard], data: {roles: [UserRole.Admin]}},
  {path: 'events/details/:id', component: EventDetailsComponent, canActivate: [AuthGuard], data: {roles: [UserRole.Admin]}},
  {path: 'reports', component: EventReportContainerComponent, canActivate: [AuthGuard], data: {roles: [UserRole.Admin]}},
  {path: 'user/events/details/:id', component: UserEventDetailsComponent, canActivate: [AuthGuard], data: {roles: [UserRole.User]}},
  {path: 'user/events', component: UserEventContainerComponent, canActivate: [AuthGuard], data: {roles: [UserRole.User]}},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  {path: '', component: HomeComponent},
  {path: 'tickets', component: UserTicketsContainerComponent, canActivate: [AuthGuard], data: {roles: [UserRole.User]}},
  {path: 'paypal', component: PaypalPaymentComponent},
  {path: 'verification/:verified', component: VerificationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
