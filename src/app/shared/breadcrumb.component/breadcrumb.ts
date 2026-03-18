import { Component, computed, inject, signal } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-breadcrumb',
  standalone: true,
  templateUrl: './breadcrumb.html'
})
export class BreadcrumbComponent {
  private router = inject(Router);
  private currentUrl = signal(this.router.url);

  constructor() {
    this.router.events
      .pipe(filter(e => e instanceof NavigationEnd))
      .subscribe(() => {
        this.currentUrl.set(this.router.url);
      });
  }

  // computed breadcrumb
  breadcrumbs = computed(() => {
    const url = this.currentUrl();
    if (url.includes('add-category')) {
      return [
        'Trang chủ',
        'Tham số danh mục theo nhóm',
        'Thêm mới'
      ];
    }
    if (url.includes('category')) {
      return [
        'Trang chủ',
        'Tham số danh mục theo nhóm'
      ];
    }
    return ['Trang chủ'];
  });
}