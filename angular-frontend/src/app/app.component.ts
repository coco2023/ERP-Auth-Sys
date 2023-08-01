import { Component, OnInit  } from '@angular/core';
import { SendMessageRequest } from './model/SendMessageRequest';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  // message: any;
  
  // title = 'angular-frontend';

  constructor() {}
  
  // constructor(private readonly webSocketService: WebSocketService) {}

  // ngOnInit() {
  //   this.webSocketService.connect();
  // }

  // sendMessage() {
  //   if (this.message) {
  //     this.webSocketService.send(new SendMessageRequest(21, 23, this.message));
  //     this.message = '';
  //     console.log("send success!");
  //   }
  // }

}
