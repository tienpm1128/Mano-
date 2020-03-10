export class Message {
  next: Message;
  previous: Message;
  data: string;

  constructor(data: string) {
    this.next = null;
    this.previous = null;
    this.data = data;
  }
}
