import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { PersonPhoneComponent } from './person-phone.component';
import { PersonPhoneDetailComponent } from './person-phone-detail.component';
import { PersonPhoneUpdateComponent } from './person-phone-update.component';
import { PersonPhoneDeleteDialogComponent } from './person-phone-delete-dialog.component';
import { personPhoneRoute } from './person-phone.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(personPhoneRoute)],
  declarations: [PersonPhoneComponent, PersonPhoneDetailComponent, PersonPhoneUpdateComponent, PersonPhoneDeleteDialogComponent],
  entryComponents: [PersonPhoneDeleteDialogComponent]
})
export class EpmwebPersonPhoneModule {}
