import { Injectable } from '@angular/core';
import { Stomp, StompConfig } from '@stomp/stompjs';
import { Client } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  private stompClient: Client;
  private receivedMessages: Subject<string> = new Subject<string>();

  constructor() { 
      const stompConfig: StompConfig = {
      brokerURL: 'ws://localhost:8081/ws', // Replace with your WebSocket endpoint URL
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
    this.stompClient.subscribe('/topic/messages', (message) => {
      this.receivedMessages.next(message.body);
    });
  }

  private onStompError(error: any): void {
    console.error('Error in WebSocket connection:', error);
  }

  public sendMessage(message: string): void {
    this.stompClient.publish({
      destination: '/app/send-message',
      body: message,
    });
  }

  public getReceivedMessages(): Observable<string> {
    return this.receivedMessages.asObservable();
  }

}
