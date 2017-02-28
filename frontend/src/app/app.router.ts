import { RouterModule, Routes } from '@angular/router';
import { AdminComponent, ErrorComponent, OperatorComponent, PublisherComponent } from './component';
import { LoginFormComponent } from './component/form/login/login-form.component';
import { AuthAdminGuard } from './guards/auth-admin.guard';
import { AuthOperatorGuard } from './guards/auth-operator.guard';
import { AuthPublisherGuard } from './guards/auth-publisher.guard';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  {
    path: 'login',
    component: LoginFormComponent
  },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuard, AuthAdminGuard]
  },
  {
    path: 'operator',
    component: OperatorComponent,
    canActivate: [AuthGuard, AuthOperatorGuard]
  },
  {
    path: 'publisher/:key',
    component: PublisherComponent,
    canActivate: [AuthGuard, AuthPublisherGuard]
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
