export class Ticket {
    eventDayTitle: string;
    price: number;
    sectorTitle: string;
    rowNumber: number;
    columnNumber: number;
    id: number;
    singleday: boolean;

    constructor(obj?: any) {
     this.id = obj && obj.id || null;
     this.price = obj && obj.price || null;
     this.sectorTitle = obj && obj.sectorTitle || null;
    }
}
