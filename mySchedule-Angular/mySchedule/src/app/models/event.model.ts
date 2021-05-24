import { EventParticipant } from "./event-participant.model";

export class Event{ 
    id: string;
    name: string;
    description: string;
    creationTime: Date;
    startTime: Date;
    endTime: Date;
    participants: EventParticipant[]
    creatorId: string;
    priority: string;
    isPersonal: boolean;
}