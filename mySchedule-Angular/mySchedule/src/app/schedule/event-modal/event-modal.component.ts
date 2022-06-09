import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { EventParticipantRepresentation } from 'src/app/models/event-participant-representation.model';
import { EventParticipant } from 'src/app/models/event-participant.model';
import { Event } from 'src/app/models/event.model';
import { Group } from 'src/app/models/group.model';
import { UserGet } from 'src/app/models/user-get.model';
import { AuthService } from 'src/app/services/auth.service';
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
  // public eventParticipants: Map<string,Array<EventParticipantRepresentation>>;
  private group: Group;

  constructor(public activeModal: NgbActiveModal,
              private route: ActivatedRoute,
              private groupService: GroupService,
              private userService: UserService,
              public modalService: NgbModal,
              public eventService: EventService,
              private authService: AuthService,
              private toastr: ToastrService) {}

  async ngOnInit(): Promise<void> {
    if (!this.isPersonal) {
      this.group = await this.groupService.getGroupById(this.id);
    }
    await this.refreshEvents();
  }
  
  async refreshEvents() {
    if (this.isPersonal) {
     let allEvents = await this.userService.getEventsByUserId(this.id);
     this.events = allEvents.filter((e: Event) => e.personal === true && this.getEventsByDay(e));
    } else {
      let allEvents = await this.groupService.getAllEventsByGroupId(this.id);
      console.log(allEvents);
      this.events = allEvents.filter((e: Event) => this.getEventsByDay(e));
    }
  }

  editEvent(eventId){
    const modalRef = this.modalService.open(EditEventModalComponent, { size: 'lg' });
    modalRef.componentInstance.Id = eventId;
    modalRef.result.then().finally(async () => await this.refreshEvents());
  }

  isElligible(event: Event) {
    if (event.personal) {
      return this.authService.isAdminOrOwner(this.id);
    } else {
      return this.authService.isAdminOrManagerOrEventOwner(this.group.managerId, event.creatorId)
    }
  }

  async deleteEvent(eventId){
   await this.eventService.deleteEvent(eventId);
   this.toastr.success("Successfully deleted event.");
   await this.refreshEvents();
  }

  getEventsByDay(event: Event) {
    let eventStartTime = new Date(event.startTime);
    let eventEndTime = new Date(event.endTime);

    return (eventStartTime.getDate() === this.date.getDate()
    && eventStartTime.getFullYear() === this.date.getFullYear()
    && eventStartTime.getMonth() === this.date.getMonth()) 
    || (eventEndTime.getDate() === this.date.getDate()
    && eventEndTime.getFullYear() === this.date.getFullYear()
    && eventEndTime.getMonth() === this.date.getMonth());
  }

  async getUsername(userId: string): Promise<string> {
    let user = await this.userService.getUserById(userId);
    return user.username;
  }

  updateEventStatusAccepted(eventId: string) {
    this.eventService.updateEventStatus(eventId, "ACCEPTED");
    this.toastr.success("Invitation for event accepted.");
  }

  updateEventStatusDeclined(eventId: string) {
    this.eventService.updateEventStatus(eventId, "DECLINED");
    this.toastr.success("Invitation for event declined.");
  }

  isInvited(event: Event) {
    for (let i = 0; i < event.participants.length; i++) {
      if (this.authService.getLoggedUserId() === event.participants[i].userId) {
        return true;
      }
    }
    return false;
  }

  public openCreateEventModal() {
    const modalRef = this.modalService.open(CreateEventModalComponent, { size: 'lg' });
    modalRef.componentInstance.date = this.date;
    modalRef.componentInstance.isPersonal = this.isPersonal;
    modalRef.result.then().finally(async () => await this.refreshEvents());
  }
}
