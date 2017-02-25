export type UserRole = 'ADMIN' | 'OPERATOR' | 'PUBLISHER';

export interface User {
  key?: string;
  name: string;
  email: string;
  role?: UserRole;
}

export interface Account {
  email: string;
  password: string;
  role?: UserRole;
}
