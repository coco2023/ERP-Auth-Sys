import { Component } from '@angular/core';
import { WebsocketService } from './websocket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  messageToSend: string = "";
  receivedMessages: string[] = [];

  constructor(private webSocketService: WebsocketService) {}

  ngOnInit(): void {
    this.webSocketService.connect();

    this.webSocketService.getReceivedMessages().subscribe((message) => {
      this.receivedMessages.push(message);
    });
  }

  sendMessage(): void {
    this.webSocketService.sendMessage(this.messageToSend);
    this.messageToSend = '';
  }

}
