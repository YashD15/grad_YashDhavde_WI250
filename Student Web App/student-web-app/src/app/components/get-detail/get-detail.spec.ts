import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetDetail } from './get-detail';

describe('GetDetail', () => {
  let component: GetDetail;
  let fixture: ComponentFixture<GetDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GetDetail],
    }).compileComponents();

    fixture = TestBed.createComponent(GetDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
