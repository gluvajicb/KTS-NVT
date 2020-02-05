import {
  Component,
  OnInit,
  Input,
  OnChanges,
  SimpleChanges,
  ChangeDetectionStrategy,
  EventEmitter,
  Output
} from '@angular/core';
import { EventPrice } from '../model/event-price';
import {TakenSeats} from "../model/taken-seats";
import {TicketHelp} from "../model/ticket-help";

declare var fabric: any;

@Component({
  selector: 'app-user-canvas-event',
  templateUrl: './user-canvas-event.component.html',
  styleUrls: ['./user-canvas-event.component.css']
})
export class CanvasUserEventComponent implements OnInit, OnChanges {

  canvas: any;
  initDone = false;

  @Input() prices: EventPrice[];
  @Input() takenSeats: TakenSeats; // promeniti

  @Output() applySelected: EventEmitter<TicketHelp>;
  ticketHelp: TicketHelp;
  constructor() {
    this.applySelected = new EventEmitter();

  }

  ngOnInit() {
    this.canvas = new fabric.Canvas('canvas');


    this.canvas.on('mouse:over', (e: any) => {
      if (e.target != null && e.target.toObject().type === 'rect') {
        e.target.set('stroke', "black" );
        this.canvas.renderAll();
      } else if (e.target != null && e.target.toObject().type === 'group') {
        // e.target.toObject().objects[0].set('fill', '#5d5d5d');
        this.canvas.renderAll();
      }
    });


    this.canvas.on('mouse:out', (e: any) => {
        e.target.set('stroke', 'white');
        this.canvas.renderAll();
    });

    this.canvas.on('mouse:up', (e: any) => {

      if (!e.target.taken) {
        this.ticketHelp = new TicketHelp();
        this.ticketHelp.sectorId = e.target.sectorId;
        this.ticketHelp.sectorTitle = e.target.sectorTitle;
        this.ticketHelp.total = e.target.price;
        this.ticketHelp.ticketType = e.target.type;
        if (this.ticketHelp.ticketType === 'SEATS') {
          this.ticketHelp.row = e.target.row_num + 1;
          this.ticketHelp.column = e.target.column_num + 1;
        } else {
          this.ticketHelp.row = -1;
          this.ticketHelp.column = -1;
        }

        this.applySelected.emit(this.ticketHelp);
      }
    });
    this.draw();
    this.initDone = true;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.initDone) {
      this.draw();
    }
  }

  draw() {
    console.log(this.takenSeats)

    if (this.canvas) {
      this.canvas.remove(...this.canvas.getObjects());
    }
    for (const sec of this.prices) {

      if (sec.sector.type === 'Stand' || sec.sector.type === 'stand') {
      const r = new fabric.Rect({
        originX: 'center',
        originY: 'center',
        width: sec.sector.width,
        height: sec.sector.height,
        fill: 'gray',
      });

      const t = new fabric.IText(sec.sector.title + '\n ' + sec.price + 'e', {
        fontFamily: 'Calibri',
        fontSize: 14,
        textAlign: 'center',
        originX: 'center',
        originY: 'center'
      });

      const g = new fabric.Group([r, t], {
        left: sec.sector.left,
        top: sec.sector.top,
        id: sec.sector.id,
        title: sec.sector.title,
        angle: sec.sector.angle,
        hasControls: false,
        lockMovementX: true,
        lockMovementY: true,
        selectable: true,
        price: sec.price,
        type: 'STAND',
        sectorId: sec.sector.id,
        sectorTitle: sec.sector.title,
        taken: false
      });

      this.canvas.add(g);
    } else {
      let taken;
      for (const id of this.takenSeats.seatsTaken) {
        if (id.sectorId === sec.sector.id) {
          taken = id.seats;
        }
      }

      const w = sec.sector.width / sec.sector.column_num;
      const h = sec.sector.height / sec.sector.row_num;


      for (let i = 0; i < sec.sector.row_num; i++) {
        for (let j = 0; j < sec.sector.column_num; j++) {
          let found = false;
          for (const seat of taken) {
            if (seat.row - 1  === i && seat.col - 1 === j) {
              found = true;
            }
          }
          if (!found) {
            this.canvas.add(new fabric.Rect({
              width: w,
              height: h,
              top: sec.sector.top + h * i,
              left: sec.sector.left + w * j,
              fill: '#00d800',
              stroke: 'white',
              strokeWidth: 1,
              hasControls: false,
              lockMovementX: true,
              lockMovementY: true,
              selectable: true,
              id: sec.sector.id,
              title: sec.sector.title,
              row_num: i,
              column_num: j,
              taken: false,
              price: sec.price,
              type: 'SEATS',
              sectorId: sec.sector.id,
              sectorTitle: sec.sector.title
            }));
          } else {
            this.canvas.add(new fabric.Rect({
              width: w,
              height: h,
              top: sec.sector.top + h * i,
              left: sec.sector.left + w * j,
              fill: '#f55',
              stroke: 'white',
              strokeWidth: 1,
              hasControls: false,
              lockMovementX: true,
              lockMovementY: true,
              selectable: true,
              id: sec.sector.id,
              title: sec.sector.title,
              row_num: i,
              column_num: j,
              taken: true,
              price: sec.price,
              type: 'SEATS',
              sectorId: sec.sector.id,
              sectorTitle: sec.sector.title
            }));
          }
        }
      }

      const t = new fabric.IText(sec.sector.title, {
        fontFamily: 'Calibri',
        fontSize: 14,
        textAlign: 'center',
        originX: 'center',
        originY: 'center',
        top: sec.sector.top + sec.sector.top / 2,
        left: sec.sector.left + sec.sector.left / 2,
        width: sec.sector.width,
        height: sec.sector.height
      });

    }
  }
  }

}
