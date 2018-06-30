import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  tapped = 0;
  pressed = 0;

  constructor(public navCtrl: NavController) {

  }

  onDidReset(resetType: string) {
    switch(resetType) {
      case 'tap':
        this.tapped = 0;
        break;
      case 'press':
        this.pressed = 0;
        break;
      default:
        this.tapped = 0;
        this.pressed = 0;
    }
  }

  onTap() {
    this.tapped++;
    console.log('tapped');
  }

  onPress() {
    this.pressed++;
    console.log('pressed')
  }

  onWin() {
    return this.tapped == 2 && this.pressed == 4;
  }
  
}
