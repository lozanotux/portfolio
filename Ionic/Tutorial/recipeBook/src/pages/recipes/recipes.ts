import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { EditRecipePage } from '../edit-recipe/edit-recipe';

@Component({
  selector: 'page-recipes',
  templateUrl: 'recipes.html',
})
export class RecipesPage {

  constructor(private navCtrl: NavController) {}

  onNewRecipe() {
    this.navCtrl.push(EditRecipePage, {mode: 'New'});
  }

}
