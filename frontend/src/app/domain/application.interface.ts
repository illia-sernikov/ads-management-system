import { User } from './';

export type AppType = 'ANDROID' | 'IOS' | 'WEBSITE';

export type ContentType = 'VIDEO' | 'IMAGE' | 'HTML';

export interface ApplicationRequest {
  name: string;
  type: AppType;
  contentTypes: ContentType[];
  publisherKey?: string;
}

export interface Application extends ApplicationRequest {
  key?: string;
  owner: User;
}
