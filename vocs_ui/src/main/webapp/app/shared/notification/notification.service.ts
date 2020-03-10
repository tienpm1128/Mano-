import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { MessageQueue } from './MessageQueue';
import { Message } from './Message';
import { JhiEventManager } from 'ng-jhipster';
import { LocalStorageService } from 'ngx-webstorage';

const NUM_NOTFICATIONS = 5;

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  public resourceUrl = SERVER_API_URL + '/sse/notifications';
  numNotifications = 0;
  eventSource = new EventSource(this.resourceUrl);
  messageQueue: MessageQueue;
  notifications = [];

  constructor(private eventManager: JhiEventManager, private localStorageService: LocalStorageService) {
    this.messageQueue = new MessageQueue();

    if (this.localStorageService.retrieve('notifications')) {
      this.notifications = this.localStorageService.retrieve('notifications');
    }
  }

  connect() {
    if (this.eventSource.readyState === 2) {
      this.eventSource = new EventSource(this.resourceUrl);
    }

    console.log('Connect to SSE', this.eventSource);
    let i = 0;
    setInterval(() => {
      while (this.messageQueue.hasMessage() && this.numNotifications < NUM_NOTFICATIONS) {
        const data = this.send();
        if (data !== '') {
          this.numNotifications++;
          this.eventManager.broadcast({ name: 'notifications', content: JSON.parse(data) });
          i++;
          this.notifications.push(JSON.parse(data));
          this.localStorageService.store('notifications', this.notifications);
        }
      }
    }, 1000);
    this.eventSource.onmessage = response => {
      if ('data' in response) {
        this.receive(response.data);
      } else {
        return null;
      }
    };
    this.eventSource.onerror = this.onErrorSse;
    this.eventSource.onopen = this.onOpenSse;
  }

  onErrorSse() {
    console.log('Error in notifications.');
  }

  onOpenSse() {
    console.log('Connected with server.');
  }

  disconnect() {
    this.eventSource.close();
  }

  receive(data: string) {
    this.messageQueue.receive(new Message(data));
  }

  send(): string {
    const message = this.messageQueue.send();
    let data = '';
    if (message !== null) {
      data = message.data;
      this.messageQueue.remove();
    }

    return data;
  }

  decreateNumOfNotifications() {
    if (this.numNotifications === 0) {
      return;
    }

    this.numNotifications--;
  }
}
