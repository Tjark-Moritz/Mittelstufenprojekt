import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import { DepartmentListComponent } from './components/department-list/department-list.component';
import { HighliterPipe } from './pipes/highliter.pipe';

@NgModule({
  declarations: [
    AppComponent,
    DepartmentListComponent,
    HighliterPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [HighliterPipe],
  bootstrap: [AppComponent]
})
export class AppModule {
}
