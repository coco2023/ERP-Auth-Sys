import { Component, OnInit, OnDestroy } from '@angular/core';
// import { UserService } from '../user.service';
// import { SendMessageRequest } from 'src/app/model/SendMessageRequest';
// import { SendMessageResponse } from 'src/app/model/SendMessageResponse';
// import { WebsocketService } from '../websocket.service';
import { WebService } from '../web.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-web-socket',
  templateUrl: './web-socket.component.html',
  styleUrls: ['./web-socket.component.css']
})
export class WebSocketComponent implements OnInit, OnDestroy{

  isConnected = false;
  messageToSend: string = '';
  webService: WebService | any ;
  // private webSocketSubscription: Subscription;

  constructor(private webSocketSubscription: Subscription) {}

  ngOnInit() {
    this.connectWebSocket();
  }

  ngOnDestroy() {
    this.webSocketSubscription?.unsubscribe();
    this.webService.closeWebSocket();
  }

  connectWebSocket() {
    this.webSocketSubscription = this.webService.connectWebSocket().subscribe(
      (message: any) => {
        // Handle received message here, update UI, etc.
      },
      (error: any) => {
        // Handle WebSocket connection error here (e.g., display an error message)
      },
      () => {
        // Handle WebSocket connection closed here (e.g., update UI state)
      }
    );
    this.isConnected = true;
  }

  sendMessage() {
    if (this.messageToSend) {
      this.webService.sendMessage(this.messageToSend);
      this.messageToSend = '';
    }
  }
  
  // ngOnInit() {}

  // ngOnDestroy() {
  //   this.webService.closeWebSocket();
  // }

  // connectWebSocket() {
  //   this.webService.connectWebSocket().subscribe(
  //     (event: { type: string; }) => {
  //       if (event.type === 'open') {
  //         this.isConnected = true;
  //         console.log("success connect!!!!!!!!!!!!!!!!")
  //       } else if (event.type === 'close') {
  //         this.isConnected = false;
  //       }
  //     },
  //     (error: any) => {
  //       console.error('WebSocket error:', error);
  //       // Handle WebSocket connection error here (e.g., display an error message)
  //     }
  //   );
  // }

  // sendMessage() {
  //   if (this.messageToSend) {
  //     this.webService.sendMessage(this.messageToSend);
  //     this.messageToSend = '';
  //   }
  // }

  /**
   * websocket2
   */

  // receivedMessages: string[] = [];

  // authToken: any;
  // sendMessageRequest: SendMessageRequest = {
  //   senderId: undefined,
  //   sendTo: undefined,
  //   msg: undefined
  // };
  // sendMessageResponse: SendMessageResponse | any;

  // constructor(private websocketService: WebsocketService) {}

  // ngOnInit(): void {
  //   // connect
  //   this.websocketService.connect();

  //   this.websocketService.getReceivedMessages(this.authToken).subscribe((message) => {
  //     this.receivedMessages.push(message);
  //   });
  // }

  // /**
  //  * Send Message to Receiver
  //  */ 
  // sendMessage() : void {

  //   this.websocketService.sendMessage(this.sendMessageRequest, this.authToken).subscribe(
  //     (data) => {
  //       console.log("***data", data)
  //       this.sendMessageResponse = data.message;
  //       return this.sendMessageResponse;
  //     },
  //     (error) => {
  //       console.log("websocket ERROR!")
  //     }
  //   )
  //   console.log("send success!")
  // }


  // sendMessage() : void {
  //   console.log("sendMessageRequest")
  //   this.userService.sendMessage(this.sendMessageRequest, this.authToken).subscribe(
  //     (data) => {
  //       console.log("data", data)
  //       this.sendMessageResponse = data.message;
  //       return this.sendMessageResponse;
  //     },
  //     (error) => {
  //       console.log("websocket ERROR!")
  //     }
  //   )
  //   console.log("this is a test")
  // }
}
