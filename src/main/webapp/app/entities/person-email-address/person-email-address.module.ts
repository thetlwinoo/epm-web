import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { PersonEmailAddressComponent } from './person-email-address.component';
import { PersonEmailAddressDetailComponent } from './person-email-address-detail.component';
import { PersonEmailAddressUpdateComponent } from './person-email-address-update.component';
import { PersonEmailAddressDeleteDialogComponent } from './person-email-address-delete-dialog.component';
import { personEmailAddressRoute } from './person-email-address.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(personEmailAddressRoute)],
  declarations: [
    PersonEmailAddressComponent,
    PersonEmailAddressDetailComponent,
    PersonEmailAddressUpdateComponent,
    PersonEmailAddressDeleteDialogComponent
  ],
  entryComponents: [PersonEmailAddressDeleteDialogComponent]
})
export class EpmwebPersonEmailAddressModule {}
