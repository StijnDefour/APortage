import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { CampusComponent } from './campus/campus.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ReportsComponent } from './reports/reports.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  //{ path: '', component: AppComponent },
  { path: '', component: DashboardComponent },
  { path: 'login', component: LoginComponent },
  { path: 'meldingen', component: ReportsComponent },
  { path: 'campussen', component: CampusComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
