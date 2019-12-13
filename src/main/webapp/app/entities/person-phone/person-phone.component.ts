import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPersonPhone } from 'app/shared/model/person-phone.model';
import { PersonPhoneService } from './person-phone.service';
import { PersonPhoneDeleteDialogComponent } from './person-phone-delete-dialog.component';

@Component({
  selector: 'jhi-person-phone',
  templateUrl: './person-phone.component.html'
})
export class PersonPhoneComponent implements OnInit, OnDestroy {
  personPhones: IPersonPhone[];
  eventSubscriber: Subscription;

  constructor(
    protected personPhoneService: PersonPhoneService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.personPhoneService.query().subscribe((res: HttpResponse<IPersonPhone[]>) => {
      this.personPhones = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPersonPhones();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPersonPhone) {
    return item.id;
  }

  registerChangeInPersonPhones() {
    this.eventSubscriber = this.eventManager.subscribe('personPhoneListModification', () => this.loadAll());
  }

  delete(personPhone: IPersonPhone) {
    const modalRef = this.modalService.open(PersonPhoneDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.personPhone = personPhone;
  }
}
