import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse  } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly baseUrl = 'http://localhost:9337/api/messages/send-message'; 

  constructor(private http: HttpClient) {}

  sendMessage(userObj: any, authToken: string): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: authToken,
    });

    console.log("httpHeaders", headers);
    console.log("Msg Obj:", userObj)

    return this.http.post<any>(this.baseUrl, userObj, { headers })
    // .pipe(
    //   catchError((error: HttpErrorResponse) => {
    //     // Handle the error here
    //     if (error.error instanceof ErrorEvent) {
    //       // Client-side error
    //       console.error('An error occurred:', error.error.message);
    //     } else {
    //       // Server-side error
    //       console.error(
    //         `Backend returned code ${error.status}, ` +
    //         `body was: ${JSON.stringify(error.error)}`
    //       );
    //     }
    //     // Rethrow the error to keep the observable chain alive
    //     return throwError('Something went wrong; please try again later.');
    //   })
    // );
  };

}
