import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeople } from 'app/shared/model/people.model';

@Component({
  selector: 'jhi-people-detail',
  templateUrl: './people-detail.component.html'
})
export class PeopleDetailComponent implements OnInit {
  people: IPeople;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ people }) => {
      this.people = people;
    });
  }

  previousState() {
    window.history.back();
  }
}
