import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscribeComponentComponent } from './subscribe-component.component';

describe('SubscribeComponentComponent', () => {
  let component: SubscribeComponentComponent;
  let fixture: ComponentFixture<SubscribeComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubscribeComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubscribeComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
