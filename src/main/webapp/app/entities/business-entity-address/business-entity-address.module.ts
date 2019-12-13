import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { BusinessEntityAddressComponent } from './business-entity-address.component';
import { BusinessEntityAddressDetailComponent } from './business-entity-address-detail.component';
import { BusinessEntityAddressUpdateComponent } from './business-entity-address-update.component';
import { BusinessEntityAddressDeleteDialogComponent } from './business-entity-address-delete-dialog.component';
import { businessEntityAddressRoute } from './business-entity-address.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(businessEntityAddressRoute)],
  declarations: [
    BusinessEntityAddressComponent,
    BusinessEntityAddressDetailComponent,
    BusinessEntityAddressUpdateComponent,
    BusinessEntityAddressDeleteDialogComponent
  ],
  entryComponents: [BusinessEntityAddressDeleteDialogComponent]
})
export class EpmwebBusinessEntityAddressModule {}
