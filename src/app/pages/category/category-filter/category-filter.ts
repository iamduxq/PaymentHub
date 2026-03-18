import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GroupCategorySearch } from '../../../models/category-group-search.model';

@Component({
  selector: 'app-category-filter',
  standalone: true,
  imports: [CommonModule, FormsModule],
  styleUrl: './category-filter.css',
  templateUrl: './category-filter.html',
})
export class CategoryFilter {
  filter: GroupCategorySearch = {
    paramType: '',
    paramValue: '',
    paramName: '',
    status: null,
    isActive: null
  }

  selectedStatus: number | null = null;
  selectedActive: number | null = null;

  statusOptions = [
    { label: 'Mới', value: 1},
    { label: 'Chờ duyệt', value: 3},
    { label: 'Đã duyệt', value: 4},
    { label: 'Từ chối', value: 5},
    { label: 'Hủy duyệt', value: 7},
  ]

  activeOptions = [
    { label: 'Hoạt động', value: 1},
    { label: 'Không hoạt động', value: 0},
  ]

  @Output() search = new EventEmitter<any>();

  // Search event
  onsearch() {
  this.filter.status = this.selectedStatus;
  this.filter.isActive = this.selectedActive;
  this.search.emit(this.filter);
}

  // Reset filter event
  reset() {
  this.filter = {
    paramType: '',
    paramValue: '',
    paramName: '',
    isActive: null,
    status: null,
  };
  this.selectedStatus = null;
  this.selectedActive = null;
  this.search.emit(this.filter);
  }
}
