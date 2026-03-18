import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './shared/header.component/header.component';
import { SidebarComponent } from './shared/sidebar.component/sidebar.component';
import { BreadcrumbComponent } from "./shared/breadcrumb.component/breadcrumb";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, SidebarComponent, BreadcrumbComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('TestRouter');
}
