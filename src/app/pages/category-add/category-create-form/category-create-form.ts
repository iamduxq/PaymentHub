import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ComponentService, ComponentItem } from '../../../services/component-service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { GroupCategory, GroupCategoryService } from '../../../services/category-service';
import Toast from 'typescript-toastify';

@Component({
  selector: 'app-category-create-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './category-create-form.html',
  styleUrl: './category-create-form.css'
})
export class CategoryCreateForm {
  components: ComponentItem[] = [];
  selectedComponent: string = '';

  // Model form
  formData: Partial<GroupCategory> = {
    paramName: '',
    paramValue: '',
    paramType: '',
    description: '',
    componentCode: '',
    effectiveDate: undefined,
    endEffectiveDate: undefined,
    status: 3,
    isActive: 1,
    isDisplay: 1
  }

  constructor(
    private router: Router,
    private componentService: ComponentService,
    private categoryService: GroupCategoryService
  ) {}

  loadComponents() {
    this.componentService.getComponentCode().subscribe({
      next: (res) => this.components = res,
      error: (err) => console.error(err)
    });
  }

  ngOnInit(): void {
    this.loadComponents();
  }

  autoResize(event: Event) {
    const textarea = event.target as HTMLTextAreaElement;
    textarea.style.height = 'auto';
    textarea.style.height = textarea.scrollHeight + 'px';
  }

  saveAndSubmit() {
    this.formData.componentCode = this.selectedComponent;
    if (!this.formData.paramType?.trim() ||
      !this.formData.paramValue?.trim() ||
      !this.formData.paramName?.trim() ||
      !this.formData.componentCode ||
      !this.formData.effectiveDate) {
        const toast = new Toast({
          position: "bottom-left",
          toastMsg: "Vui lòng điền đầy đủ các trường bắt buộc",
          autoCloseTime: 2000,
          canClose: true,
          showProgress: true,
          pauseOnHover: true,
          pauseOnFocusLoss: true,
          type: "error",
          theme: "light"
        }); return;
    }

    this.categoryService.addParam(this.formData).subscribe({
      next: (res) => {
        const toast = new Toast({
          position: "bottom-left",
          toastMsg: "🦚 Thêm thành công",
          autoCloseTime: 2000,
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
        });
        console.error(err);
      }
    });
  }
}