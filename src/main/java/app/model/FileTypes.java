package app.model;

public enum FileTypes {

    TEXT("text/plain"),
    IMG("image/jpeg");

    private String name;

    FileTypes(String name) {
        this.name = name;
    }

    public String getType() {
        return name;
    }
}
