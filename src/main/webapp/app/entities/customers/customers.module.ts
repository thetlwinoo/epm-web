import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { CustomersComponent } from './customers.component';
import { CustomersDetailComponent } from './customers-detail.component';
import { CustomersUpdateComponent } from './customers-update.component';
import { CustomersDeleteDialogComponent } from './customers-delete-dialog.component';
import { customersRoute } from './customers.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(customersRoute)],
  declarations: [CustomersComponent, CustomersDetailComponent, CustomersUpdateComponent, CustomersDeleteDialogComponent],
  entryComponents: [CustomersDeleteDialogComponent]
})
export class EpmwebCustomersModule {}
