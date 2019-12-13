import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMaterials } from 'app/shared/model/materials.model';

@Component({
  selector: 'jhi-materials-detail',
  templateUrl: './materials-detail.component.html'
})
export class MaterialsDetailComponent implements OnInit {
  materials: IMaterials;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ materials }) => {
      this.materials = materials;
    });
  }

  previousState() {
    window.history.back();
  }
}
