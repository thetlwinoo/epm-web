import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactType } from 'app/shared/model/contact-type.model';

@Component({
  selector: 'jhi-contact-type-detail',
  templateUrl: './contact-type-detail.component.html'
})
export class ContactTypeDetailComponent implements OnInit {
  contactType: IContactType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contactType }) => {
      this.contactType = contactType;
    });
  }

  previousState() {
    window.history.back();
  }
}
