import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { AddressTypesComponent } from './address-types.component';
import { AddressTypesDetailComponent } from './address-types-detail.component';
import { AddressTypesUpdateComponent } from './address-types-update.component';
import { AddressTypesDeleteDialogComponent } from './address-types-delete-dialog.component';
import { addressTypesRoute } from './address-types.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(addressTypesRoute)],
  declarations: [AddressTypesComponent, AddressTypesDetailComponent, AddressTypesUpdateComponent, AddressTypesDeleteDialogComponent],
  entryComponents: [AddressTypesDeleteDialogComponent]
})
export class EpmwebAddressTypesModule {}
