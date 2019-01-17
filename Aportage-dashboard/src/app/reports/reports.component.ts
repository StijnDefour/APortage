import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';

import { HttpClient, HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  Meldingen: any;

  constructor(private _http: HttpClient, private spinner: NgxSpinnerService) {
  }

  ngOnInit() {
    // show spinner
    this.spinner.show();

    // get meldingen
    this.getMeldingen();

    // hide spinner
    this.spinner.hide();

  }

  getMeldingen() {
    const url = 'https://api.mlab.com/api/1/databases/campusdb/collections/meldingen?apiKey=OlG7Ic3_9_iemwwMvmErBnkK-N0DrZs4';
    
    // create async request
    const obs = this._http.get(url);
    obs.subscribe((response) => {
      this.Meldingen = response;
    })
  }
}
