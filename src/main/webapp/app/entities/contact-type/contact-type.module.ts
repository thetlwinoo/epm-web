import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { ContactTypeComponent } from './contact-type.component';
import { ContactTypeDetailComponent } from './contact-type-detail.component';
import { ContactTypeUpdateComponent } from './contact-type-update.component';
import { ContactTypeDeleteDialogComponent } from './contact-type-delete-dialog.component';
import { contactTypeRoute } from './contact-type.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(contactTypeRoute)],
  declarations: [ContactTypeComponent, ContactTypeDetailComponent, ContactTypeUpdateComponent, ContactTypeDeleteDialogComponent],
  entryComponents: [ContactTypeDeleteDialogComponent]
})
export class EpmwebContactTypeModule {}
