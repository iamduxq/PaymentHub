import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GroupCategory, GroupCategoryService } from '../../../services/category-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import Toast from 'typescript-toastify';

@Component({
  selector: 'app-category-update-form',
  imports: [CommonModule, FormsModule],
  templateUrl: './category-update-form.html',
  styleUrl: './category-update-form.css',
})
export class CategoryUpdateForm {
  id!: number;
  
  // Model form
  formData: GroupCategory = {
    id: 0,
    paramType: '',
    paramValue: '',
    paramName: '',
    description: '',
    componentCode: '',
    effectiveDate: '' as any,
    endEffectiveDate: '' as any,
    status: 0,
    isActive: 0,
    isDisplay: 1
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: GroupCategoryService
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.service.getById(this.id).subscribe(res => {
      this.formData = res;
    });
  }
  
  update() {
    if (!this.formData.paramType?.trim() ||
            !this.formData.paramValue?.trim() ||
            !this.formData.paramName?.trim() ||
            !this.formData.componentCode ||
            !this.formData.effectiveDate) {
              const toast = new Toast({
                position: "bottom-left",
                toastMsg: "Vui lòng điền đầy đủ các trường bắt buộc",
                autoCloseTime: 2000,  // tự ẩn sau 2s
                canClose: true,
                showProgress: true,
                pauseOnHover: true,
                pauseOnFocusLoss: true,
                type: "error",
                theme: "light"
              }); return;
    }
    this.service.editCategory(this.id, this.formData).subscribe({
      next: () => {
        const toast = new Toast({
          position: "bottom-left",
          toastMsg: "🦚 Sửa thành công",
          autoCloseTime: 2000,  // tự ẩn sau 2s
          canClose: true,
          showProgress: true,
          pauseOnHover: true,
          pauseOnFocusLoss: true,
          type: "success",
          theme: "light"
          });
        setTimeout(() => this.router.navigate(['/category']), 1500);
      },
      error: (err) => {
        const toast = new Toast({
                  position: "top-right",
                  toastMsg: "❌ Lỗi khi thêm dữ liệu",
                  autoCloseTime: 3000,
                  canClose: true,
                  showProgress: true,
                  pauseOnHover: true,
                  pauseOnFocusLoss: true,
                  type: "error",
                  theme: "light"
                });              }
    });
  }

  autoResize(event: Event) {
    const textarea = event.target as HTMLTextAreaElement;
    textarea.style.height = 'auto';
    textarea.style.height = textarea.scrollHeight + 'px';
  }

}