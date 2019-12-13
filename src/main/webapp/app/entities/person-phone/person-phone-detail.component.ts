import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonPhone } from 'app/shared/model/person-phone.model';

@Component({
  selector: 'jhi-person-phone-detail',
  templateUrl: './person-phone-detail.component.html'
})
export class PersonPhoneDetailComponent implements OnInit {
  personPhone: IPersonPhone;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ personPhone }) => {
      this.personPhone = personPhone;
    });
  }

  previousState() {
    window.history.back();
  }
}
