import { TestBed } from '@angular/core/testing';

import { PetBreederRestService } from './pet-breeder-rest.service';

describe('PetBreederRestService', () => {
  let service: PetBreederRestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PetBreederRestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
