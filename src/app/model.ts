export class Interval {
    constructor (minimum:Number, maximum:Number){}
  }
  
  export class Specie {
    constructor (
        public id:string, 
        public name:string, 
        public temperatureRange:Interval){}
  }
  
  export class Pet {
    constructor (
        public id:string, 
        public name:string, 
        public specie: Specie, 
        public temperatureRange: Interval) {}
  }
  
  export class Environment{
    constructor(
      public id: string, 
      public deviceId: string, 
      public description: string, 
      public state: string, 
      public temperatureRange: Interval, 
      public currentTemperature: Number, 
      public pets: Pet[]){}
  }