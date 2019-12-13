import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBuyingGroups } from 'app/shared/model/buying-groups.model';

@Component({
  selector: 'jhi-buying-groups-detail',
  templateUrl: './buying-groups-detail.component.html'
})
export class BuyingGroupsDetailComponent implements OnInit {
  buyingGroups: IBuyingGroups;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ buyingGroups }) => {
      this.buyingGroups = buyingGroups;
    });
  }

  previousState() {
    window.history.back();
  }
}
