import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Event } from 'src/app/models/event.model';
import { UserGet } from 'src/app/models/user-get.model';
import { EventService } from 'src/app/services/event.service';
import { UserService } from 'src/app/services/user.service';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-edit-event-modal',
  templateUrl: './edit-event-modal.component.html',
  styleUrls: ['./edit-event-modal.component.css']
})
export class EditEventModalComponent implements OnInit {
  @Input() Id: string;

  public users: Array<UserGet>;
  currentEvent: Event;

  createEventForm: FormGroup;
  errorMessage: string;
  isPasswordVisible: boolean = false;
  eventParticipants: Array<String>;
  
  constructor(private toastr: ToastrService,
    private formBuilder: FormBuilder,
    private eventService:EventService,
    private userService: UserService,
    public activeModal: NgbActiveModal) {

  }

  async ngOnInit() {
    this.users = await this.userService.getAllUsers();
    this.currentEvent = await this.eventService.getById(this.Id);
    this.createEventForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', []],
      startTime: ['', [Validators.required]],
      endTime: ['', [Validators.required]],
      priority: ['', [Validators.required]],
      personal: [this.currentEvent.personal, [Validators.required]]
      })

    this.createEventForm.setValue(
    {
      name: this.currentEvent.name,
      description: this.currentEvent.description,
      startTime: new DatePipe('en-US').transform(this.currentEvent.startTime, 'yyyy-MM-ddThh:mm'),
      endTime: new DatePipe('en-US').transform(this.currentEvent.endTime, 'yyyy-MM-ddThh:mm'),
      priority: this.currentEvent.priority,
      personal: this.currentEvent.personal ? "true" : "false"
    });
  }

  async editEvent() {
    try {
      await this.eventService.editEvent(this.createEventForm.value, this.Id);
      this.errorMessage = null;
      this.toastr.success("Successfully edited event.");
      this.activeModal.close("Success"); 
    } catch(error) {
      this.toastr.error("Error editing event.")
      this.errorMessage = error.error;
    }
  }

  get name() {
    return this.createEventForm.get('name');
  }

  get description() {
    return this.createEventForm.get('description');
  }

  get startTime() {
    return this.createEventForm.get('startTime');
  }

  get endTime() {
    return this.createEventForm.get('endTime');
  }

  get priority() {
    return this.createEventForm.get('priority');
  }
  
  get personal() {
    return this.createEventForm.get('personal');
  }

}
