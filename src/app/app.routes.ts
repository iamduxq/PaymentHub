import { Routes } from '@angular/router';
import { CategoryAddPage } from './pages/category-add/category-add-page/category-add-page';
import { CategoryPage } from './pages/category/category-page/category-page';
import { CategoryUpdatePage } from './pages/category-update/category-update-page/category-update-page';

export const routes: Routes = [
    {path: 'category', component: CategoryPage},
    {path: 'add-category', component: CategoryAddPage},
    {path: 'update-category/:id', component: CategoryUpdatePage},
    {path: '', redirectTo: 'category', pathMatch: 'full'}
];
