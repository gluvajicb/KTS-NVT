import { Sector } from '../../locations/model/sector';

export class EventPrice {
  id: number;
  price: number;
  sector: Sector;
  eventId: number;
}
