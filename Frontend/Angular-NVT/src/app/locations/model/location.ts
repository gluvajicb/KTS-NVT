import { Sector } from '../model/sector';

export class Location {
    id: number;
    title: string;
    address: string;
    sectors: Sector[];

    constructor(obj?: any) {
     this.id = obj && obj.id || null;
     this.title = obj && obj.name || null;
     this.address = obj && obj.name || null;
    }
}
