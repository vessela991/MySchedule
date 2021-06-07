import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Event } from 'src/app/models/event.model';
import { EventService } from 'src/app/services/event.service';
import { GroupService } from 'src/app/services/group.service';
import { UserService } from 'src/app/services/user.service';
import { CreateEventModalComponent } from './create-event-modal/create-event-modal.component';
import { EditEventModalComponent } from './edit-event-modal/edit-event-modal.component';

@Component({
  selector: 'app-event-modal',
  templateUrl: './event-modal.component.html',
  styleUrls: ['./event-modal.component.css']
})
export class EventModalComponent implements OnInit {

  @Input() date: Date;

  @Input() id: string;

  @Input() isPersonal: boolean;

  public events = Array<Event>();
  
  constructor(public activeModal: NgbActiveModal,
              private route: ActivatedRoute,
              private groupService: GroupService,
              private userService: UserService,
              public modalService: NgbModal,
              public eventService: EventService) {}

  async ngOnInit(): Promise<void> {
    await this.refreshEvents();
  }
  
  async refreshEvents() {
    if (this.isPersonal) {
     let allEvents = await this.userService.getEventsByUserId(this.id);
     this.events = allEvents.filter((e: Event) => e.personal === true && this.getEventsByDay(e));
    } else {
      let allEvents = await this.groupService.getAllEventsByGroupId(this.id);
      this.events = allEvents.filter((e: Event) => this.getEventsByDay(e));
    }
  }

  editEvent(eventId){
    const modalRef = this.modalService.open(EditEventModalComponent, { size: 'lg' });
    modalRef.componentInstance.Id = eventId;
    modalRef.result.then().finally(async () => await this.refreshEvents());
  }

  async deleteEvent(eventId){
   await this.eventService.deleteEvent(eventId);
   await this.refreshEvents();
  }

  getEventsByDay(event: Event) {
    let eventStartTime = new Date(event.startTime);

    return eventStartTime.getDate() === this.date.getDate()
    && eventStartTime.getFullYear() === this.date.getFullYear()
    && eventStartTime.getMonth() === this.date.getMonth();
  }

  public openCreateEventModal() {
    const modalRef = this.modalService.open(CreateEventModalComponent, { size: 'lg' });
    modalRef.componentInstance.date = this.date;
    modalRef.componentInstance.isPersonal = this.isPersonal;
    modalRef.result.then().finally(async () => await this.refreshEvents());
  }
}
