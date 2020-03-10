import { Message } from './Message';

export class MessageQueue {
  private first: Message;
  private last: Message;
  private num: number;

  constructor() {
    this.first = null;
    this.last = null;
    this.num = 0;
  }

  hasMessage() {
    return this.num !== 0;
  }

  receive(message: Message) {
    if (this.first === null || this.last === null) {
      this.first = message;
      this.first.next = this.first.previous = null;

      this.last = message;
      this.last.next = this.last.previous = null;
    } else {
      message.next = this.first;
      this.first.previous = message;
      this.first = message;
    }

    ++this.num;
  }

  send(): Message {
    if (this.first === null || this.last === null) {
      console.log('There is no message.');
      return null;
    }

    return this.last;
  }

  remove() {
    if (!this.first) {
      console.log('There is no message.');
    } else if (!this.first.next) {
      this.first = null;
      this.last = null;
      --this.num;
    } else {
      const remarkedMessage = this.last;
      this.last.next = null;
      this.last = remarkedMessage.previous;
      --this.num;
    }
  }

  getData(): Message[] {
    if (!(this.first && this.last)) {
      return;
    }

    const messages: Message[] = [];

    for (let message = this.first; message.next; message = message.next) {
      messages.push(message);

      if (!message.next) {
        messages.push(message);
        break;
      }
    }

    console.log('getData() method: ', messages);

    return messages;
  }
}
