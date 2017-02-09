package app.model;

public enum FileTypes {

    text("text/plain");

    String name;

    FileTypes(String name){
        this.name = name;
    }

    public String getType(){
        return name;
    }
}
