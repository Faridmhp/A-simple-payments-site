package server;

public class uuidHolder{

    private Object uuid;

    public uuidHolder(){
          this.uuid = null;
    }

    public Object get(){
        return uuid;
    }

    public void set(Object uuid){
        this.uuid = uuid;
    }
}
