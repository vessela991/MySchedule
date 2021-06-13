import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { UserGet } from 'src/app/models/user-get.model';
import { EventService } from 'src/app/services/event.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-create-event-modal',
  templateUrl: './create-event-modal.component.html',
  styleUrls: ['./create-event-modal.component.css']
})
export class CreateEventModalComponent implements OnInit {
  @Input() date: Date;

  @Input() public isPersonal: boolean;
  

  public users: Array<UserGet>;
  createEventForm: FormGroup;
  errorMessage: string;
  eventParticipants: Array<String>;

  constructor(private toastr: ToastrService,
              private formBuilder: FormBuilder,
              private eventService:EventService,
              private userService: UserService,
              public activeModal: NgbActiveModal) {
   
   }
  
  async ngOnInit() {
    console.log(this.isPersonal)
    this.users = await this.userService.getAllUsers();
    this.createEventForm = this.formBuilder.group({
      'name': ['', [Validators.required]],
      'description': ['', []],
      'startTime': ['', [Validators.required]],
      'endTime': ['', [Validators.required]],
      'participants': [[], []],
      'priority': ['', [Validators.required]],
      'personal': [this.isPersonal]
    })
  }

  async createEvent() {
    try {
      console.log(this.createEventForm.value)
      await this.eventService.createEvent(this.createEventForm.value);
      this.errorMessage = null;
      this.toastr.success("Successfully created event.");
      this.activeModal.close("Success"); 
    } catch(error) {
      this.toastr.error("Error creating event.")
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

  get participants() {
    return this.createEventForm.get('participants');
  }

  get priority() {
    return this.createEventForm.get('priority');
  }
  
  get personal() {
    return this.createEventForm.get('personal');
  }
}
