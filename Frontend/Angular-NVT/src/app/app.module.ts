import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './routing/app-routing.module';
import {FormsModule, FormBuilder, ReactiveFormsModule, AbstractControl} from '@angular/forms';

import {AppComponent} from './app.component';
import {HeaderComponent} from './core/header/header.component';
import {NavbarAdminComponent} from './core/navbar-admin/navbar-admin.component';
import {TableComponent} from './locations/table/table.component';
import {LocationListComponent} from './locations/location-list/location-list.component';
import {SearchLocationFormComponent} from './locations/search-location-form/search-location-form.component';
import {AddLocationFormComponent} from './locations/add-location-form/add-location-form.component';
import {LocationContainerComponent} from './locations/location-container/location-container.component';
import {PaginationComponent} from './locations/pagination/pagination.component';
import {LocationDetailsComponent} from './locations/location-details/location-details.component';
import {SectorCanvasComponent} from './locations/sector-canvas/sector-canvas.component';
import {AddSectorComponent} from './locations/add-sector/add-sector.component';
import {HttpClientModule} from '@angular/common/http';
import {LocationsService} from './locations/services/locations.service';

import {TableEventDayComponent} from './events/event-day-table/event-day-table.component';
import {TableEventComponent} from './events/table/table.component';
import {EventListComponent} from './events/event-list/event-list.component';
import {AddEventFormComponent} from './events/add-event-form/add-event-form.component';
import {EventContainerComponent} from './events/event-container/event-container.component';
import {EventsService} from './events/services/events.service';
import {EventDetailsComponent} from './events/event-details/event-details.component';
import {CanvasEventsComponent} from './events/canvas-events/canvas-events.component';
import {CanvasEventPricesComponent} from './events/canvas-event-prices/canvas-event-prices.component';
import {AddSectorpricesFormComponent} from './events/add-sectorprices-form/add-sectorprices-form.component';
import {SectorPriceFormValidatorDirective} from './events/directives/sector-price-form-validator.directive';
import {PriceFieldValidatorDirective} from './events/directives/price-field-validator.directive';
import {EventReportContainerComponent} from './reports/event-report-container/event-report-container.component';
import {TicketTableComponent} from './reports/ticket-table/table.component';
import {TicketListComponent} from './reports/ticket-list/ticket-list.component';
import {UserEventDetailsComponent} from './events/user-event-details/user-event-details.component';

import {UserEventContainerComponent} from './events/user-event-container/user-event-container.component';
import {UserEventListComponent} from './events/user-event-list/user-event-list.component';
import {UserEventCardComponent} from './events/user-event-card/user-event-card.component';
import {LocationSearchFormComponent} from './locations/location-search-form/location-search-form.component';
import {LocationUpdateComponent} from './locations/location-update/location-update.component';
import {AddEventDaysComponent} from './events/add-event-days/add-event-days.component';
import {LoginComponent} from './security/login/login.component';
import {SecurityService} from './security/services/security/security.service';
import {RegisterComponent} from './security/register/register.component';
import {MatchValueDirective} from './helpers/match-value.directive';
import {HomeComponent} from './core/home/home.component';
import {ProfileComponent} from './security/profile/profile.component';

import {CanvasUserEventComponent} from './events/user-canvas-event/user-canvas-event.component';
import {authInterceptorProviders} from './helpers/auth.interceptor';
import {UserTicketsContainerComponent} from './reports/user-tickets-container/user-tickets-container.component';
import {UserTicketTableComponent} from './reports/user-ticket-table/table.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NavbarAdminComponent,
    TableComponent,
    LocationListComponent,
    SearchLocationFormComponent,
    AddLocationFormComponent,
    LocationContainerComponent,
    PaginationComponent,
    LocationDetailsComponent,
    SectorCanvasComponent,
    AddSectorComponent,
    TableEventComponent,
    EventListComponent,
    AddEventFormComponent,
    EventContainerComponent,
    EventDetailsComponent,
    CanvasEventsComponent,
    TableEventDayComponent,
    CanvasEventPricesComponent,
    AddSectorpricesFormComponent,
    SectorPriceFormValidatorDirective,
    PriceFieldValidatorDirective,
    EventReportContainerComponent,
    TicketTableComponent,
    TicketListComponent,
    UserEventDetailsComponent,
    UserEventContainerComponent,
    UserEventListComponent,
    UserEventCardComponent,
    LocationSearchFormComponent,
    LocationUpdateComponent,
    AddEventDaysComponent,
    LoginComponent,
    RegisterComponent,
    MatchValueDirective,
    HomeComponent,
    ProfileComponent,
    CanvasUserEventComponent,
    UserTicketsContainerComponent,
    UserTicketTableComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    LocationsService,
    EventsService,
    SecurityService,
    authInterceptorProviders
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
