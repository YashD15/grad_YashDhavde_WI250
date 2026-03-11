import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetStudents } from './get-students';

describe('GetStudents', () => {
  let component: GetStudents;
  let fixture: ComponentFixture<GetStudents>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GetStudents],
    }).compileComponents();

    fixture = TestBed.createComponent(GetStudents);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
