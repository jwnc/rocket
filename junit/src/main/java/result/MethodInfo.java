package result;

public class MethodInfo {
    String method_name;
    Boolean result;
    String error_msg;

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    @Override
    public String toString() {
        return "MethodInfo [method_name=" + method_name + ", result=" + result + ", error_msg=" + error_msg + "]";
    }

}