import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent implements OnInit {

  userId: string;
  groupId: string;

  constructor(private auth: AuthService, private router:Router) {
  }

  ngOnInit(): void {
    this.getPersonalOrGroup();
  }

  getPersonalOrGroup() {
    let url = this.router.url;
    if (url.includes("users")){
      this.userId=url.split("/")[3];
    }

    if (url.includes("groups")){
      this.groupId=url.split("/")[3];
      console.log(this.groupId);
    }
  }
}
