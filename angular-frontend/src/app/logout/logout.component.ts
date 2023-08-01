import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent {

  constructor(private http: HttpClient) { }

  logout() {
    this.http.post('http://localhost:9337/api/v1/auth/logout', {}).subscribe(
      (response: any) => {
        // Handle successful logout response (e.g., clear token, redirect to login, etc.).
      },
      (error) => {
        // Handle logout error (e.g., show error message).
      }
    );
  }

}
