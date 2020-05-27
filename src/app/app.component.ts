import { Component, OnInit } from '@angular/core';
import { PetBreederRestService } from './pet-breeder-rest.service';
import { catchError, retry } from 'rxjs/operators';
import { Interval} from "./model";
import { Specie} from "./model";
import { Pet} from "./model";
import { Environment} from "./model";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Pet Breeder';
  mainScreenTitle = 'Environments';

  //private petBreederRestService;
  selectedEnvironment: Environment;
  selectedPet: Pet;

  environments: Environment[];
  petsToAllocate: Pet[];

  constructor(
    private petBreederRestService: PetBreederRestService){
    
      //this.petBreederRestService = petBreederRestService;
  }

  ngOnInit(): void {    
      this.petBreederRestService
        .environments()
        .subscribe(data => {
          this.environments = data;
          for (let env of this.environments) {
            console.log(env.description);              
          }
          console.log(this.environments);
         });

      this.petBreederRestService
         .pets('DEALLOCATED')
         .subscribe(data => {
          this.petsToAllocate = data;
         });
  }

  public environmentSelected(env: Environment) {
    this.selectedEnvironment = env;
  }

  public petSelected(pet: Pet) {
    this.selectedPet = pet;
  }

  public editEnvironment(env:Environment) {
    this.environmentSelected(env);
    //this.router.navigate(['edit-environment', { env: Environment }]);
  }

  public editPet(pet:Pet) {
    this.petSelected(pet);
  }
}
