import { Component, input } from '@angular/core';

@Component({
  selector: 'app-stat-card',
  standalone: true,
  template: `
    <div class="card">
      <span class="label">{{ label() }}</span>
      <strong class="value">{{ value() }}</strong>
      @if (hint()) { <span class="hint">{{ hint() }}</span> }
    </div>
  `,
  styles: [`
  .card { padding: 22px; border: 1px solid var(--color-border); border-radius: 10px;
          background: var(--color-surface); }
  .label { display: block; color: var(--color-muted); font-size: .85rem; }
  .value { display: block; margin: .5rem 0 .25rem; font-family: var(--font-title);
           font-size: 2rem; font-weight: 400; }
  .hint { color: rgba(255,255,255,.65); font-size: .8rem; }
`],
})
export class StatCard {
  label = input.required<string>();
  value = input.required<string | number>();
  hint = input<string>('');
}
