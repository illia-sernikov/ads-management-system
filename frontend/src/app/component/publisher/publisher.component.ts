import 'rxjs/add/operator/takeWhile';
import { Location } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Application } from '../../domain';
import { ApplicationService } from '../../service';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'ams-publisher',
  templateUrl: './publisher.component.html',
  styleUrls: ['./publisher.component.css']
})
export class PublisherComponent implements OnInit, OnDestroy {

  publisherKey: string;
  applications: Application[] = [];

  private active = true;

  constructor(private route: ActivatedRoute,
              private appService: ApplicationService,
              private location: Location,
              private authService: AuthService) {
  }

  ngOnInit() {
    this.publisherKey = this.route.snapshot.params['key'];

    this.appService.getAllPublisherApps(this.publisherKey)
        .takeWhile(() => this.active)
        .subscribe(apps => this.applications = apps);
  }

  ngOnDestroy(): void {
    this.active = false;
  }

  onAppCreated(app: Application): void {
    this.applications.push(app);
  }

  isVisibleBackButton(): boolean {
    return this.authService.isOperator();
  }

  onBack(): void {
    this.location.back();
  }
}
