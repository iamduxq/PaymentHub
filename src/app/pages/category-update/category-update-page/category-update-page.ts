import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CategoryUpdateForm } from '../category-update-form/category-update-form';
@Component({
  selector: 'app-category-update-page',
  imports: [CategoryUpdateForm],
  templateUrl: './category-update-page.html',
  styleUrl: './category-update-page.css',
})
export class CategoryUpdatePage {
  constructor(private router: Router){};
  gotoCategoryPage() {
    this.router.navigate(['/category']);
  }
}
