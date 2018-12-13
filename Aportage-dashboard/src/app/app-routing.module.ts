import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { CampusComponent } from './campus/campus.component';

const routes: Routes = [
  { path: '', component: AppComponent },
  { path: 'campussen', component: CampusComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
