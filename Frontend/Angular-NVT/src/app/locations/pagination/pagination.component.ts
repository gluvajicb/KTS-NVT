import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit {

  @Input() totalItems: number;
  @Input() pageSize: number;
  @Output() eventPageSelected: EventEmitter<number>;
  pages: number[];
  activePage: number;

  constructor() {
    this.activePage = 1;
    this.eventPageSelected = new EventEmitter();
   }

  private getNoPages(): number {
   return Math.ceil(this.totalItems / this.pageSize);
  }

  ngOnInit() {
    this.pages = [];
    for (let i = 1; i <= this.getNoPages(); i++) {
      this.pages.push(i);
    }
  }

  pageSelected(newPage: number) {
    if (newPage >= 1 && newPage <= this.getNoPages()) {
      this.activePage = newPage;
      this.eventPageSelected.emit(this.activePage);
    }
  }

}
