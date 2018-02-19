package comm;

    public class Service {
    private String domain, host, name, port, type;

    public Service(String domain, String host, String name, String port, String type){
        this.domain = domain;
        this.host = host;
        this.name = name;
        this.port = port;
        this.type = type;
    }

    @Override
    public String toString(){
        return domain + host + name + port + type;
    }

    public String getDomain(){
        return this.domain;
    }

    public String getHost(){
        return this.host;
    }

    public String getName(){
        return this.name;
    }

    public String getPort(){
        return this.port;
    }

    public String getType(){
        return this.type;
    }

    public void setDomain(String domain){
        this.domain = domain;
    }

    public void setHost(String host){
        this.host = host;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPort(String port){
        this.port = port;
    }

    public void setType(String type){
        this.type = type;
    }
}
