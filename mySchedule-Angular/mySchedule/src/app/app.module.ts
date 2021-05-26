import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './identity/login/login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SchedulesComponent } from './schedule/schedules.component';
import { ScheduleComponent } from './schedule/schedule/schedule.component';
import { GroupsComponent } from './group/groups/groups.component';
import { UsersComponent } from './users/users/users.component';
import { UserProfileComponent } from './users/user-profile/user-profile.component';
import { UserCreateComponent } from './users/user-create/user-create.component';
import { NavigationComponent } from './navigarion/navigation/navigation.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ScheduleComponent,
    SchedulesComponent,
    GroupsComponent,
    UsersComponent,
    UserProfileComponent,
    UserCreateComponent,
    NavigationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
