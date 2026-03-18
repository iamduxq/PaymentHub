import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CategoryCreateForm } from '../category-create-form/category-create-form';

@Component({
  selector: 'app-category-add-page',
  imports: [CategoryCreateForm],
  templateUrl: './category-add-page.html',
  styleUrl: './category-add-page.css',
})
export class CategoryAddPage {
  constructor(private router: Router){};
  gotoCategoryPage() {
    this.router.navigate(['/category']);
  }
}
