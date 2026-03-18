import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
export interface GroupCategory {
  id: number,
  paramType: string,
  paramValue: string,
  paramName: string,
  description: string,
  componentCode: string,
  effectiveDate: Date,
  endEffectiveDate: Date,
  status: number,
  isActive: number,
  isDisplay: number
}

@Injectable({
  providedIn: 'root',
})
export class GroupCategoryService {
  private API = 'http://localhost:8080/api/category';
  constructor(private http: HttpClient) {}
  // Lấy db table
  getAll(): Observable<GroupCategory[]> {
    return this.http.get<GroupCategory[]>(`${this.API}`);
  }

  // Xóa
  delete(id: number) {
    return this.http.delete(`${this.API}/${id}`);
  }
  
  // Tìm kiếm theo tiêu chí
  search(filter: any) {
    return this.http.post<any[]>(`${this.API}/search`, filter);
  }

  // Thêm
  addParam(data: Partial<GroupCategory>) {
    return this.http.post<GroupCategory>(`${this.API}/add-param-type`, data);
  }

  // Sửa dữ liệu
  editCategory(id: number, data: Partial<GroupCategory>) {
    return this.http.put<GroupCategory>(`${this.API}/edit/${id}`, data);
  }

  // Lấy dữ liệu theo id
  getById(id: number): Observable<GroupCategory> {
    return this.http.get<GroupCategory>(`${this.API}/search/${id}`);
  }

}
