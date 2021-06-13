
import { Component, OnInit } from '@angular/core';
import { CalendarDay } from './calendar-day';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import { EventModalComponent } from '../event-modal/event-modal.component';
import { ActivatedRoute, UrlSegment } from '@angular/router';
import { jsPDF } from "jspdf";
import 'jspdf-autotable';
import { UserService } from 'src/app/services/user.service';
import { GroupService } from 'src/app/services/group.service';
import { Event } from 'src/app/models/event.model';
import { UserGet } from 'src/app/models/user-get.model';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})

export class ScheduleComponent implements OnInit {

  public calendar: CalendarDay[] = [];
  public monthNames = ["January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
  ];
  private id: string;
  private isPersonal: boolean;

  public displayMonth: string;
  private monthIndex: number = 0;

  ngOnInit(): void {
    this.generateCalendarDays(this.monthIndex);
    this.route.url.subscribe((url: UrlSegment[]) => {
      this.isPersonal = url[1].path === "users";
      this.id = url[2].path;
    })
  }

  constructor(private modalService: NgbModal, private route: ActivatedRoute, private userService: UserService, private groupService: GroupService) {}

  private generateCalendarDays(monthIndex: number): void {
    // we reset our calendar
    this.calendar = [];

    // we set the date 
    let day: Date = new Date(new Date().setMonth(new Date().getMonth() + monthIndex));

    // set the display month for UI
    this.displayMonth = this.monthNames[day.getMonth()];

    let startingDateOfCalendar = this.getStartDateForCalendar(day);

    let dateToAdd = startingDateOfCalendar;

    for (var i = 0; i < 42; i++) {
      this.calendar.push(new CalendarDay(new Date(dateToAdd)));
      dateToAdd = new Date(dateToAdd.setDate(dateToAdd.getDate() + 1));
    }
  }

  private getStartDateForCalendar(selectedDate: Date){
    let lastDayOfPreviousMonth = new Date(selectedDate.setDate(0));

    let startingDateOfCalendar: Date = lastDayOfPreviousMonth;
    if (startingDateOfCalendar.getDay() != 1) {
      do {
        startingDateOfCalendar = new Date(startingDateOfCalendar.setDate(startingDateOfCalendar.getDate() - 1));
      } while (startingDateOfCalendar.getDay() != 1);
    }

    return startingDateOfCalendar;
  }

   public increaseMonth() {
    this.monthIndex++;
    this.generateCalendarDays(this.monthIndex);
  }

  public decreaseMonth() {
    this.monthIndex--
    this.generateCalendarDays(this.monthIndex);
  }

  public setCurrentMonth() {
    this.monthIndex = 0;
    this.generateCalendarDays(this.monthIndex);
  }


  public openEventsModal(date) {
    const modalRef = this.modalService.open(EventModalComponent, { size: 'lg' });
    modalRef.componentInstance.date = date;
    modalRef.componentInstance.id = this.id;
    modalRef.componentInstance.isPersonal = this.isPersonal;
  }

  public async exportToPdf() {
    let name = this.isPersonal
    ? (await this.userService.getUserById(this.id)).username
    : (await this.groupService.getGroupById(this.id)).name;
    let allEvents: Array<Event> = this.isPersonal 
    ? await this.userService.getEventsByUserId(this.id)
    : await this.groupService.getAllEventsByGroupId(this.id);

    let head = [['Name', 'Description', 'Creation Time', 'Start Time', 'End Time',  'Priority']]

    let data = []

    allEvents.forEach(async (event: Event) => {
      data.push([event.name, event.description, event.creationTime.toString(), event.startTime.toString(), event.endTime.toString(), event.priority])
    });
    

    var doc = new jsPDF();

    doc.setFontSize(18);
    doc.text(`${this.isPersonal ? "Personal schedule for" : "Group schedule for"} ${name}`, 11, 8);
    doc.setFontSize(11);
    doc.setTextColor(100);

    (doc as any).autoTable({
      head: head,
      body: data,
      theme: 'plain',
      didDrawCell: data => {
      }
    })

    doc.save('myteamdetail.pdf');
  }
  
}

