import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map } from "rxjs/operators";
import { catchError, retry } from 'rxjs/operators';
import { Environment, Pet} from "./model";




@Injectable({
  providedIn: 'root'
})
export class PetBreederRestService {

  constructor(private http: HttpClient) { }

  environments(): Observable<Environment[]>{
    return this
      .http
      .get('http://localhost:8081/environments')
      .pipe(
       
        map(
          (data: any[]) =>
          data.map(
            (item: any) =>
              new Environment(
                item.id, 
                item.deviceId, 
                item.description, 
                item.state, 
                item.temperatureRange, 
                item.currentTemperature, 
                item.pets))),        
      );
  }

  pets(state:String): Observable<Pet[]>{
    return this.http.get('http://localhost:8081/pets?state='+state)
      .pipe(
        map(
          (data: any[]) => data.map(
            (item: any) => new Pet(
              item.id,
              item.name,
              item.specie,
              item.temperatureRange
            ))),        
      );
  }

 
}
