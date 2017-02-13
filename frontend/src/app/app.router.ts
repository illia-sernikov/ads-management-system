import { RouterModule, Routes } from '@angular/router';
import { AdminComponent, ErrorComponent, OperatorComponent } from './component';

const routes: Routes = [
  {
    path: 'admin',
    component: AdminComponent
  },
  {
    path: 'operator',
    component: OperatorComponent
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
