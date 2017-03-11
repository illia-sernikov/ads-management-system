import { User } from './';
import { AppType } from './application-type';
import { ContentType } from './content-type';

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
