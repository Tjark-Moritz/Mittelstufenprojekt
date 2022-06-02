import { Component, OnInit } from '@angular/core';
import {GetEmployee} from "../../models/dto/GetEmployee";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {ActivatedRoute} from "@angular/router";
import {Observable, switchAll, switchMap} from "rxjs";
import {EmployeeService} from "../../services/employee.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  private para: any;
  private selectedEmployee: GetEmployee | undefined;
  private profilePictureBase64 : string = "";

  constructor(private domSanitizer: DomSanitizer, private route: ActivatedRoute, private empService: EmployeeService) {
    this.domSanitizer.bypassSecurityTrustResourceUrl(this.profilePictureBase64);
  }

  ngOnInit(): void {
    this.para = this.route.params.subscribe(params => {
      this.empService.getEmployeeById(params['id']).subscribe(emp => {
         this.selectedEmployee = emp;
      });
    });
  }

  public isProfilePictureSet(): boolean{
    if(this.profilePictureBase64 != "")
      return true;
    else
      return false;
  }

  public getProfilePicture(): SafeResourceUrl | undefined{
    if(this.isProfilePictureSet()) {
      return this.domSanitizer.bypassSecurityTrustResourceUrl(this.profilePictureBase64);
    }

    return undefined;
  }

}
