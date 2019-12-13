import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { PackageTypesComponent } from './package-types.component';
import { PackageTypesDetailComponent } from './package-types-detail.component';
import { PackageTypesUpdateComponent } from './package-types-update.component';
import { PackageTypesDeleteDialogComponent } from './package-types-delete-dialog.component';
import { packageTypesRoute } from './package-types.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(packageTypesRoute)],
  declarations: [PackageTypesComponent, PackageTypesDetailComponent, PackageTypesUpdateComponent, PackageTypesDeleteDialogComponent],
  entryComponents: [PackageTypesDeleteDialogComponent]
})
export class EpmwebPackageTypesModule {}
