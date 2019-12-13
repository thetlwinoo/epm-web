import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { CustomerCategoriesComponent } from './customer-categories.component';
import { CustomerCategoriesDetailComponent } from './customer-categories-detail.component';
import { CustomerCategoriesUpdateComponent } from './customer-categories-update.component';
import { CustomerCategoriesDeleteDialogComponent } from './customer-categories-delete-dialog.component';
import { customerCategoriesRoute } from './customer-categories.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(customerCategoriesRoute)],
  declarations: [
    CustomerCategoriesComponent,
    CustomerCategoriesDetailComponent,
    CustomerCategoriesUpdateComponent,
    CustomerCategoriesDeleteDialogComponent
  ],
  entryComponents: [CustomerCategoriesDeleteDialogComponent]
})
export class EpmwebCustomerCategoriesModule {}
