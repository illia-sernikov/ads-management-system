import { RouterModule, Routes } from '@angular/router';
import { AdminComponent, ErrorComponent, OperatorComponent, PublisherComponent } from './component';

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
    path: 'publisher/:key',
    component: PublisherComponent
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
