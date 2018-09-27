package cucumberskeleton.utils;


public class GetHostUrl {

    private String defaultUrl = "https://amazon.com";
    private String defaultEnv = "qa";

    public GetHostUrl() { }

    public GetHostUrl(String url) {
        if (null == url) {
            this.defaultUrl = System.getProperty("hostUrl", this.defaultUrl);
        } else {
            this.defaultUrl = url;
        }
    }

    private String figureUrlByEnv() {
        String environment = System.getProperty("environment", this.defaultEnv);
        String url = defaultUrl;

        switch (environment) {
            case "qa":
                url = "https://amazon.com";
                break;
            default:
                break;
        }
        return url;
    }

    public String getUrl(){
        return this.figureUrlByEnv();
    }

}
