import { Component, OnInit } from '@angular/core';
import { Schedule } from '../models/schedule.model';
import { AuthService } from '../services/auth.service';
import { GroupService } from '../services/group.service';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent implements OnInit {

  schedule: Schedule;
  constructor(private auth: AuthService, private groupService: GroupService) {
    console.log(auth.getDecodedToken());
   }

  ngOnInit(): void {
  }

  getGroupById(id) {
    return this.groupService.getGroupById(id).subscribe(data=>{
      this.schedule.group = data;
      });
  }

  isLogged(){
    return this.auth.isLogged();
  }
}
