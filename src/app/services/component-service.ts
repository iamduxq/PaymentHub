import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ComponentItem {
  id: number;
  componentCode: string;
}

@Injectable({ providedIn: 'root' })
export class ComponentService {
  private API = 'http://localhost:8080/api/components';

  constructor(private http: HttpClient) {}

  getComponentCode(): Observable<ComponentItem[]> {
    return this.http.get<ComponentItem[]>(this.API);
  }
}