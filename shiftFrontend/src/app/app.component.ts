import {Component} from '@angular/core';
import {Employee} from "./Employee";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  bearer = 'eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzUFQ0dldiNno5MnlQWk1EWnBqT1U0RjFVN0lwNi1ELUlqQWVGczJPbGU0In0.eyJleHAiOjE2NDIwMDI2MjIsImlhdCI6MTY0MTk4ODIyMiwianRpIjoiNjlkNWQxYmItZGZlMC00NTI5LWJhYmUtNmFkOGNlYzdjYzg2IiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5zenV0LmRldi9hdXRoL3JlYWxtcy9zenV0IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjU1NDZjZDIxLTk4NTQtNDMyZi1hNDY3LTRkZTNlZWRmNTg4OSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImVtcGxveWVlLW1hbmFnZW1lbnQtc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiI5MDRiZjY4NC0xMDJlLTRiYjQtYTEwZC04NTRhYjg1YmQwNWIiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1zenV0IiwidW1hX2F1dGhvcml6YXRpb24iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ1c2VyIn0.F9SbT6Ogi35Q6mJIL-tL7N7E36GGdWnZ6XST_FU9PSh_nPD7TQAXKN4DXnFIQHAviZLJWagblEGanehoWfzWmjKfkW8fjAoLVA_3LjNoHyI2LCvPeznChXxeNAPgVAdjpGWKie-dWBop1xUxhtCizT2Gh1T8KOruSMQqPdgtLAp35sCLfqB1DYjGI4Hf6vnXW9YEydttBCRAzb8yUXl39PPX4ffo1pUUk_8BAzQbLxQl548lxvCIN4XOg2TxuIOmbZxV0fZPYp35UvwJpEGBv8ZG2THvNLmJ_Jsv0BSW_M4HuOeXqeW3ROX7Nl_M_t_14jbzDrRsf55tx9pTh-df8A';
  employees$: Observable<Employee[]>;

  constructor(private http: HttpClient) {
    this.employees$ = of([]);
    this.fetchData();
  }

  fetchData() {
    this.employees$ = this.http.get<Employee[]>('/backend', {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${this.bearer}`)
    });
  }
}
