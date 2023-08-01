To convert the provided WebSocket code into an Angular TypeScript code, you'll need to use the `WebSocket` API and Angular's event binding instead of jQuery. Additionally, we'll encapsulate the WebSocket logic within a service in Angular. Here's how you can do it:

**Step 1: Create WebSocket Service**

Create a WebSocket service to handle WebSocket communication. In this service, we'll encapsulate the WebSocket logic and provide methods for connecting, disconnecting, and sending data.

```typescript
// web-socket.service.ts
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private ws: WebSocket;
  private readonly serverUrl = 'ws://localhost:9001/messaging';

  constructor() {}

  connect(accessToken: string): void {
    this.ws = new WebSocket(`${this.serverUrl}?accessToken=${accessToken}`);
    this.ws.addEventListener('open', () => this.setConnected(true));
    this.ws.addEventListener('error', (error) => {
      console.error('Error:', error.message);
      this.setConnected(false);
    });
    this.ws.addEventListener('close', (event) => {
      console.log('Close:', event.code + ' - ' + event.reason);
      this.setConnected(false);
    });
    this.ws.addEventListener('message', (event) => this.onMessageReceived(event.data));
  }

  disconnect(): void {
    if (this.ws) {
      this.ws.close();
    }
    this.setConnected(false);
    console.log('WebSocket is in disconnected state');
  }

  sendData(data: any): void {
    if (this.ws && this.ws.readyState === WebSocket.OPEN) {
      this.ws.send(JSON.stringify(data));
    }
  }

  private setConnected(connected: boolean): void {
    // Implement your logic for setting the connected status here
    // For example, you can emit an event using RxJS Subject
  }

  private onMessageReceived(message: string): void {
    // Implement your logic for processing the received message here
  }
}
```

**Step 2: Use the WebSocket Service in Component**

Now, use the `WebSocketService` in your Angular component to handle the user interactions and WebSocket communication.

```typescript
import { Component } from '@angular/core';
import { WebSocketService } from './web-socket.service';

@Component({
  selector: 'app-your-component',
  templateUrl: './your-component.component.html',
  styleUrls: ['./your-component.component.css'],
})
export class YourComponent {
  accessToken: string;
  message: string;

  constructor(private webSocketService: WebSocketService) {}

  connect(): void {
    this.webSocketService.connect(this.accessToken);
  }

  disconnect(): void {
    this.webSocketService.disconnect();
  }

  sendData(): void {
    const data = {
      topic: 'WEATHER',
      message: {
        accessToken: this.accessToken,
        sendTo: 'receiver_id', // Replace with the actual receiver's ID
        msg: this.message,
      },
    };
    this.webSocketService.sendData(data);
  }
}
```

**Step 3: Update the Template**

Update your component's template to include the input fields and buttons for connecting, disconnecting, and sending data.

```html
<!-- your-component.component.html -->
<div>
  <h1>WebSocket Chat</h1>
  <label for="accessToken">Access Token:</label>
  <input type="text" id="accessToken" [(ngModel)]="accessToken">
  <button (click)="connect()">Connect</button>
  <button (click)="disconnect()">Disconnect</button>
  <br>
  <label for="message">Message:</label>
  <input type="text" id="message" [(ngModel)]="message">
  <button (click)="sendData()">Send</button>
</div>
```

**Step 4: AppModule Configuration**

Make sure to include the `WebSocketService` in the `providers` array of your app module.

With these steps, you have converted the original AJAX WebSocket code into an Angular TypeScript code that handles WebSocket communication using the `WebSocketService`. Make sure to implement the `setConnected()` and `onMessageReceived()` methods according to your application's requirements.


# Q2
> how to set httpheader's Authorization token in Angular when using websocket to connect spring-security backend


When using WebSocket to connect to a Spring Security backend, you cannot directly set the `Authorization` header in the WebSocket connection request, as you would with regular HTTP requests using `HttpClient`. WebSocket connections are established through the WebSocket protocol, and the protocol itself does not include a mechanism for setting headers like HTTP requests.

Instead, when using WebSocket with Spring Security, you need to send the authentication token as a query parameter or within the WebSocket URL. On the server-side, Spring Security can then extract the token from the URL and use it for authentication.

Here's how you can send the authentication token in the URL when establishing the WebSocket connection in Angular:

**Step 1: Create WebSocket Service**

First, create a WebSocket service to handle the WebSocket communication. This service will manage the WebSocket connection and send/receive messages.

```typescript
// web-socket.service.ts
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private ws: WebSocket;
  private readonly serverUrl = 'ws://localhost:9001/messaging'; // Replace with your WebSocket server URL

  constructor() {}

  connect(authToken: string): void {
    this.ws = new WebSocket(`${this.serverUrl}?access_token=${authToken}`);
    // Implement your WebSocket event handlers and logic here
  }

  disconnect(): void {
    if (this.ws) {
      this.ws.close();
    }
  }

  // Other WebSocket methods and logic
}
```

**Step 2: Use WebSocket Service in Component**

In your component, use the `WebSocketService` to connect to the WebSocket server and pass the authentication token as a parameter.

```typescript
import { Component } from '@angular/core';
import { WebSocketService } from './web-socket.service';

@Component({
  selector: 'app-your-component',
  templateUrl: './your-component.component.html',
  styleUrls: ['./your-component.component.css'],
})
export class YourComponent {
  accessToken: string;

  constructor(private webSocketService: WebSocketService) {}

  connect(): void {
    this.webSocketService.connect(this.accessToken);
  }

  disconnect(): void {
    this.webSocketService.disconnect();
  }
}
```

**Step 3: Update the Template**

Update your component's template to include input fields and buttons for connecting and disconnecting.

```html
<!-- your-component.component.html -->
<div>
  <label for="accessToken">Access Token:</label>
  <input type="text" id="accessToken" [(ngModel)]="accessToken">
  <button (click)="connect()">Connect</button>
  <button (click)="disconnect()">Disconnect</button>
</div>
```

**Step 4: AppModule Configuration**

Make sure to include the `WebSocketService` in the `providers` array of your app module.

By passing the `authToken` as a query parameter in the WebSocket URL, you can handle the authentication on the server-side within Spring Security, just as you would with regular HTTP requests. Please note that this approach may not be as secure as using token-based authentication like JWT, so be cautious when handling sensitive data.