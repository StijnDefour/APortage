import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModaltemplateComponent } from './modaltemplate.component';

describe('ModaltemplateComponent', () => {
  let component: ModaltemplateComponent;
  let fixture: ComponentFixture<ModaltemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModaltemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModaltemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
