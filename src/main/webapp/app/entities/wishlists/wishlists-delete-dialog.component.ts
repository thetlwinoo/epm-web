import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWishlists } from 'app/shared/model/wishlists.model';
import { WishlistsService } from './wishlists.service';

@Component({
  templateUrl: './wishlists-delete-dialog.component.html'
})
export class WishlistsDeleteDialogComponent {
  wishlists: IWishlists;

  constructor(protected wishlistsService: WishlistsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.wishlistsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'wishlistsListModification',
        content: 'Deleted an wishlists'
      });
      this.activeModal.dismiss(true);
    });
  }
}
