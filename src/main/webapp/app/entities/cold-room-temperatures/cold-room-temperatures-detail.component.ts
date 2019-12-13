import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IColdRoomTemperatures } from 'app/shared/model/cold-room-temperatures.model';

@Component({
  selector: 'jhi-cold-room-temperatures-detail',
  templateUrl: './cold-room-temperatures-detail.component.html'
})
export class ColdRoomTemperaturesDetailComponent implements OnInit {
  coldRoomTemperatures: IColdRoomTemperatures;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ coldRoomTemperatures }) => {
      this.coldRoomTemperatures = coldRoomTemperatures;
    });
  }

  previousState() {
    window.history.back();
  }
}
