import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBusinessEntityContact } from 'app/shared/model/business-entity-contact.model';

@Component({
  selector: 'jhi-business-entity-contact-detail',
  templateUrl: './business-entity-contact-detail.component.html'
})
export class BusinessEntityContactDetailComponent implements OnInit {
  businessEntityContact: IBusinessEntityContact;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ businessEntityContact }) => {
      this.businessEntityContact = businessEntityContact;
    });
  }

  previousState() {
    window.history.back();
  }
}
