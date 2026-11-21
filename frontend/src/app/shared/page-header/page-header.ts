import { Component, input } from '@angular/core';

@Component({
  selector: 'app-page-header',
  standalone: true,
  template: `
    <header>
      <p class="eyebrow">{{ eyebrow() }}</p>
      <h1>{{ title() }}</h1>
      @if (subtitle()) {
        <p class="sub">{{ subtitle() }}</p>
      }
    </header>
  `,
  styles: [
    `
      .eyebrow {
        margin: 0 0 0.4rem;
        color: var(--color-primary);
        font-size: 0.76rem;
        font-weight: 700;
        letter-spacing: 0.18em;
        text-transform: uppercase;
      }
      h1 {
        margin: 0;
        font-family: var(--font-title);
        font-size: 2.2rem;
        line-height: 1.2;
      }
      .sub {
        margin: 0.4rem 0 0;
        color: var(--color-muted);
      }
    `,
  ],
})
export class PageHeader {
  eyebrow = input('Administration');
  title = input.required<string>();
  subtitle = input('');
}
