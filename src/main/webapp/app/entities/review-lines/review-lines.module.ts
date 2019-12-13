import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpmwebSharedModule } from 'app/shared/shared.module';
import { ReviewLinesComponent } from './review-lines.component';
import { ReviewLinesDetailComponent } from './review-lines-detail.component';
import { ReviewLinesUpdateComponent } from './review-lines-update.component';
import { ReviewLinesDeleteDialogComponent } from './review-lines-delete-dialog.component';
import { reviewLinesRoute } from './review-lines.route';

@NgModule({
  imports: [EpmwebSharedModule, RouterModule.forChild(reviewLinesRoute)],
  declarations: [ReviewLinesComponent, ReviewLinesDetailComponent, ReviewLinesUpdateComponent, ReviewLinesDeleteDialogComponent],
  entryComponents: [ReviewLinesDeleteDialogComponent]
})
export class EpmwebReviewLinesModule {}
