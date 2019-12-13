import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpmwebTestModule } from '../../../test.module';
import { WishlistsComponent } from 'app/entities/wishlists/wishlists.component';
import { WishlistsService } from 'app/entities/wishlists/wishlists.service';
import { Wishlists } from 'app/shared/model/wishlists.model';

describe('Component Tests', () => {
  describe('Wishlists Management Component', () => {
    let comp: WishlistsComponent;
    let fixture: ComponentFixture<WishlistsComponent>;
    let service: WishlistsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EpmwebTestModule],
        declarations: [WishlistsComponent],
        providers: []
      })
        .overrideTemplate(WishlistsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WishlistsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WishlistsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Wishlists(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.wishlists[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
