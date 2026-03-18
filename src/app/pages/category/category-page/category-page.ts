import { Component, OnInit } from '@angular/core';
import { GroupCategory, GroupCategoryService } from '../../../services/category-service';
import { CategoryTable } from '../category-table/category-table';
import { CategoryFilter } from '../category-filter/category-filter';
import { Router } from '@angular/router';

@Component({
  selector: 'app-group-category-page',
  standalone: true,
  imports: [CategoryFilter, CategoryTable],
  styleUrl: './category-page.css',
  templateUrl: './category-page.html',
})

  export class CategoryPage implements OnInit {
    categories: GroupCategory[] = [];
    constructor(private categoryService: GroupCategoryService, private router: Router) {}
    
    ngOnInit(): void {
      this.loadCategories();
    }

    loadCategories() {
    this.categoryService.getAll().subscribe({
      next: (res) => (this.categories = res),
      error: (err) => console.error('Lỗi load dữ liệu', err),
    });
  }

    onSearch(filter: any) {
      this.categoryService.search(filter).subscribe({
        next: (res) => {
          this.categories = res;
        },
        error: (err) => console.error(err)
      });
    }

    redirectCreate(): void {
      this.router.navigate(['/add-category']);
    }
}

