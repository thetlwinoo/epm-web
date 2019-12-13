import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { ComparesComponent } from './compares.component';
import { ComparesDetailComponent } from './compares-detail.component';
import { ComparesUpdateComponent } from './compares-update.component';
import { ComparesDeleteDialogComponent } from './compares-delete-dialog.component';
import { comparesRoute } from './compares.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(comparesRoute)],
  declarations: [ComparesComponent, ComparesDetailComponent, ComparesUpdateComponent, ComparesDeleteDialogComponent],
  entryComponents: [ComparesDeleteDialogComponent]
})
export class EpmwebComparesModule {}
