import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBusinessEntityContact } from 'app/shared/model/business-entity-contact.model';
import { BusinessEntityContactService } from './business-entity-contact.service';
import { BusinessEntityContactDeleteDialogComponent } from './business-entity-contact-delete-dialog.component';

@Component({
  selector: 'jhi-business-entity-contact',
  templateUrl: './business-entity-contact.component.html'
})
export class BusinessEntityContactComponent implements OnInit, OnDestroy {
  businessEntityContacts: IBusinessEntityContact[];
  eventSubscriber: Subscription;

  constructor(
    protected businessEntityContactService: BusinessEntityContactService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.businessEntityContactService.query().subscribe((res: HttpResponse<IBusinessEntityContact[]>) => {
      this.businessEntityContacts = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInBusinessEntityContacts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBusinessEntityContact) {
    return item.id;
  }

  registerChangeInBusinessEntityContacts() {
    this.eventSubscriber = this.eventManager.subscribe('businessEntityContactListModification', () => this.loadAll());
  }

  delete(businessEntityContact: IBusinessEntityContact) {
    const modalRef = this.modalService.open(BusinessEntityContactDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.businessEntityContact = businessEntityContact;
  }
}
