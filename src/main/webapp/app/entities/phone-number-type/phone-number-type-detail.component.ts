import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPhoneNumberType } from 'app/shared/model/phone-number-type.model';

@Component({
  selector: 'jhi-phone-number-type-detail',
  templateUrl: './phone-number-type-detail.component.html'
})
export class PhoneNumberTypeDetailComponent implements OnInit {
  phoneNumberType: IPhoneNumberType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ phoneNumberType }) => {
      this.phoneNumberType = phoneNumberType;
    });
  }

  previousState() {
    window.history.back();
  }
}
