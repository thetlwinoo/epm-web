import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContactType } from 'app/shared/model/contact-type.model';
import { ContactTypeService } from './contact-type.service';
import { ContactTypeDeleteDialogComponent } from './contact-type-delete-dialog.component';

@Component({
  selector: 'jhi-contact-type',
  templateUrl: './contact-type.component.html'
})
export class ContactTypeComponent implements OnInit, OnDestroy {
  contactTypes: IContactType[];
  eventSubscriber: Subscription;

  constructor(
    protected contactTypeService: ContactTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.contactTypeService.query().subscribe((res: HttpResponse<IContactType[]>) => {
      this.contactTypes = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInContactTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IContactType) {
    return item.id;
  }

  registerChangeInContactTypes() {
    this.eventSubscriber = this.eventManager.subscribe('contactTypeListModification', () => this.loadAll());
  }

  delete(contactType: IContactType) {
    const modalRef = this.modalService.open(ContactTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contactType = contactType;
  }
}
