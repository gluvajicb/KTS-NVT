import { EventDay } from '../model/event-day';
import { EventPrice } from '../model/event-price';
import { Sectorprice } from './sectorprice';

export class Event {
    id: number;
    title: string;
    description: string;
    days: EventDay[];
    isActive: boolean;
    eventCategory: string;
    // tslint:disable-next-line: variable-name
    max_tickets: number;
    locationID: number;
    active: number;
    prices: EventPrice[];
    sectorPrices: Sectorprice[];

    constructor(obj?: any) {
     this.id = obj && obj.id || null;
     this.title = obj && obj.name || null;
     this.description = obj && obj.name || null;
    }
}
