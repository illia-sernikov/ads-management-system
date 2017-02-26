import { RouterModule, Routes } from '@angular/router';
import { AdminComponent, ErrorComponent, OperatorComponent, PublisherComponent } from './component';
import { LoginFormComponent } from './component/form/login/login-form.component';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  {
    path: 'login',
    component: LoginFormComponent
  },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'operator',
    component: OperatorComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'publisher/:key',
    component: PublisherComponent,
    canActivate: [AuthGuard]
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
