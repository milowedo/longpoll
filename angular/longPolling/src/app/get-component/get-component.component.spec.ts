import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GetComponentComponent } from './get-component.component';

describe('GetComponentComponent', () => {
  let component: GetComponentComponent;
  let fixture: ComponentFixture<GetComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GetComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GetComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
