import { Component } from '@angular/core';
import { Hero } from './sections/hero/hero';
import { Chefs } from './sections/chefs/chefs';

@Component({
  selector: 'app-home',
  imports: [Hero, Chefs],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {}