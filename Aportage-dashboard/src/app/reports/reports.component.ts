import { Component, OnInit } from '@angular/core';

import { HttpClient, HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  Meldingen: any;

  constructor(private _http: HttpClient) {
  }

  ngOnInit() {
    this.getMeldingen();
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
