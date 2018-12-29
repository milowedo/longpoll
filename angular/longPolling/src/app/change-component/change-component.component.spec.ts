import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeComponentComponent } from './change-component.component';

describe('ChangeComponentComponent', () => {
  let component: ChangeComponentComponent;
  let fixture: ComponentFixture<ChangeComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangeComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
