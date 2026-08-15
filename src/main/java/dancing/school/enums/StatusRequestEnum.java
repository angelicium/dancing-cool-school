package dancing.school.enums;

public enum StatusRequestEnum {
    NOT_VIEWED("Не просмотрено"),
    REJECTED("Отказ"),
    REMOVED("Удаленный"),
    ACCEPTED("Одобрено");

    private String value;

    StatusRequestEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
