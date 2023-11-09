package christmas.constant.message;

public enum OutputMessage {
    PREVIEW_EVENT_BENEFITS_FORMAT("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n");

    private final String messageFormat;

    OutputMessage(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public String getMessage(){
        return this.messageFormat;
    }

    public String getIncludingValueMessage(int value){
        return String.format(this.messageFormat, value);
    }
}
