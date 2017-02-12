import { RouterModule, Routes } from '@angular/router';
import { AdminComponent, ErrorComponent } from './component';

const routes: Routes = [
  {
    path: 'admin',
    component: AdminComponent
  },
  {
    path: '',
    redirectTo: '/admin',
    pathMatch: 'full'
  },
  {
    path: '**',
    component: ErrorComponent
  }
];

export const AppRouterModule = RouterModule.forRoot(routes);
