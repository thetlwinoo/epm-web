import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from './people.service';
import { PeopleDeleteDialogComponent } from './people-delete-dialog.component';

@Component({
  selector: 'jhi-people',
  templateUrl: './people.component.html'
})
export class PeopleComponent implements OnInit, OnDestroy {
  people: IPeople[];
  eventSubscriber: Subscription;

  constructor(protected peopleService: PeopleService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.peopleService.query().subscribe((res: HttpResponse<IPeople[]>) => {
      this.people = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPeople();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPeople) {
    return item.id;
  }

  registerChangeInPeople() {
    this.eventSubscriber = this.eventManager.subscribe('peopleListModification', () => this.loadAll());
  }

  delete(people: IPeople) {
    const modalRef = this.modalService.open(PeopleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.people = people;
  }
}
