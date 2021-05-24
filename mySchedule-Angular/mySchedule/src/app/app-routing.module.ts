import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './identity/login/login.component';
import { ScheduleComponent } from './schedule/schedule.component';
import { AuthGuard } from  './guard/auth.guard';


const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'schedules', component: ScheduleComponent, canActivate: [AuthGuard]},
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
