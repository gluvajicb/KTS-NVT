import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './routing/app-routing.module';
import { FormsModule, FormBuilder, ReactiveFormsModule, AbstractControl } from '@angular/forms';

import { AppComponent } from './app.component';
import { HeaderComponent } from './core/header/header.component';
import { NavbarAdminComponent } from './core/navbar-admin/navbar-admin.component';
import { TableComponent } from './locations/table/table.component';
import { LocationListComponent } from './locations/location-list/location-list.component';
import { SearchLocationFormComponent } from './locations/search-location-form/search-location-form.component';
import { AddLocationFormComponent } from './locations/add-location-form/add-location-form.component';
import { LocationContainerComponent } from './locations/location-container/location-container.component';
import { PaginationComponent } from './locations/pagination/pagination.component';
import { LocationDetailsComponent } from './locations/location-details/location-details.component';
import { SectorCanvasComponent } from './locations/sector-canvas/sector-canvas.component';
import { AddSectorComponent } from './locations/add-sector/add-sector.component';
import { HttpClientModule} from '@angular/common/http';
import { LocationsService } from './locations/services/locations.service';

import { TableEventComponent } from './events/table/table.component';
import { EventListComponent } from './events/event-list/event-list.component';
import { AddEventFormComponent } from './events/add-event-form/add-event-form.component';
import { EventContainerComponent } from './events/event-container/event-container.component';
import { EventsService } from './events/services/events.service';
import { EventDetailsComponent } from './events/event-details/event-details.component';


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
    EventDetailsComponent
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
    EventsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
