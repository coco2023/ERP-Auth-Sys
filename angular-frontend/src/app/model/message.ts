export class Message {
    constructor(
        public messageId: number,
        public fromUserId: number,
        public toUserId: number,
        public message: any,
        public sentAt: any,
    ) {}
}