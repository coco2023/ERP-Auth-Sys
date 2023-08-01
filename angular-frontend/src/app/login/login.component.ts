import { Component } from '@angular/core';
import { HttpClient, HttpHeaders  } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  userName: string = 'user';
  password: string = '123';
  rememberMe: string = 'true';

  constructor(private http: HttpClient) { }

  login() {

    const loginForm = { userName: this.userName, password: this.password, rememberMe: this.rememberMe };
    console.log("get user's info!" + this.userName + ' ', this.password)

    this.http.post('http://localhost:9337/api/v1/auth/login', loginForm, { observe: 'response' })
      .subscribe(
        (response: any) => {
          // Handle successful login response (e.g., store token in local storage, redirect, etc.).
          console.log("Login success!" + this.userName + ' ', this.password)
          const authToken: HttpHeaders = response.body.httpHeader;
          console.log(authToken);
          localStorage.setItem("Authorization:", JSON.stringify(authToken));
        },
        (error) => {
          // Handle login error (e.g., show error message).
          console.log("error Login! ")
        }
      );
  }
}
