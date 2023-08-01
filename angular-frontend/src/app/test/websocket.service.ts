import { Injectable } from '@angular/core';
import { Stomp, StompConfig } from '@stomp/stompjs';
import { Client } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse  } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  private stompClient: Client;
  private receivedMessages: Subject<string> = new Subject<string>();

  private readonly baseUrl = 'http://localhost:9337/api/messages/send-message'; 
  private topic: string = "topic0727";
  private token : string = "";
  private websocketURL: string = `ws://localhost:9337/messaging?token=${this.token}`;

  constructor(private http: HttpClient) { 
    const stompConfig: StompConfig = {
    brokerURL: 'ws://localhost:9337/api/messaging', 
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  };

  this.stompClient = new Client(stompConfig);
  this.stompClient.onConnect = this.onStompConnect.bind(this);
  this.stompClient.onStompError = this.onStompError.bind(this);
  }

  public connect(): void {
    this.stompClient.activate();
  }

  private onStompConnect(frame: any): void {
    this.stompClient.subscribe(this.topic, (message) => {
      this.receivedMessages.next(message.body);
    });
  }

  private onStompError(error: any): void {
    console.error('Error in WebSocket connection:', error);
  }

  // public sendMessage(message: string): void {
  //   this.stompClient.publish({
  //     destination: '/app/send-message',
  //     body: message,
  //   });
  // }

  public sendMessage(userObj: any, authToken: string): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: authToken,
    });

    console.log("httpHeaders", headers);
    console.log("Msg Obj:", userObj)

    return this.http.post<any>(this.baseUrl, userObj, { headers })
  }

  public getReceivedMessages(authToken: string): Observable<string> {
    const headers = new HttpHeaders({
      Authorization: authToken,
    });
    console.log("try to receive msg")
    return this.receivedMessages.asObservable();
  }
}
