import { Component, OnInit, Input, OnChanges, SimpleChanges, ChangeDetectionStrategy } from '@angular/core';
import { EventPrice } from '../model/event-price';

declare var fabric: any;

@Component({
  selector: 'app-canvas-event-prices',
  templateUrl: './canvas-event-prices.component.html',
  styleUrls: ['./canvas-event-prices.component.css']
})
export class CanvasEventPricesComponent implements OnInit, OnChanges {

  canvas: any;
  initDone = false;

  @Input() prices: EventPrice[];
  @Input() reservedSeats: any; // promeniti

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.initDone) {
      this.draw();
    }
  }

  ngOnInit() {
    this.canvas = new fabric.Canvas('canvas');

    this.draw();
    this.initDone = true;
  }

  draw() {
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
        selectable: true
      });

      this.canvas.add(g);
    } else {
      const w = sec.sector.width / sec.sector.column_num;
      const h = sec.sector.height / sec.sector.row_num;


      for (let i = 0; i < sec.sector.row_num; i++) {
        for (let j = 0; j < sec.sector.column_num; j++) {
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
            column_num: j
          }));
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
