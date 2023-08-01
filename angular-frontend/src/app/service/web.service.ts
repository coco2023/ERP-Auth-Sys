import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';

@Injectable({
  providedIn: 'root'
})
export class WebService {

  private websocketSubject: WebSocketSubject<any> | undefined;

  private token = localStorage.getItem("Authorization"); // JWT token
  private websocketURL: string  = `ws://localhost:9337/messaging`;

  // private websocket: WebSocket,
  constructor(private http: HttpClient) {

    // const token = localStorage.getItem('Authorization'); // Replace 'jwtToken' with the key you use to store the token

    // const websocketURL = `ws://localhost:9337/messaging?token=${token}`;

    // this.websocket = new WebSocket(websocketURL);

    // this.websocket.onopen = (event) => {
    //   console.log('WebSocket connection established!');
    // };

    // this.websocket.onmessage = (event) => {
    //   console.log('Received message:', event.data);
    // };

    // this.websocket.onclose = (event) => {
    //   console.log('WebSocket connection closed:', event);
    // };

    // this.websocket.onerror = (event) => {
    //   console.error('WebSocket error:', event);
    // };  
  }

  // public connectWebSocket(): Observable<any> {

  //   this.websocket = new WebSocket(this.websocketURL);

  //   return new Observable((observer) => {
  //     this.websocket.onopen = (event) => {
  //       console.log('WebSocket connection established!');
  //       observer.next({ type: 'open', event: event });
  //     };

  //     this.websocket.onmessage = (event) => {
  //       console.log('Received message:', event.data);
  //       observer.next({ type: 'message', data: event.data });
  //     };

  //     this.websocket.onclose = (event) => {
  //       console.log('WebSocket connection closed:', event);
  //       observer.next({ type: 'close', event: event });
  //       observer.complete();
  //     };

  //     this.websocket.onerror = (event) => {
  //       console.error('WebSocket error:', event);
  //       observer.error(event);
  //     };
  //   });
  // }

  public connectWebSocket(): Observable<any> {
    return new Observable((observer) => {
      // const token = data.token; // Extract the JWT token from the response
      this.websocketSubject = webSocket<any>(this.websocketURL + '?token=' + this.token);

      this.websocketSubject.subscribe(
        (message) => {
          console.log('Received message:', message);
          observer.next(message);
        },
        (error) => {
          console.error('WebSocket error:', error);
          observer.error(error);
        },
        () => {
          console.log('WebSocket connection closed.');
          observer.complete();
        }
      );
    });
  }


  // public sendMessage(message: string): void {
  //   if (this.websocket.readyState === WebSocket.OPEN) {
  //     this.websocket.send(message);
  //   } else {
  //     console.warn('WebSocket not open. Message not sent.');
  //   }
  // }

  // public closeWebSocket(): void {
  //   this.websocket.close();
  // }

  public sendMessage(message: any): void {
    if (this.websocketSubject && !this.websocketSubject.closed) {
      this.websocketSubject.next(message);
    }
  }

  public closeWebSocket(): void {
    if (this.websocketSubject) {
      this.websocketSubject.complete();
    }
  }

  // sendMessage(message: string) {
  //   if (this.websocket.readyState === WebSocket.OPEN) {
  //     this.websocket.send(message);
  //   } else {
  //     console.warn('WebSocket not open. Message not sent.');
  //   }
  // }

  // closeWebSocket() {
  //   this.websocket.close();
  // }

}
