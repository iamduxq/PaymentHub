import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { GroupCategory, GroupCategoryService } from '../../../services/category-service';
import Toast from 'typescript-toastify';
import { Router } from '@angular/router';

@Component({
  selector: 'app-category-table',
  imports: [CommonModule],
  templateUrl: './category-table.html',
})

export class CategoryTable {
  @Input() categories: GroupCategory[] = [];
  constructor(private service: GroupCategoryService, private router: Router) {}

  statusMap: Record<number, {label: string, class: string}> = {
    1: { label: 'Mới', class: 'text-blue-700 bg-blue-50 border border-blue-200' },
    3: { label: 'Chờ duyệt', class: 'text-yellow-700 bg-yellow-50 border border-yellow-200' },
    4: { label: 'Đã duyệt', class: 'text-green-700 bg-green-50 border border-green-200' },
    5: { label: 'Từ chối', class: 'text-red-700 bg-red-50 border border-red-200' },
    7: { label: 'Hủy duyệt', class: 'text-gray-700 bg-gray-50 border border-gray-200' }
  }

  deleteCategory(id: number) {
    const confirmDelete = confirm("Bạn có chắc muốn xóa bản ghi này?");
    if (!confirmDelete) return;
    this.service.delete(id).subscribe({
      next: () => {
        const toast = new Toast({
          position: "bottom-left",
          toastMsg: "🦚 Xóa thành công",
          autoCloseTime: 2000,
          canClose: true,
          showProgress: true,
          pauseOnHover: true,
          pauseOnFocusLoss: true,
          type: "default",
          theme: "light"
        });
        this.categories = this.categories.filter((c) => c.id !== id);
      },
      error: (err) => {
        console.error("Xóa thất bại", err);
      }
    });
  }
  
  gotoUpdatePage(id: number) {
    this.router.navigate(['/update-category', id]);
  }
}