import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { BusinessEntityContactComponent } from './business-entity-contact.component';
import { BusinessEntityContactDetailComponent } from './business-entity-contact-detail.component';
import { BusinessEntityContactUpdateComponent } from './business-entity-contact-update.component';
import { BusinessEntityContactDeleteDialogComponent } from './business-entity-contact-delete-dialog.component';
import { businessEntityContactRoute } from './business-entity-contact.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(businessEntityContactRoute)],
  declarations: [
    BusinessEntityContactComponent,
    BusinessEntityContactDetailComponent,
    BusinessEntityContactUpdateComponent,
    BusinessEntityContactDeleteDialogComponent
  ],
  entryComponents: [BusinessEntityContactDeleteDialogComponent]
})
export class EpmwebBusinessEntityContactModule {}
