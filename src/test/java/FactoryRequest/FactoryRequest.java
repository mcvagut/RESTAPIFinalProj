package FactoryRequest;

public class FactoryRequest {
    public static IRequest make(String type){
        type = type.toLowerCase();

        switch (type) {
            case "post":
                return new RequestPOST();
            case "put":
                return new RequestPUT();
            case "delete":
                return new RequestDELETE();
            default:
                return new RequestGET();
        }
    }
}
