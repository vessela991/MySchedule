import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './identity/login/login.component';
import { SchedulesComponent } from './schedule/schedules.component';
import { AuthGuard } from  './guard/auth.guard';
import { ScheduleComponent } from './schedule/schedule/schedule.component';
import { GroupsComponent } from './group/groups/groups.component';
import { UsersComponent } from './users/users/users.component';
import { UserProfileComponent } from './users/user-profile/user-profile.component';
import { UserCreateComponent } from './users/user-create/user-create.component';
import { GroupCreateComponent } from './group/group-create/group-create.component';
import { UserEditComponent } from './users/user-edit/user-edit.component';
import { GroupEditComponent } from './group/group-edit/group-edit.component';
import { GroupViewComponent } from './group/group-view/group-view.component';


const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'schedules', component: SchedulesComponent, canActivate: [AuthGuard]},
  {path: 'groups', component: GroupsComponent, canActivate: [AuthGuard]},
  {path: 'groups/:id', component: GroupViewComponent, canActivate: [AuthGuard]},
  {path: 'groups/create', component: GroupCreateComponent, canActivate: [AuthGuard]},
  {path: 'groups/:id/edit', component: GroupEditComponent, canActivate: [AuthGuard]},
  {path: 'users', component: UsersComponent, canActivate: [AuthGuard]},
  {path: 'users/create', component: UserCreateComponent, canActivate: [AuthGuard]},
  {path: 'users/:id', component: UserProfileComponent, canActivate: [AuthGuard]},
  {path: 'users/:id/edit', component: UserEditComponent, canActivate: [AuthGuard]},
  {path: 'schedules/groups/:id', component: ScheduleComponent, canActivate: [AuthGuard]},
  {path: 'schedules/users/:id', component: ScheduleComponent, canActivate: [AuthGuard]},
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
