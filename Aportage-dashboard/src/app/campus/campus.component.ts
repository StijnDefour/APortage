import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-campus',
  templateUrl: './campus.component.html',
  styleUrls: ['./campus.component.css']
})
export class CampusComponent {
  title = 'Campus Verdiepingen';
  fileContent = '';

  csvContent: [];

  constructor(public http: HttpClient) {
  }

  ngOnInit() {
    /*this.http.get('./assets/csv/ellermansstraat-lokaal.json').subscribe(data => {
      for (let key in data) {
        let value = data[key];
        this.http.post('https://api.mlab.com/api/1/databases/campusdb/collections/lokalen?apiKey=OlG7Ic3_9_iemwwMvmErBnkK-N0DrZs4', value)
          .subscribe(
            err => {
              console.log("Error occured");
            }
          );
      }
    });*/
  }

  onFileLoad(fileLoadedEvent) {
    const textFromFileLoaded = fileLoadedEvent.target.result;
    let csvString = textFromFileLoaded;
    let csvItems = [];

    this.csvContent = csvString.split("\n");

    for (let i = 0; i < this.csvContent.length; i++) {
      if (i != 0 && this.csvContent[i] != "") {
        csvItems = String(this.csvContent[i]).split(",");
        let data = {
          "campus afk": csvItems[0],
          "verdieping": csvItems[1]
        };
        console.log(data);


      }
    }
  }



  /*
  this.httpClient.post('https://api.mlab.com/api/1/databases/campusdb/collections/verdiepen?apiKey=OlG7Ic3_9_iemwwMvmErBnkK-N0DrZs4', JSON.stringify(data) )
    .subscribe(
      res => {
        console.log(res);
      },
      err => {
        console.log("Error occured");
      }
    );*/

  onFileSelect(input: HTMLInputElement) {
    const files = input.files;
    var content = this.csvContent;

    if (files && files.length) {
      const fileToRead = files[0];

      const fileReader = new FileReader();
      fileReader.onload = this.onFileLoad;

      fileReader.readAsText(fileToRead, "UTF-8");
    }
  }
}
