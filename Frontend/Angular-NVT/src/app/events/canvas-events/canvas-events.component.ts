import { Component, OnInit, Input, OnChanges, SimpleChanges, ChangeDetectionStrategy } from '@angular/core';
import { Sector } from '../../locations/model/sector';

declare var fabric: any;

@Component({
  selector: 'app-canvas-events',
  templateUrl: './canvas-events.component.html',
  styleUrls: ['./canvas-events.component.css']
})
export class CanvasEventsComponent implements OnInit, OnChanges {

  canvas: any;
  initDone = false;

  @Input() sectors: Sector[];
  @Input() reservedSeats: any; // promeniti

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.initDone) {
      this.draw();
    }
  }

  ngOnInit() {
    this.canvas = new fabric.Canvas('canvas');

    this.canvas.on('mouse:over', (e: any) => {
      if (e.target != null && e.target.toObject().type === 'rect') {
        e.target.set('fill', 'red');
        this.canvas.renderAll();
      } else if (e.target != null && e.target.toObject().type === 'group') {
        // e.target.toObject().objects[0].set('fill', '#5d5d5d');
        this.canvas.renderAll();
      }
    });


    this.canvas.on('mouse:out', (e: any) => {
      if (e.target != null && e.target.toObject().type === 'rect') {
        e.target.set('fill', '#f55');
        this.canvas.renderAll();
      } else if (e.target != null && e.target.toObject().type === 'group') {
        e.target.set('fill', 'gray');
        this.canvas.renderAll();
      }
    });

    this.canvas.on('mouse:up', (e: any) => {
      if (e.target != null) {
        let message = '';
        if (e.target.title) {
          message += 'Title: ' + e.target.title;
        }
        if (e.target.row_num) {
          message += '\nRow number: ' + e.target.row_num;
          message += '\nColumn number: ' + e.target.column_num;
        }
        alert(message);
      }
    });

    this.draw();
    this.initDone = true;
  }

  draw() {
    if (this.canvas) {
      this.canvas.remove(...this.canvas.getObjects());
    }
    for (const sec of this.sectors) {
      if (sec.type === 'Stand' || sec.type === 'stand') {
      const r = new fabric.Rect({
        originX: 'center',
        originY: 'center',
        width: sec.width,
        height: sec.height,
        fill: 'gray',
      });

      const t = new fabric.IText(sec.title, {
        fontFamily: 'Calibri',
        fontSize: 14,
        textAlign: 'center',
        originX: 'center',
        originY: 'center'
      });

      const g = new fabric.Group([r, t], {
        left: sec.left,
        top: sec.top,
        id: sec.id,
        title: sec.title,
        angle: sec.angle,
        hasControls: false,
        lockMovementX: true,
        lockMovementY: true,
        selectable: true
      });

      this.canvas.add(g);
    } else {
      const w = sec.width / sec.column_num;
      const h = sec.height / sec.row_num;


      for (let i = 0; i < sec.row_num; i++) {
        for (let j = 0; j < sec.column_num; j++) {
          this.canvas.add(new fabric.Rect({
            width: w,
            height: h,
            top: sec.top + h * i,
            left: sec.left + w * j,
            fill: '#f55',
            stroke: 'white',
            strokeWidth: 1,
            hasControls: false,
            lockMovementX: true,
            lockMovementY: true,
            selectable: true,
            id: sec.id,
            title: sec.title,
            row_num: i,
            column_num: j
          }));
        }
      }

      const t = new fabric.IText(sec.title, {
        fontFamily: 'Calibri',
        fontSize: 14,
        textAlign: 'center',
        originX: 'center',
        originY: 'center',
        top: sec.top + sec.top / 2,
        left: sec.left + sec.left / 2,
        width: sec.width,
        height: sec.height
      });

    }
  }
  }

}
