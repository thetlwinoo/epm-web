import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWishlists } from 'app/shared/model/wishlists.model';
import { WishlistsService } from './wishlists.service';
import { WishlistsDeleteDialogComponent } from './wishlists-delete-dialog.component';

@Component({
  selector: 'jhi-wishlists',
  templateUrl: './wishlists.component.html'
})
export class WishlistsComponent implements OnInit, OnDestroy {
  wishlists: IWishlists[];
  eventSubscriber: Subscription;

  constructor(protected wishlistsService: WishlistsService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.wishlistsService.query().subscribe((res: HttpResponse<IWishlists[]>) => {
      this.wishlists = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInWishlists();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IWishlists) {
    return item.id;
  }

  registerChangeInWishlists() {
    this.eventSubscriber = this.eventManager.subscribe('wishlistsListModification', () => this.loadAll());
  }

  delete(wishlists: IWishlists) {
    const modalRef = this.modalService.open(WishlistsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.wishlists = wishlists;
  }
}
