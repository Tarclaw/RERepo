package web.example.realestate.commands;

public class MappingCommand {

    private String pageName;

    public MappingCommand(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
}
