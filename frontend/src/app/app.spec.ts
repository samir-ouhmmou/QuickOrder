import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { App } from './app';
import { routes } from './app.routes';
import { Home } from './pages/home/home';

describe('App', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
      providers: [provideRouter(routes)],
    })
      .compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should render the home page sections', async () => {
    const fixture = TestBed.createComponent(Home);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('Experience Authentic');
    expect(compiled.querySelector('#about')).toBeTruthy();
    expect(compiled.querySelector('#services')).toBeTruthy();
    expect(compiled.querySelector('#gallery')).toBeTruthy();
    expect(compiled.querySelector('#testimonials')).toBeTruthy();
    expect(compiled.querySelector('#contact')).toBeTruthy();
  });
});
