import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class NavigatorService {

  constructor(private router: Router) { }

  navigate(route: string): Promise<boolean> {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation='reload';

    return this.router.navigate([route]);
  }
}