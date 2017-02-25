import { RouterModule, Routes } from '@angular/router';
import { AdminComponent, ErrorComponent, OperatorComponent, PublisherComponent } from './component';
import { LoginFormComponent } from './component/form/login/login-form.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginFormComponent
  },
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
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: '**',
    component: ErrorComponent
  }
];

export const AppRouterModule = RouterModule.forRoot(routes);
