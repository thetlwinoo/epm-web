import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpmwebTestModule } from '../../../test.module';
import { BuyingGroupsDetailComponent } from 'app/entities/buying-groups/buying-groups-detail.component';
import { BuyingGroups } from 'app/shared/model/buying-groups.model';

describe('Component Tests', () => {
  describe('BuyingGroups Management Detail Component', () => {
    let comp: BuyingGroupsDetailComponent;
    let fixture: ComponentFixture<BuyingGroupsDetailComponent>;
    const route = ({ data: of({ buyingGroups: new BuyingGroups(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [BuyingGroupsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BuyingGroupsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BuyingGroupsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.buyingGroups).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
