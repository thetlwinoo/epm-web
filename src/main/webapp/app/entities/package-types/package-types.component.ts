import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPackageTypes } from 'app/shared/model/package-types.model';
import { PackageTypesService } from './package-types.service';
import { PackageTypesDeleteDialogComponent } from './package-types-delete-dialog.component';

@Component({
  selector: 'jhi-package-types',
  templateUrl: './package-types.component.html'
})
export class PackageTypesComponent implements OnInit, OnDestroy {
  packageTypes: IPackageTypes[];
  eventSubscriber: Subscription;

  constructor(
    protected packageTypesService: PackageTypesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.packageTypesService.query().subscribe((res: HttpResponse<IPackageTypes[]>) => {
      this.packageTypes = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPackageTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPackageTypes) {
    return item.id;
  }

  registerChangeInPackageTypes() {
    this.eventSubscriber = this.eventManager.subscribe('packageTypesListModification', () => this.loadAll());
  }

  delete(packageTypes: IPackageTypes) {
    const modalRef = this.modalService.open(PackageTypesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.packageTypes = packageTypes;
  }
}
