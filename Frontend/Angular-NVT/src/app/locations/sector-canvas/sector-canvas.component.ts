import { Component, OnInit, Input, SimpleChanges, OnChanges, Output, EventEmitter} from '@angular/core';
import { LocationsService } from '../services/locations.service';
import { Sector } from '../model/sector';

declare var fabric: any;

@Component({
  selector: 'app-sector-canvas',
  templateUrl: './sector-canvas.component.html',
  styleUrls: ['./sector-canvas.component.css']
})
export class SectorCanvasComponent implements OnInit, OnChanges {

  zoomLevel: number;
  canvasHeight = 700;
  canvasWidth = 700;
  canvas: any;
  numOfSectors: number;
  hasUnsavedChanges: boolean;
  itemSelected: boolean;

  @Output() submitChangesEvent = new EventEmitter<any>();
  @Output() deleteSectorClicked = new EventEmitter<string>();
  @Input() sectors: Sector[];
  @Input() editable: boolean;

  @Input('numberOfSectors')
  set numberOfSectors(value: number) {
    this.numOfSectors = value;
    if (this.numOfSectors > 0) {
      this.addLastSector();
    }
}

  constructor(private locationService: LocationsService) { }

  ngOnChanges(changes: SimpleChanges): void {
    console.log('**** ' + this.sectors);
  }

  ngOnInit() {
    this.canvas = new fabric.Canvas('canvas');
    this.hasUnsavedChanges = false;
    this.itemSelected = false;

    this.canvas.on('object:modified', (event: any) => {
      this.hasUnsavedChanges = true;
    });

    this.canvas.on('selection:created', () => {
      if (this.canvas.getActiveObject().toObject().type === 'activeSelection') {
        this.itemSelected = false;
      } else {
        this.itemSelected = true;
      }
    });

    this.canvas.on('selection:cleared', () => {
      this.itemSelected = false;
    });

    this.draw();
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
        selectable: this.editable,
        id: sec.id,
        angle: sec.angle
      });

      this.canvas.add(g);
    } else {
      const w = sec.width / sec.column_num;
      const h = sec.height / sec.row_num;

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

      const g = new fabric.Group([], {
        top: sec.top,
        left: sec.left
      });

      for (let i = 0; i < sec.row_num; i++) {
        for (let j = 0; j < sec.column_num; j++) {
          g.addWithUpdate(new fabric.Rect({
            width: w,
            height: h,
            top: sec.top + h * i,
            left: sec.left + w * j,
            fill: '#f55',
            stroke: 'white',
            strokeWidth: 1
          }));
        }
      }

      const container = new fabric.Group([t, g], {
        selectable: this.editable,
        width: sec.width,
        height: sec.height,
        top: sec.top,
        left: sec.left,
        angle: sec.angle
      });

      this.canvas.add(container);
    }
  }
  }

  addLastSector() {
    const sec = this.sectors[this.sectors.length - 1];
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
        centeredRotation: true,
        // snapAngle: 45,
        selectable: this.editable,
        type: 'table',
        id: sec.id,
        number: 5,
      });

      this.canvas.add(g);
    } else {

      const w = sec.width / sec.column_num;
      const h = sec.height / sec.row_num;

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

      const g = new fabric.Group([], {
        top: sec.top,
        left: sec.left
      });

      for (let i = 0; i < sec.row_num; i++) {
        for (let j = 0; j < sec.column_num; j++) {
          g.addWithUpdate(new fabric.Rect({
            width: w,
            height: h,
            top: sec.top + h * i,
            left: sec.left + w * j,
            fill: '#f55',
            stroke: 'white',
            strokeWidth: 1
          }));
        }
      }

      const container = new fabric.Group([t, g], {
        selectable: this.editable,
        width: sec.width,
        height: sec.height,
        /*top: sec.top,
        left: sec.left*/
      });

      this.canvas.add(container);
    }
    console.log(JSON.stringify(this.canvas));
  }

  setZoom(newZoomLevel: number) {
    this.zoomLevel = newZoomLevel;
    this.canvas.setZoom(this.zoomLevel);

    this.canvas.setDimensions({
      width: this.canvas.width * this.zoomLevel,
      height: this.canvas.height * this.zoomLevel
    });
  }

 resetZoom() {
    this.zoomLevel = 1;
    this.canvas.setZoom(1);

    this.canvas.setDimensions({
      width: 700,
      height: 700
    });
  }

  clickedDelete() {
    const activeObject = this.canvas.getActiveObject().toObject();
    if (activeObject.type === 'table') {
      const sectorTitle = activeObject.objects[1].text;
      this.deleteSectorClicked.emit(sectorTitle);
      this.canvas.getActiveObjects().forEach((obj: any) => {
        this.canvas.remove(obj);
      });
      this.canvas.discardActiveObject().renderAll();
      console.log('Delete');
    } else {
      if (activeObject.type === 'group') {
        const sectorTitle = activeObject.objects[0].text;
        this.deleteSectorClicked.emit(sectorTitle);
        this.canvas.getActiveObjects().forEach((obj: any) => {
          this.canvas.remove(obj);
        });
        this.canvas.discardActiveObject().renderAll();
      }
    }
  }


sendCanvas() {
    this.hasUnsavedChanges = false;
    this.submitChangesEvent.emit(this.canvas.toObject());
  }

}
