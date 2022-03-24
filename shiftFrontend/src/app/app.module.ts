import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import { EmployeeListComponent } from './components/employee-list/employee-list.component';
import { HighliterPipe } from './pipes/highliter.pipe';

@NgModule({
  declarations: [
    AppComponent,
    EmployeeListComponent,
    HighliterPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
