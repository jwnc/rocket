package demo.proxy.cglib;

public class BookFacadeImpl1 implements BookFacade1 {
    @Override
    public void addBook() {
        System.out.println("BookFacadeImpl增加图书方法。。。");
    }

    @Override
    public final void finalMethod() {
        System.out.println("finalMethod...");
    }
}