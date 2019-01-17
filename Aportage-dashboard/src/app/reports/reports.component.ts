import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { NgxSmartModalService } from 'ngx-smart-modal';

import { HttpClient, HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  Meldingen: any;

  constructor(private _http: HttpClient, private spinner: NgxSpinnerService, public ngxSmartModalService: NgxSmartModalService) {
  }

  ngOnInit() {
    // show spinner
    this.spinner.show();

    // get meldingen
    this.getMeldingen();

    // hide spinner
    this.spinner.hide();

  }

  editItem(itemURL) {
    console.log(itemURL);
    let item = null; 
    item = this.Meldingen.find(i => i.imgUrl === itemURL);
    console.log(item.titel);
    this.ngxSmartModalService.setModalData(item, 'editModal');
    this.ngxSmartModalService.getModal('editModal').open()
  }

  getMeldingen() {
    const url = 'https://api.mlab.com/api/1/databases/campusdb/collections/meldingen?apiKey=OlG7Ic3_9_iemwwMvmErBnkK-N0DrZs4';

    // create async request
    const obs = this._http.get(url);
    obs.subscribe((response) => {
      function compare(a, b) {
        // check for correct date
        if (!Date.parse(a.datum)) {
          var date = a.imgUrl.split("-", 3);
          a.datum = new Date(date[0], date[1], date[2]);
        } else if (!Date.parse(b.datum)) {
          var date = b.imgUrl.split("-", 3);
          b.datum = new Date(date[0], date[1], date[2]);
        }

        // sort by date
        if (Date.parse(a.datum) < Date.parse(b.datum))
          return -1;
        if (Date.parse(a.datum) > Date.parse(b.datum))
          return 1;
        return 0;
      }

      this.Meldingen = response;

      this.Meldingen.sort(compare);
    })
  }

}
